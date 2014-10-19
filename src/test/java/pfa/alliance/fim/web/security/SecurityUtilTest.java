/**
 * 
 */
package pfa.alliance.fim.web.security;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class is used for testing {@link SecurityUtil}.
 * 
 * @author Csaba
 */
public class SecurityUtilTest
{
    @Test
    public void testNoAuthenticatedCase()
    {
        // prepare
        AuthenticatedUserDTO user = null;
        HttpSession session = Mockito.mock( HttpSession.class );
        Mockito.when( session.getAttribute( "USER_DTO" ) ).thenReturn( user );

        // call
        AuthenticatedUserDTO userFromSession = SecurityUtil.getUserFromSession( session );
        boolean isLoggedIn = SecurityUtil.isAuthenticated( session );

        // test
        Assert.assertNull( "User must be null", userFromSession );
        Assert.assertFalse( "User should not be logged in", isLoggedIn );
        Mockito.verify( session, Mockito.atLeastOnce() ).getAttribute( "USER_DTO" );
    }

    @Test
    public void testAuthenticatedCase()
    {
        // prepare
        AuthenticatedUserDTO user =
            new AuthenticatedUserDTO( 1, "Test", "User", "email@email.com", "username",
                                      new Timestamp( System.currentTimeMillis() ) );
        HttpSession session = Mockito.mock( HttpSession.class );
        Mockito.when( session.getAttribute( "USER_DTO" ) ).thenReturn( user );

        // call
        AuthenticatedUserDTO userFromSession = SecurityUtil.getUserFromSession( session );
        boolean isLoggedIn = SecurityUtil.isAuthenticated( session );

        // test
        Assert.assertNotNull( "User must NOT be null", userFromSession );
        Assert.assertSame( "User issues", user, userFromSession );
        Assert.assertTrue( "User should be logged in", isLoggedIn );
        Mockito.verify( session, Mockito.atLeastOnce() ).getAttribute( "USER_DTO" );
    }

    @Test
    public void test_putUserIntoSession_nullUser()
    {
        // prepare
        AuthenticatedUserDTO user = null;
        HttpSession session = Mockito.mock( HttpSession.class );

        // call
        SecurityUtil.putUserIntoSession( user, session );

        // test
        Mockito.verify( session, Mockito.atLeastOnce() ).setAttribute( "USER_DTO", null );
    }

    @Test
    public void test_putUserIntoSession_withUser()
    {
        // prepare
        AuthenticatedUserDTO user =
            new AuthenticatedUserDTO( 1, "Test", "User", "email@email.com", "username",
                                      new Timestamp( System.currentTimeMillis() ) );
        HttpSession session = Mockito.mock( HttpSession.class );

        // call
        SecurityUtil.putUserIntoSession( user, session );

        // test
        Mockito.verify( session, Mockito.atLeastOnce() ).setAttribute( "USER_DTO", user );
    }
}
