/**
 * 
 */
package pfa.alliance.fim.servlets;

import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.batoo.jpa.JPASettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.impl.FimServiceModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.util.Modules;

/**
 * This class is used for defining the {@link GuiceServletContextListener}
 * 
 * @author Csaba
 */
public class FimServletContextListener
    extends GuiceServletContextListener
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( FimServletContextListener.class );

    @Override
    public void contextInitialized( ServletContextEvent servletContextEvent )
    {
        super.contextInitialized( servletContextEvent );
        LOG.info( "Fim application started..." );
    }

    @Override
    protected Injector getInjector()
    {
        JpaPersistModule jpaPersistModule = new JpaPersistModule( "fimJpaUnit" );
        jpaPersistModule.properties( buildJpaProperties() );
        //return Guice.createInjector( Modules.combine( jpaPersistModule, new FimServiceModule(),  new FimServletModule()) );
        return Guice.createInjector( new FimServletModule());
    }

    @Override
    public void contextDestroyed( ServletContextEvent servletContextEvent )
    {
        LOG.info( "Stopping Fim application..." );
        super.contextDestroyed( servletContextEvent );
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
