/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for base class in user profile view.
 * 
 * @author Csaba
 */
public abstract class AbstractUserViewActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractUserViewActionBean.class );

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

    private User user;

    protected AbstractUserViewActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        int userId = getUserId();
        user = userManagerService.findById( userId );
        // TODo handle the case that ID is not valid
        return new ForwardResolution( FimPageURLs.USER_DASBOARD_JSP.getURL() );
    }

    /**
     * Gets the ID of the user we should display.
     * 
     * @return the ID of user to display
     */
    protected abstract int getUserId();

    /**
     * Gets the flag value that decides if last login and session age should be displayed.
     * 
     * @return true if the information should be displayed
     */
    public abstract boolean isShowLastLoginAndSessionAge();

    public User getUser()
    {
        return user;
    }

    public String getStateName()
    {
        return getEnumMessage( user.getStatus() );
    }

    public String getStateImage()
    {
        return "/images/user-state/" + user.getStatus().name().toLowerCase() + ".png";
    }

    public String getDefaultRoleName()
    {
        return getEnumMessage( user.getDefaultRole() );
    }

}
