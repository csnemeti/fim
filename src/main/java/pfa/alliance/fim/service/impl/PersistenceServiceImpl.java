/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.batoo.jpa.JPASettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.PersistenceConfigurationService;
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

    private final PersistenceConfigurationService persistenceConfigurationService;

    /** Flag to indicate the fact that {@link PersistService} is started or not. */
    private boolean running = false;

    @Inject
    public PersistenceServiceImpl( PersistService service,
                                   PersistenceConfigurationService persistenceConfigurationService )
    {
        this.service = service;
        this.persistenceConfigurationService = persistenceConfigurationService;
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
                persistenceConfigurationService.setConfiguration( readConfiguration() );
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

    private Properties readConfiguration()
    {
        Properties properties = new Properties();
        String host = System.getenv( "OPENSHIFT_POSTGRESQL_DB_HOST" );
        if ( host != null && host.length() > 0 )
        {
            properties.setProperty( JPASettings.JDBC_USER, "adminqru6wjz" );
            properties.setProperty( JPASettings.JDBC_PASSWORD, "rdAMCMCF3p" );
            String port = System.getenv( "OPENSHIFT_POSTGRESQL_DB_PORT" );
            if ( port != null && port.length() > 0 )
            {
                port = ":" + port;
            }
            else
            {
                port = "";
            }
            properties.setProperty( JPASettings.JDBC_URL, "jdbc:postgresql://" + host + port + "/dev" );
        }
        else
        {
            fillDefaultPropeties( properties );
        }

        return properties;
    }

    private void fillDefaultPropeties( final Properties props )
    {
        props.setProperty( JPASettings.JDBC_USER, "fim" );
        props.setProperty( JPASettings.JDBC_PASSWORD, "fim" );
        props.setProperty( JPASettings.JDBC_URL, "jdbc:postgresql://127.0.0.1:5432/fim" );
    }
}
