/**
 * 
 */
package pfa.alliance.fim.jmx;

import pfa.alliance.fim.model.user.UserStatus;

/**
 * The interface defines methods for managing users through JMX.
 * 
 * @author Csaba
 */
public interface UserManagerMBean
{
    /**
     * Delete the expired one-time links.
     * 
     * @return the number of links that were deleted
     */
    int deleteExpiredOneTimeLinks();

    /**
     * Mark all user accounts with {@link UserStatus#NEW} that were not confirmed in time as
     * {@link UserStatus#SCHEDULED_FOR_DELETE}.
     * 
     * @return the amount of accounts marked.
     */
    int markDeleteNotActivatedUserAccounts();

}
