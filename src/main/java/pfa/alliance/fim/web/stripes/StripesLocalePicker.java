/**
 * 
 */
package pfa.alliance.fim.web.stripes;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.localization.DefaultLocalePicker;
import pfa.alliance.fim.web.util.LocaleUtils;

/**
 * This class is used for changing the used {@link Locale} in a session.
 * 
 * @author Csaba
 */
public class StripesLocalePicker
    extends DefaultLocalePicker
{
    /** The attribute name used to store on session desired Locale. */
    public static final String LOCALE = "pfa.alliance.fim.web.LOCALE";

    @Override
    public Locale pickLocale( HttpServletRequest request )
    {
        Locale locale = LocaleUtils.getLocaleFrom( request );

        if ( locale == null )
        {
            locale = super.pickLocale( request );
        }
        return locale;
    }
}