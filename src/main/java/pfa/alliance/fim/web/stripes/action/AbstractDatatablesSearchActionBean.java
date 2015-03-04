/**
 * 
 */
package pfa.alliance.fim.web.stripes.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.AbstractSearchDTO;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.datatables.DatatablesOrder;

/**
 * Abstract class for pages that contains a Datatables control for showing search result by making server side
 * processing.
 * 
 * @author Csaba
 */
public abstract class AbstractDatatablesSearchActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractDatatablesSearchActionBean.class );

    /** The place where calls should be forwarder. */
    private final FimPageURLs forwardPage;

    /** Flag indicating results table should be displayed. */
    private boolean showResults = false;

    /** Used in search result, this is a flag we need to respond with when return results. */
    private int draw;

    /** Ordering criteria in table. */
    private DatatablesOrder order0 = new DatatablesOrder();

    /**
     * Called when instance of this class is created.
     * 
     * @param forwardPage the page where calls should be forwarded
     */
    protected AbstractDatatablesSearchActionBean( final FimPageURLs forwardPage )
    {
        this.forwardPage = forwardPage;
    }

    @DefaultHandler
    public Resolution view()
    {
        LOG.debug( "Show search page" );
        return new ForwardResolution( forwardPage.getURL() );
    }

    /**
     * This method is built to display the search result page. The method will not do the search itself. The search is
     * controlled from client side where items / page, ordering criteria and other properties can be specified.
     * 
     * @return the forward to User search page
     */
    public Resolution search()
    {
        LOG.debug( "Searching for: {}", getSearchObject() );
        showResults = true;
        return new ForwardResolution( forwardPage.getURL() );
    }

    /**
     * Gets the search object.
     * 
     * @return the search object
     */
    protected abstract AbstractSearchDTO getSearchObject();

    public boolean isShowResults()
    {
        return showResults;
    }

    public void setDraw( int draw )
    {
        this.draw = draw;
    }

    protected int getDraw()
    {
        return draw;
    }

    /**
     * Used in search result, this is a flag telling us the index of the item is search start offset.
     * 
     * @param start the start index
     */
    public void setStart( int start )
    {
        getSearchObject().setStartIndex( start );
    }

    /**
     * Used in search result, this is a flag telling us expected items per page.
     * 
     * @param length the items per page
     */
    public void setLength( int length )
    {
        getSearchObject().setItemsPerPage( length );
    }

    public DatatablesOrder getOrder0()
    {
        return order0;
    }

    /**
     * Encode the value into not-null parameter value.
     * 
     * @param value the value to encode
     * @return the value itself UTF-8 encoded OR empty string if value is null
     */
    protected static String getNotNullParameterValue( String value )
    {
        String result = null;
        try
        {
            if ( StringUtils.isNotBlank( value ) )
            {
                result = URLEncoder.encode( value, "UTF-8" );
            }
            else
            {
                result = "";
            }
        }
        catch ( UnsupportedEncodingException e )
        {
            LOG.error( "Could not UrlEncode {}", value, e );
        }
        if ( result == null )
        {
            result = value;
        }
        return result;
    }

    /**
     * Encode the values into not-null parameter values.
     * 
     * @param values the value to encode
     * @return the value itself UTF-8 encoded OR empty string if value is null
     */
    protected static Object[] getNotNullParameterValues( String[] values )
    {
        List<String> results = new ArrayList<String>();
        if ( values != null )
        {
            for ( String value : values )
            {
                if ( StringUtils.isNotBlank( value ) )
                {
                    try
                    {
                        results.add( URLEncoder.encode( value, "UTF-8" ) );
                    }
                    catch ( UnsupportedEncodingException e )
                    {
                        LOG.error( "Could not UrlEncode {}", value, e );
                        results.add( value );
                    }
                }
            }
        }
        return results.toArray();
    }

}
