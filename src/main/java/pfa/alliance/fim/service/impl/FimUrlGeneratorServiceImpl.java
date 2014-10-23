/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Singleton;

import pfa.alliance.fim.service.FimUrlGeneratorService;

/**
 * This class implements the {@link FimUrlGeneratorService}.
 * 
 * @author Csaba
 */
@Singleton
public class FimUrlGeneratorServiceImpl
    implements FimUrlGeneratorService
{

    @Override
    public String getActivateAccountLink( String activationUuid )
    {
        return getBaseLink() + "user/activate/" + activationUuid;
    }

    /**
     * Gets the F.I.M. base link.
     * 
     * @return the base link
     */
    private String getBaseLink()
    {
        // return "https://localhost:8443/fim/";
        return "http://localhost:8080/fim/";
    }
}
