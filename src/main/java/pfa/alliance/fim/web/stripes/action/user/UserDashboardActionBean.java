package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;

import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.lang.time.DurationFormatUtils;

import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.SecurityUtil;

/**
 * ActionBean for the HomePage(UserDashBoard page ) .
 * 
 * @author Balaceanu Sergiu-Denis
 */
@UrlBinding( value = "/user/dashboard" )
@FimSecurity
public class UserDashboardActionBean
    extends AbstractUserViewActionBean
{
    @Inject
    public UserDashboardActionBean( UserManagerService userManagerService )
    {
        super( userManagerService );
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.user.dashboard" );
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

    public String getEditUserProfileTitle()
    {
        return getMessage( "usercard-EditDetails.title" );
    }

    private AuthenticatedUserDTO getLoggedUser()
    {
        return SecurityUtil.getUserFromSession( getContext().getRequest().getSession() );
    }

    @Override
    protected int getUserId()
    {
        return getLoggedUser().getId();
    }

    @Override
    public boolean isShowLastLoginAndSessionAge()
    {
        return true;
    }

    @Override
    public boolean isShowLastLoginCard()
    {
        return true;
    }
}
