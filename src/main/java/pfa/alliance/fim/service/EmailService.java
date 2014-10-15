/**
 * 
 */
package pfa.alliance.fim.service;

import javax.mail.MessagingException;

/**
 * This service is used for sending e-mails.
 * 
 * @author Csaba
 */
public interface EmailService
{
    /**
     * Send an e-mail.
     * 
     * @param from the FROM address
     * @param to the TO address
     * @param cc the list of persons who should be in CC
     * @param bcc the list of persons who should be in BCC
     * @param subject the e-mail subject
     * @param content the e-mail HTML content
     * @throws MessagingException in case sending fails
     */
    void sendEmail( String from, String[] to, String[] cc, String[] bcc, String subject, String content )
        throws MessagingException;
}
