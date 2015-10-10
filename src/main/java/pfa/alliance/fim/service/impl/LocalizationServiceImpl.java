/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Singleton;

import pfa.alliance.fim.service.LocalizationService;
import pfa.alliance.fim.util.UTF8Control;

/**
 * This is the implementation for {@link LocalizationService}.
 * 
 * @author Csaba
 */
@Singleton
class LocalizationServiceImpl
    implements LocalizationService
{

    /** This class is used for loading UTF-8 based resources from properties file. */
    private final ResourceBundle.Control utf8Rbontrol = new UTF8Control();

    @Override
    public ResourceBundle getBundle( Locale locale )
    {
        return ResourceBundle.getBundle( "StripesResources", locale, utf8Rbontrol );
    }

}
