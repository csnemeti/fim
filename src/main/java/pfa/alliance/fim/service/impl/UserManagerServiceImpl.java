/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.ActivationFailReason;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.UserActivationFailException;
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

    private final EmailService emailService;

    private final EmailGeneratorService emailGeneratorService;

    private final FimUrlGeneratorService fimUrlGeneratorService;

    @Inject
    public UserManagerServiceImpl( UserRepository userRepository, EmailService emailService,
                                   EmailGeneratorService emailGeneratorService,
                                   FimUrlGeneratorService fimUrlGeneratorService )
    {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.emailGeneratorService = emailGeneratorService;
        this.fimUrlGeneratorService = fimUrlGeneratorService;
    }

    @Override
    @Transactional
    public User registerUser( String email, String cleanPassword, String firstName, String lastName, Locale locale )
    {
        LOG.debug( "Trying to create new user: email = {}, first name = {}, last name = {}, locale = {}", email,
                   firstName, lastName, locale );
        User user = createNewUser( email, cleanPassword, firstName, lastName );
        try
        {
            UserOneTimeLink link = createUserOneTimeLinkFor( user, OneTimeLinkType.USER_REGISTRATION );
            Set<UserOneTimeLink> links = new HashSet<UserOneTimeLink>();
            links.add( link );
            user.setOneTimeLinks( links );
            User savedUser = userRepository.save( user );
            sendRegistrationEmail( link, locale );
            user = savedUser;
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
        catch ( MessagingException e )
        {
            LOG.error( "User created, but fail to send e-mail. Rollback all: {}", user, e );
            throw new RuntimeException( e );
        }
        return user;
    }

    private void sendRegistrationEmail( UserOneTimeLink link, Locale locale )
        throws MessagingException
    {
        User user = link.getUser();
        String to = user.getEmail();
        String subject = emailGeneratorService.getSubject( EmailType.REGISTER_USER, null, locale );
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put( "name", user.getFirstName() + " " + user.getLastName() );
        parameters.put( "link", fimUrlGeneratorService.getActivateAccountLink( link.getUuid() ) );
        String content = emailGeneratorService.getContent( EmailType.REGISTER_USER, parameters, locale );
        emailService.sendEmail( to, subject, content );
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

    private void sendRegistrationEmail( User user )
    {
        // TODO implement this

    }

    @Override
    @Transactional
    public void activateUser( final String uuid )
    {
        LOG.debug( "Trying to activate user with uuid = {}", uuid );
        UserOneTimeLink link = userRepository.getOneTimeLinkBy( uuid, OneTimeLinkType.USER_REGISTRATION );
        validateLinkAndUser( link, uuid );
        setLinkActivated( link );
        // TODO save - JPA saves the modified data automatically, so we do not really need to do this
    }

    /**
     * Validate link and user for activation.
     * 
     * @param link the link that contains the user too
     */
    private void validateLinkAndUser( UserOneTimeLink link, final String uuid )
    {
        // if link is null, UUID was not valid (or UUID us valid but is not for user activation)
        if ( link == null )
        {
            LOG.info( "UUID {} is not found for user registration", uuid );
            throw new UserActivationFailException( ActivationFailReason.UUID_NOT_FOUND );
        }
        // check if link is not expired
        if ( link.getExpiresAt().before( new Timestamp( System.currentTimeMillis() ) ) )
        {
            LOG.info( "Link coresponding to requested UUID is expired: {}", link );
            throw new UserActivationFailException( ActivationFailReason.UUID_EXPIRED );
        }
        // check if user status is NEW (the only legal status for activation)
        User user = link.getUser();
        if ( !UserStatus.NEW.equals( user.getStatus() ) )
        {
            LOG.info( "User has a status that suggests activation already happened: {}", user );
            throw new UserActivationFailException( ActivationFailReason.USER_ALREADY_ACTIVE );
        }
    }

    /**
     * Make all changes necessary so that {@link User} represented by the link will be {@link UserStatus#ACTIVE}.
     * 
     * @param link the link with the {@link User}
     */
    private void setLinkActivated( UserOneTimeLink link )
    {
        LOG.debug( "Preparing data for user activation: {}", link );
        link.getUser().setStatus( UserStatus.ACTIVE );
        // this will make the link expired
        link.setExpiresAt( new Timestamp( System.currentTimeMillis() ) );
    }
}
