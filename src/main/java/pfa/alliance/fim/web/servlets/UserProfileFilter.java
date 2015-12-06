/**
 * 
 */
package pfa.alliance.fim.web.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.LoggedInUserDTO;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.util.LoginUtil;

/**
 * This filter is used for monitor tokens in User profile call and load the corresponding user or redirect to an error
 * page if user is not available, disabled, etc.
 * 
 * @author Csaba
 */
@Singleton
public class UserProfileFilter
    implements Filter
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserProfileFilter.class );

    /** The user management service to use. */
    private UserManagerService userManagerService;

    @Inject
    public UserProfileFilter( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
    }

    @Override
    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        // nothing to do here
    }

    @Override
    public void destroy()
    {
        // nothing to do here
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        String token = request.getParameter( "token" );
        ServletRequest newRequest;
        if ( request instanceof HttpServletRequest && StringUtils.isNotBlank( token ) )
        {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            newRequest = processToken( token, httpRequest );
        }
        else
        {
            newRequest = request;
        }
        chain.doFilter( newRequest, response );
    }

    /**
     * Process the token from request.
     * 
     * @param token the token
     * @param httpRequest the request
     * @return the wrapped request
     */
    private HttpServletRequest processToken( final String token, HttpServletRequest httpRequest )
    {
        LOG.debug( "Processing token: {}", token );
        LoggedInUserDTO userDTO = userManagerService.login( token );
        User user = userDTO.getUser();
        if ( user != null )
        {
            setUserOnSession( userDTO, httpRequest );
        }
        else
        {
            LOG.info( "Token {} is invalid or expired", token );
            // TODO we need to thrown an exception here
        }
        return parseRequestAndEmilinateParams( httpRequest );
    }

    /**
     * Sets the user on the session.
     * 
     * @param user the user to set on session
     */
    private void setUserOnSession( LoggedInUserDTO userDTO, HttpServletRequest httpRequest )
    {
        User user = userDTO.getUser();
        Timestamp lastLogin = LoginUtil.getUserLastLogin( user.getLogins() );
        AuthenticatedUserDTO authenticatedUserDTO =
            new AuthenticatedUserDTO( user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                                      user.getLogin(), lastLogin, LoginUtil.convertPermissions( userDTO ) );
        LOG.debug( "Adding on session: {}", authenticatedUserDTO );
        HttpSession session = httpRequest.getSession( true );
        SecurityUtil.putUserIntoSession( authenticatedUserDTO, session );
        session.setAttribute( "showOnlyNewPassword", true );
    }

    /**
     * Parse request and eliminate some parameters.
     * 
     * @param request the request to parse
     * @return a new request without some parameters
     */
    private static HttpServletRequest parseRequestAndEmilinateParams( HttpServletRequest request )
    {
        // Prepare the new request parameter map
        Map<String, String[]> newParameterMap = new HashMap<String, String[]>();
        for ( Map.Entry<String, String[]> parameterMap : request.getParameterMap().entrySet() )
        {
            String paramName = parameterMap.getKey();
            if ( paramName.equalsIgnoreCase( "token" ) )
            {
                newParameterMap.put( paramName, parameterMap.getValue() );
            }
        }

        // Wrap the request with the parameter map which we just created and return it.
        return wrapRequest( request, newParameterMap );
    }

    /**
     * Wrap the given HttpServletRequest with the given parameterMap.
     * 
     * @param request The HttpServletRequest of which the given parameterMap have to be wrapped in.
     * @param parameterMap The parameterMap to be wrapped in the given HttpServletRequest.
     * @return The HttpServletRequest with the parameterMap wrapped in.
     */
    private static HttpServletRequest wrapRequest( HttpServletRequest request, final Map<String, String[]> parameterMap )
    {
        return new HttpServletRequestWrapper( request )
        {
            @Override
            public Map<String, String[]> getParameterMap()
            {
                return parameterMap;
            }

            @Override
            public String[] getParameterValues( String name )
            {
                return parameterMap.get( name );
            }

            @Override
            public String getParameter( String name )
            {
                String[] params = getParameterValues( name );
                return params != null && params.length > 0 ? params[0] : null;
            }

            @Override
            public Enumeration<String> getParameterNames()
            {
                return Collections.enumeration( parameterMap.keySet() );
            }
        };
    }
}
