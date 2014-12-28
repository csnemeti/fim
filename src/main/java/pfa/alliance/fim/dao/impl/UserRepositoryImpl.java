/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.UserRepository;
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
        if ( user != null && user.getStatus() == UserStatus.ACTIVE )
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
     * Handle unique result.
     * 
     * @param users the list of users
     * @return the result or null if {@link List} is empty
     */
    private static <T> T uniqueResult( List<T> users )
    {
        T result = null;
        switch ( users.size() )
        {
            case 0:
                result = null;
                break;
            case 1:
                result = users.get( 0 );
                break;
            default:
                NonUniqueResultException ex = new NonUniqueResultException( users.size() + " > 1" );
                LOG.error( "Got too many results for a unique result", ex );
                throw ex;
        }
        return result;
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
        if(designation != null){
            predicates = new Predicate[2];
            predicates[1] = cb.equal( root.get( "designation" ), designation );
        }else{
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
    protected Class<User> getEntityClass()
    {
        return User.class;
    }

    @Override
    protected Class<Integer> getIdClass()
    {
        return Integer.class;
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
