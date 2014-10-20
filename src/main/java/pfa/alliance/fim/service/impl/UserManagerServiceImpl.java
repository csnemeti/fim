/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.UserManagerService;

import com.google.inject.persist.Transactional;

/**
 * The {@link UserManagerService} implementation.
 * 
 * @author Csaba
 */
@Singleton
class UserManagerServiceImpl
    implements UserManagerService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserManagerServiceImpl.class );

    private static final String PREFIX = "Hello! This is The F.I.M. software. Your password is... ";

    private static final String SUFIX = "Nah... We encrypt that so it's secret.";

    /** One day in milliseconds. */
    private static final long ONE_DAY_IN_MILLISECONDS = 24L * 3600L * 1000L;

    private final UserRepository userRepository;

    @Inject
    public UserManagerServiceImpl( UserRepository userRepository )
    {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User registerUser( String email, String cleanPassword, String firstName, String lastName )
    {
        LOG.debug( "Trying to create new user: email = {}, first name = {}, last name = {}", email, firstName, lastName );
        User user = createNewUser( email, cleanPassword, firstName, lastName );
        try
        {
            UserOneTimeLink link = createUserOneTimeLinkFor( user, OneTimeLinkType.USER_REGISTRATION );
            Set<UserOneTimeLink> links = new HashSet<UserOneTimeLink>();
            links.add( link );
            user.setOneTimeLinks( links );
            User savedUser = userRepository.save( user );
            // TODO send e-mail
            return savedUser;
        }
        catch ( PersistenceException e )
        {
            if ( isDuplicateUserInfoRelatedException( e ) )
            {
                LOG.warn( "Duplicate data for user: {}", user, e );
                throw new DuplicateUserDataException( "Duplicate user data", e );
            }
            else
            {
                LOG.error( "Could not create the user: {}", user, e );
                throw e;
            }
        }
    }

    /**
     * Creates a {@link UserOneTimeLink} for a given {@link User} and for a given designation.
     * 
     * @param user the {@link User} for whom this link is name
     * @param designation the designation of the link
     * @return the created object
     */
    private UserOneTimeLink createUserOneTimeLinkFor( User user, OneTimeLinkType designation )
    {
        UserOneTimeLink link = new UserOneTimeLink();
        link.setDesignation( designation );
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() + ONE_DAY_IN_MILLISECONDS ) );
        link.setUser( user );
        UUID uuid = new UUID( System.nanoTime(), System.currentTimeMillis() );
        link.setUuid( uuid.toString() );
        return link;
    }
    /**
     * Checks if the user data that is provided is duplicate or not.
     * 
     * @param e the exception we received
     * @return true if information about duplicate e-mail or username was found
     */
    private boolean isDuplicateUserInfoRelatedException( PersistenceException e )
    {
        Throwable t = e;
        boolean found = false;
        do
        {
            String message = t.getMessage();
            if ( message.contains( "violates unique constraint" ) )
            {
                found = true;
            }
            t = t.getCause();
        }
        while ( t != null && !found );
        return found;
    }

    /**
     * Create the {@link User} object from properties.
     * 
     * @param email the user e-mail address
     * @param cleanPassword the user password in clean text form
     * @param firstName the user first name
     * @param lastName the user last name
     * @return the built object
     */
    private User createNewUser( String email, String cleanPassword, String firstName, String lastName )
    {
        User user = new User();
        user.setEmail( email );
        user.setLogin( email );
        user.setFirstName( firstName );
        user.setLastName( lastName );
        user.setPassword( encryptPassword( cleanPassword ) );
        user.setStatus( UserStatus.NEW );
        return user;
    }

    @Override
    @Transactional
    public User login( String username, String cleanPassword )
    {
        LOG.debug( "Trying to login: {}", username );
        User user = userRepository.findBy( username, encryptPassword( cleanPassword ) );
        LOG.debug( "Authentication result... username = {} --> user = {}", username, user );
        if ( user != null )
        {
            checkUserStatusForLogin( user );
        }
        return user;
    }

    /**
     * Checks the user status to be {@link UserStatus#ACTIVE}.
     * 
     * @param user the user that must be verified
     */
    private void checkUserStatusForLogin( User user )
    {
        switch ( user.getStatus() )
        {
            case ACTIVE:
                userRepository.addNewLoginInfoAndLimitLogs( user );
                break;
            case NEW:
                sendRegistrationEmail( user );
                break;
            default:
                LOG.info( "User status has invalid status for login: {}", user );
                break;
        }
    }

    /**
     * Encrypt a clear text representing a password.
     * 
     * @param cleanPassword the password in clear text
     * @return the encrypted password in hexadecimal form
     */
    private String encryptPassword( String cleanPassword )
    {
        return DigestUtils.sha512Hex( PREFIX + cleanPassword + SUFIX );
    }

    @Override
    public void sendRegistrationEmail( User user )
    {
        // TODO implement this

    }
}
