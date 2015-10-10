/**
 * 
 */
package pfa.alliance.fim.service;

import pfa.alliance.fim.dto.issue.IssueIdentifier;
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

    /**
     * Builds URL to a project. The method doesn't check if the project exists.
     * 
     * @param projectCode the code of the project
     * @return the build URL
     */
    String getProjectLink( final String projectCode );

    /**
     * Builds URL to an issue. The method doesn't check if the issue exists.
     * 
     * @param identifier the identifier of the
     * @return the build URL
     */
    String getIssueLink( IssueIdentifier identifier );
}
