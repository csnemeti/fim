/**
 * 
 */
package pfa.alliance.fim.service;


/**
 * This service is used for starting JPA persistence
 * 
 * @author Csaba
 */
public interface PersistenceService
{
    /**
     * Starts the persistence service. In case the service is already started, this method will do nothing.
     */
    void startPersistence();

    /**
     * Stop the persistence service.
     */
    void stopService();

    /**
     * Gets the persistence service running status.
     * 
     * @return true if the service is running
     */
    boolean isRunning();
}
