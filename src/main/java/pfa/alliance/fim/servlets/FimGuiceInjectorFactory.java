/**
 * 
 */
package pfa.alliance.fim.servlets;

import java.util.Properties;

import javax.inject.Singleton;
import javax.servlet.ServletContext;

import org.batoo.jpa.JPASettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import pfa.alliance.fim.service.impl.FimServiceModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.silvermindsoftware.sg.guice.GuiceInjectorFactory;

/**
 * Alternative Guice {@link Injector} initialization method that uses in relation with Stripes. This class is invoked
 * from {@link com.silvermindsoftware.sg.context.GuiceContextListener} when Guice {@link Injector} has to be build.
 * 
 * @author Csaba
 */
@Singleton
public class FimGuiceInjectorFactory
    implements GuiceInjectorFactory
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( FimGuiceInjectorFactory.class );

    static
    {
        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
    }

    /**
     * Called when instance of this class is created
     */
    public FimGuiceInjectorFactory()
    {
        LOG.debug( "Initializing..." );
    }

    @Override
    public Injector getInjector( ServletContext aServletContext )
    {
        LOG.debug( "Init Injector..." );
        JpaPersistModule jpaPersistModule = new JpaPersistModule( "fimJpaUnit" );
        jpaPersistModule.properties( readConfiguration() );
        return Guice.createInjector( jpaPersistModule, new FimServiceModule(), new FimServletModule() );
    }

    private Properties readConfiguration()
    {
        Properties properties = new Properties();
        String host = System.getenv( "OPENSHIFT_POSTGRESQL_DB_HOST" );
        LOG.debug( "OPENSHIFT_POSTGRESQL_DB_HOST = {}", host );
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
        LOG.debug( "Read JPA configuration: {}", properties );
        return properties;
    }

    private void fillDefaultPropeties( final Properties props )
    {
        props.setProperty( JPASettings.JDBC_USER, "fim" );
        props.setProperty( JPASettings.JDBC_PASSWORD, "fim" );
        props.setProperty( JPASettings.JDBC_URL, "jdbc:postgresql://127.0.0.1:5432/fim" );
    }
}
