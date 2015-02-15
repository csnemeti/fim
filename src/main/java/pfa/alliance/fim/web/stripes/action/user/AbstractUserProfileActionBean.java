/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.InvalidUserPasswordException;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;

/**
 * This class represents base class for editing user profile
 * 
 * @author Csaba
 */
public abstract class AbstractUserProfileActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractUserProfileActionBean.class );

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

    @Validate( required = true, trim = true, on = "changeData" )
    private String firstName;

    @Validate( required = true, trim = true, on = "changeData" )
    private String lastName;

    @Validate( required = true, trim = true, converter = EmailTypeConverter.class, on = "changeEmail" )
    private String email;

    private UserStatus status;

    private UserRole role;

    @Validate( required = true, trim = true, on = "disableAccout" )
    private String password;

    @Validate( required = true, trim = true, on = "changePassword" )
    private String password0;

    @Validate( required = true, trim = true, minlength = 6, on = "changePassword" )
    private String password1;

    @Validate( required = true, trim = true, minlength = 6, expression = "this eq password1", on = "changePassword" )
    private String password2;

    private final static String USER_DATA_UPDATED = "EditProfileActionBean.UserDataUpdated";
    private final static String INVALID_PASSWORD = "EditProfileActionBean.InvalidPassword";

    private final static String PASSWORD_CHANGED = "EditProfileActionBean.PasswordChanged";

    /** The code of the disable account DB operation. */
    private String diableAccountDbOperation;

    /** The code of the change password operation. */
    private String changePasswordDbOperation;

    /** The code of the change email operation. */
    private String changeEmailDbOperation;

    /** The code of the update data operation. */
    private String updateDataDbOperation;

    protected AbstractUserProfileActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
    }

    @DefaultHandler
    public Resolution view()
    {
        User user = getUserFromDb();
        if ( user != null )
        {
            fillPropertiesFromUser( user );
            return new ForwardResolution( FimPageURLs.USER_EDIT_PROFILE_JSP.getURL() );
        }
        else
        {
            return new ForwardResolution( FimPageURLs.USER_EDIT_PROFILE_JSP.getURL() );
        }
    }

    /**
     * Disables an account by user request.
     * 
     * @return the page where to go next
     */
    public Resolution disableAccout()
    {
        final int userId = getUserId();
        LOG.debug( "User with ID = {} asked for disable account", userId );
        try
        {
            userManagerService.disableUserAtOwnRequest( userId, getPassword() );
            // forced log out of user and redirect to main page
            SecurityUtil.putUserIntoSession( null, getSession() );
            LOG.info( "User {} account disabled at own request", userId );
            return new RedirectResolution( FimPageURLs.MAIN_PAGE.getURL() );
        }
        catch ( InvalidUserPasswordException e )
        {
            LOG.info( "User {} account disabled rejected because of wrong password", userId );
            // write that password is not correct
            diableAccountDbOperation = INVALID_PASSWORD;
            // do the same thing as you would do when access the page for view
            return view();
        }
    }

    /**
     * Change the user e-mail address.
     * 
     * @return the page where to go next
     */
    public Resolution changeEmail()
    {
        // TODO after the e-mail change, update the user from session too
        AuthenticatedUserDTO userDto = SecurityUtil.getUserFromSession( getSession() );
        return new ForwardResolution( FimPageURLs.USER_EDIT_PROFILE_JSP.getURL() );
    }

    /**
     * Change the user password.
     * 
     * @return the page where to go next
     */
    public Resolution changePassword()
    {
        final int userId = getUserId();
        LOG.debug( "User with ID = {} asked for password change", userId );
        try
        {
            userManagerService.changePassword( userId, getPassword0(), getPassword1() );
            changePasswordDbOperation = PASSWORD_CHANGED;
            LOG.info( "User {} password change successfull", userId );
        }
        catch ( InvalidUserPasswordException e )
        {
            LOG.info( "User {} password change rejected because of wrong OLD password", userId );
            // write that password is not correct
            changePasswordDbOperation = INVALID_PASSWORD;
        }
        // do the same thing as you would do when access the page for view
        return view();
    }

    /**
     * Update user data.
     * 
     * @return the page where to go next
     */
    public Resolution changeData()
    {
        final int userId = getUserId();
        LOG.debug( "Change user data with ID = {}", userId );
        userManagerService.changeUserData( userId, firstName, lastName );
        // update the user from session too
        AuthenticatedUserDTO userDto = SecurityUtil.getUserFromSession( getSession() );
        userDto.setFirstName( firstName );
        userDto.setLastName( lastName );
        updateDataDbOperation = USER_DATA_UPDATED;
        // do the same thing as you would do when access the page for view
        return view();
    }

    /**
     * Gets the ID of the user the we need to retrieve / update.
     * 
     * @return the ID of the user
     */
    protected abstract Integer getUserId();

    /**
     * Gets the User from DB.
     * 
     * @return the user from DB, null if user is not found
     */
    private User getUserFromDb()
    {
        Integer userId = getUserId();
        LOG.debug( "Show edit own profile for user ID = {}", userId );
        return userManagerService.findById( userId );
    }

    /**
     * Fills in the properties from User
     * 
     * @param user the user that is used as source of information
     */
    private void fillPropertiesFromUser( User user )
    {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        this.status = user.getStatus();
        this.role = user.getDefaultRole();
    }

    public String getLocalizationString()
    {
        JSONObject root = new JSONObject();
        for ( UserStatus userStatus : UserStatus.values() )
        {
            root.put( userStatus.name(), getEnumMessage( userStatus ) );
        }
        for ( UserRole role : UserRole.values() )
        {
            root.put( role.name(), getEnumMessage( role ) );
        }
        return root.toString();
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public List<StripesDropDownOption> getDefaultRoles()
    {
        List<StripesDropDownOption> roles = new ArrayList<StripesDropDownOption>();
        UserRole[] orderedRoles =
            new UserRole[] { UserRole.ADMIN, UserRole.PROJECT_ADMIN, UserRole.PRODUCT_OWNER, UserRole.SCRUM_MASTER,
                UserRole.TEAM, UserRole.STATISTICAL };
        for ( UserRole role : orderedRoles )
        {
            roles.add( new StripesDropDownOption( role, getEnumMessage( role ) ) );
        }
        return roles;
    }

    public List<StripesDropDownOption> getDefaultStatuses()
    {
        List<StripesDropDownOption> statuses = new ArrayList<StripesDropDownOption>();
        UserStatus[] orderedStatuses = new UserStatus[] { UserStatus.NEW, UserStatus.ACTIVE, UserStatus.DISABLED };
        for ( UserStatus status : orderedStatuses )
        {
            statuses.add( new StripesDropDownOption( status, getEnumMessage( status ) ) );
        }
        return statuses;
    }

    public String getStatus()
    {
        return status.name();
    }

    public void setStatus( String status )
    {
        if ( status != null )
        {
            this.status = UserStatus.valueOf( status );
        }
    }

    public String getRole()
    {
        return role.name();
    }

    public void setRole( String role )
    {
        if ( role != null )
        {
            this.role = UserRole.valueOf( role );
        }
    }

    /**
     * Verifies if user status should be enabled.
     * 
     * @return true if user status should be disabled
     */
    protected abstract boolean shouldDisableStatus();

    public String getStatusDisabled()
    {
        return ( shouldDisableStatus() ) ? "disabled" : "";
    }

    /**
     * Verifies if user role should be enabled.
     * 
     * @return true if user role should be disabled
     */
    protected abstract boolean shouldDisableRole();

    public String getRoleDisabled()
    {
        return ( shouldDisableRole() ) ? "disabled" : "";
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public void setPassword0( String password0 )
    {
        this.password0 = password0;
    }

    public void setPassword1( String password1 )
    {
        this.password1 = password1;
    }

    public void setPassword2( String password2 )
    {
        this.password2 = password2;
    }

    public String getPassword0()
    {
        return password0;
    }

    public String getPassword1()
    {
        return password1;
    }

    public String getPassword2()
    {
        return password2;
    }

    public String getDiableAccountDbOperationText()
    {
        return getLocalizedOperation( diableAccountDbOperation );
    }

    public String getChangePasswordDbOperationText()
    {
        return getLocalizedOperation( changePasswordDbOperation );
    }

    public String getChangeEmailDbOperationText()
    {
        return getLocalizedOperation( changeEmailDbOperation );
    }

    public String getUpdateDataDbOperationText()
    {
        return getLocalizedOperation( updateDataDbOperation );
    }

    private String getLocalizedOperation( String operationCode )
    {
        String result = "";
        if ( operationCode != null )
        {
            result = getMessage( operationCode );
        }
        return result;
    }
}
