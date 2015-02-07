/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for allow user editing it's own profile. Based on this, user will be able to change it's name,
 * e-mail address and password.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/profile" )
@FimSecurity( )
public class EditOwnProfileActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( EditOwnProfileActionBean.class );

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

    @Inject
    public EditOwnProfileActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution view()
    {
        AuthenticatedUserDTO userDto = SecurityUtil.getUserFromSession( getSession() );
        LOG.debug( "Show edit own profile: {}", userDto );
        return new ForwardResolution( FimPageURLs.USER_EDIT_PROFILE_JSP.getURL() );
    }

    public String getLocalizationString()
    {
        JSONObject root = new JSONObject();
        for ( UserStatus userStatus : UserStatus.values() )
        {
            root.put( userStatus.name(), getEnumMessage( userStatus ) );
        }
        for ( UserRole role : UserRole.values() )
        {
            root.put( role.name(), getEnumMessage( role ) );
        }
        return root.toString();
    }

}
