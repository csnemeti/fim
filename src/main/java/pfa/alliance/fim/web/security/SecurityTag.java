/**
 * 
 */
package pfa.alliance.fim.web.security;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a custom tag developed for easier security checking.
 * 
 * @author Csaba
 */
public class SecurityTag
    extends TagSupport
{
    private static final long serialVersionUID = 7027039750740242135L;

    private static final Logger LOG = LoggerFactory.getLogger( SecurityTag.class );

    /** List of permissions to check. */
    private final List<Permission> permissions = new ArrayList<>();

    /** Flag indicating a {@link #setCheckIfAny(String)} method call was made. */
    private boolean checkForAny = false;

    /**
     * Method called for providing the parameters in case of an check if ANY of the given {@link Permission}s the
     * current user has.
     * 
     * @param permissions the comma separated permission to check. Content will be displayed if currently logged in user
     *            has at least one of the given {@link Permission}s
     */
    public void setCheckIfAny( String permissions )
    {
        checkForAny = true;
        storePermissionsFrom( permissions );
        LOG.debug( "setCheckIfAny({})", this.permissions );
    }

    /**
     * Method called for providing the parameters in case of an check if ALL of the given {@link Permission}s the
     * current user has.
     * 
     * @param permissions the permission to check. Content will be displayed if currently logged in user has all of the
     *            given {@link Permission}s
     */
    public void setCheckIfAll( String permissions )
    {
        checkForAny = false;
        storePermissionsFrom( permissions );
        LOG.debug( "setCheckIfAll({})", this.permissions );
    }

    /**
     * Store in the {@link #permissions} the permissions from tag
     * 
     * @param permissions the comma separate list of permissions
     */
    private void storePermissionsFrom( String permissions )
    {
        // in case both tags are present (a bit useless), clear previous tag settings
        this.permissions.clear();
        // add new permission set
        String permissions2Check = StringUtils.isBlank( permissions ) ? "" : permissions;
        StringTokenizer st = new StringTokenizer( permissions2Check, ",;" );
        while ( st.hasMoreElements() )
        {
            String permission = st.nextToken().trim();
            if ( permission.length() > 0 )
            {
                this.permissions.add( Permission.valueOf( permission ) );
            }
        }
    }

    @Override
    public int doStartTag()
        throws JspException
    {
        HttpSession session = pageContext.getSession();
        final AuthenticatedUserDTO userDTO = SecurityUtil.getUserFromSession( session );
        final boolean result = ( checkForAny ) ? checkForAny( userDTO ) : checkForAll( userDTO );
        return ( result ) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    /**
     * Verifies if the user given as parameter has ANY of the {@link #permissions}.
     * 
     * @param userDTO the logged in user to check
     * @return true if at least one permission is found
     */
    private boolean checkForAny( final AuthenticatedUserDTO userDTO )
    {
        boolean result = false;
        if ( userDTO != null )
        {
            if ( permissions.size() == 0 )
            {
                result = true;
            }
            else
            {
                for ( Permission permission : permissions )
                {
                    if ( userDTO.hasPermission( null, permission ) )
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Verifies if the user given as parameter has ALL of the {@link #permissions}.
     * 
     * @param userDTO the logged in user to check
     * @return true if all permissions are found
     */
    private boolean checkForAll( final AuthenticatedUserDTO userDTO )
    {
        boolean result = false;
        if ( userDTO != null )
        {
            result = true;
            for ( Permission permission : permissions )
            {
                if ( !userDTO.hasPermission( null, permission ) )
                {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
