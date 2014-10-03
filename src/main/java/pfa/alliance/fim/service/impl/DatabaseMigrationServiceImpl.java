/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.batoo.jpa.JPASettings;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.DatabaseMigrationService;

/**
 * This is the implementation for {@link DatabaseMigrationService}
 * 
 * @author Nemeti
 */
@Singleton
class DatabaseMigrationServiceImpl
    implements DatabaseMigrationService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( DatabaseMigrationServiceImpl.class );

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
        LOG.debug( "Instance created..." );
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
        LOG.info( "DB upgrade started" );
        Flyway flyway = new Flyway();
        flyway.setSqlMigrationPrefix( "" );
        flyway.setLocations( "classpath:/db/migration/postgres" );
        flyway.setDataSource( jpaConfiguration.getProperty( JPASettings.JDBC_URL ),
                              jpaConfiguration.getProperty( JPASettings.JDBC_USER ),
                              jpaConfiguration.getProperty( JPASettings.JDBC_PASSWORD ), "" );
        flyway.migrate();

        LOG.info( "DB upgrade completed" );
        return false;
    }

}
