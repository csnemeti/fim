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

}
