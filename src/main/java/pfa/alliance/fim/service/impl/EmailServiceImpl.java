/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Arrays;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.EmailService;

/**
 * This class is used for sending e-mails.
 * 
 * @author Csaba
 */
@Singleton
class EmailServiceImpl
    implements EmailService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( EmailServiceImpl.class );

    /** The e-mail configuration provider. */
    private Provider<Properties> emailConfiguration;

    @Inject
    EmailServiceImpl( @ServiceConfiguration( ServiceConfigurationType.EMAIL ) Provider<Properties> emailConfiguration )
    {
        this.emailConfiguration = emailConfiguration;
    }

    @Override
    public void sendEmail( String to, String subject, String content )
        throws MessagingException
    {
        sendEmail( null, new String[] { to }, null, null, subject, content );

    }

    @Override
    public void sendEmail( String from, String[] to, String[] cc, String[] bcc, String subject, String content )
        throws MessagingException
    {
        LOG.debug( "Received for sending e-mail: FROM = {}, TO = {}, CC = {}, BCC = {}, Subject = {}, content size = {}",
                   from, Arrays.toString( to ), Arrays.toString( cc ), Arrays.toString( bcc ), subject,
                   content.length() );

        Properties properties = emailConfiguration.get();
        String localFrom = from;
        if ( StringUtils.isBlank( localFrom ) )
        {
            localFrom = properties.getProperty( "mail.smtp.from" ).trim();
        }
        Authenticator authenticator = getAuthenticator( properties );
        Session session = Session.getInstance( properties, authenticator );
        LOG.debug( "Building message..." );
        Message message =
            createMessage( session, localFrom, to, cc, bcc, properties.getProperty( "mail.smtp.subjectPrefix" ), subject,
                           content );
        LOG.debug( "Sending message..." );
        sendMessage( message );
        LOG.info( "E-mail sent: FROM = {}, TO = {}, CC = {}, BCC = {}, Subject = {}", localFrom, Arrays.toString( to ),
                  Arrays.toString( cc ), Arrays.toString( bcc ), subject );
    }

    /**
     * Sends the message.
     * 
     * @param message the message to be sent
     * @throws MessagingException if sending fails
     */
    void sendMessage( Message message )
        throws MessagingException
    {
        try
        {
            Transport.send( message );
        }
        catch ( MessagingException e )
        {
            LOG.error( "Unable to send message", e );
            throw e;
        }
    }

    /**
     * Gets the {@link Authenticator} to be used in e-mail sending.
     * 
     * @param properties the {@link Properties} with all configurations
     * @return the {@link Authenticator} that should be used or null if authentication is not required
     */
    private Authenticator getAuthenticator( Properties properties )
    {
        Authenticator authenticator = null;
        if ( "true".equals( properties.getProperty( "mail.smtp.auth" ) ) )
        {
            authenticator = new Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication( properties.getProperty( "mail.smtp.username" ),
                                                       properties.getProperty( "mail.smtp.password" ) );
                }
            };
        }
        return authenticator;
    }

    /**
     * Create the message that should be sent.
     * 
     * @param session the {@link Session} required for creating the message
     * @param from the address from where e-mails are sent
     * @param to the list of users who are targeted for TO
     * @param cc the list of users who are targeted for CC
     * @param bcc the list of users who are targeted for BCC
     * @param subjectPrefix if not null, this text will be preceding subject
     * @param subject the e-mail subject
     * @param content the e-mail content
     * @return the created {@link Message}
     */
    private Message createMessage( Session session, String from, String[] to, String[] cc, String[] bcc,
                                   String subjectPrefix, String subject, String content )
        throws MessagingException
    {
        Message message = new MimeMessage( session );
        try
        {
            addRecipient( message, to, RecipientType.TO );
            addRecipient( message, cc, RecipientType.CC );
            addRecipient( message, bcc, RecipientType.BCC );

            String subjectValue;
            if ( StringUtils.isNotBlank( subjectPrefix ) )
            {
                subjectValue = subjectPrefix.trim() + " " + subject;
            }
            else
            {
                subjectValue = subject;
            }
            message.setFrom( new InternetAddress( from ) );
            message.setSubject( subjectValue );
            message.setContent( content, "text/html; charset=utf-8" );
        }
        catch ( MessagingException e )
        {
            LOG.error( "Could not create message", e );
            throw e;
        }
        return message;
    }

    /**
     * Add recipients to message.
     * 
     * @param message the {@link Message} that should be sent
     * @param recipients the recipients that should receive the message
     * @param type the type of the recipients
     * @throws javax.mail.MessagingException if the address is not correct
     */
    private void addRecipient( Message message, String[] recipients, RecipientType type )
        throws MessagingException
    {
        if ( recipients != null )
        {
            for ( String recipient : recipients )
            {
                try
                {
                    message.addRecipient( type, new InternetAddress( recipient ) );
                }
                catch ( MessagingException e )
                {
                    LOG.error( "Could not add recipient: {}", recipient, e );
                    throw e;
                }
            }
        }
    }
}
