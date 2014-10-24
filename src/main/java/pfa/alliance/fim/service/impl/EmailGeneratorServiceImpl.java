/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.inject.Singleton;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.NameProvider;

/**
 * This class implements the {@link EmailGeneratorService}.
 * 
 * @author Csaba
 */
@Singleton
public class EmailGeneratorServiceImpl
    implements EmailGeneratorService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( EmailGeneratorServiceImpl.class );

    /** This class is used for loading UTF-8 based resources from properties file. */
    private final ResourceBundle.Control utf8Rbontrol = new UTF8Control();

    @Override
    public String getSubject( NameProvider emailType, Map<String, Object> parameters, Locale locale )
    {
        LOG.debug( "Getting subject for: EmailType = {}, parameters = {}, locale = {}", emailType, parameters, locale );
        ResourceBundle stripesResourceBundle = ResourceBundle.getBundle( "StripesResources", locale, utf8Rbontrol );
        String message = stripesResourceBundle.getString( "email.subject." + emailType );
        // TODO process message
        LOG.debug( "Subject for: EmailType = {}, parameters = {}, locale = {} --> {}", emailType, parameters, locale,
                   message );
        return message;
    }

    @Override
    public String getContent( NameProvider emailType, Map<String, Object> parameters, Locale locale )
    {
        LOG.debug( "Getting content for: EmailType = {}, parameters = {}, locale = {}", emailType, parameters, locale );
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template template =
            ve.getTemplate( "email/" + emailType.name().toLowerCase() + "_" + locale.getLanguage() + ".vm", "UTF-8" );
        VelocityContext context = new VelocityContext();
        for ( Map.Entry<String, Object> parameter : parameters.entrySet() )
        {
            context.put( parameter.getKey(), parameter.getValue().toString() );
        }
        StringWriter writer = new StringWriter();
        template.merge( context, writer );

        return writer.toString();
    }

    private static class UTF8Control
        extends Control
    {
        @Override
        public ResourceBundle newBundle( String baseName, Locale locale, String format, ClassLoader loader,
                                         boolean reload )
            throws IllegalAccessException, InstantiationException, IOException
        {
            // The below is a copy of the default implementation.
            String bundleName = toBundleName( baseName, locale );
            String resourceName = toResourceName( bundleName, "properties" );
            ResourceBundle bundle = null;
            InputStream stream = null;
            if ( reload )
            {
                URL url = loader.getResource( resourceName );
                if ( url != null )
                {
                    URLConnection connection = url.openConnection();
                    if ( connection != null )
                    {
                        connection.setUseCaches( false );
                        stream = connection.getInputStream();
                    }
                }
            }
            else
            {
                stream = loader.getResourceAsStream( resourceName );
            }
            if ( stream != null )
            {
                try
                {
                    // Only this line is changed to make it to read properties files as UTF-8.
                    bundle = new PropertyResourceBundle( new InputStreamReader( stream, "UTF-8" ) );
                }
                finally
                {
                    stream.close();
                }
            }
            return bundle;
        }
    }
}
