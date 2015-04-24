/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.service.FimUrlGeneratorService;

/**
 * This class implements the {@link FimUrlGeneratorService}.
 * 
 * @author Csaba
 */
@Singleton
class FimUrlGeneratorServiceImpl
    implements FimUrlGeneratorService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( FimUrlGeneratorServiceImpl.class );

    /** The F.I.M. base URL. */
    private final Provider<String> fimBaseUrl;

    /**
     * Called when instance of this class is created
     * 
     * @param fimBaseUrl the F.I.M. base URL
     */
    @Inject
    FimUrlGeneratorServiceImpl( @FimUrlConfiguration Provider<String> fimBaseUrl )
    {
        this.fimBaseUrl = fimBaseUrl;
        LOG.info( "FIM base URL: {}", fimBaseUrl.get() );
    }

    @Override
    public String getOneTimeLinkLink( final UserOneTimeLink link )
    {
        String result = null;
        switch ( link.getDesignation() )
        {
            case USER_REGISTRATION:
                result = getBaseLink() + "user/activate/" + link.getUuid();
                break;
            case FORGOT_PASWORD:
            case USER_INVITE:
                result = getBaseLink() + "user/profile?token=" + link.getUuid();
                break;

            default:
                throw new IllegalArgumentException( "Invalid value: " + link.getDesignation() );
        }
        return result;
    }

    @Override
    public String getProjectLink( String projectCode )
    {
        return getBaseLink() + "project/show/" + projectCode;
    }

    /**
     * Gets the F.I.M. base link.
     * 
     * @return the base link
     */
    private String getBaseLink()
    {
        // return "https://localhost:8443/fim/";
        return fimBaseUrl.get();
    }
}
