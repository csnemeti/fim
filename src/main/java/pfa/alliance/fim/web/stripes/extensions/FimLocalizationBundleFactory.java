/**
 * 
 */
package pfa.alliance.fim.web.stripes.extensions;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.LocalizationBundleFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.util.UTF8Control;

/**
 * This class is used as {@link LocalizationBundleFactory} instead of default one in order to allow
 * {@link ResourceBundle} properties files to be read using UTF-8. Since
 * {@link net.sourceforge.stripes.localization.DefaultLocalizationBundleFactory} is useless if extended in the form it
 * is, I copy-paste it here and did the necessary changes.
 * 
 * @author Csaba
 */
public class FimLocalizationBundleFactory
    implements LocalizationBundleFactory
{
    private static final Logger LOG = LoggerFactory.getLogger( FimLocalizationBundleFactory.class );

    /** This class is used for loading UTF-8 based resources from properties file. */
    private static final ResourceBundle.Control UTF8_CONTROL = new UTF8Control();

    /** The name of the default resource bundle for Stripes. */
    public static final String BUNDLE_NAME = "StripesResources";

    /** The configuration parameter for changing the default error message resource bundle. */
    public static final String ERROR_MESSAGE_BUNDLE = "LocalizationBundleFactory.ErrorMessageBundle";

    /** The configuration parameter for changing the default field name resource bundle. */
    public static final String FIELD_NAME_BUNDLE = "LocalizationBundleFactory.FieldNameBundle";

    /** Holds the configuration passed in at initialization time. */
    private Configuration configuration;

    private String errorBundleName;

    private String fieldBundleName;

    /**
     * Uses the BootstrapPropertyResolver attached to the Configuration in order to look for configured bundle names in
     * the servlet init parameters etc. If those can't be found then the default bundle names are put in place.
     */
    @Override
    public void init( Configuration configuration )
        throws Exception
    {
        setConfiguration( configuration );

        this.errorBundleName = configuration.getBootstrapPropertyResolver().getProperty( ERROR_MESSAGE_BUNDLE );
        if ( this.errorBundleName == null )
        {
            this.errorBundleName = BUNDLE_NAME;
        }

        this.fieldBundleName = configuration.getBootstrapPropertyResolver().getProperty( FIELD_NAME_BUNDLE );
        if ( this.fieldBundleName == null )
        {
            this.fieldBundleName = BUNDLE_NAME;
        }
        LOG.info( "Localization initialized" );
    }

    /**
     * Looks for a bundle called StripesResources with the supplied locale if one is provided, or with the default
     * locale if the locale provided is null.
     *
     * @param locale an optional locale, may be null.
     * @return ResourceBundle a bundle in which to look for localized error messages
     * @throws MissingResourceException if a suitable bundle cannot be found
     */
    @Override
    public ResourceBundle getErrorMessageBundle( Locale locale )
        throws MissingResourceException
    {
        try
        {
            if ( locale == null )
            {
                return ResourceBundle.getBundle( this.errorBundleName, UTF8_CONTROL );
            }
            else
            {
                return ResourceBundle.getBundle( this.errorBundleName, locale, UTF8_CONTROL );
            }
        }
        catch ( MissingResourceException mre )
        {
            MissingResourceException mre2 =
                new MissingResourceException(
                                              "Could not find the error message resource bundle needed by Stripes. This "
                                                  + "almost certainly means that a properties file called '"
                                                  + this.errorBundleName
                                                  + ".properties' could not be found in the classpath. "
                                                  + "This properties file is needed to lookup validation error messages. Please "
                                                  + "ensure the file exists in WEB-INF/classes or elsewhere in your classpath.",
                                              this.errorBundleName, "" );
            mre2.setStackTrace( mre.getStackTrace() );
            throw mre2;
        }
    }

    /**
     * Looks for a bundle called StripesResources with the supplied locale if one is provided, or with the default
     * locale if the locale provided is null.
     *
     * @param locale an optional locale, may be null.
     * @return ResourceBundle a bundle in which to look for localized field names
     * @throws MissingResourceException if a suitable bundle cannot be found
     */
    @Override
    public ResourceBundle getFormFieldBundle( Locale locale )
        throws MissingResourceException
    {
        try
        {
            if ( locale == null )
            {
                return ResourceBundle.getBundle( this.fieldBundleName, UTF8_CONTROL );
            }
            else
            {
                return ResourceBundle.getBundle( this.fieldBundleName, locale, UTF8_CONTROL );
            }
        }
        catch ( MissingResourceException mre )
        {
            MissingResourceException mre2 =
                new MissingResourceException( "Could not find the form field resource bundle needed by Stripes. This "
                    + "almost certainly means that a properties file called '" + this.fieldBundleName
                    + ".properties' could not be found in the classpath. "
                    + "This properties file is needed to lookup form field names. Please "
                    + "ensure the file exists in WEB-INF/classes or elsewhere in your classpath.",
                                              this.fieldBundleName, "" );
            mre2.setStackTrace( mre.getStackTrace() );
            throw mre2;
        }
    }

    protected Configuration getConfiguration()
    {
        return configuration;
    }

    protected void setConfiguration( Configuration configuration )
    {
        this.configuration = configuration;
    }
}