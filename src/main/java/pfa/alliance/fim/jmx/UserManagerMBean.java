/**
 * 
 */
package pfa.alliance.fim.jmx;

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
}
