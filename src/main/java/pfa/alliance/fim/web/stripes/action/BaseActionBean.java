package pfa.alliance.fim.web.stripes.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.LocalizableMessage;

import org.joda.time.Period;

import pfa.alliance.fim.util.DateUtils;

/**
 * Base entity for action beans that implements trivial methods.
 * 
 * @author Balaceanu Sergiu-Denis
 */
public abstract class BaseActionBean
    implements ActionBean
{
    private ActionBeanContext context;

    @Override
    public void setContext( ActionBeanContext context )
    {
        this.context = context;
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
