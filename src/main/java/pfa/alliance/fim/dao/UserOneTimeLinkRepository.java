/**
 * 
 */
package pfa.alliance.fim.dao;

import pfa.alliance.fim.model.user.UserOneTimeLink;

/**
 * This class manages {@link UserOneTimeLink} persistence.
 * 
 * @author Csaba
 */
public interface UserOneTimeLinkRepository
    extends JpaRepository<UserOneTimeLink, Long>
{
    /**
     * Delete all expired one time links.
     * 
     * @return the number of deleted records
     */
    int deleteExpiredLinks();
}
