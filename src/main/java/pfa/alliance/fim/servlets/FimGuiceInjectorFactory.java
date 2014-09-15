/**
 * 
 */
package pfa.alliance.fim.servlets;

import java.util.Properties;

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
public class FimGuiceInjectorFactory
    implements GuiceInjectorFactory
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( FimGuiceInjectorFactory.class );

    /**
     * Called when instance of this class is created
     */
    public FimGuiceInjectorFactory()
    {
        LOG.debug( "Initializing..." );
        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
    }

    @Override
    public Injector getInjector( ServletContext aServletContext )
    {
        LOG.debug( "Init Injector..." );
        JpaPersistModule jpaPersistModule = new JpaPersistModule( "fimJpaUnit" );
        jpaPersistModule.properties( buildJpaProperties() );
        return Guice.createInjector( jpaPersistModule, new FimServiceModule(), new FimServletModule() );
        // return Guice.createInjector(new FimServiceModule(), new FimServletModule());
    }

    /**
     * Builds a map with the JPA properties.
     * 
     * @return the properties for JPA
     */
    private Properties buildJpaProperties()
    {
        Properties props = new Properties();
        props.setProperty( JPASettings.JDBC_DRIVER, "org.postgresql.Driver" );
        props.setProperty( JPASettings.JDBC_USER, "fim" );
        props.setProperty( JPASettings.JDBC_PASSWORD, "fim" );
        props.setProperty( JPASettings.JDBC_URL, "jdbc:postgresql://127.0.0.1:5432/fim" );
        // props.setProperty( JPASettings.JDBC_DRIVER , "" );
        return props;
    }

}
