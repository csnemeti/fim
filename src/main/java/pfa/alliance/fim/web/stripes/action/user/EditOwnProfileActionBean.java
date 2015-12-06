/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.SecurityUtil;

/**
 * This class is used for allow user editing it's own profile. Based on this, user will be able to change it's name,
 * e-mail address and password.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/profile" )
@FimSecurity( )
public class EditOwnProfileActionBean
    extends AbstractUserProfileActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( EditOwnProfileActionBean.class );

    @Inject
    public EditOwnProfileActionBean( UserManagerService userManagerService )
    {
        super( userManagerService );
        LOG.debug( "New instance created..." );
    }

    @Override
    protected Integer getUserId()
    {
        return SecurityUtil.getUserFromSession( getSession() ).getId();
    }

    /**
     * Change the user password without asking for the old password.
     * 
     * @return the page where to go next
     */
    public Resolution changePassword2()
    {
        final int userId = getUserId();
        LOG.debug( "User with ID = {} asked for password change", userId );
        try
        {
            getUserManagerService().changerPassword( userId, getPassword1() );
            setChangePasswordDbOperation( PASSWORD_CHANGED );
            LOG.info( "User's {} password change successfull", userId );
        }
        catch ( RuntimeException e )
        {
            LOG.info( "User {} password change failed", userId );
            // write that password is not correct
            setChangePasswordDbOperation( INVALID_PASSWORD );
        }
        // do the same thing as you would do when access the page for view
        return view();
    }

    @Override
    protected boolean shouldDisableStatus()
    {
        return true;
    }

    @Override
    protected boolean shouldDisableRole()
    {
        return true;
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.user.editMyProfile" );
    }
}
