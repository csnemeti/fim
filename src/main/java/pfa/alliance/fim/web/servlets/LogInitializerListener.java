/**
 * 
 */
package pfa.alliance.fim.web.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is user for initialize the log (directory)
 * 
 * @author Csaba
 */
public class LogInitializerListener
    implements ServletContextListener
{

    public LogInitializerListener()
    {
        String value = getBaseDirectory();
        // add logs to base dir
        if ( !value.endsWith( "/" ) )
        {
            value += "/";
        }
        value += "logs";

        // set the property
        System.setProperty( "fim.log.dir", value );
        // make a first log
        final Logger log = LoggerFactory.getLogger( LogInitializerListener.class );
        log.info( "FIM log initialized. Log directory: {}", value );
    }

    /**
     * Gets base directory where to log.
     * 
     * @return the log base directory
     */
    private String getBaseDirectory()
    {
        String value = System.getProperty( "catalina.base" );
        if ( StringUtils.isBlank( value ) )
        {
            value = System.getProperty( "jetty.base" );
            if ( StringUtils.isBlank( value ) )
            {
                value = ".";
            }
        }
        return value;
    }

    @Override
    public void contextInitialized( ServletContextEvent sce )
    {
        // we do not care about this
    }
    @Override
    public void contextDestroyed( ServletContextEvent sce )
    {
        // we do not care about this
    }

}
