/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockRoundtrip;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserLogin;
import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.LoggedInUserDTO;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.AbstractActionBeanTest;
import pfa.alliance.fim.web.stripes.action.ServiceModuleMock;

/**
 * This class is used for testing {@link LoginUserActionBean}.
 * 
 * @author Csaba
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class LoginUserActionBeanTest
    extends AbstractActionBeanTest
{

    @Before
    public void init()
    {
        // init Mock objects
        ServiceModuleMock.initMocks();

    }

    @Test
    public void testShowLoginPage()
        throws Exception
    {
        MockRoundtrip trip = new MockRoundtrip( getContext(), LoginUserActionBean.class );
        trip.execute();

        LoginUserActionBean bean = trip.getActionBean( LoginUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.USER_LOGIN_JSP.getURL(), trip.getDestination() );
        Assert.assertNull( "DB operation should be null", bean.getDbOperationResult() );

        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        Assert.assertFalse( "User should not be logged in", SecurityUtil.isAuthenticated( session ) );
    }

    @Test
    public void test_tryLogin_UserNotFound()
        throws Exception
    {
        UserManagerService userManagerService = ServiceModuleMock.getUserManagerServiceMock();
        LoggedInUserDTO dto = new LoggedInUserDTO( null, null );
        Mockito.when( userManagerService.login( "user", "pass" ) ).thenReturn( dto );

        MockRoundtrip trip = new MockRoundtrip( getContext(), LoginUserActionBean.class );
        trip.setParameter( "username", "user" );
        trip.setParameter( "password", "pass" );
        trip.execute( "tryLogin" );

        LoginUserActionBean bean = trip.getActionBean( LoginUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.USER_LOGIN_JSP.getURL(), trip.getDestination() );
        Assert.assertEquals( 0, bean.getContext().getValidationErrors().size() );
        Assert.assertEquals( "user", bean.getUsername() );
        Assert.assertEquals( "pass", bean.getPassword() );
        Assert.assertNotNull( "DB operation should NOT be null", bean.getDbOperationResultValue() );

        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        Assert.assertFalse( "User should not be logged in", SecurityUtil.isAuthenticated( session ) );

        Mockito.verify( userManagerService, Mockito.atLeastOnce() ).login( "user", "pass" );
    }

    @Test
    public void test_tryLogin_UserNew()
        throws Exception
    {
        UserManagerService userManagerService = ServiceModuleMock.getUserManagerServiceMock();
        User user = new User();
        user.setId( 10 );
        user.setStatus( UserStatus.NEW );
        LoggedInUserDTO dto = new LoggedInUserDTO( user, null );
        Mockito.when( userManagerService.login( "user", "pass" ) ).thenReturn( dto );

        MockRoundtrip trip = new MockRoundtrip( getContext(), LoginUserActionBean.class );
        trip.setParameter( "username", "user" );
        trip.setParameter( "password", "pass" );
        trip.execute( "tryLogin" );

        LoginUserActionBean bean = trip.getActionBean( LoginUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.USER_LOGIN_JSP.getURL(), trip.getDestination() );
        Assert.assertEquals( 0, bean.getContext().getValidationErrors().size() );
        Assert.assertEquals( "user", bean.getUsername() );
        Assert.assertEquals( "pass", bean.getPassword() );
        Assert.assertNotNull( "DB operation should NOT be null", bean.getDbOperationResultValue() );

        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        Assert.assertFalse( "User should not be logged in", SecurityUtil.isAuthenticated( session ) );

        Mockito.verify( userManagerService, Mockito.atLeastOnce() ).login( "user", "pass" );
    }

    @Test
    public void test_tryLogin_UserDisabled()
        throws Exception
    {
        UserManagerService userManagerService = ServiceModuleMock.getUserManagerServiceMock();
        User user = new User();
        user.setId( 10 );
        user.setStatus( UserStatus.DISABLED );
        LoggedInUserDTO dto = new LoggedInUserDTO( user, null );
        Mockito.when( userManagerService.login( "user", "pass" ) ).thenReturn( dto );

        MockRoundtrip trip = new MockRoundtrip( getContext(), LoginUserActionBean.class );
        trip.setParameter( "username", "user" );
        trip.setParameter( "password", "pass" );
        trip.execute( "tryLogin" );

        LoginUserActionBean bean = trip.getActionBean( LoginUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.USER_LOGIN_JSP.getURL(), trip.getDestination() );
        Assert.assertEquals( 0, bean.getContext().getValidationErrors().size() );
        Assert.assertEquals( "user", bean.getUsername() );
        Assert.assertEquals( "pass", bean.getPassword() );
        Assert.assertNotNull( "DB operation should NOT be null", bean.getDbOperationResultValue() );

        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        Assert.assertFalse( "User should not be logged in", SecurityUtil.isAuthenticated( session ) );

        Mockito.verify( userManagerService, Mockito.atLeastOnce() ).login( "user", "pass" );
    }

    @Test
    public void test_tryLogin_UserValidNoProjectAssignment()
        throws Exception
    {
        UserManagerService userManagerService = ServiceModuleMock.getUserManagerServiceMock();
        User user = new User();
        user.setId( 10 );
        user.setStatus( UserStatus.ACTIVE );
        user.setFirstName( "First" );
        user.setLastName( "Last" );
        user.setLogin( "userLoggedIn" );
        user.setDefaultRole( UserRole.PRODUCT_OWNER );
        Set<UserLogin> logins = new HashSet<>();
        UserLogin userLogin = new UserLogin();
        // I want to test an impossible situatyion, so only one object and no timestamp set,
        // so that we're forced to add a value by default.
        // userLogin.setCreatedAt( new Timestamp( 2015, 3, 3, 12, 13, 14, 0 ) );
        logins.add( userLogin );
        user.setLogins( logins );
        Map<UserRole, List<UserPermission>> userDtoPermissions = new HashMap<>();
        userDtoPermissions.put( UserRole.PRODUCT_OWNER, Arrays.asList( UserPermission.ADMIN_SEARCH_USERS,
                                                                       UserPermission.PROJECT_CREATE_PROJECT,
                                                                       UserPermission.PROJECT_LIST_PROJECTS ) );
        LoggedInUserDTO dto = new LoggedInUserDTO( user, userDtoPermissions );
        Mockito.when( userManagerService.login( "user", "pass" ) ).thenReturn( dto );

        MockRoundtrip trip = new MockRoundtrip( getContext(), LoginUserActionBean.class );
        trip.setParameter( "username", "user" );
        trip.setParameter( "password", "pass" );
        trip.execute( "tryLogin" );

        LoginUserActionBean bean = trip.getActionBean( LoginUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.USER_DASBOARD_PAGE.getURL(), trip.getDestination() );
        Assert.assertEquals( 0, bean.getContext().getValidationErrors().size() );
        Assert.assertEquals( "user", bean.getUsername() );
        Assert.assertEquals( "pass", bean.getPassword() );

        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        Assert.assertTrue( "User should be logged in", SecurityUtil.isAuthenticated( session ) );

        Mockito.verify( userManagerService, Mockito.atLeastOnce() ).login( "user", "pass" );
    }

    @Test
    public void test_tryLogin_UserValid()
        throws Exception
    {
        UserManagerService userManagerService = ServiceModuleMock.getUserManagerServiceMock();
        User user = new User();
        user.setId( 10 );
        user.setStatus( UserStatus.ACTIVE );
        user.setFirstName( "First" );
        user.setLastName( "Last" );
        user.setLogin( "userLoggedIn" );
        user.setDefaultRole( UserRole.TEAM );
        // LinkedHashSet is necessary in order to make timestamp comparison go on all branches
        Set<UserLogin> logins = new LinkedHashSet<>();
        UserLogin userLogin = new UserLogin();
        // year must be - 1900
        userLogin.setCreatedAt( new Timestamp( 115, 2, 2, 12, 13, 14, 0 ) );
        logins.add( userLogin );
        userLogin = new UserLogin();
        Timestamp lastLogin = new Timestamp( 115, 2, 3, 22, 23, 24, 0 );
        userLogin.setCreatedAt( lastLogin );
        logins.add( userLogin );
        userLogin = new UserLogin();
        userLogin.setCreatedAt( new Timestamp( 115, 2, 1, 2, 3, 4, 0 ) );
        logins.add( userLogin );
        user.setLogins( logins );
        Set<UserProjectRelation> userProjectRelation = new HashSet<>();
        UserProjectRelation relation = new UserProjectRelation();
        relation.setUserRole( UserRoleInsideProject.SCRUM_MASTER );
        Project project = new Project();
        project.setId( 1 );
        relation.setProject( project );
        userProjectRelation.add( relation );
        user.setUserProjectRelation( userProjectRelation );
        Map<UserRole, List<UserPermission>> userDtoPermissions = new HashMap<>();
        userDtoPermissions.put( UserRole.TEAM, Arrays.asList( UserPermission.ADMIN_SEARCH_USERS,
                                                                       UserPermission.PROJECT_LIST_PROJECTS ) );
        userDtoPermissions.put( UserRole.SCRUM_MASTER, Arrays.asList( UserPermission.ADMIN_SEARCH_USERS,
                                                                      UserPermission.PROJECT_CREATE_PROJECT,
                                                                      UserPermission.PROJECT_LIST_PROJECTS ) );
        LoggedInUserDTO dto = new LoggedInUserDTO( user, userDtoPermissions );
        Mockito.when( userManagerService.login( "user", "pass" ) ).thenReturn( dto );

        MockRoundtrip trip = new MockRoundtrip( getContext(), LoginUserActionBean.class );
        trip.setParameter( "username", "user" );
        trip.setParameter( "password", "pass" );
        trip.execute( "tryLogin" );

        LoginUserActionBean bean = trip.getActionBean( LoginUserActionBean.class );
        Assert.assertNotNull( "Bean should not be null", bean );
        Assert.assertEquals( "URL issues", FimPageURLs.USER_DASBOARD_PAGE.getURL(), trip.getDestination() );
        Assert.assertEquals( 0, bean.getContext().getValidationErrors().size() );
        Assert.assertEquals( "user", bean.getUsername() );
        Assert.assertEquals( "pass", bean.getPassword() );

        MockHttpServletRequest request = trip.getRequest();
        Assert.assertNotNull( "Request should not be null", request );
        HttpSession session = request.getSession( true );
        Assert.assertNotNull( "Session should not be null", session );
        AuthenticatedUserDTO userDto = SecurityUtil.getUserFromSession( session );

        Assert.assertNotNull( "User should be logged in", userDto );
        Assert.assertEquals( "Last login issues", lastLogin, userDto.getLastLogin() );

        Mockito.verify( userManagerService, Mockito.atLeastOnce() ).login( "user", "pass" );
    }
}
;