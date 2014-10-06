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
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for handle user registration.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/register" )
public class RegisterUserActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( RegisterUserActionBean.class );

    private final UserManagerService userManagerService;

    @Validate( required = true, trim = true, on = "tryRegister" )
    private String firstName;

    @Validate( required = true, trim = true, on = "tryRegister" )
    private String lastName;

    @Validate( required = true, trim = true, converter = EmailTypeConverter.class, on = "tryRegister" )
    private String email;

    @Validate( required = true, trim = true, minlength = 6, on = "tryRegister" )
    private String password;

    @Validate( required = true, trim = true, minlength = 6, expression = "this eq password", on = "tryRegister" )
    private String password2;

    @Inject
    public RegisterUserActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        return new ForwardResolution( FimPageURLs.USER_REGISTER_JSP.getURL() );
    }

    /**
     * Method called when user presses Submit button from registration form.
     * 
     * @return the place where it should go after the operation
     */
    public Resolution tryRegister()
    {
        LOG.debug( "Trying to register user: firstName = {}, lastName = {}, email = {}", firstName, lastName, email );
        userManagerService.registerUser( email, password, firstName, lastName );
        return new ForwardResolution( "/" );
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getPassword2()
    {
        return password2;
    }

    public void setPassword2( String password2 )
    {
        this.password2 = password2;
    }

}
