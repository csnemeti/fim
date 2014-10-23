/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.Locale;
import java.util.Map;

/**
 * This service is used for getting the subject and the content of an e-mail.
 * 
 * @author Csaba
 */
public interface EmailGeneratorService
{
    /**
     * Gets the subject of an e-mail.
     * 
     * @param emailType the type of the e-mail (part of the key)
     * @param parameters the key - value mapping to be used while generating the text
     * @param locale the desired {@link Locale}
     * @return the e-mail subject to be used
     */
    String getSubject( EmailType emailType, Map<String, Object> parameters, Locale locale );

    /**
     * Gets the content of an e-mail.
     * 
     * @param emailType the type of the e-mail
     * @param parameters the key - value mapping to be used while generating the text
     * @param locale the desired {@link Locale}
     * @return the e-mail content to be used
     */
    String getContent( EmailType emailType, Map<String, Object> parameters, Locale locale );
}
