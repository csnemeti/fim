/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;
import javax.mail.MessagingException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
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
 * This action is called when user tries to log in but realizes that he / she forgot the user password. A one time login
 * link will be sent to user in order to allow logging in without password and change it.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/forgot-password" )
public class ForgotPasswordActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( ForgotPasswordActionBean.class );

    private static final String DB_OPERATION_INVALID_DATA = "ForgotPassword.InvalidLogin";

    private static final String DB_OPERATION_UNABLE_TO_SEND_EMAIL = "ForgotPassword.UnableToSendEmail";

    private static final String DB_OPERATION_USER_NEW = "ForgotPassword.UserStatusNew";

    private static final String DB_OPERATION_USER_DISABLED = "ForgotPassword.UserStatusDisabled";

    private static final String DB_OPERATION_SUCCESSFUL = "ForgotPassword.OperationSuccessful";

    private final UserManagerService userManagerService;

    @Validate( required = true, trim = true, on = "tryForgotPassword" )
    private String username;

    /** Message key regarding Database operation */
    private String dbOperationResult;

    @Inject
    public ForgotPasswordActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        return new ForwardResolution( FimPageURLs.USER_FORGOT_PASSWORD_JSP.getURL() );
    }

    /**
     * Method called when authentication form Submit button is pressed.
     * 
     * @return where to go next
     */
    public Resolution tryForgotPassword()
    {
        LOG.debug( "Trying to reset password for user: {}", username );
        try
        {
            User user = userManagerService.forgotPassword( username, getContext().getLocale() );
            processUserResultAndResultForwardToDashboard( user );
            // reset username
            username = null;
        }
        catch ( MessagingException e )
        {
            dbOperationResult = DB_OPERATION_UNABLE_TO_SEND_EMAIL;
        }
        return new ForwardResolution( FimPageURLs.USER_FORGOT_PASSWORD_JSP.getURL() );
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
                    dbOperationResult = DB_OPERATION_SUCCESSFUL;
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
