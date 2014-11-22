/**
 * 
 */
package pfa.alliance.fim.web.stripes.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
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
            else if ( arePrivilegesSatisfied( securityAnnotation, context.getActionBean(), userDTO ) )
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
     * @param action the ActionBean where the security annotation was found
     * @param userDTO the suer
     * @return true if privileges are satisfied
     */
    private boolean arePrivilegesSatisfied( final FimSecurity securityAnnotation, final ActionBean action,
                                            final AuthenticatedUserDTO userDTO )
    {
        return checkIfAll( securityAnnotation.checkIfAll(), action, userDTO )
            && checkIfAny( securityAnnotation.checkIfAll(), action, userDTO );

    }

    /**
     * Check is ALL {@link Permission}s specified are present in {@link AuthenticatedUserDTO}.
     * 
     * @param permissions the {@link Permission}s the user must have. It might be empty.
     * @param action the {@link ActionBean} that requests security constraints, it may have additional information that
     *            may help in permission checking
     * @param userDTO the user with it's {@link Permission}s
     * @return true if there are no {@link Permission}s to check OR in case {@link Permission}s are satisfied, false in
     *         all other cases
     */
    private boolean checkIfAll( final Permission[] permissions, final ActionBean action,
                                final AuthenticatedUserDTO userDTO )
    {
        if ( permissions != null && permissions.length > 0 )
        {
            for ( Permission permission : permissions )
            {
                if ( !doesUserHavePermission( permission, action, userDTO ) )
                {
                    LOG.info( "User {} rejected from ActionBean {} because it doesn't have permission: {}", userDTO,
                              action, permission );
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check is ANY {@link Permission} specified is present in {@link AuthenticatedUserDTO}.
     * 
     * @param permissions the {@link Permission}s from which user must have at least one. It might be empty.
     * @param action the {@link ActionBean} that requests security constraints, it may have additional information that
     *            may help in permission checking
     * @param userDTO the user with it's {@link Permission}s
     * @return true if there are no {@link Permission}s to check OR in case one {@link Permission} is found, false in
     *         all other cases
     */
    private boolean checkIfAny( final Permission[] permissions, final ActionBean action,
                                final AuthenticatedUserDTO userDTO )
    {
        if ( permissions != null && permissions.length > 0 )
        {
            for ( Permission permission : permissions )
            {
                if ( doesUserHavePermission( permission, action, userDTO ) )
                {
                    return true;
                }
            }
            // if arrive here, no permission was found
            LOG.info( "User {} rejected from ActionBean {} because it doesn't have ANY permission from: {}", userDTO,
                      action, Arrays.toString( permissions ) );
            return false;
        }
        return true;
    }

    /**
     * Check if the user has a {@link Permission}.
     * 
     * @param permission the {@link Permission} to verify
     * @param action the {@link ActionBean} that requests security constraints, it may have additional information that
     *            may help in permission checking
     * @param userDTO the user with it's {@link Permission}s
     * @return true if {@link Permission} is found, false otherwise
     */
    private boolean doesUserHavePermission( final Permission permission, final ActionBean action,
                                            final AuthenticatedUserDTO userDTO )
    {
        return true;
    }

    /**
     * Handle the case when user is not authenticated.
     * 
     * @param context request context
     * @return redirect to login page
     */
    private Resolution handleUserNotAuthenticated( ExecutionContext context )
    {
        return new RedirectResolution( FimPageURLs.USER_LOGIN_PAGE.getURL() );
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
