package pfa.alliance.fim.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains methods for formating dates.
 * 
 * @author Balaceanu Sergiu-Denis
 */
public class DateUtils
{

    private static final Logger LOG = LoggerFactory.getLogger( DateUtils.class );

    public static final String DATE_FORMAT_DAY_FIRST = "dd/MM/yyyy";

    public static final String DATETIME_FORMAT_DAY_FIRST = "dd/MM/yyyy HH:mm:ss";

    public static final String DATE_FORMAT_YEAR_FIRST = "yyyy-MM-dd";

    public static final String DATE_FORMAT_MONTH_FIRST = "MM/dd/yyyy";

    public static final String TIME_FORMAT_HOUR_MINUTE = "HH:mm";

    public static final int EQUAL_DATE = 0;

    /**
     * Converts a string representation in java.util.Date
     * 
     * @param date string representation of the date
     * @return a java.util.Date object
     */
    public static Date formatDate( String date )
    {
        java.util.Date formatDate = new java.util.Date();
        DateFormat dateFormat = new SimpleDateFormat( DATE_FORMAT_DAY_FIRST );

        try
        {
            formatDate = dateFormat.parse( date );
        }
        catch ( ParseException e )
        {
            LOG.debug( "Error {}", e );
        }

        return formatDate;
    }

    public static Date formatDate( String date, String format )
    {
        java.util.Date formatDate = new java.util.Date();
        DateFormat dateFormat = new SimpleDateFormat( format );

        try
        {
            formatDate = dateFormat.parse( date );
        }
        catch ( ParseException e )
        {
            LOG.debug( "{}", e );
        }

        return formatDate;
    }

    /**
     * Converts the Date object representation to a String.
     * 
     * @param date the date to be converted
     * @return the String representing the date in question
     */
    public static String formatDate( Date date )
    {
        String str;

        DateFormat dateFormat = new SimpleDateFormat( DATE_FORMAT_DAY_FIRST );

        str = dateFormat.format( date );

        return str;
    }

    public static String formatDate( Date date, String format )
    {
        String str;

        DateFormat dateFormat = new SimpleDateFormat( format );

        str = dateFormat.format( date );

        return str;
    }

    public static final Date getCurrentDate()
    {
        return Calendar.getInstance().getTime();
    }

    /**
     * Checks if the first argument is before the second argument
     * 
     * @param beforeDate the first argument
     * @param afterDate the second argument
     * @return returns true the first argument is before the second argument.
     */
    public static final Boolean beforeOrEqualDate( Date beforeDate, Date afterDate )
    {
        return beforeDate.compareTo( afterDate ) <= EQUAL_DATE;

    }

    /**
     * Displays a time interval.
     * 
     * @param from the FROM time
     * @param to the TO time
     * @return the time interval
     */
    public static String getTimeInterval( Date from, Date to )
    {
        Period toPeriod = buildTimePeriod( from, to );

        PeriodFormatter dateFormat =
            new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits( 2 ).appendHours().minimumPrintedDigits( 2 ).appendSeparator( ":" ).appendMinutes().minimumPrintedDigits( 2 ).toFormatter();

        return toPeriod.toString( dateFormat );
    }

    /**
     * Builds a time period.
     * 
     * @param from from time
     * @param to to time
     * @return the time period
     */
    public static Period buildTimePeriod( Date from, Date to )
    {
        if ( from == null || to == null )
        {
            throw new IllegalArgumentException( "ALL parameters are manadatory" );
        }

        Interval interval = new Interval( from.getTime(), to.getTime() );
        Period toPeriod = interval.toPeriod();
        return toPeriod;
    }

    /**
     * Builds a time period.
     * 
     * @param from from time
     * @param to to time
     * @return the time period
     */
    public static Period buildTimePeriodUntilNow( Date from )
    {
        if ( from == null )
        {
            throw new IllegalArgumentException( "From parameter is manadatory" );
        }

        Interval interval = new Interval( from.getTime(), System.currentTimeMillis() );
        Period toPeriod = interval.toPeriod();
        return toPeriod;
    }
}
