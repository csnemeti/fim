/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.io.StringReader;

import javax.mail.MessagingException;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.stripes.action.BaseActionBean;

/**
 * Modify the user status and reset user password.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/user/ajax/{userId}" )
@FimSecurity
public class ModifyUserActioBean
    extends BaseActionBean
{
    @Validate( required = true )
    private Integer userId;

    private final UserManagerService userManagerService;

    @Inject
    public ModifyUserActioBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
    }

    @FimSecurity( checkIfAll = Permission.ADMIN_RESET_USER_PASSWORD )
    public Resolution resetPassword()
    {
        String result = "{\"result\": \"OK\"}";
        try
        {
            userManagerService.resetPassword( userId, getLocale() );
        }
        catch ( MessagingException e )
        {
            result = "{\"result\": \"ERROR\", \"message\": \"Could not send e-mail\"}";
        }
        StreamingResolution sr = new StreamingResolution( "text/json", new StringReader( result ) );
        sr.setFilename( "reset.json" ).setLastModified( System.currentTimeMillis() );
        return sr;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

}
