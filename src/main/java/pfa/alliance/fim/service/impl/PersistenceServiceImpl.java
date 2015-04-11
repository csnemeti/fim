/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.DatabaseMigrationService;
import pfa.alliance.fim.service.PersistenceService;
import pfa.alliance.fim.service.SolrManager;

import com.google.inject.persist.PersistService;

/**
 * Implementation of {@link PersistenceService}
 * 
 * @author Csaba
 */
@Singleton
class PersistenceServiceImpl
    implements PersistenceService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( PersistenceServiceImpl.class );

    private final PersistService service;

    private final Provider<DatabaseMigrationService> databaseMigrationService;

    private final SolrManager solrManager;

    /** Flag to indicate the fact that {@link PersistService} is started or not. */
    private boolean running = false;

    @Inject
    PersistenceServiceImpl( PersistService service, Provider<DatabaseMigrationService> databaseMigrationService,
                            SolrManager solrManager )
    {
        this.service = service;
        this.databaseMigrationService = databaseMigrationService;
        this.solrManager = solrManager;
    }

    @Override
    public void startPersistence()
    {
        if ( running )
        {
            LOG.debug( "PersistService already running!" );
        }
        else
        {
            try
            {
                synchronized ( this )
                {
                    LOG.info( "Trying to start the service..." );
                    databaseMigrationService.get().upgradeDb();
                    solrManager.initDb();
                    service.start();
                    solrManager.start();
                    running = true;
                }
            }
            catch ( Exception e )
            {
                LOG.error( "Error starting service", e );
            }
        }
    }

    @Override
    public void stopService()
    {
        if ( running )
        {
            synchronized ( this )
            {
                LOG.debug( "Trying to stop the service..." );
                service.stop();
                running = false;
            }
        }
        else
        {
            LOG.debug( "PersistService is not running" );
        }
    }

    @Override
    public synchronized boolean isRunning()
    {
        return running;
    }

}
