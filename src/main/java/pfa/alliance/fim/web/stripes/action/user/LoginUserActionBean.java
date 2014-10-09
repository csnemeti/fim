/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
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

    private static final String DB_OPERATION_INVALID_DATA = "LoginUser.InvalidLoginOrPassword";

    private static final String DB_OPERATION_USER_NEW = "LoginUser.UserStatusNew";

    private static final String DB_OPERATION_USER_DISABLED = "LoginUser.UserStatusDisabled";

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
        User user = userManagerService.login( username, password );
        FimPageURLs page = null;
        processUserResultAndResultForwardToDashboard( user );
        if ( dbOperationResult == null )
        {
            page = FimPageURLs.USER_DASBOARD_PAGE;
        }
        else
        {
            page = FimPageURLs.USER_LOGIN_JSP;
        }
        return new RedirectResolution( page.getURL() );
    }

    /**
     * Check the {@link User} received as parameter and set {@link #dbOperationResult} according to that.
     * 
     * @param user the {@link User} that contains information
     */
    private void processUserResultAndResultForwardToDashboard( User user )
    {
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
                    // TODO set user on session
                    break;
            }

        }
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
