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

import pfa.alliance.fim.service.ActivationFailReason;
import pfa.alliance.fim.service.UserActivationFailException;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * Activate account action bean.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/activate/{uuid}" )
public class ActivateAccountActionBean
    extends BasePageActionBean

{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( ActivateAccountActionBean.class );

    /** The UUID received as parameter in request. */
    private String uuid;

    /** Indicates the fail reason. */
    private ActivationFailReason activationFailReason;

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

    @Inject
    public ActivateAccountActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution view()
    {
        LOG.debug( "Validating: {}", uuid );
        if ( uuid == null )
        {
            LOG.debug( "UUID is null, forwarding to main page" );
            return new RedirectResolution( FimPageURLs.MAIN_PAGE.getURL() );
        }
        else
        {
            try
            {
                userManagerService.activateUser( uuid );
                LOG.info( "Account activation succesfull. UUID = {}", uuid );
            }
            catch ( UserActivationFailException e )
            {
                LOG.info( "Account activation failed. UUID = {}, reason = {}", uuid, e.getReason() );
                this.activationFailReason = e.getReason();
            }
        }
        return new ForwardResolution( FimPageURLs.USER_ACTIVATION_JSP.getURL() );
    }

    public String getSuccessResult()
    {
        return ( activationFailReason == null ) ? "block" : "none";
    }

    public String getErrorResult()
    {
        return ( activationFailReason != null ) ? "block" : "none";
    }

    /**
     * Gets the localized reason why activation failed.
     * 
     * @return the localized reason of failure
     */
    public String getFailReason()
    {
        return null;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid( String uuid )
    {
        this.uuid = uuid;
    }

}
