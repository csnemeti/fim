/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import static pfa.alliance.fim.dao.impl.DaoUtil.uniqueResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.UserSearchDTO;
import pfa.alliance.fim.dto.UserSearchResultDTO;
import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserLogin;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * This class is used for managing {@link User}.
 * 
 * @author Csaba
 */
@Singleton
class UserRepositoryImpl
    extends AbstractJpaRepository<User, Integer>
    implements UserRepository
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserRepositoryImpl.class );

    /** The number of total user logins we remember. */
    private static final int TOTAL_LOGIN_RECORDS = 10;

    @Override
    public User findBy( String username, String password )
    {
        LOG.debug( "Getting user with username = {}, password ... is secret", username );
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery( User.class );
        Root<User> root = criteria.from( User.class );
        // root.fetch( "logins" ); - didn't returned 1 user with logins set but as many users as user_logins
        criteria.where( cb.equal( root.get( "login" ), username ), cb.equal( root.get( "password" ), password ) );

        TypedQuery<User> query = em.createQuery( criteria );
        List<User> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        User user = uniqueResult( results );
        // we get the logins only if there's a chance to log in the user
        if ( isUserActive( user ) )
        {
            user.getLogins().size();
        }
        return user;
    }

    @Override
    public User findByUsername( String username )
    {
        LOG.debug( "Getting user with username = {}", username );
        EntityManager em = getEntityManager();
        TypedQuery<User> query =
            em.createQuery( "SELECT u FROM pfa.alliance.fim.model.user.User u WHERE u.login = :login", User.class );
        query.setParameter( "login", username );
        List<User> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        return uniqueResult( results );
    }

    @Override
    public User findByEmail( String email )
    {
        LOG.debug( "Getting user with email = {}", email );
        EntityManager em = getEntityManager();
        TypedQuery<User> query =
            em.createQuery( "SELECT u FROM pfa.alliance.fim.model.user.User u WHERE u.email = :email", User.class );
        query.setParameter( "email", email );
        List<User> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        return uniqueResult( results );
    }

    /**
     * Verifies if the given object (representing a User) is not null and has status ACTIVE.
     * 
     * @param user the user to check
     * @return true if is not null and active, false otherwise
     */
    static boolean isUserActive( User user )
    {
        return user != null && UserStatus.ACTIVE == user.getStatus();
    }

    @Override
    public void addNewLoginInfoAndLimitLogs( User user )
    {
        // create the new record & save it
        UserLogin userLogin = new UserLogin();
        userLogin.setUser( user );
        userLogin.setCreatedAt( new Timestamp( System.currentTimeMillis() ) );
        getEntityManager().persist( userLogin );
        // add it to our user object
        Set<UserLogin> logins = user.getLogins();
        logins.add( userLogin );
        // delete records that are more than 10
        if ( logins.size() > TOTAL_LOGIN_RECORDS )
        {
            deleteOlderLoginsExceptFirstOne( logins );
        }
    }

    /**
     * Deletes the oldest user logins but keeps the first one.
     * 
     * @param logins the logins to delete
     */
    private void deleteOlderLoginsExceptFirstOne( Set<UserLogin> logins )
    {
        List<UserLogin> sortedUserLogins = new ArrayList<UserLogin>( logins );
        Collections.sort( sortedUserLogins, new UserLoginComparator() );
        final int lastValid = sortedUserLogins.size() - TOTAL_LOGIN_RECORDS;
        for ( int i = 1; i <= lastValid; i++ )
        {
            UserLogin login = sortedUserLogins.get( i );
            logins.remove( login );
            getEntityManager().remove( login );
        }
    }

    @Override
    public UserOneTimeLink getOneTimeLinkBy( String uuid, OneTimeLinkType designation )
    {
        LOG.debug( "Getting one time link for uuid = {}, designation = {}", uuid, designation );
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserOneTimeLink> criteria = cb.createQuery( UserOneTimeLink.class );
        Root<UserOneTimeLink> root = criteria.from( UserOneTimeLink.class );
        root.fetch( "user" );
        Predicate[] predicates = null;
        if ( designation != null )
        {
            predicates = new Predicate[2];
            predicates[1] = cb.equal( root.get( "designation" ), designation );
        }
        else
        {
            predicates = new Predicate[1];
        }
        predicates[0] = cb.equal( root.get( "uuid" ), uuid );
        criteria.where( predicates );

        TypedQuery<UserOneTimeLink> query = em.createQuery( criteria );
        List<UserOneTimeLink> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        UserOneTimeLink link = uniqueResult( results );
        return link;
    }

    @Override
    public long count( UserSearchDTO searchCriteria )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery( Long.class );
        Root<User> root = criteria.from( User.class );
        criteria.select( cb.count( root ) );
        // add where constraints
        addWhereFor( cb, criteria, root, searchCriteria );

        TypedQuery<Long> query = em.createQuery( criteria );
        return query.getSingleResult();
    }

    @Override
    public List<UserSearchResultDTO> search( UserSearchDTO searchCriteria )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // TODO we should use projection here but Batoo has a problem with String -> enum convertion
        // CriteriaQuery<UserSearchResultDTO> countCriteria = cb.createQuery( UserSearchResultDTO.class );
        CriteriaQuery<User> criteria = cb.createQuery( User.class );
        Root<User> root = criteria.from( User.class );

        // add where constraints
        addWhereFor( cb, criteria, root, searchCriteria );

        // add ordering
        Path<?> orderPath = root.get( searchCriteria.getOrderBy() );
        Order order = ( searchCriteria.isAscending() ) ? cb.asc( orderPath ) : cb.desc( orderPath );
        criteria.orderBy( order, cb.asc( root.get( "id" ) ) );

        TypedQuery<User> query = em.createQuery( criteria );

        // set pagination constraints
        final int offset = searchCriteria.getStartIndex();
        query.setFirstResult( offset );
        query.setMaxResults( searchCriteria.getItemsPerPage() );

        List<User> result = query.getResultList();
        return convertToUserSearchResultDTO( result, offset );
    }

    private void addWhereFor( CriteriaBuilder cb, CriteriaQuery<?> criteria, Root<User> root,
                              UserSearchDTO searchCriteria )
    {
        List<Predicate> whereList = new ArrayList<Predicate>();
        String firstName = searchCriteria.getFirstName();
        if ( StringUtils.isNotBlank( firstName ) )
        {
            whereList.add( cb.like( root.get( "firstName" ), firstName + "%" ) );
        }
        String lastName = searchCriteria.getLastName();
        if ( StringUtils.isNotBlank( lastName ) )
        {
            whereList.add( cb.like( root.get( "lastName" ), lastName + "%" ) );
        }
        String email = searchCriteria.getEmail();
        if ( StringUtils.isNotBlank( email ) )
        {
            whereList.add( cb.like( root.get( "email" ), "%" + email + "%" ) );
        }
        String[] roles = searchCriteria.getRoles();
        if ( roles != null && roles.length > 0 )
        {
            whereList.add( root.get( "defaultRole" ).in( Arrays.asList( roles ) ) );
        }
        String[] statuses = searchCriteria.getStatuses();
        if ( statuses != null && statuses.length > 0 )
        {
            whereList.add( root.get( "status" ).in( Arrays.asList( statuses ) ) );
        }
        if ( whereList.size() > 0 )
        {
            criteria.where( whereList.toArray( new Predicate[whereList.size()] ) );
        }
    }

    @Override
    protected Class<User> getEntityClass()
    {
        return User.class;
    }

    @Override
    protected Class<Integer> getIdClass()
    {
        return Integer.class;
    }

    /**
     * Builds a {@link List} of {@link UserSearchResultDTO} from {@link List} of {@link User}s.
     * 
     * @param users the {@link List} of {@link User}s read from DB.
     * @param firstItemIndexInTotalResults the index of the first item in total results
     * @return the list of users converted to DTOs or an empty {@link List} if original parameter is null or empty
     *         string
     */
    private static List<UserSearchResultDTO> convertToUserSearchResultDTO( List<User> users,
                                                                           final int firstItemIndexInTotalResults )
    {
        List<UserSearchResultDTO> result = new ArrayList<>();
        if ( CollectionUtils.isNotEmpty( users ) )
        {
            final int totalUsers = users.size();
            for ( int i = 0; i < totalUsers; i++ )
            {
                User user = users.get( i );
                UserSearchResultDTO dto =
                    new UserSearchResultDTO( user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                                             user.getStatus(), user.getDefaultRole() );
                dto.setIndexInCurrentResults( i );
                dto.setIndexInTotalResults( firstItemIndexInTotalResults + i );
                result.add( dto );
            }
        }
        return result;
    }

    static class UserLoginComparator
        implements Comparator<UserLogin>
    {

        @Override
        public int compare( UserLogin o1, UserLogin o2 )
        {
            return o1.getCreatedAt().compareTo( o2.getCreatedAt() );
        }

    }
}
