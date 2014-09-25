/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.user.User;

/**
 * This class is used for managing {@link User}.
 * 
 * @author Csaba
 */
@Singleton
public class UserRepositoryImpl
    extends AbstractJpaRepository<User, Integer>
    implements UserRepository
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserRepositoryImpl.class );

    @Override
    public User findBy( String username, String password )
    {
        LOG.debug( "Getting user with username = {}, password ... is secret", username );
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery( User.class );
        Root<User> root = criteria.from( User.class );
        criteria.where( cb.equal( root.get( "login" ), username ), cb.equal( root.get( "password" ), password ) );

        TypedQuery<User> query = em.createQuery( criteria );
        List<User> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        return uniqueResult( results );
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
    private static User uniqueResult( List<User> users )
    {
        User result = null;
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
    protected Class<User> getEntityClass()
    {
        return User.class;
    }
}
