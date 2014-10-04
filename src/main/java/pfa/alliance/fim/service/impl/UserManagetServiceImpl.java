/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.UserManagerService;

import com.google.inject.persist.Transactional;

/**
 * The {@link UserManagerService} implementation.
 * 
 * @author Csaba
 */
@Singleton
class UserManagetServiceImpl
    implements UserManagerService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserManagetServiceImpl.class );

    private static final String PREFIX = "Hello! This is The F.I.M. software. Your password is... ";

    private static final String SUFIX = "Nah... We encrypt that so it's secret.";

    private final UserRepository userRepository;

    @Inject
    public UserManagetServiceImpl( UserRepository userRepository )
    {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User registerUser( String email, String cleanPassword, String firstName, String lastName )
    {
        User user = createNewUser( email, cleanPassword, firstName, lastName );
        User savedUser = userRepository.save( user );
        // TODO send e-mail
        return savedUser;
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
        // TODO remember a new last login record
        return user;
    }

    /**
     * Checks the user status to be {@link UserStatus#ACTIVE}.
     * 
     * @param user the user that must be verified
     */
    private void checkUserStatusForLogin( User user )
    {
        if ( !( UserStatus.ACTIVE.equals( user.getStatus() ) ) )
        {
            LOG.info( "User is not ACTIVE: {}", user );
            // TODO throw exception
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
}
