package pfa.alliance.fim.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.ConfigurationService;

/**
 * Implementation for {@link ConfigurationService}
 * 
 * @author Balaceanu Sergiu-Denis
 */
@Singleton
class ConfigurationServiceImpl
    implements ConfigurationService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( ConfigurationServiceImpl.class );

    /** Keeps the configuration we're interested in. */
    private Properties configuration;

    private final Provider<Properties> emailConfiguration;

    @Inject
    public ConfigurationServiceImpl( @ServiceConfiguration( ServiceConfigurationType.EMAIL_SEND ) Provider<Properties> emailConfiguration )
    {
        this.emailConfiguration = emailConfiguration;
    }

    /**
     * Method called after initialization is completed and before other methods from this class are called.
     */
    void init()
    {
        if ( configuration == null )
        {
            LOG.debug( "Execute PostConstruct..." );
            configuration = new Properties();
            List<String> fieldsList = getEmailSendConfigurationKeys();
            initEmailSendConfiguration( fieldsList );
        }
    }

    /**
     * Gets the email send configuration keys.
     * 
     * @return the keys for e-mail send
     */
    private List<String> getEmailSendConfigurationKeys()
    {
        Field[] fields = ConfigurationService.class.getFields();
        List<String> fieldsList = new ArrayList<>();
        for ( Field field : fields )
        {
            String fieldName = field.getName();
            if ( fieldName.startsWith( "EMAIL_SEND_" ) )
            {
                try
                {
                    fieldsList.add( field.get( null ).toString() );
                }
                catch ( Exception e )
                {
                    LOG.error( "Error gettting value for: {}", fieldName, e );
                }
            }
        }
        return fieldsList;
    }

    private void initEmailSendConfiguration( List<String> fieldsList )
    {
        Properties configurationFromFile = emailConfiguration.get();
        for ( String field : fieldsList )
        {
            configuration.setProperty( field, String.valueOf( getBoolean( field, true, configurationFromFile ) ) );
        }
    }

    @Override
    public boolean isConfigurationCompleted()
    {
        init();
        // TODO: replace the mocked value with real configuration status
        return true;
    }

    @Override
    public boolean getBoolean( String key )
    {
        return Boolean.TRUE.equals( getBooleanObject( key ) );
    }

    @Override
    public Boolean getBooleanObject( String key )
    {
        return getBooleanObject( key, configuration );
    }

    private boolean getBoolean( final String key, final boolean defaultValue, Properties properties )
    {
        Boolean booleanValue = getBooleanObject( key, properties );
        if ( booleanValue == null )
        {
            booleanValue = defaultValue;
        }
        return booleanValue.booleanValue();
    }

    private static Boolean getBooleanObject( String key, Properties properties )
    {
        String value = properties.getProperty( key );
        Boolean result = null;
        if ( value != null )
        {
            result = "true".equalsIgnoreCase( value );
        }
        return result;
    }
}
