/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;

import net.sourceforge.stripes.action.UrlBinding;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.security.SecurityUtil;

/**
 * This action bean shows a user profile.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/user/show/{userId}" )
@FimSecurity
public class ShowUserProfileActionBean
    extends AbstractUserViewActionBean
{
    /** The code of the user. */
    private int userId;

    @Inject
    public ShowUserProfileActionBean( UserManagerService userManagerService )
    {
        super( userManagerService );
    }

    @Override
    protected int getUserId()
    {
        return userId;
    }

    @Override
    public boolean isShowLastLoginAndSessionAge()
    {
        return false;
    }

    @Override
    public boolean isShowProjectAssignments()
    {
        return SecurityUtil.hasPermission( Permission.ADMIN_SHOW_ASSIGNED_PROJECTS, getSession() );
    }

    public void setUserId( int userId )
    {
        this.userId = userId;
    }

}
