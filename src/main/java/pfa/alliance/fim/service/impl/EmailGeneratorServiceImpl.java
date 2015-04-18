/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
import pfa.alliance.fim.util.UTF8Control;

/**
 * This class implements the {@link EmailGeneratorService}.
 * 
 * @author Csaba
 */
@Singleton
class EmailGeneratorServiceImpl
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
        // process message
        LOG.debug( "Subject for: EmailType = {}, parameters = {}, locale = {} --> {}", emailType, parameters, locale,
                   message );
        if ( parameters != null )
        {
            for ( Map.Entry<String, Object> parameter : parameters.entrySet() )
            {
                message = message.replaceAll( "\\{" + parameter.getKey() + "\\}", parameter.getValue().toString() );
            }
        }
        return message;
    }

    @Override
    public String getContent( NameProvider emailType, Map<String, Object> parameters, Locale locale )
    {
        LOG.debug( "Getting content for: EmailType = {}, parameters = {}, locale = {}", emailType, parameters, locale );
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty( RuntimeConstants.RESOURCE_LOADER, "classpath" );
        ve.setProperty( "classpath.resource.loader.class", ClasspathResourceLoader.class.getName() );
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
}
