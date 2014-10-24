/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Provider;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class is used for testing {@link FimUrlGeneratorServiceImpl}
 * 
 * @author Csaba
 */
public class FimUrlGeneratorServiceImplTest
{
    @Test
    public void test_getActivateAccountLink()
    {
        // prepare
        Provider<String> urlProviderMock = Mockito.mock( Provider.class );
        Mockito.when( urlProviderMock.get() ).thenReturn( "http://test-localhost:8080/fim/" );
        FimUrlGeneratorServiceImpl fimUrlGeneratorServiceImpl = new FimUrlGeneratorServiceImpl( urlProviderMock );
        // call
        String url = fimUrlGeneratorServiceImpl.getActivateAccountLink( "uuid" );
        // test
        Assert.assertNotNull( "URL should not be null", url );
        Assert.assertEquals( "URL issues", "http://test-localhost:8080/fim/user/activate/uuid", url );
        Mockito.verify( urlProviderMock, Mockito.atLeastOnce() ).get();
    }
}
