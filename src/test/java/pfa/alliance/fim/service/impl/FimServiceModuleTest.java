/**
 * 
 */
package pfa.alliance.fim.service.impl;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class is used for testing {@link FimServiceModule}.
 * 
 * @author Csaba
 */
public class FimServiceModuleTest
{
    @Test
    @Ignore
    public void test_getFimUrlConfiguration()
    {
        // prepare
        System.setProperty( "fim.env", "unittest1" );
        FimServiceModule module = new FimServiceModule();
        //call
        String url = module.getFimUrlConfiguration();
        // test
        Assert.assertNotNull( "URL should not be null", url );
        Assert.assertEquals( "URL issues", "https://test.localhost:8443/fim/", url );
    }
}
