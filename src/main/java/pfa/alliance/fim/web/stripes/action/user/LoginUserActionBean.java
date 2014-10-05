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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final UserManagerService userManagerService;

    private String username;

    private String password;

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
        // TODO do the authentication and dedirect to dashboard
        return new RedirectResolution( FimPageURLs.USER_DASBOARD_PAGE.getURL() );
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

}
