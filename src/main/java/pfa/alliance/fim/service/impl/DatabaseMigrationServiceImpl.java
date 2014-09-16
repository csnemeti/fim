/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Properties;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;

import pfa.alliance.fim.service.DatabaseMigrationService;

/**
 * This is the implementation for {@link DatabaseMigrationService}
 * 
 * @author Nemeti
 */
public class DatabaseMigrationServiceImpl
    implements DatabaseMigrationService
{
    private final Properties jpaConfiguration;

    /**
     * Called by Guice when instance of this class is created
     * 
     * @param jpaConfiguration the configuration properties for JPA
     */
    @Inject
    DatabaseMigrationServiceImpl( @JpaConfiguration Properties jpaConfiguration )
    {
        this.jpaConfiguration = jpaConfiguration;
    }

    @Override
    public String getCurrentDbVersion()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean upgradeDb()
    {
        Flyway flyway = new Flyway();

        return false;
    }

}
