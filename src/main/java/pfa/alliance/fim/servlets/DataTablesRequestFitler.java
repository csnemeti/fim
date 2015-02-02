/**
 * 
 */
package pfa.alliance.fim.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a special filter that applies for all requests that come from DataTables. This component sends lots of
 * information back to server (some of it might be useless in simplest occasion) but some of these parameters are index
 * based. Here is such an example request:
 * 
 * <pre>
 * columns[0][data]    
 * columns[0][name]    
 * columns[0][orderable]   false
 * columns[0][search][regex]   false
 * columns[0][search][value]   
 * columns[0][searchable]  true
 * columns[1][data]    
 * columns[1][name]    
 * columns[1][orderable]   false
 * columns[1][search][regex]   false
 * columns[1][search][value]   
 * columns[1][searchable]  true
 * columns[2][data]    firstName
 * columns[2][name]    
 * columns[2][orderable]   true
 * columns[2][search][regex]   false
 * columns[2][search][value]   
 * columns[2][searchable]  true
 * columns[3][data]    lastName
 * columns[3][name]    
 * columns[3][orderable]   true
 * columns[3][search][regex]   false
 * columns[3][search][value]   
 * columns[3][searchable]  true
 * columns[4][data]    email
 * columns[4][name]    
 * columns[4][orderable]   true
 * columns[4][search][regex]   false
 * columns[4][search][value]   
 * columns[4][searchable]  true
 * columns[5][data]    
 * columns[5][name]    
 * columns[5][orderable]   false
 * columns[5][search][regex]   false
 * columns[5][search][value]   
 * columns[5][searchable]  true
 * columns[6][data]    
 * columns[6][name]    
 * columns[6][orderable]   false
 * columns[6][search][regex]   false
 * columns[6][search][value]   
 * columns[6][searchable]  true
 * draw    1
 * length  1
 * order[0][column]    3
 * order[0][dir]   asc
 * search[regex]   false
 * search[value]   
 * start   0
 * </pre>
 * 
 * In the given case, columns[...]... is useless parameter while order[...].. is important. Give the fact that Stripes
 * disabled index based parameter accessing in order to ensure that validators are working OK on them (
 * https://stripesframework.atlassian.net/browse/STS-841 ) we'll convert these from property[index][keyword] into
 * propertyindex.keyword. Example: <code>order[0][column] -&gt; order0.column, order[0][dir] -&gt; order0.dir </code>
 * 
 * @author Csaba
 */
public class DataTablesRequestFitler
    implements Filter
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( DataTablesRequestFitler.class );

    /** Flag indicating if columns parameters should be "transfered" into new request. */
    private boolean transferColumnsParams = true;

    @Override
    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        String transferColumnsParamsValue = filterConfig.getInitParameter( "transferColumnsParams" );
        if ( StringUtils.isNotBlank( transferColumnsParamsValue ) )
        {
            transferColumnsParams = !"false".equalsIgnoreCase( transferColumnsParamsValue );
        }
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        ServletRequest currentRequest;
        if ( shouldPerformFilter( request ) )
        {
            LOG.debug( "Received incomming request... APPLY FILTERING" );
            currentRequest = decorateCurrentRequest( request );
        }
        else
        {
            LOG.debug( "Received incomming request... SKIP FILTERING" );
            currentRequest = request;
        }
        chain.doFilter( currentRequest, response );
    }

    @Override
    public void destroy()
    {
        // nothing to do here
    }

    /**
     * This method is called in order to verify if datatables.net request parameters should be filtered.
     * 
     * @param request the original request
     * @return true if original request contains a parameter: <code>filterDataTablesCall=true</code>
     */
    private static boolean shouldPerformFilter( ServletRequest request )
    {
        String filterDataTablesCallValue = request.getParameter( "filterDataTablesCall" );
        return "true".equalsIgnoreCase( filterDataTablesCallValue );
    }

    /**
     * Decorate current request in order to "fix" current parameters (make them stripes friendly).
     * 
     * @param request the current request
     * @return the decorated request
     */
    private ServletRequest decorateCurrentRequest( ServletRequest request )
    {
        if ( request instanceof HttpServletRequest )
        {
            return new DecoratedRequest( (HttpServletRequest) request, transferColumnsParams );
        }
        else
        {
            return request;
        }

    }

    /**
     * Request wrapper that "fix" requests parameters that stripes doesn't like.
     */
    private static class DecoratedRequest
        extends HttpServletRequestWrapper
    {
        /** This keeps the parameters to return. */
        private final Map<String, String[]> params;

        private static final Pattern SEARCH_PATTERN = Pattern.compile( "search\\[\\w+\\]" );

        private static final Pattern ORDER_PATTERN = Pattern.compile( "order\\[\\d+\\]\\[\\w+\\]" );

        private static final Pattern COLUMN_PATTERN = Pattern.compile( "columns\\[\\d+\\]\\[\\w+\\].*" );

        public DecoratedRequest( HttpServletRequest request, final boolean transferColumnsParams )
        {
            super( request );
            Map<String, String[]> requestParams =
                processRequestParameters( request.getParameterMap(), transferColumnsParams );
            params = Collections.unmodifiableMap( requestParams );
        }

        @Override
        public Map<String, String[]> getParameterMap()
        {
            return params;
        }

        @Override
        public Enumeration<String> getParameterNames()
        {
            return Collections.enumeration( params.keySet() );
        }

        private static Map<String, String[]> processRequestParameters( Map<String, String[]> params,
                                                                       final boolean transferColumnsParams )
        {
            Map<String, String[]> requestParams = new TreeMap<>();
            for ( Map.Entry<String, String[]> param : params.entrySet() )
            {
                String paramName = param.getKey();
                if ( SEARCH_PATTERN.matcher( paramName ).matches() )
                {
                    // do nothing with this, we will not forward this yet
                }
                else if ( ORDER_PATTERN.matcher( paramName ).matches() )
                {
                    requestParams.put( getConvertedName( paramName ), param.getValue() );
                }
                else if ( COLUMN_PATTERN.matcher( paramName ).matches() )
                {
                    if ( transferColumnsParams )
                    {
                        requestParams.put( getConvertedName( paramName ), param.getValue() );
                    }
                }
                else
                {
                    requestParams.put( paramName, param.getValue() );
                }
            }
            return requestParams;
        }

        private static String getConvertedName( final String originalName )
        {
            String result = null;
            int firstPosition = originalName.indexOf( '[' );
            if ( firstPosition < 0 )
            {
                result = originalName;
            }
            else
            {
                int lastPosition = originalName.indexOf( ']', firstPosition );
                if ( lastPosition < 0 )
                {
                    LOG.warn( "Stupid parameter received: {}", originalName );
                    result = originalName;
                }
                else
                {
                    String begin = ( firstPosition > 0 ) ? originalName.substring( 0, firstPosition ) : "";
                    String content = originalName.substring( firstPosition + 1, lastPosition );
                    lastPosition++;
                    String remaining =
                        ( lastPosition == originalName.length() ) ? "" : originalName.substring( lastPosition );
                    try
                    {
                        // if content is a number we join the two parts
                        Integer.parseInt( content );
                        result = begin + content + getConvertedName( remaining );
                    }
                    catch ( RuntimeException e )
                    {
                        result = begin + "." + content + getConvertedName( remaining );
                    }
                }
            }
            return result;
        }
    }
}
