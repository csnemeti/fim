/**
 * 
 */
package pfa.alliance.fimsolr.web.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
    private Filter solrFilter = null;

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
        ServletContext context = filterConfig.getServletContext();
        filterBasePath = context.getContextPath() + "/fim-solr/";
        // set Solr home
        System.setProperty( "solr.solr.home", context.getRealPath( "/WEB-INF/multicore/" ) );
        LOG.debug( "Filter initialized" );
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        if ( request instanceof HttpServletRequest && response instanceof HttpServletResponse )
        {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if ( isForThisFilter( httpRequest ) )
            {
                LOG.debug( "Received request for this filter: {}, queryString = {}", httpRequest.getRequestURI(),
                           httpRequest.getQueryString() );
                processLocalRequest( httpRequest, httpResponse );
            }
            else
            {
                processSolrRequest( httpRequest, httpResponse, chain );
            }
        }
        else
        {
            PrintWriter writer = response.getWriter();
            writer.append( "Error: wrong request / response type!" );
        }
    }

    /**
     * Check if the call is for this filter.
     * 
     * @param request the current request
     * @return true if the call is for this filter, false otherwise
     */
    private boolean isForThisFilter( HttpServletRequest request )
    {
        boolean forThis = false;
        String pathInfo = request.getRequestURI();
        forThis = pathInfo != null && pathInfo.startsWith( filterBasePath );
        return forThis;
    }

    /**
     * Process the request for this filter.
     * 
     * @param request the current HTTP request
     * @param response the response
     * @throws IOException in case response could not be written
     * @throws ServletException in case filter has some problem
     */
    private void processLocalRequest( HttpServletRequest request, HttpServletResponse response )
        throws IOException, ServletException
    {
        String pathInfo = request.getRequestURI();
        String command = pathInfo.substring( filterBasePath.length() );
        LOG.debug( "Local request: command = {}, pathInfo = {}, queryString = {}, user-agent = {}", command, pathInfo,
                   request.getQueryString(), request.getHeader( "User-Agent" ) );
        switch ( command )
        {
            case "start":
            case "start/":
                start( request, response );
                break;
            case "stop":
            case "stop/":
                stop( response );
                break;
            case "initdb":
            case "initdb/":
                dbInit( request, response );
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
    private void processSolrRequest( HttpServletRequest request, HttpServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        if ( solrFilter != null )
        {
            solrFilter.doFilter( request, response, chain );
        }
        else
        {
            response.sendError( HttpServletResponse.SC_CONFLICT,
                                "Solr is not started! You must start solr first in order to make calls." );
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
    private void dbInit( HttpServletRequest httpRequest, HttpServletResponse response )
        throws IOException
    {
        if ( !dbInitComplete )
        {
            // get request parameter
            String driver = httpRequest.getParameter( "driver" );
            if ( StringUtils.isBlank( driver ) )
            {
                LOG.warn( "JDBC Driver class is not specified" );
                response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Driver class is missing" );
            }
            String url = httpRequest.getParameter( "url" );
            if ( StringUtils.isBlank( url ) )
            {
                LOG.warn( "JDBC URL is not specified" );
                response.sendError( HttpServletResponse.SC_BAD_REQUEST, "DB URL is missing" );
            }
            String username = httpRequest.getParameter( "username" );
            if ( StringUtils.isBlank( username ) )
            {
                LOG.warn( "JDBC username is not specified" );
                response.sendError( HttpServletResponse.SC_BAD_REQUEST, "DB username is missing" );
            }
            String password = httpRequest.getParameter( "password" );
            configureDb( driver, url, username, password );
            LOG.info( "Solr init completed" );
            sendResponse( ResultStatus.OK, "Solr init complete", response );
            dbInitComplete = true;
        }
        else
        {
            LOG.warn( "DB init already made!" );
            sendResponse( ResultStatus.WARNING, "Initialization already made", response );
        }
    }

    /**
     * Configure the DB.
     * 
     * @param driver the driver class
     * @param url the JDBC URL
     * @param username the user name to DB
     * @param password the password to DB
     * @throws IOException if I/O error occurs
     */
    private void configureDb( final String driver, final String url, final String username, final String password )
        throws IOException
    {
        LOG.debug( "DB parameters: driver = {}, url = {}, username = {}, passweord = {}", driver, url, username,
                   password );
        String[] fileLocations =
            new String[] { "WEB-INF/multicore/users/conf/", "WEB-INF/multicore/active_users/conf/" };
        for ( String location : fileLocations )
        {
            String templateContent = readTemplateFromLocation( location );
            String configuration = replaceVariables( templateContent, driver, url, username, password );
            writeConfiguration( location, configuration );
        }
    }

    /**
     * Read the template content.
     * 
     * @param location the directory where the template file should be
     * @return the template file content
     * @throws IOException if an I/O error encounted of file is not found
     */
    private String readTemplateFromLocation( final String location )
        throws IOException
    {
        String file = ( ( location.endsWith( "/" ) ) ? location : location + "/" ) + "db-data-config-template.xml";
        String filePath = filterConfig.getServletContext().getRealPath( file );

        try (FileReader reader = new FileReader( filePath ))
        {
            char[] content = new char[16384];
            int length = reader.read( content );
            return new String( content, 0, length );
        }
    }

    /**
     * Replaces the variables from the template content.
     * 
     * @param templateContent the template content
     * @param driver the JDBC Driver class name
     * @param url the JDBC URL
     * @param username the JDBC user name
     * @param password the JDBC password
     * @return the content with modified variable (in fact the configuration file)
     */
    private static String replaceVariables( final String templateContent, final String driver, final String url,
                                            final String username, final String password )
    {
        String content = templateContent.replace( "${jdbc.driver}", driver );
        content = content.replace( "${jdbc.url}", url );
        content = content.replace( "${jdbc.username}", username );
        String passwordData = ( StringUtils.isBlank( password ) ) ? "" : password;
        content = content.replace( "${jdbc.password}", passwordData );
        return content;
    }

    private void writeConfiguration( final String location, final String config )
        throws IOException
    {
        String relativeFilePath = ( ( location.endsWith( "/" ) ) ? location : location + "/" ) + "db-data-config.xml";
        String fullFilePath = filterConfig.getServletContext().getRealPath( relativeFilePath );
        File file = new File( fullFilePath );
        if ( file.isFile() )
        {
            if ( !file.delete() )
            {
                throw new IOException( "Could not delete file: " + fullFilePath );
            }
        }
        try (FileOutputStream writer = new FileOutputStream( file ))
        {
            byte[] content = config.getBytes( StandardCharsets.UTF_8 );
            writer.write( content );
        }
    }

    /**
     * Starts the filter.
     * 
     * @param httpRequest the original request
     * @param response the response object
     */
    private void start( HttpServletRequest httpRequest, HttpServletResponse response )
        throws IOException
    {
        if ( solrFilter == null )
        {
            if ( dbInitComplete )
            {
                solrFilter = createSolrDispatchFilter();
                try
                {
                    solrFilter.init( filterConfig );
                    LOG.info( "Solr server started" );
                    sendResponse( ResultStatus.OK, "Solr server started", response );
                }
                catch ( Exception e )
                {
                    solrFilter = null;
                    LOG.error( "Error while trying to initialize Solr Filter", e );
                    sendResponse( ResultStatus.ERROR, "Error encounted while starting solr server.", response );
                }
            }
            else
            {
                LOG.error( "Could not start solr server, DB initialization is not made." );
                sendResponse( ResultStatus.ERROR, "Solr server is not initialized! DB connections is NOT set.",
                              response );
            }
        }
        else
        {
            LOG.warn( "Trying to start Solr. Solr server is already started." );
            sendResponse( ResultStatus.WARNING, "Solr server is already running.", response );
        }
    }

    /**
     * This method is called in order to create a {@link SolrDispatchFilter} instance.
     * 
     * @return the created instance
     */
    protected Filter createSolrDispatchFilter()
    {
        return new SolrDispatchFilter();
    }

    /**
     * Stops the filter.
     * 
     * @param response the response object (could be null)
     */
    private void stop( HttpServletResponse response )
        throws IOException
    {
        if ( solrFilter != null )
        {
            Filter filter = this.solrFilter;
            this.solrFilter = null;
            filter.destroy();
            LOG.info( "Solr server stopped" );
            sendResponse( ResultStatus.OK, "Solr server stopped", response );
        }
        else
        {
            LOG.warn( "Trying to stop Solr. Solr server was not started." );
            sendResponse( ResultStatus.WARNING, "Solr server was not started", response );
        }
    }

    /**
     * Method called to return a JSon response in the page.
     * 
     * @param status the response status value
     * @param message the response message
     * @param response the response object
     * @throws IOException if an I/O error occurs
     */
    private static void sendResponse( ResultStatus status, String message, HttpServletResponse response )
        throws IOException
    {
        if ( response != null )
        {
            StringBuilder sb = new StringBuilder( "{\n\t\"status\" : \"" );
            sb.append( status ).append( "\",\n\t\"message\" : \"" ).append( message ).append( "\"\n}" );

            response.setCharacterEncoding( "UTF-8" );
            response.setContentLength( sb.length() );
            response.setContentType( "application/json" );
            response.getWriter().append( sb );
        }
    }

    private static enum ResultStatus
    {
        /** Operation executed successfully. */
        OK,
        /**
         * Operation executed but encounter some problems on the way OR operation not executed because it was not
         * necessary.
         */
        WARNING,
        /** Operation not executed or failed to execute. */
        ERROR;
    }
}
