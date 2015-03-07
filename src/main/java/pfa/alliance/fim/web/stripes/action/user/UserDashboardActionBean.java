package pfa.alliance.fim.web.stripes.action.user;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * ActionBean for the HomePage(UserDashBoard page ) .
 * 
 * @author Balaceanu Sergiu-Denis
 */
@UrlBinding( value = "/user/dashboard" )
@FimSecurity
public class UserDashboardActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserDashboardActionBean.class );

    @DefaultHandler
    public Resolution goToHomePage()
    {
        // if no user is logged on the session we redirect it to LoginPage
        if ( getLoggedUser() != null )
        {
            LOG.debug( "Show page..." );
            return new ForwardResolution( FimPageURLs.USER_DASBOARD_JSP.getURL() );
        }
        else
        {
            return new ForwardResolution( FimPageURLs.USER_LOGIN_JSP.getURL() );
        }
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.user.dashboard" );
    }

    /**
     * Returns the current logged user name or username (if name is missing).
     * 
     * @return the name of the user or the login name if we have no name information
     */
    public String getUsername()
    {
        AuthenticatedUserDTO userDTO = getLoggedUser();
        return userDTO.getName();
    }

    /**
     * Returns the current logged user email .
     * 
     * @return the user e-mail
     */
    public String getUserEmail()
    {
        return getLoggedUser().getEmail();
    }

    /**
     * Returns the last login date of the current user .
     * 
     * @return the last time when user logged in
     */
    public String getLastLoginTime()
    {
        return DateUtils.formatDate( getLoggedUser().getLastLogin(), DateUtils.DATETIME_FORMAT_DAY_FIRST );
    }

    /**
     * Gets the age of the session
     * 
     * @return the session age
     */
    public String getSessionAge()
    {
        long durationInMillis =
            System.currentTimeMillis() - getContext().getRequest().getSession( true ).getCreationTime();
        return DurationFormatUtils.formatDuration( durationInMillis, "H:mm:ss", true );
    }

    private AuthenticatedUserDTO getLoggedUser()
    {
        return SecurityUtil.getUserFromSession( getContext().getRequest().getSession() );
    }
}
