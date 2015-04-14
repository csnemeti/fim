package pfa.alliance.fim.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sets the character encoding to desired one (usually is UTF-8).
 */
public class SetCharacterEncodingFilter
    implements Filter
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( SetCharacterEncodingFilter.class );

    /**
     * The default character encoding to set for requests that pass through this filter.
     */
    private String encoding = null;

    /**
     * Select and set (if specified) the character encoding to be used to interpret request parameters for this request.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {

        if ( encoding != null )
        {
            request.setCharacterEncoding( encoding );
        }

        // Pass control on to the next filter
        chain.doFilter( request, response );
    }

    /**
     * Select an appropriate character encoding to be used, based on the characteristics of the current request and/or
     * filter initialization parameters. If no character encoding should be set, return <code>null</code>.
     * <p>
     * The default implementation unconditionally returns the value configured by the <strong>encoding</strong>
     * initialization parameter for this filter.
     *
     * @param request The servlet request we are processing
     */
    protected String selectEncoding( ServletRequest request )
    {
        return this.encoding;
    }

    @Override
    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        String characterEncoding = filterConfig.getInitParameter( "encoding" );
        if ( StringUtils.isNotBlank( characterEncoding ) )
        {
            this.encoding = characterEncoding;
        }
        LOG.info( "Character encoding set to: {}", characterEncoding );
    }

    @Override
    public void destroy()
    {
        // nothing to do
    }

    public void setEncoding( String encoding )
    {
        this.encoding = encoding;
    }

    public String getEncoding()
    {
        return encoding;
    }
}
