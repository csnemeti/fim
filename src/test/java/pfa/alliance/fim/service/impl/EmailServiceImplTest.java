/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Properties;

import javax.inject.Provider;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class is used for testing {@link EmailServiceImpl}.
 * 
 * @author Csaba
 */
public class EmailServiceImplTest
{
    /** This keeps the e-mail configuration. */
    private Properties emailProperties;

    /** This provides the e-mail configuration. */
    private Provider<Properties> emailConfigurationProvider;

    private EmailServiceImplMock emailServiceImpl;

    @Before
    public void init()
    {
        emailProperties = new Properties();
        emailConfigurationProvider = Mockito.mock( Provider.class );
        Mockito.when( emailConfigurationProvider.get() ).thenReturn( emailProperties );
        emailServiceImpl = new EmailServiceImplMock( emailConfigurationProvider );
    }

    @Test
    public void test_sendEmail_withAuthentication()
        throws Exception
    {
        emailProperties.setProperty( "mail.smtp.auth", "true" );
        emailProperties.setProperty( "mail.smtp.username", "username" );
        emailProperties.setProperty( "mail.smtp.password", "password" );
        emailProperties.setProperty( "mail.smtp.subjectPrefix", "[Testing]" );
        emailServiceImpl.sendEmail( "from@test.com", new String[] { "to1@test.com" }, new String[0], null, "Subject 1",
                                    "Content" );
        Assert.assertEquals( "Invokation issue", true, emailServiceImpl.sendMessageCalled );
    }

    @Test
    public void test_sendEmail_noAuthentication()
        throws Exception
    {
        emailProperties.setProperty( "mail.smtp.from", "from@test.com" );
        emailServiceImpl.sendEmail( "to1@test.com", "Subject 1", "Content" );
        Assert.assertEquals( "Invokation issue", true, emailServiceImpl.sendMessageCalled );
    }

    @Test( expected = MessagingException.class )
    public void test_sendEmail_wrongEmail()
        throws Exception
    {
        emailServiceImpl.sendEmail( "from@test.com", new String[] { "" }, null, null, "Subject 2", "Content" );
    }

    private static class EmailServiceImplMock
        extends EmailServiceImpl
    {
        /** Flag telling that sendMessage method was called. */
        private boolean sendMessageCalled = false;

        public EmailServiceImplMock( Provider<Properties> emailConfiguration )
        {
            super( emailConfiguration );
        }

        @Override
        void sendMessage( Message message )
            throws MessagingException
        {
            sendMessageCalled = true;
        }
    }
}
