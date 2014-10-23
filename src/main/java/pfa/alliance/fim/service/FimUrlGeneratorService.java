/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This service is used for generating URLs (that are in most of the cases added in e-mails).
 * 
 * @author Csaba
 */
public interface FimUrlGeneratorService
{
    /**
     * Gets the link for activate account.
     * 
     * @param activationUuid the UUID of user that should be part of the link
     * @return the built link
     */
    String getActivateAccountLink( String activationUuid );
}
