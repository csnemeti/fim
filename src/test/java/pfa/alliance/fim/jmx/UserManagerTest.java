/**
 * 
 */
package pfa.alliance.fim.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.service.UserManagerService;

/**
 * @author Csaba
 *
 */
public class UserManagerTest
{
    private UserManagerService userManagerServiceMock;
    private UserManager userManager;

    @Before
    public void init()
    {
        userManagerServiceMock = Mockito.mock( UserManagerService.class );
        userManager = new UserManager( userManagerServiceMock );
    }

    @Test
    public void test_deleteExpiredOneTimeLinks_ok()
    {
        Mockito.when( userManagerServiceMock.deleteExpiredOneTimeLinks() ).thenReturn( 10 );
        int deleted = userManager.deleteExpiredOneTimeLinks();

        Assert.assertEquals( "Deleted issues", 10, deleted );
        Mockito.verify( userManagerServiceMock, Mockito.atLeastOnce() ).deleteExpiredOneTimeLinks();
    }

    @Test
    public void test_deleteExpiredOneTimeLinks_fail()
    {
        Mockito.when( userManagerServiceMock.deleteExpiredOneTimeLinks() ).thenThrow( new RuntimeException( "4 testing" ) );
        int deleted = userManager.deleteExpiredOneTimeLinks();

        Assert.assertEquals( "Deleted issues", -1, deleted );
        Mockito.verify( userManagerServiceMock, Mockito.atLeastOnce() ).deleteExpiredOneTimeLinks();
    }

    @Test
    public void test_markDeleteNotActivatedUserAccounts_ok()
    {
        Mockito.when( userManagerServiceMock.markDeleteNotActivatedUserAccounts() ).thenReturn( 10 );
        int deleted = userManager.markDeleteNotActivatedUserAccounts();

        Assert.assertEquals( "Deleted issues", 10, deleted );
        Mockito.verify( userManagerServiceMock, Mockito.atLeastOnce() ).markDeleteNotActivatedUserAccounts();
    }

    @Test
    public void test_markDeleteNotActivatedUserAccounts_fail()
    {
        Mockito.when( userManagerServiceMock.markDeleteNotActivatedUserAccounts() ).thenThrow( new RuntimeException(
                                                                                                                     "4 testing" ) );
        int deleted = userManager.markDeleteNotActivatedUserAccounts();

        Assert.assertEquals( "Deleted issues", -1, deleted );
        Mockito.verify( userManagerServiceMock, Mockito.atLeastOnce() ).markDeleteNotActivatedUserAccounts();
    }

    @Test
    public void test_register_ok()
        throws Exception
    {
        MBeanServer server = Mockito.mock( MBeanServer.class );
        ObjectName oName = new ObjectName( "fim:name=test" );
        ObjectInstance instance = new ObjectInstance( oName, "fim.test" );
        Mockito.when( server.registerMBean( Mockito.any(), Mockito.any( ObjectName.class ) ) ).thenReturn( instance );

        userManager.register( server );

        Mockito.verify( server, Mockito.atLeastOnce() ).registerMBean( Mockito.any(), Mockito.any( ObjectName.class ) );
    }
}
