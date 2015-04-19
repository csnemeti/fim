/**
 * 
 */
package pfa.alliance.fim.web.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.LoggedInUserDTO;
import pfa.alliance.fim.service.UserManagerService;

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
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        String token = request.getParameter( "token" );
        if ( request instanceof HttpServletRequest && StringUtils.isNotBlank( token ) )
        {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            processToken( token, httpRequest, response, chain );
        }
        chain.doFilter( request, response );
    }

    private void processToken( final String token, HttpServletRequest httpRequest, ServletResponse response,
                               FilterChain chain )
    {
        LOG.debug( "Processing token: {}", token );
        LoggedInUserDTO userDTO = userManagerService.login( token );
    }

    @Override
    public void destroy()
    {
        // nothing to do here
    }
}
