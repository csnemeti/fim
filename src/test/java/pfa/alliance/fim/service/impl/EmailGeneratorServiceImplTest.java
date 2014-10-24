/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.NameProvider;

/**
 * This class is used for testing {@link EmailGeneratorServiceImpl}.
 * 
 * @author Csaba
 */
public class EmailGeneratorServiceImplTest
{
    @Test
    public void test_getSubject_localeEnNoParams()
    {
        // prepare
        EmailGeneratorServiceImpl service = new EmailGeneratorServiceImpl();
        // call
        String subject = service.getSubject( EmailType.REGISTER_USER, null, Locale.UK );
        // test
        Assert.assertNotNull( "Subject should not be null", subject );
        // see in StripesResources.properties the message
        Assert.assertEquals( "Subject issue", "Your F.I.M. account has been created!", subject );
    }

    @Test
    public void test_getContent_localeEnWithParams()
    {
        // prepare
        EmailGeneratorServiceImpl service = new EmailGeneratorServiceImpl();
        NameProvider nameProvider = Mockito.mock( NameProvider.class );
        Mockito.when( nameProvider.name() ).thenReturn( "Content1" );
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "name", "velocity" );
        // call
        String content = service.getContent( nameProvider, data, Locale.UK );
        // test
        Assert.assertNotNull( "Content should not be null", content );
        Assert.assertEquals( "Content issue", "Testing velocity.", content );
    }
}
