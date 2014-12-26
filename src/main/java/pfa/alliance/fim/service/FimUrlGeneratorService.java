/**
 * 
 */
package pfa.alliance.fim.service;

import pfa.alliance.fim.model.user.UserOneTimeLink;

/**
 * This service is used for generating URLs (that are in most of the cases added in e-mails).
 * 
 * @author Csaba
 */
public interface FimUrlGeneratorService
{
    /**
     * Gets a one time usable HTTP(S) link.
     * 
     * @param link the link record
     * @return the built link
     */
    String getOneTimeLinkLink( final UserOneTimeLink link );
}
