/**
 * 
 */
package pfa.alliance.fim.web.security;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is an utility class used by / for security issues.
 * 
 * @author Nemeti
 */
public final class SecurityUtil
{
    private static final Logger LOG = LoggerFactory.getLogger( SecurityUtil.class );

    /** The key name that is used for storing user on the session. */
    private final static String USER_DTO_KEY = "USER_DTO";

    /**
     * Hidden constructor for utility class.
     */
    private SecurityUtil()
        throws IllegalAccessException
    {
        throw new IllegalAccessException( "utility classes do not require instance" );
    }

    /**
     * Gets the user from session.
     * 
     * @param session the session
     * @return the found user or null if session does not have a user
     */
    public static AuthenticatedUserDTO getUserFromSession( HttpSession session )
    {
        return (AuthenticatedUserDTO) session.getAttribute( USER_DTO_KEY );
    }

    /**
     * Checks if there is an authenticated user in the session.
     * 
     * @param session the session
     * @return true if there is an authenticated user in the session
     */
    public static boolean isAuthenticated( HttpSession session )
    {
        return getUserFromSession( session ) != null;
    }

    /**
     * Place the given user into session.
     * 
     * @param user the user to add, null means not authenticated
     * @param session the session where user should be added
     */
    public static void putUserIntoSession( AuthenticatedUserDTO user, HttpSession session )
    {
        LOG.debug( "Putting in session user: {}", user );
        session.setAttribute( USER_DTO_KEY, user );
    }
}
