/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserLogin;
import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.service.LoggedInUserDTO;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for handle user login.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/login" )
public class LoginUserActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( LoginUserActionBean.class );

    static final String DB_OPERATION_INVALID_DATA = "LoginUser.InvalidLoginOrPassword";

    static final String DB_OPERATION_USER_NEW = "LoginUser.UserStatusNew";

    static final String DB_OPERATION_USER_DISABLED = "LoginUser.UserStatusDisabled";

    private final UserManagerService userManagerService;

    @Validate( required = true, trim = true, on = "tryLogin" )
    private String username;

    @Validate( required = true, trim = true, on = "tryLogin" )
    private String password;

    /** Message key regarding Database operation */
    private String dbOperationResult;

    @Inject
    public LoginUserActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        return new ForwardResolution( FimPageURLs.USER_LOGIN_JSP.getURL() );
    }

    /**
     * Method called when authentication form Submit button is pressed.
     * 
     * @return where to go next
     */
    public Resolution tryLogin()
    {
        LOG.debug( "Trying to login..., username = {}", username );
        LoggedInUserDTO userDTO = userManagerService.login( username, password );
        processUserResultAndResultForwardToDashboard( userDTO );
        if ( dbOperationResult == null )
        {
            return new RedirectResolution( FimPageURLs.USER_DASBOARD_PAGE.getURL() );
        }
        else
        {
            return new ForwardResolution( FimPageURLs.USER_LOGIN_JSP.getURL() );
        }
    }

    /**
     * Check the {@link User} received as parameter and set {@link #dbOperationResult} according to that.
     * 
     * @param userDTO the {@link LoggedInUserDTO} that contains the {@link User} information
     */
    private void processUserResultAndResultForwardToDashboard( LoggedInUserDTO userDTO )
    {
        User user = userDTO.getUser();
        if ( user == null )
        {
            // handle invalid user or wrong password
            dbOperationResult = DB_OPERATION_INVALID_DATA;
        }
        else
        {
            switch ( user.getStatus() )
            {
                case NEW:
                    dbOperationResult = DB_OPERATION_USER_NEW;
                    break;
                case DISABLED:
                    dbOperationResult = DB_OPERATION_USER_DISABLED;
                    break;
                case ACTIVE:
                    setUserOnSession( userDTO );
                    break;
            }

        }
    }

    /**
     * Sets the user on the session.
     * 
     * @param user the user to set on session
     */
    private void setUserOnSession( LoggedInUserDTO userDTO )
    {
        User user = userDTO.getUser();
        Timestamp lastLogin = getUserLastLogin( user.getLogins() );
        AuthenticatedUserDTO authenticatedUserDTO =
            new AuthenticatedUserDTO( user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                                      user.getLogin(), lastLogin, convertPermissions( userDTO ) );
        SecurityUtil.putUserIntoSession( authenticatedUserDTO, getContext().getRequest().getSession( true ) );
    }

    /**
     * Convert the existing user permission into expected format.
     * 
     * @param userDTO the {@link LoggedInUserDTO} containing the {@link User} with it's own permission system
     * @return the expected format for {@link AuthenticatedUserDTO}. Key is a Project ID, value is list or
     *         {@link Permission}s from that Project. null key keeps the default Permissions.
     */
    private static Map<Integer, Set<Permission>> convertPermissions( LoggedInUserDTO userDTO )
    {
        // we convert the map with permissions
        Map<UserRole, Set<Permission>> rolePermissionsMap = new HashMap<>();
        Map<UserRole, List<UserPermission>> userDtoPermissions = userDTO.getPermissions();
        for ( Map.Entry<UserRole, List<UserPermission>> permissionEntry : userDtoPermissions.entrySet() )
        {
            Set<Permission> permissionsForRole = new HashSet<>();
            rolePermissionsMap.put( permissionEntry.getKey(), permissionsForRole );
            for ( UserPermission userPermission : permissionEntry.getValue() )
            {
                permissionsForRole.add( Permission.valueOf( userPermission.name() ) );
            }
        }
        // for small memory footprint we clear the OLD permissions
        userDtoPermissions.clear();

        User user = userDTO.getUser();
        Map<Integer, Set<Permission>> permissionsMap = new HashMap<>();
        // we add the default mapping
        permissionsMap.put( null, rolePermissionsMap.get( user.getDefaultRole() ) );
        // we add the mapping based on project ID, null means default set
        Set<UserProjectRelation> userProjectRelation = user.getUserProjectRelation();
        if ( CollectionUtils.isNotEmpty( userProjectRelation ) )
        {
            for ( UserProjectRelation relation : userProjectRelation )
            {
                UserRole role = relation.getUserRole().getCorrespondingUserRole();
                permissionsMap.put( relation.getProject().getId(), rolePermissionsMap.get( role ) );
            }
            // for small memory footprint we clear the userProjectRelation
            userProjectRelation.clear();
        }

        LOG.debug( "Permissions for user ID = {} : {}", user.getId(), permissionsMap );
        return permissionsMap;
    }

    /**
     * Gets the last login information from a given set.
     * 
     * @param logins the list of last logins
     * @return the last login or the current time if list is empty
     */
    private static Timestamp getUserLastLogin( final Set<UserLogin> logins )
    {
        Timestamp lastLogin = null;
        for ( UserLogin login : logins )
        {
            if ( lastLogin == null )
            {
                lastLogin = login.getCreatedAt();
            }
            else
            {
                if ( lastLogin.before( login.getCreatedAt() ) )
                {
                    lastLogin = login.getCreatedAt();
                }
            }
        }
        // for small memory footprint we clear the logins
        logins.clear();
        // theoretical null guard
        if ( lastLogin == null )
        {
            lastLogin = new Timestamp( System.currentTimeMillis() );
        }
        return lastLogin;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getDbOperationResult()
    {
        String result = null;

        if ( dbOperationResult != null )
        {
            result = getMessage( dbOperationResult );
        }
        return result;
    }

}
