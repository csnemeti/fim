/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockRoundtrip;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.AbstractActionBeanTest;
import pfa.alliance.fim.web.stripes.action.ServiceModuleMock;

/**
 * This class is used for testing {@link LogoutUserActionBean}.
 * 
 * @author Csaba
 */
public class LogoutUserActionBeanTest
    extends AbstractActionBeanTest
{

    @Before
    public void init()
    {
        // init Mock objects
        ServiceModuleMock.initMocks();
    }

    @Test
    public void test_logout_noUserBefore()
        throws Exception
    {
        MockRoundtrip trip = new MockRoundtrip( getContext(), LogoutUserActionBean.class );
        trip.execute();

        LogoutUserActionBean bean = trip.getActionBean( LogoutUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.MAIN_PAGE.getURL(), trip.getDestination() );

        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        Assert.assertFalse( "User should not be logged in", SecurityUtil.isAuthenticated( session ) );
    }

    @Test
    public void test_logout_withUserBefore()
        throws Exception
    {
        MockRoundtrip trip = new MockRoundtrip( getContext(), LogoutUserActionBean.class );
        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        AuthenticatedUserDTO userDto = new AuthenticatedUserDTO( 1, "First", "Last", "email", "username", null, null );
        SecurityUtil.putUserIntoSession( userDto, session );

        trip.execute();

        LogoutUserActionBean bean = trip.getActionBean( LogoutUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.MAIN_PAGE.getURL(), trip.getDestination() );

        request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        Assert.assertFalse( "User should not be logged in", SecurityUtil.isAuthenticated( session ) );
    }

}
