/**
 * 
 */
package pfa.alliance.fim.jobs;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.service.UserManagerService;

/**
 * Class used for testing {@link UserStateUpdateJob}.
 * 
 * @author Csaba
 */
public class UserStateUpdateJobTest
{
    private UserManagerService userManagerServiceMock;

    private UserStateUpdateJob userStateUpdateJob;

    @Before
    public void init()
    {
        userManagerServiceMock = Mockito.mock( UserManagerService.class );
        userStateUpdateJob = new UserStateUpdateJob( userManagerServiceMock );
    }

    @Test
    public void test_execute()
        throws Exception
    {
        Mockito.when( userManagerServiceMock.deleteExpiredOneTimeLinks() ).thenReturn( 1 );
        Mockito.when( userManagerServiceMock.markDeleteNotActivatedUserAccounts() ).thenReturn( 2 );

        userStateUpdateJob.execute( null );

        Mockito.verify( userManagerServiceMock, Mockito.atLeastOnce() ).deleteExpiredOneTimeLinks();
        Mockito.verify( userManagerServiceMock, Mockito.atLeastOnce() ).markDeleteNotActivatedUserAccounts();
    }
}
