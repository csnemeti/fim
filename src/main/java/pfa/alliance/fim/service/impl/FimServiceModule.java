/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.batoo.jpa.JPASettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.impl.FimDaoModule;
import pfa.alliance.fim.service.ConfigurationService;
import pfa.alliance.fim.service.DatabaseMigrationService;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.IssueManagerService;
import pfa.alliance.fim.service.PersistenceService;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.service.SolrManager;
import pfa.alliance.fim.service.UserManagerService;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Guice module for Service level
 * 
 * @author Csaba
 */
public class FimServiceModule
    extends AbstractModule
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( FimServiceModule.class );

    /** The default configuration file for JPA. */
    private static final String DEFAULT_JPA_CONFIG_FILE = "/env/fim.properties";

    private static final String[] JPA_CONFIG_PROPERTIES = { "javax.persistence.jdbc.user",
        "javax.persistence.jdbc.password", "javax.persistence.jdbc.url", "javax.persistence.jdbc.driver" };

    private static final String[] EMAIL_CONFIG_PROPERTIES = { "mail.smtp.auth", "mail.smtp.starttls.enable",
        "mail.smtp.host", "mail.smtp.port", "mail.smtp.socketFactory.port", "mail.smtp.socketFactory.class",
        "mail.smtp.username", "mail.smtp.password", "mail.smtp.subjectPrefix", "mail.smtp.from", "mail.debug" };

    private static final String[] SOLR_CONFIG_PROPERTIES = { "solr.url", "solr.users.indexTime.full",
        "solr.users.indexTime.delta" };

    private static final String FIM_HTTPS_URL = "fim.https.url";

    @Override
    protected void configure()
    {
        LOG.debug( "Configuring services..." );
        install( new FimDaoModule() );
        // bind services
        bind( ConfigurationService.class ).to( ConfigurationServiceImpl.class );
        bind( DatabaseMigrationService.class ).to( DatabaseMigrationServiceImpl.class );
        bind( EmailService.class ).to( EmailServiceImpl.class );
        bind( EmailGeneratorService.class ).to( EmailGeneratorServiceImpl.class );
        bind( PersistenceService.class ).to( PersistenceServiceImpl.class );
        bind( FimUrlGeneratorService.class ).to( FimUrlGeneratorServiceImpl.class );
        bind( SolrManager.class ).to( SolrManagerImpl.class );

        bind( UserManagerService.class ).to( UserManagerServiceImpl.class );
        bind( ProjectManagementService.class ).to( ProjectManagementServiceImpl.class );
        bind( IssueManagerService.class ).to( IssueManagerServiceImpl.class );
    }

    /**
     * Gets the JPA configuration from environment file.
     * 
     * @return the JPA configuration
     */
    @Provides
    @ServiceConfiguration( ServiceConfigurationType.JPA )
    public Properties getJpaConfiguration()
    {
        LOG.debug( "Reading JPA configuration..." );
        String fimEnvFileName = getEnvironmentFileName();
        LOG.debug( "Reading configuration from environment file: {}", fimEnvFileName );
        Properties props = readConfigurationFrom( fimEnvFileName );
        LOG.debug( "Read JPAconfiguration: {}", props );
        return filterProperties( props, JPA_CONFIG_PROPERTIES );
    }

    /**
     * Gets the Solr configuration from environment file.
     * 
     * @return the Solr configuration
     */
    @Provides
    @ServiceConfiguration( ServiceConfigurationType.SOLR )
    public Properties getSolrConfiguration()
    {
        LOG.debug( "Reading Solr configuration..." );
        String fimEnvFileName = getEnvironmentFileName();
        LOG.debug( "Reading configuration from environment file: {}", fimEnvFileName );
        Properties props = readConfigurationFrom( fimEnvFileName );
        LOG.debug( "Read SolrConfiguration: {}", props );
        return filterProperties( props, SOLR_CONFIG_PROPERTIES );
    }
    /**
     * Gets the e-mail configuration from environment file.
     * 
     * @return the email configuration
     */
    @Provides
    @ServiceConfiguration( ServiceConfigurationType.EMAIL )
    public Properties getEmailConfiguration()
    {
        LOG.debug( "Reading e-mail configuration..." );
        String fimEnvFileName = getEnvironmentFileName();
        LOG.debug( "Reading configuration from environment file: {}", fimEnvFileName );
        Properties props = readConfigurationFrom( fimEnvFileName );
        LOG.debug( "Read EmailConfiguration: {}", props );
        Properties result = filterProperties( props, EMAIL_CONFIG_PROPERTIES );
        addProperty( result, "mail.smtp.password", "fim.email.password" );
        return result;
    }

    /**
     * Gets the URL for F.I.M.
     * 
     * @return the FIM url
     */
    @Provides
    @FimUrlConfiguration
    public String getFimUrlConfiguration()
    {
        LOG.debug( "Reading FIM url configuration..." );
        String fimEnvFileName = getEnvironmentFileName();
        LOG.debug( "Reading configuration from environment file: {}", fimEnvFileName );
        Properties props = readConfigurationFrom( fimEnvFileName );
        String url = props.getProperty( FIM_HTTPS_URL );
        if ( !url.endsWith( "/" ) )
        {
            url += "/";
        }
        return url;
    }

    /**
     * Add / overwrite a property into {@link Properties}. The property is searched in System Properties first and if
     * not found there search continues in Environment Properties.
     * 
     * @param props the {@link Properties} with some already existing mappings
     * @param propsName the key name of the record in {@link Properties}
     * @param systemName the key name of the system property we search for
     */
    private static void addProperty( Properties props, String propsName, String systemName )
    {
        String value = System.getProperty( systemName );
        if ( value == null )
        {
            String envName = systemName.replace( '.', '_' ).toUpperCase();
            value = System.getenv( envName );
        }
        if ( value != null )
        {
            props.setProperty( propsName, value );
        }
    }

    /**
     * Creates a new property that keeps only the specified values in collection.
     * 
     * @param props the original properties
     * @param propertyNames the list of properties to keep
     * @return the new properties
     */
    private static Properties filterProperties( Properties props, String... propertyNames )
    {
        Properties result = new Properties();
        if ( props != null )
        {
            for ( String key : propertyNames )
            {
                String value = props.getProperty( key );
                if ( value != null )
                {
                    result.setProperty( key, value );
                }
            }
        }
        return result;
    }

    /**
     * Gets the environment file name that contains JPA configuration
     * 
     * @return the name of the file with configuration
     */
    private static String getEnvironmentFileName()
    {
        String fimEnv = System.getProperty( "fim.env" );
        if ( StringUtils.isBlank( fimEnv ) )
        {
            fimEnv = System.getenv( "FIM_ENV" );
        }
        if ( StringUtils.isBlank( fimEnv ) )
        {
            fimEnv = "";
        }
        else
        {
            fimEnv = "-" + fimEnv;
        }
        return "env/fim" + fimEnv + ".properties";
    }

    /**
     * Read the configuration from the given file or from default if this file is not found.
     * 
     * @param file the file to read
     * @return the properties from the file
     */
    private Properties readConfigurationFrom( final String file )
    {
        Properties props = new Properties();
        try
        {
            InputStream is = getResourceAsStream( file );
            props.load( is );
            is.close();
            handlePropertyReplacement( props );
        }
        catch ( IOException e )
        {
            LOG.error( "Could not readproperties file", e );
        }
        return props;
    }

    /**
     * Get the config file as {@link InputStream}.
     * 
     * @param file the config file
     * @return the stream
     * @throws FileNotFoundException if file was not found
     */
    private InputStream getResourceAsStream( final String file )
        throws FileNotFoundException
    {
        InputStream is = getClass().getClassLoader().getResourceAsStream( file );
        if ( is == null && !DEFAULT_JPA_CONFIG_FILE.equals( file ) )
        {
            is = getClass().getClassLoader().getResourceAsStream( DEFAULT_JPA_CONFIG_FILE );
        }
        if ( is == null )
        {
            throw new FileNotFoundException( "File " + file + " is not in the classpath" );
        }
        return is;
    }

    /**
     * Handles the property replacement. System variables are replaced here.
     * 
     * @param props the properties to be replaced
     */
    private void handlePropertyReplacement( Properties props )
    {
        LOG.debug( "Performing property replace on: {}", props );
        String url = props.getProperty( JPASettings.JDBC_URL, "" );
        String host = System.getenv( "OPENSHIFT_POSTGRESQL_DB_HOST" );
        if ( StringUtils.isNotBlank( host ) )
        {
            url = url.replace( "${OPENSHIFT_POSTGRESQL_DB_HOST}", host );
        }
        String port = System.getenv( "OPENSHIFT_POSTGRESQL_DB_PORT" );
        if ( StringUtils.isBlank( port ) )
        {
            port = "5432";
        }
        url = url.replace( "${OPENSHIFT_POSTGRESQL_DB_PORT}", port );
        props.setProperty( JPASettings.JDBC_URL, url );
        LOG.debug( "Replaced properties: {}", props );
    }
}
