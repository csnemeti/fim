/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.RoleAndPermissionRepository;
import pfa.alliance.fim.dao.UserOneTimeLinkRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.UserSearchDTO;
import pfa.alliance.fim.dto.UserSearchResultDTO;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.ActivationFailReason;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.InvalidUserPasswordException;
import pfa.alliance.fim.service.LoggedInUserDTO;
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

    private final UserOneTimeLinkRepository userOneTimeLinkRepository;

    private final EmailService emailService;

    private final EmailGeneratorService emailGeneratorService;

    private final FimUrlGeneratorService fimUrlGeneratorService;

    private final RoleAndPermissionRepository roleAndPermissionRepository;

    @Inject
    public UserManagerServiceImpl( UserRepository userRepository, EmailService emailService,
                                   EmailGeneratorService emailGeneratorService,
                                   FimUrlGeneratorService fimUrlGeneratorService,
                                   UserOneTimeLinkRepository userOneTimeLinkRepository,
                                   RoleAndPermissionRepository roleAndPermissionRepository )
    {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.emailGeneratorService = emailGeneratorService;
        this.fimUrlGeneratorService = fimUrlGeneratorService;
        this.userOneTimeLinkRepository = userOneTimeLinkRepository;
        this.roleAndPermissionRepository = roleAndPermissionRepository;
    }

    @Override
    @Transactional
    public User registerUser( String email, String cleanPassword, String firstName, String lastName, Locale locale )
    {
        return saveNewlyCreatedUser( email, cleanPassword, firstName, lastName, locale,
                                     OneTimeLinkType.USER_REGISTRATION, UserRole.TEAM );
    }

    @Override
    @Transactional
    public User inviteUser( String email, String firstName, String lastName, UserRole defaultRole, Locale locale )
    {
        return saveNewlyCreatedUser( email, "User: " + System.nanoTime(), firstName, lastName, locale,
                                     OneTimeLinkType.USER_INVITE, defaultRole );
    }

    /**
     * Saves a newly created {@link User} and send it's welcome e-mail.
     * 
     * @param email the user e-mail address
     * @param cleanPassword the user desired clean password
     * @param firstName the user first name
     * @param lastName the user last name
     * @param locale the {@link Locale} (language) of welcome e-mail
     * @param linkType the designation of {@link UserOneTimeLink} to create
     * @param defaultRole the default role this user should have
     * @return the created {@link User} after it was saved in database
     */
    private User saveNewlyCreatedUser( String email, String cleanPassword, String firstName, String lastName,
                                       Locale locale, OneTimeLinkType linkType, UserRole defaultRole )
    {
        LOG.debug( "Trying to create new user: email = {}, first name = {}, last name = {}, locale = {}", email,
                   firstName, lastName, locale );
        User user = createNewUser( email, cleanPassword, firstName, lastName, defaultRole );
        try
        {
            UserOneTimeLink link = createUserOneTimeLinkFor( user, linkType );
            Set<UserOneTimeLink> links = new HashSet<UserOneTimeLink>();
            links.add( link );
            user.setOneTimeLinks( links );
            User savedUser = userRepository.save( user );
            sendNewUserWelcomeEmail( link, locale );
            user = savedUser;
        }
        catch ( PersistenceException e )
        {
            if ( isDuplicateUserInfoRelatedException( e ) )
            {
                LOG.warn( "Duplicate data for user: {}", user, e );
                throw new DuplicateDataException( "Duplicate user data", e );
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

    /**
     * Sends the registration / invite e-mail.
     * 
     * @param link the {@link UserOneTimeLink} with {@link User} data filled in.
     * @param locale the language for e-mail
     * @throws MessagingException in case sending fails
     */
    private void sendNewUserWelcomeEmail( UserOneTimeLink link, Locale locale )
        throws MessagingException
    {
        User user = link.getUser();
        String to = user.getEmail();
        EmailType emailType = getEmailTypeFor( link.getDesignation() );
        String subject = emailGeneratorService.getSubject( emailType, null, locale );
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put( "name", user.getFirstName() + " " + user.getLastName() );
        parameters.put( "link", fimUrlGeneratorService.getOneTimeLinkLink( link ) );
        String content = emailGeneratorService.getContent( emailType, parameters, locale );
        emailService.sendEmail( to, subject, content );
    }

    /**
     * Gets the {@link EmailType} value corresponding for given {@link OneTimeLinkType}.
     * 
     * @param linkType the provided {@link OneTimeLinkType}
     * @return the corresponding {@link EmailType}
     */
    private static EmailType getEmailTypeFor( final OneTimeLinkType linkType )
    {
        EmailType emailType = null;
        switch ( linkType )
        {
            case USER_REGISTRATION:
                emailType = EmailType.REGISTER_USER;
                break;
            case USER_INVITE:
                emailType = EmailType.INVITE_USER;
                break;
            case FORGOT_PASWORD:
                emailType = EmailType.FORGOT_PASSWORD;
                break;
            default:
                LOG.error( "Unknown email type to choose for: {}", linkType );
                throw new IllegalArgumentException( "Invalid value: " + linkType );
        }
        return emailType;
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
     * @param defaultRole the default role this user should have
     * @return the built object
     */
    private User createNewUser( String email, String cleanPassword, String firstName, String lastName,
                                UserRole defaultRole )
    {
        User user = new User();
        user.setEmail( email );
        user.setLogin( email );
        user.setFirstName( firstName );
        user.setLastName( lastName );
        user.setPassword( encryptPassword( cleanPassword ) );
        user.setStatus( UserStatus.NEW );
        user.setDefaultRole( defaultRole );
        return user;
    }

    @Override
    @Transactional
    public LoggedInUserDTO login( String username, String cleanPassword )
    {
        LOG.debug( "Trying to login: {}", username );
        LoggedInUserDTO userDto = null;
        User user = userRepository.findBy( username, encryptPassword( cleanPassword ) );
        LOG.debug( "Authentication result... username = {} --> user = {}", username, user );
        if ( user != null )
        {
            Map<UserRole, List<UserPermission>> permissions = null;
            if ( UserStatus.ACTIVE.equals( user.getStatus() ) )
            {
                Set<UserRole> roles = extractDistinctUserRoleInsideProjects( user );
                LOG.debug( "Roles for user with ID = {}: {}", user.getId(), roles );
                permissions = roleAndPermissionRepository.findPermissionsFor( roles );
                LOG.debug( "Permissions for user with ID = {}: {}", user.getId(), permissions );
            }
            else
            {
                LOG.debug( "User with ID = {} is not ACTIVE, roles will not be retrived", user.getId() );
                permissions = new HashMap<>();
            }
            userDto = new LoggedInUserDTO( user, permissions );
            checkUserStatusForLogin( user );
        }
        return userDto;
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
     * Extracts all {@link UserRoleInsideProject} types this {@link User} has.
     * 
     * @param user the {@link User} that may be assigned to several roles
     * @return a non-null {@link Set} of {@link UserRoleInsideProject}
     */
    private static Set<UserRole> extractDistinctUserRoleInsideProjects( User user )
    {
        Set<UserRole> roles = new HashSet<UserRole>();
        roles.add( user.getDefaultRole() );
        for ( UserProjectRelation relation : user.getUserProjectRelation() )
        {
            roles.add( relation.getUserRole().getCorrespondingUserRole() );
        }
        return roles;
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
    public User forgotPassword( String username, Locale locale )
        throws MessagingException
    {
        User user = userRepository.findByUsername( username );
        if ( user != null && UserStatus.ACTIVE.equals( user.getStatus() ) )
        {
            Set<UserOneTimeLink> links = user.getOneTimeLinks();
            UserOneTimeLink forgotPassword = getForgotPasswordOneTimeLink( links );
            if ( forgotPassword == null )
            {
                // create a new one and send the e-mail
                forgotPassword = createUserOneTimeLinkFor( user, OneTimeLinkType.FORGOT_PASWORD );
            }
            else
            {
                // extend the lifetime of existing one and resend the e-mail
                forgotPassword.setExpiresAt( new Timestamp( System.currentTimeMillis() + ONE_DAY_IN_MILLISECONDS ) );
            }
            userOneTimeLinkRepository.save( forgotPassword );
            // send e-mail
            sendForgotPasswordLink( forgotPassword, locale );
        }
        return user;
    }

    /**
     * Sends forgot password link.
     * 
     * @param forgotPasswordLink the forgot password link
     * @param locale the {@link Locale} used for sending e-mail
     * @throws MessagingException in case e-mail sending fails
     */
    private void sendForgotPasswordLink( UserOneTimeLink forgotPasswordLink, Locale locale )
        throws MessagingException
    {
        User user = forgotPasswordLink.getUser();
        String to = user.getEmail();
        EmailType emailType = getEmailTypeFor( forgotPasswordLink.getDesignation() );
        String subject = emailGeneratorService.getSubject( emailType, null, locale );
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put( "name", user.getFirstName() + " " + user.getLastName() );
        parameters.put( "link", fimUrlGeneratorService.getOneTimeLinkLink( forgotPasswordLink ) );
        String content = emailGeneratorService.getContent( emailType, parameters, locale );
        emailService.sendEmail( to, subject, content );
    }

    /**
     * Gets the {@link UserOneTimeLink} for {@link OneTimeLinkType#FORGOT_PASWORD} designation.
     * 
     * @param links all the links a user has
     * @return the one with right designation or null if no such link is found or list is empty
     */
    private UserOneTimeLink getForgotPasswordOneTimeLink( Set<UserOneTimeLink> links )
    {
        UserOneTimeLink result = null;
        if ( CollectionUtils.isNotEmpty( links ) )
        {
            final Timestamp now = new Timestamp( System.currentTimeMillis() );
            for ( UserOneTimeLink link : links )
            {
                if ( OneTimeLinkType.FORGOT_PASWORD.equals( link.getDesignation() ) && link.getExpiresAt().after( now ) )
                {
                    result = link;
                    break;
                }
            }
        }
        return result;
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

    @Override
    public long count( UserSearchDTO criteria )
    {
        LOG.debug( "Count the users matching criteria: {}", criteria );
        return userRepository.count( criteria );
    }

    @Override
    public List<UserSearchResultDTO> search( UserSearchDTO criteria )
    {
        LOG.debug( "Getting the users matching criteria: {}", criteria );
        return userRepository.search( criteria );
    }

    @Override
    public User findById( int id )
    {
        LOG.debug( "Find by ID = {}", id );
        return userRepository.findOne( id );
    }

    @Override
    public User findById( int id, boolean loadLastLogins, boolean loadProjectAssignments )
    {
        LOG.debug( "Find by ID = {}, loadLastLogins = {}, loadProjectAssignments = {}", id, loadLastLogins,
                   loadProjectAssignments );
        User user = userRepository.findOne( id );
        if ( user != null )
        {
            if ( loadLastLogins )
            {
                LOG.debug( "Loading last logins for user ID = {}", id );
                user.getLogins().size();
            }
            if ( loadProjectAssignments )
            {
                LOG.debug( "Loading project assignments for user ID = {}", id );
                user.getUserProjectRelation().size();
            }
        }
        return user;
    }

    @Transactional
    @Override
    public void disableUserAtOwnRequest( final int userId, final String clearTextPassword )
    {
        User user = getSupossedlyValidUserAndValidatePassword( userId, clearTextPassword );
        LOG.debug( "Disabling account (user ID = {}) at own request: {}", userId, user );
        if ( user != null )
        {
            user.setStatus( UserStatus.DISABLED );
            userRepository.save( user );
        }
    }

    @Transactional
    @Override
    public void changePassword( int userId, String curentPassword, String newPassword )
    {
        User user = getSupossedlyValidUserAndValidatePassword( userId, curentPassword );
        LOG.debug( "Changing password for (user ID = {}): {}", userId, user );
        if ( user != null )
        {
            user.setPassword( encryptPassword( newPassword ) );
            userRepository.save( user );
        }
    }

    @Transactional
    @Override
    public void changeUserData( final int userId, final String firstName, final String lastName )
    {
        LOG.debug( "Updating user data user ID = {}, first name = {}, last name = {}", userId, firstName, lastName );
        User user = userRepository.findOne( userId );
        LOG.debug( "Updating user data for (user ID = {}): {}", userId, user );
        if ( user != null )
        {
            user.setFirstName( firstName );
            user.setLastName( lastName );
            userRepository.save( user );
        }
    }

    /**
     * Gets the {@link User} with given ID and verifies if the provided password is correct.
     * 
     * @param userId the ID of the user
     * @param clearTextPassword the password in clear text form
     * @return the User if found and password is valid, null otherwise
     */
    private User getSupossedlyValidUserAndValidatePassword( final int userId, final String clearTextPassword )
    {
        String encyptedPassword = encryptPassword( clearTextPassword );
        LOG.debug( "Get user by ID = {} and validate the password = {}", encyptedPassword );
        User user = userRepository.findOne( userId );
        // the ID should be valid but extra careful always welcomed
        if ( user != null )
        {
            if ( !encyptedPassword.equals( user.getPassword() ) )
            {
                LOG.info( "User with ID {} provided and invalid password", userId );
                throw new InvalidUserPasswordException( "Wrong password for user with ID: " + userId );
            }
        }
        else
        {
            LOG.error( "User with ID {} doesn't exists. According to APP ID is valid", userId );
        }
        return user;
    }
}
