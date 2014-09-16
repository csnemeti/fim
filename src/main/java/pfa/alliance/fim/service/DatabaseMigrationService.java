/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This class defines the methods necessary for database versioning & migration.
 * 
 * @author Nemeti
 */
public interface DatabaseMigrationService
{
    /**
     * Gets the current version of the database.
     * 
     * @return the current db version
     */
    String getCurrentDbVersion();

    /**
     * Perform database upgrade if necessary.
     * 
     * @return true if upgrade was necessary, false if not
     */
    boolean upgradeDb();
}
