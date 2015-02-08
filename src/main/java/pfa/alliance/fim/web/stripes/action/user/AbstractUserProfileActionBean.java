/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
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

    private String firstName;

    private String lastName;

    private String email;

    private UserStatus status;

    private UserRole role;

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
}
