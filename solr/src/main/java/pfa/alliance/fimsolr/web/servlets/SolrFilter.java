/**
 * 
 */
package pfa.alliance.fimsolr.web.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.servlet.SolrDispatchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class decorates the {@link SolrDispatchFilter} in order to control it when to start = stop.
 * 
 * @author Csaba
 */
public class SolrFilter
    implements Filter
{
    private static final Logger LOG = LoggerFactory.getLogger( SolrFilter.class );

    /** The real solr filter. */
    private SolrDispatchFilter solrFilter = null;

    /** The filter original configuration. */
    private FilterConfig filterConfig;

    /** Path used for listening for commands by THIS filter. */
    private String filterBasePath;

    /** Flag telling us if DB was initialized. */
    private boolean dbInitComplete = false;

    @Override
    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        this.filterConfig = filterConfig;
        filterBasePath = filterConfig.getServletContext().getContextPath() + "/fim-solr/";
        LOG.debug( "Filter initialized" );
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        if ( isForThisFilter( request ) )
        {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            LOG.debug( "Received request for this filter: {}, queryString = {}", httpRequest.getRequestURI(),
                       httpRequest.getQueryString() );
            processLocalRequest( httpRequest, response );
        }
        else
        {
            processSolrRequest( request, response, chain );
        }
    }

    /**
     * Check if the call is for this filter.
     * 
     * @param request the current request
     * @return true if the call is for this filter, false otherwise
     */
    private boolean isForThisFilter( ServletRequest request )
    {
        boolean forThis = false;
        if ( request instanceof HttpServletRequest )
        {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String pathInfo = httpRequest.getRequestURI();
            forThis = pathInfo != null && pathInfo.startsWith( filterBasePath );
        }
        return forThis;
    }

    /**
     * Process the request for this filter.
     * 
     * @param httpRequest the current HTTP request
     * @param response the response
     * @throws IOException in case response could not be written
     * @throws ServletException in case filter has some problem
     */
    private void processLocalRequest( HttpServletRequest httpRequest, ServletResponse response )
        throws IOException, ServletException
    {
        String pathInfo = httpRequest.getRequestURI();
        String command = pathInfo.substring( filterBasePath.length() );
        switch ( command )
        {
            case "/start":
            case "/start/":
                start( httpRequest, response );
                break;
            case "/stop":
            case "/stop/":
                stop( response );
                break;
            case "/initdb":
            case "/initdb/":
                dbInit( httpRequest, response );
                break;
        }
    }

    /**
     * Forwards the call to SOLR filter or provide an error response if filter is not ready to receive calls.
     * 
     * @param request the current request
     * @param response the response
     * @throws IOException in case response could not be written
     * @throws ServletException in case filter has some problem
     */
    private void processSolrRequest( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        if ( solrFilter != null )
        {
            solrFilter.doFilter( request, response, chain );
        }
        else
        {

        }
    }

    @Override
    public void destroy()
    {
        try
        {
            stop( null );
        }
        catch ( IOException e )
        {
            // this should not be thrown here since we do not provide an output where to write response
            LOG.warn( "Error occured while trying to destroy filter", e );
        }
    }

    /**
     * Initialize database for solr.
     * 
     * @param httpRequest the original request
     * @param response the response object
     */
    private void dbInit( HttpServletRequest httpRequest, ServletResponse response )
        throws IOException
    {
        if ( !dbInitComplete )
        {
            // TODO init
            // TODO update dbInitComplete
        }
        else
        {
            // TODO ERR: already init complete
        }
    }

    /**
     * Starts the filter.
     * 
     * @param httpRequest the original request
     * @param response the response object
     */
    private void start( HttpServletRequest httpRequest, ServletResponse response )
        throws IOException
    {
        if ( solrFilter == null )
        {
            if ( dbInitComplete )
            {
                // TODO start it
            }
            else
            {
                // TODO ERR: cannot start, init not complete
            }
        }
        else
        {
            // TODO ERR: already started
        }
    }

    /**
     * Stops the filter.
     * 
     * @param response the response object (could be null)
     */
    private void stop( ServletResponse response )
        throws IOException
    {
        if ( solrFilter != null )
        {
            SolrDispatchFilter filter = this.solrFilter;
            this.solrFilter = null;
            filter.destroy();
        }
        else
        {
            // TODO ERR: not started
        }
    }
}
