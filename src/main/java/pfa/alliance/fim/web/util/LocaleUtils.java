/**
 * 
 */
package pfa.alliance.fim.web.util;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This is an utility class used for storing and reading locale on session.
 * 
 * @author Csaba
 */
public final class LocaleUtils
{
    /** The attribute name used to store on session desired Locale. */
    private static final String LOCALE = "pfa.alliance.fim.web.LOCALE";

    /**
     * Gets the {@link Locale} from the {@link HttpSession} mapped to this request.
     * 
     * @param request the request that might have Locale set on session
     * @return the {@link Locale} from session or null if session or locale on session is not found
     */
    public static Locale getLocaleFrom( HttpServletRequest request )
    {
        Locale locale = null;
        HttpSession session = request.getSession();
        if ( session != null )
        {
            locale = (Locale) session.getAttribute( LOCALE );
        }
        return locale;
    }

    /**
     * Method used for setting {@link Locale} on session.
     * 
     * @param request the request
     * @param locale the new locale to set
     */
    public static void setLocaleOnSession( ServletRequest request, Locale locale )
    {
        if ( request instanceof HttpServletRequest )
        {
            HttpSession session = ( (HttpServletRequest) request ).getSession( true );
            session.setAttribute( LOCALE, locale );
        }
    }
}
