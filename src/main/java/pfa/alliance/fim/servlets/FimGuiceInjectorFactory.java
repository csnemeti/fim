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

import pfa.alliance.fim.service.PersistenceConfigurationService;
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
    implements GuiceInjectorFactory, PersistenceConfigurationService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( FimGuiceInjectorFactory.class );

    /** The JPA configuration properties. */
    private final Properties JPA_PROPS = new Properties();

    static {
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
        jpaPersistModule.properties( JPA_PROPS );
        return Guice.createInjector( jpaPersistModule, new FimServiceModule(), new FimServletModule() );
    }

    @Override
    public void setConfiguration( Properties properties )
    {
        JPA_PROPS.clear();
        
        JPA_PROPS.putAll( properties );
        JPA_PROPS.setProperty( JPASettings.JDBC_DRIVER, "org.postgresql.Driver" );
    }

}
