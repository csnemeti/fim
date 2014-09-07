/**
 * 
 */
package pfa.alliance.fim.servlets;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;

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
        return Guice.createInjector( new FimServletModule(), new JpaPersistModule( "fimJpaUnit" ) );
    }

    @Override
    public void contextDestroyed( ServletContextEvent servletContextEvent )
    {
        LOG.info( "Stopping Fim application..." );
        super.contextDestroyed( servletContextEvent );
    }
}
