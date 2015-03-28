/**
 * 
 */
package pfa.alliance.fim.web.security;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class is used for testing {@link SecurityTag}
 * 
 * @author Csaba
 */
public class SecurityTagTest
{
    @Test
    public void test_setCheckIfAny_nullPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        tag.setCheckIfAny( null );

        List<Permission> permission = getPermission( tag );
        Assert.assertNotNull( "Permission should NOT be null", permission );
        Assert.assertEquals( "Permission should be empty", 0, permission.size() );
        Assert.assertEquals( "Chewck if ANY issue", true, isCheckIfAny( tag ) );
    }

    @Test
    public void test_setCheckIfAny_emptyPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        tag.setCheckIfAny( " " );

        List<Permission> permission = getPermission( tag );
        Assert.assertNotNull( "Permission should NOT be null", permission );
        Assert.assertEquals( "Permission should be empty", 0, permission.size() );
        Assert.assertEquals( "Chewck if ANY issue", true, isCheckIfAny( tag ) );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_setCheckIfAny_invalidPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        try
        {
            tag.setCheckIfAny( "PROJECT_CREATE_PROJECT, PROJECT_CREATE_ANOTHER_PROJECT" );
        }
        finally
        {

            List<Permission> permission = getPermission( tag );
            Assert.assertNotNull( "Permission should NOT be null", permission );
            Assert.assertEquals( "Permission should be empty", 1, permission.size() );
            Assert.assertEquals( "Chewck if ANY issue", true, isCheckIfAny( tag ) );
        }
    }

    @Test
    public void test_setCheckIfAny_validPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        try
        {
            tag.setCheckIfAny( "PROJECT_CREATE_PROJECT, ,PROJECT_EDIT_PROJECT;PROJECT_LIST_PROJECTS" );
        }
        finally
        {

            List<Permission> permission = getPermission( tag );
            Assert.assertNotNull( "Permission should NOT be null", permission );
            Assert.assertEquals( "Permission should be empty", 3, permission.size() );
            Assert.assertEquals( "Chewck if ANY issue", true, isCheckIfAny( tag ) );
        }
    }

    @Test
    public void test_doStartTag_checkIfAny_noUser()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext = Mockito.mock( PageContext.class );
        tag.setPageContext( pageContext );
        tag.setCheckIfAny( "ADMIN_INVITE_USER" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.SKIP_BODY, result );
    }

    @Test
    public void test_doStartTag_checkIfAny_noPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext = mockUserWithPermissions();
        tag.setPageContext( pageContext );
        tag.setCheckIfAny( "ADMIN_INVITE_USER" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.SKIP_BODY, result );
    }

    @Test
    public void test_doStartTag_checkIfAny_noPermissionFound()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext =
            mockUserWithPermissions( Permission.PROJECT_CREATE_PROJECT, Permission.PROJECT_EDIT_PROJECT );
        tag.setPageContext( pageContext );
        tag.setCheckIfAny( "ADMIN_INVITE_USER" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.SKIP_BODY, result );
    }

    @Test
    public void test_doStartTag_checkIfAny_nothingAsked()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext = mockUserWithPermissions();
        tag.setPageContext( pageContext );
        tag.setCheckIfAny( " " );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.EVAL_BODY_INCLUDE, result );
    }

    @Test
    public void test_doStartTag_checkIfAny_permissionFound()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext = mockUserWithPermissions( Permission.ADMIN_INVITE_USER );
        tag.setPageContext( pageContext );
        tag.setCheckIfAny( "ADMIN_INVITE_USER" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.EVAL_BODY_INCLUDE, result );
    }

    @Test
    public void test_setCheckIfAll_nullPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        tag.setCheckIfAll( null );

        List<Permission> permission = getPermission( tag );
        Assert.assertNotNull( "Permission should NOT be null", permission );
        Assert.assertEquals( "Permission should be empty", 0, permission.size() );
        Assert.assertEquals( "Chewck if ANY issue", false, isCheckIfAny( tag ) );
    }

    @Test
    public void test_setCheckIfAll_emptyPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        tag.setCheckIfAll( " " );

        List<Permission> permission = getPermission( tag );
        Assert.assertNotNull( "Permission should NOT be null", permission );
        Assert.assertEquals( "Permission should be empty", 0, permission.size() );
        Assert.assertEquals( "Chewck if ANY issue", false, isCheckIfAny( tag ) );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_setCheckIfAll_invalidPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        try
        {
            tag.setCheckIfAll( "PROJECT_CREATE_PROJECT, PROJECT_CREATE_ANOTHER_PROJECT" );
        }
        finally
        {

            List<Permission> permission = getPermission( tag );
            Assert.assertNotNull( "Permission should NOT be null", permission );
            Assert.assertEquals( "Permission should be empty", 1, permission.size() );
            Assert.assertEquals( "Chewck if ANY issue", false, isCheckIfAny( tag ) );
        }
    }

    @Test
    public void test_setCheckIfAll_validPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        try
        {
            tag.setCheckIfAll( "PROJECT_CREATE_PROJECT, ,PROJECT_EDIT_PROJECT;PROJECT_LIST_PROJECTS" );
        }
        finally
        {

            List<Permission> permission = getPermission( tag );
            Assert.assertNotNull( "Permission should NOT be null", permission );
            Assert.assertEquals( "Permission should be empty", 3, permission.size() );
            Assert.assertEquals( "Chewck if ANY issue", false, isCheckIfAny( tag ) );
        }
    }

    @Test
    public void test_doStartTag_checkIfAll_noUser()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext = Mockito.mock( PageContext.class );
        tag.setPageContext( pageContext );
        tag.setCheckIfAll( "ADMIN_INVITE_USER" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.SKIP_BODY, result );
    }

    @Test
    public void test_doStartTag_checkIfAll_noPermission()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext = mockUserWithPermissions();
        tag.setPageContext( pageContext );
        tag.setCheckIfAll( "ADMIN_INVITE_USER" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.SKIP_BODY, result );
    }

    @Test
    public void test_doStartTag_checkIfAny_notAllPermissionFound()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext = mockUserWithPermissions( Permission.ADMIN_INVITE_USER, Permission.ADMIN_SEARCH_USERS );
        tag.setPageContext( pageContext );
        tag.setCheckIfAll( "ADMIN_SEARCH_USERS, PROJECT_LIST_PROJECTS" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.SKIP_BODY, result );
    }

    @Test
    public void test_doStartTag_checkIfAny_allPermissionFound()
        throws Exception
    {
        SecurityTag tag = new SecurityTag();
        PageContext pageContext =
            mockUserWithPermissions( Permission.ADMIN_SEARCH_USERS, Permission.PROJECT_LIST_PROJECTS,
                                     Permission.PROJECT_SHOW_HIDDEN_PROJECTS );
        tag.setPageContext( pageContext );
        tag.setCheckIfAll( "ADMIN_SEARCH_USERS, PROJECT_LIST_PROJECTS" );

        int result = tag.doStartTag();
        Assert.assertEquals( "Tag result issue", Tag.EVAL_BODY_INCLUDE, result );
    }

    private static PageContext mockUserWithPermissions( Permission... permissions )
    {
        PageContext pageContext = Mockito.mock( PageContext.class );
        HttpSession session = Mockito.mock( HttpSession.class );
        Mockito.when( pageContext.getSession() ).thenReturn( session );
        Map<Integer, List<Permission>> projectPermissions = new HashMap<>();
        projectPermissions.put( null, Arrays.asList( permissions ) );
        AuthenticatedUserDTO user =
            new AuthenticatedUserDTO( 1, "First", "Last", "email", "username",
                                      new Timestamp( System.currentTimeMillis() ), projectPermissions );
        Mockito.when( session.getAttribute( "USER_DTO" ) ).thenReturn( user );
        return pageContext;
    }

    private static List<Permission> getPermission( SecurityTag tag )
        throws Exception
    {
        Field field = SecurityTag.class.getDeclaredField( "permissions" );
        field.setAccessible( true );
        return (List<Permission>) field.get( tag );
    }

    private static boolean isCheckIfAny( SecurityTag tag )
        throws Exception
    {
        Field field = SecurityTag.class.getDeclaredField( "checkForAny" );
        field.setAccessible( true );
        return (boolean) field.get( tag );
    }
}
