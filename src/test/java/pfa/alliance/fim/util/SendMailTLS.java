/**
 * 
 */
package pfa.alliance.fim.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Ignore;

@Ignore
public class SendMailTLS
{

    public static void main( String[] args )
    {

        final String username = "fim.pfaalliance@gmail.com";
        final String password = "Far1ng0Sfert";

        Properties props = new Properties();
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.debug", "true" );
        props.put( "mail.smtp.starttls.enable", "true" );
        props.put( "mail.smtp.host", "smtp.gmail.com" );
        props.put( "mail.smtp.port", "587" );

        Session session = Session.getInstance( props, new javax.mail.Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication( username, password );
            }
        } );

        try
        {

            Message message = new MimeMessage( session );
            message.setFrom( new InternetAddress( username ) );
            message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( "nd7275@gmail.com" ) );
            message.setSubject( "Testing Subject TSL" );
            message.setText( "Dear Mail Crawler," + "\n\n No spam to my email, please!" );

            Transport.send( message );

            System.out.println( "Done" );

        }
        catch ( MessagingException e )
        {
            throw new RuntimeException( e );
        }
    }
}