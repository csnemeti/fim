package pfa.alliance.fim.web.stripes.action;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.LocalizableMessage;

import org.joda.time.Period;

import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.util.UTF8Control;

/**
 * Base entity for action beans that implements trivial methods.
 * 
 * @author Balaceanu Sergiu-Denis
 */
public abstract class BaseActionBean
    implements ActionBean
{
    /** This class is used for loading UTF-8 based resources from properties file. */
    private static final ResourceBundle.Control UTF8_CONTROL = new UTF8Control();

    private ActionBeanContext context;

    @Override
    public void setContext( ActionBeanContext context )
    {
        this.context = context;

        // configuration for JSTL - FMT
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession( true );
        Locale locale = request.getLocale();

        ResourceBundle bundle = ResourceBundle.getBundle( "StripesResources", locale, UTF8_CONTROL );
        Config.set( session, Config.FMT_LOCALIZATION_CONTEXT, new LocalizationContext( bundle, locale ) );
    }

    @Override
    public ActionBeanContext getContext()
    {
        return context;
    }

    /**
     * Gets the user session. A new session will be created if there's none.
     * 
     * @return the user session
     */
    protected HttpSession getSession()
    {
        return context.getRequest().getSession( true );
    }

    /**
     * Gets a localization messages.
     * 
     * @param key the message key
     * @param parameters parameters for the message
     * @return the message to be displayed
     */
    protected String getMessage( String key, Object... parameters )
    {
        return new LocalizableMessage( key, parameters ).getMessage( context.getLocale() );
    }

    /**
     * Gets the localized name of an enumeration values.
     * 
     * @param value the enumeration
     * @return the localized value
     */
    protected String getEnumMessage( Enum<?> value )
    {
        return getMessage( value.getDeclaringClass().getName() + "." + value.name() );
    }

    /**
     * Gets a formated period representing the time difference from the past date until now.
     * 
     * @param pastDate the past date
     * @return the formated time period
     */
    protected String getFormatedUntilNowPeriod( Date pastDate )
    {
        Period period = DateUtils.buildTimePeriodUntilNow( pastDate );
        String localizedMessage = null;

        int years = period.getYears();
        int months = period.getMonths();
        int weeks = period.getWeeks();
        int days = period.getDays();
        int hours = period.getHours();
        int minutes = period.getMinutes();
        if ( years > 0 )
        {
            localizedMessage = getMessage( "period.yearsAdnMonths", years, months );
        }
        else if ( months > 0 )
        {
            localizedMessage = getMessage( "period.monthsAndWeeks", months, weeks );
        }
        else if ( weeks > 0 )
        {
            localizedMessage = getMessage( "period.weeksAndDays", weeks, days );
        }
        else if ( days > 0 )
        {
            localizedMessage = getMessage( "period.daysAndHours", days, hours );
        }
        else if ( hours > 0 )
        {
            localizedMessage = getMessage( "period.hoursAndMinutes", hours, minutes );
        }
        else if ( minutes > 0 )
        {
            localizedMessage = getMessage( "period.minutes", minutes );
        }
        else
        {
            localizedMessage = getMessage( "period.now" );
        }

        return localizedMessage;
    }
}
