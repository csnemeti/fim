package pfa.alliance.fim.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.ConfigurationService;
import pfa.alliance.fim.service.PersistenceService;

/**
 * Filter that verify if the FIM Application is properly configured.
 *
 * @author Balaceanu Sergiu-Denis
 */
@Singleton
public class SetupVerifyFilter
    implements Filter
{
    private static final Logger LOG = LoggerFactory.getLogger( SetupVerifyFilter.class );

    protected static final String WIZZARD_PATH = "/setup";

    private final ConfigurationService configurationService;

    private final PersistenceService persistenceService;

    /**
     * Called when instance of this class is created
     *
     * @param configurationService the
     * @param persistenceService
     */
    @Inject
    SetupVerifyFilter( ConfigurationService configurationService, PersistenceService persistenceService )
    {
        this.configurationService = configurationService;
        this.persistenceService = persistenceService;
    }

    @Override
    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        LOG.debug( "SetupVerifyFilter initialized ..." );
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        LOG.debug( "Request received..." );
        // if the application configuration is not completed , we redirect it to the wizard setup
        if ( !configurationService.isConfigurationCompleted() && response instanceof HttpServletResponse )
        {
            LOG.info( "Application is not configured !" );
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect( WIZZARD_PATH );
        }
        else
        {
            // this will start Persistence IF service is NOT started
            LOG.debug( "Starting persistence if necessary..." );
            persistenceService.startPersistence();
            chain.doFilter( request, response );
        }
    }

    @Override
    public void destroy()
    {
        LOG.debug( "SetupVerifyFilter destroyed ..." );
        // add code to release any resource
    }
}
