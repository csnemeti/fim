/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;

import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
