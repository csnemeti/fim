/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Provider;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.UserOneTimeLink;

/**
 * This class is used for testing {@link FimUrlGeneratorServiceImpl}
 * 
 * @author Csaba
 */
public class FimUrlGeneratorServiceImplTest
{
    @Test
    public void test_getOneTimeLink_forUserRegistration()
    {
        // prepare
        Provider<String> urlProviderMock = Mockito.mock( Provider.class );
        Mockito.when( urlProviderMock.get() ).thenReturn( "http://test-localhost:8080/fim/" );
        FimUrlGeneratorServiceImpl fimUrlGeneratorServiceImpl = new FimUrlGeneratorServiceImpl( urlProviderMock );
        // call
        UserOneTimeLink link = new UserOneTimeLink();
        link.setDesignation( OneTimeLinkType.USER_REGISTRATION );
        link.setUuid( "uuid" );
        String url = fimUrlGeneratorServiceImpl.getOneTimeLinkLink( link );
        // test
        Assert.assertNotNull( "URL should not be null", url );
        Assert.assertEquals( "URL issues", "http://test-localhost:8080/fim/user/activate/uuid", url );
        Mockito.verify( urlProviderMock, Mockito.atLeastOnce() ).get();
    }

    @Test
    public void test_getOneTimeLink_forUserInvite()
    {
        // prepare
        Provider<String> urlProviderMock = Mockito.mock( Provider.class );
        Mockito.when( urlProviderMock.get() ).thenReturn( "http://test-localhost:8080/fim/" );
        FimUrlGeneratorServiceImpl fimUrlGeneratorServiceImpl = new FimUrlGeneratorServiceImpl( urlProviderMock );
        // call
        UserOneTimeLink link = new UserOneTimeLink();
        link.setDesignation( OneTimeLinkType.USER_INVITE );
        link.setUuid( "uuid" );
        String url = fimUrlGeneratorServiceImpl.getOneTimeLinkLink( link );
        // test
        Assert.assertNotNull( "URL should not be null", url );
        Assert.assertEquals( "URL issues", "http://test-localhost:8080/fim/user/profile?token=uuid", url );
        Mockito.verify( urlProviderMock, Mockito.atLeastOnce() ).get();
    }

    @Test
    public void test_getProjectLink()
    {
        // prepare
        Provider<String> urlProviderMock = Mockito.mock( Provider.class );
        Mockito.when( urlProviderMock.get() ).thenReturn( "http://test-localhost:8080/fim/" );
        FimUrlGeneratorServiceImpl fimUrlGeneratorServiceImpl = new FimUrlGeneratorServiceImpl( urlProviderMock );
        // call
        String url = fimUrlGeneratorServiceImpl.getProjectLink( "Project1" );
        // test
        Assert.assertNotNull( "URL should not be null", url );
        Assert.assertEquals( "URL issues", "http://test-localhost:8080/fim/project/show/Project1", url );
        Mockito.verify( urlProviderMock, Mockito.atLeastOnce() ).get();
    }
}
