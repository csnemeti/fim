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
        AuthenticatedUserDTO userDTO = null;
        if ( session != null )
        {
            userDTO = (AuthenticatedUserDTO) session.getAttribute( USER_DTO_KEY );
        }
        return userDTO;
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

    /**
     * Verifies if logged in user has the given {@link Permission}.
     * 
     * @param permission the permission to check
     * @param session the request session where logged in user should be found
     * @return true if user is logged in and has the permission, false otherwise
     */
    public static boolean hasPermission( Permission permission, HttpSession session )
    {
        boolean hasPermission = false;
        AuthenticatedUserDTO userDTO = getUserFromSession( session );
        if ( userDTO != null )
        {
            hasPermission = userDTO.hasPermission( null, permission );
        }
        return hasPermission;
    }
}
