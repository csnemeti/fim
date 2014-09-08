/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.PersistenceService;

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

    /** Flag to indicate the fact that {@link PersistService} is started or not. */
    private boolean running = false;

    @Inject
    public PersistenceServiceImpl( PersistService service )
    {
        this.service = service;
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
            synchronized ( this )
            {
                LOG.debug( "Trying to start the service..." );
                service.start();
                running = true;
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
