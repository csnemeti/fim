/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This service is used in order to allow Service methods to put localized text in response.
 * 
 * @author Csaba
 */
public interface LocalizationService
{
    /**
     * Gets the resource bundle responsible for localization messages.
     * 
     * @param locale the desired locale
     * @return the corresponding {@link ResourceBundle}
     */
    ResourceBundle getBundle( Locale locale );
}
