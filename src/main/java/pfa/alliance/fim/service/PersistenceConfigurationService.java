/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.Properties;

/**
 * This interface defines the possibility to set JPA configuration properties.
 * 
 * @author Nemeti
 */
public interface PersistenceConfigurationService
{
    /**
     * Sets the configuration properties for JPA.
     * 
     * @param properties the JPA configuration properties
     */
    void setConfiguration( Properties properties );
}
