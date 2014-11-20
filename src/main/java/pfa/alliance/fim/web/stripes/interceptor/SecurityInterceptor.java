/**
 * 
 */
package pfa.alliance.fim.web.stripes.interceptor;

import java.lang.reflect.Method;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.SecurityUtil;

/**
 * This class will be responsible for verifying if the user is authenticated and if it has proper rights to execute an
 * operation.
 * 
 * @author Csaba
 */
@Intercepts( { LifecycleStage.ActionBeanResolution, LifecycleStage.HandlerResolution } )
public class SecurityInterceptor
    implements Interceptor
{
    private static final Logger LOG = LoggerFactory.getLogger( SecurityInterceptor.class );

    @Override
    public Resolution intercept( ExecutionContext context )
        throws Exception
    {
        // this will perform the desired operation (depending on lifecycle)
        Resolution resolution = context.proceed();
        // now we know the what ActionBean is involved
        FimSecurity securityAnnotation = getFimSecurity( context );
        if ( securityAnnotation != null )
        {
            // security constraints are present, we must have an authenticated user
            AuthenticatedUserDTO userDTO = getAuthenticatedUser( context.getActionBeanContext() );
            if ( userDTO == null )
            { // we hit a page that requires an authenticated user and we have no user logged in
                LOG.info( "A page was found that requires authenticated user, redirecting user to login..." );
                resolution = handleUserNotAuthenticated( context );
            }
            else if ( privilegesAreSatisfied( securityAnnotation, userDTO ) )
            {
                // everything is OK, you may proceed
                LOG.debug( "Found security constraints, and they are satisfied by the logged in user role" );
            }
            else
            {
                // there's a logged in user but doesn't have the required permission(s), redirect to access denied
                LOG.info( "Insufficient privileges were found for user, redirect to access deny page" );
                resolution = handleAccessDenied( context );
            }
        }
        return resolution;
    }

    /**
     * Gets the annotation responsible for security constraints.
     * 
     * @param context the execution context
     * @return the annotation if found, null if not found
     */
    private static FimSecurity getFimSecurity( ExecutionContext context )
    {
        FimSecurity result = null;
        // we search first the annotation on method from ActionBean
        Method method = context.getHandler();
        if ( method != null )
        {
            result = method.getAnnotation( FimSecurity.class );
        }
        // if not found any annotation or method is not defined yet or invalid
        if ( result == null )
        {
            ActionBean action = context.getActionBean();
            if ( action != null )
            {
                result = action.getClass().getAnnotation( FimSecurity.class );
            }
        }
        return result;
    }

    /**
     * Gets the authenticated user for current request.
     * 
     * @param context the request processing context
     * @return the authenticated user or null if not found
     */
    private AuthenticatedUserDTO getAuthenticatedUser( ActionBeanContext context )
    {
        return SecurityUtil.getUserFromSession( context.getRequest().getSession() );
    }

    /**
     * Check if requested permissions are satisfied by current user permissions.
     * 
     * @param securityAnnotation the security constraints
     * @param userDTO the suer
     * @return true if privileges are satisfied
     */
    private boolean privilegesAreSatisfied( final FimSecurity securityAnnotation, final AuthenticatedUserDTO userDTO )
    {
        return false;
    }

    /**
     * Handle the case when user is not authenticated.
     * 
     * @param context request context
     * @return redirect to login page
     */
    private Resolution handleUserNotAuthenticated( ExecutionContext context )
    {
        return null;
    }

    /**
     * Handle the case when used has not enough privileges.
     * 
     * @param context request content
     * @return redirect to access denied page
     */
    private Resolution handleAccessDenied( ExecutionContext context )
    {
        return null;
    }
}
