/**
 * 
 */
package pfa.alliance.fim.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import pfa.alliance.fim.web.util.LocaleUtils;

/**
 * This class is used for changing {@link Locale} on current session.
 * 
 * @author Csaba
 */
public class LocaleChangeFilter
    implements Filter
{
    /** The list of available locale codes. */
    private static final Set<String> AVAILABLE_LOCALES =
        Collections.unmodifiableSet( new HashSet<>( Arrays.asList( "en", "ro" ) ) );

    @Override
    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        // nothing to do here
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        Locale locale = getLocaleFromRequest( request );
        if ( locale != null )
        {
            LocaleUtils.setLocaleOnSession( request, locale );
        }

        // continue request processing
        chain.doFilter( request, response );
    }

    /**
     * Extracts a {@link Locale} from request. In order to do that it searches for a lang parameter and checks if it's
     * value is in accepted values.
     * 
     * @param request the request that may contain the lang parameter
     * @return the built {@link Locale} or null if parameter is missing, has empty string value or its value is not
     *         accepted
     */
    private Locale getLocaleFromRequest( ServletRequest request )
    {
        Locale locale = null;
        String language = request.getParameter( "lang" );
        if ( StringUtils.isNotEmpty( language ) && AVAILABLE_LOCALES.contains( language ) )
        {
            locale = new Locale( language );
        }
        return locale;
    }

    @Override
    public void destroy()
    {
        // nothing to do here
    }

}
