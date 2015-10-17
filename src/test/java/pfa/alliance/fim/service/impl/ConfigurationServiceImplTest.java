/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import pfa.alliance.fim.service.ConfigurationService;

import com.google.inject.Provider;

/**
 * Test class for {@link ConfigurationServiceImpl}.
 * 
 * @author Csaba
 */
public class ConfigurationServiceImplTest
{

    @Test
    public void test_init_empltyConfig()
    {
        Provider<Properties> propsProvider = new PropertiesProvider( new Properties() );
        ConfigurationServiceImpl service = new ConfigurationServiceImpl( propsProvider );

        service.init();

        Assert.assertTrue( "Value should be null",
                           service.getBooleanObject( ConfigurationService.EMAIL_SEND_PROJECT_CREATE ) );
        Assert.assertTrue( "Value should be false", service.getBoolean( ConfigurationService.EMAIL_SEND_PROJECT_CREATE ) );
    }

    @Test
    public void test_init_notEmpltyConfig()
    {
        Properties props = new Properties();
        props.setProperty( ConfigurationService.EMAIL_SEND_PROJECT_CREATE, "true" );
        props.setProperty( ConfigurationService.EMAIL_SEND_PROJECT_EDIT, "false" );
        Provider<Properties> propsProvider = new PropertiesProvider( props );
        ConfigurationServiceImpl service = new ConfigurationServiceImpl( propsProvider );

        service.init();

        Assert.assertTrue( "Value should be true",
                           service.getBooleanObject( ConfigurationService.EMAIL_SEND_PROJECT_CREATE ) );
        Assert.assertTrue( "Value should be true", service.getBoolean( ConfigurationService.EMAIL_SEND_PROJECT_CREATE ) );

        Assert.assertFalse( "Value should be false",
                            service.getBooleanObject( ConfigurationService.EMAIL_SEND_PROJECT_EDIT ) );
        Assert.assertFalse( "Value should be false", service.getBoolean( ConfigurationService.EMAIL_SEND_PROJECT_EDIT ) );

        Assert.assertTrue( "Value should be true",
                           service.getBooleanObject( ConfigurationService.EMAIL_SEND_PROJECT_OWNER_CHANGE ) );
        Assert.assertTrue( "Value should be true",
                           service.getBoolean( ConfigurationService.EMAIL_SEND_PROJECT_OWNER_CHANGE ) );
    }

    private static class PropertiesProvider
        implements Provider<Properties>
    {
        private final Properties props;

        public PropertiesProvider( Properties props )
        {
            this.props = props;
        }

        @Override
        public Properties get()
        {
            return props;
        }

    }
}
