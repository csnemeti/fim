/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for handle user logout.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/logout" )
public class LogoutUserActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( LogoutUserActionBean.class );

    @DefaultHandler
    public Resolution goToHomePage()
    {
        HttpSession session = getSession();
        AuthenticatedUserDTO user = SecurityUtil.getUserFromSession( session );
        LOG.debug( "User logout: {}", user );
        session.invalidate();
        return new RedirectResolution( FimPageURLs.MAIN_PAGE.getURL() );
    }

}
