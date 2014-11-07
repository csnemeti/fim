/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.service.impl.DuplicateUserDataException;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for inviting persons to join FIM.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/invite" )
public class InviteUserActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( InviteUserActionBean.class );

    private final UserManagerService userManagerService;

    @Validate( required = false, trim = true, on = "tryRegister" )
    private String firstName;

    @Validate( required = false, trim = true, on = "tryRegister" )
    private String lastName;

    @Validate( required = true, trim = true, converter = EmailTypeConverter.class, on = "tryRegister" )
    private String email;

    /** Message key regarding Database operation */
    private String dbOperationResult;

    private final static String USER_CREATED_RESPONSE = "userInvite.userCreated";

    private final static String DUPLICATE_USER_RESPONSE = "userRegistration.duplicateUser";

    @Inject
    public InviteUserActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        return new ForwardResolution( FimPageURLs.USER_INVITE_JSP.getURL() );
    }

    /**
     * Method called when user presses Submit button from registration form.
     * 
     * @return the place where it should go after the operation
     */
    public Resolution tryRegister()
    {
        LOG.debug( "Inviting user: firstName = {}, lastName = {}, email = {}", firstName, lastName, email );
        try
        {
            userManagerService.inviteUser( email, firstName, lastName, getContext().getLocale() );
            dbOperationResult = USER_CREATED_RESPONSE;
        }
        catch ( DuplicateUserDataException e )
        {
            dbOperationResult = DUPLICATE_USER_RESPONSE;
        }
        return new ForwardResolution( FimPageURLs.USER_INVITE_JSP.getURL() );
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getDbOperationResult()
    {
        String result = null;

        if ( dbOperationResult != null )
        {
            result = getMessage( dbOperationResult );
        }
        return result;
    }

}
