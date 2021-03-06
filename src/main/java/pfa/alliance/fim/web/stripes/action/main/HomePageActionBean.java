package pfa.alliance.fim.web.stripes.action.main;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * ActionBean for the HomePage(UserDashBoard page ) .
 * 
 * @author Balaceanu Sergiu-Denis
 */
@UrlBinding( value = "/user/dashboard" )
public class HomePageActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( HomePageActionBean.class );

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

    /**
     * Returns the current logged user name or username (if name is missing).
     * 
     * @return
     */
    public String getUsername()
    {
        AuthenticatedUserDTO userDTO = getLoggedUser();
        String name = StringUtils.join( new String[] { userDTO.getFirstName(), userDTO.getLastName() }, " " );
        if ( StringUtils.isBlank( name ) )
        {
            name = userDTO.getEmail();
        }
        return name;
    }

    /**
     * Returns the current logged user email .
     * 
     * @return
     */
    public String getUserEmail()
    {
        return getLoggedUser().getEmail();
    }

    /**
     * Returns the last login date of the current user .
     * 
     * @return
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
