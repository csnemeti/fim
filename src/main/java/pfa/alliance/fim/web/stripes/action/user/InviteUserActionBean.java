/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.service.impl.DuplicateDataException;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for inviting persons to join FIM.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/invite" )
@FimSecurity( checkIfAll = { Permission.ADMIN_INVITE_USER } )
public class InviteUserActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( InviteUserActionBean.class );

    private final UserManagerService userManagerService;

    @Validate( required = false, trim = true, on = "tryRegister" )
    private String firstName;

    @Validate( required = false, trim = true, on = "tryRegister" )
    private String lastName;

    @Validate( required = true, trim = true, converter = EmailTypeConverter.class, on = "tryRegister" )
    private String email;

    private UserRole defaultRole = UserRole.TEAM;

    /** Message key regarding Database operation */
    private String dbOperationResult;

    private final static String USER_CREATED_RESPONSE = "userInvite.userCreated";

    private final static String DUPLICATE_USER_RESPONSE = "userInvite.duplicateUser";

    @Inject
    public InviteUserActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        return new ForwardResolution( FimPageURLs.USER_INVITE_JSP.getURL() );
    }

    /**
     * Method called when user presses Submit button from registration form.
     * 
     * @return the place where it should go after the operation
     */
    public Resolution tryRegister()
    {
        LOG.debug( "Inviting user: firstName = {}, lastName = {}, email = {}, default role = {}", firstName, lastName,
                   email, defaultRole );
        try
        {
            userManagerService.inviteUser( email, firstName, lastName, defaultRole, getContext().getLocale() );
            dbOperationResult = USER_CREATED_RESPONSE;
        }
        catch ( DuplicateDataException e )
        {
            dbOperationResult = DUPLICATE_USER_RESPONSE;
        }
        return new ForwardResolution( FimPageURLs.USER_INVITE_JSP.getURL() );
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

    public String getDbOperationResult()
    {
        String result = null;

        if ( dbOperationResult != null )
        {
            result = getMessage( dbOperationResult );
        }
        return result;
    }

    public String getDefaultRole()
    {
        return defaultRole.name();
    }

    public void setDefaultRole( String defaultRole )
    {
        this.defaultRole = UserRole.valueOf( defaultRole );
    }

    public List<StripesUserRole> getDefaultRoles()
    {
        List<StripesUserRole> roles = new ArrayList<StripesUserRole>();
        UserRole[] orderedRoles =
            new UserRole[] { UserRole.ADMIN, UserRole.PROJECT_ADMIN, UserRole.PRODUCT_OWNER, UserRole.SCRUM_MASTER,
                UserRole.TEAM, UserRole.STATISTICAL };
        for ( UserRole role : orderedRoles )
        {
            roles.add( new StripesUserRole( role, getMessage( role.getDeclaringClass().getName() + "." + role.name() ) ) );
        }
        return roles;
    }
}
