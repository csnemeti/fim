/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.UserSearchDTO;
import pfa.alliance.fim.dto.UserSearchResultDTO;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.datatables.DatatablesOrder;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;

/**
 * User search action bean.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/search" )
@FimSecurity( )
public class SearchUsersActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( SearchUsersActionBean.class );

    private UserSearchDTO userSearch = new UserSearchDTO();

    private List<UserSearchDTO> results;

    private boolean showResults = false;

    /** Used in search result, this is a flag we need to respond with when return results. */
    private int draw;

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

    /** Ordering criteria in table. */
    private DatatablesOrder order0 = new DatatablesOrder();

    @Inject
    public SearchUsersActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution view()
    {
        LOG.debug( "Show search page: {}", userSearch );
        return new ForwardResolution( FimPageURLs.USER_SEARCH_JSP.getURL() );
    }

    /**
     * This method is built to display the search result page. The method will not do the search itself. The search is
     * controlled from client side where items / page, ordering criteria and other properties can be specified.
     * 
     * @return the forward to User search page
     */
    public Resolution search()
    {
        LOG.debug( "Searching for: {}", userSearch );
        showResults = true;
        return new ForwardResolution( FimPageURLs.USER_SEARCH_JSP.getURL() );
    }

    /**
     * This method is called through AJAX from DataTables in order to fill in the results.
     * 
     * @return a JSon with results
     */
    public Resolution results()
    {
        fillInOrdering();
        LOG.debug( "Results for: {}, {}", userSearch, order0 );
        JSONObject result = new JSONObject();
        result.put( "draw", draw );
        long resultsNumber = userManagerService.count( userSearch );
        result.put( "recordsTotal", resultsNumber );
        List<UserSearchResultDTO> filteredResults;
        if ( resultsNumber != 0L )
        {
            filteredResults = userManagerService.search( userSearch );
        }
        else
        {
            filteredResults = new ArrayList<>();
        }
        // result.put( "recordsFiltered", filteredResults.size() );
        result.put( "recordsFiltered", resultsNumber );
        result.put( "data", filteredResults );
        return new StreamingResolution( "application/json", new StringReader( result.toString( 3 ) ) );
    }

    public UserSearchDTO getUserSearch()
    {
        return userSearch;
    }

    public void setUserSearch( UserSearchDTO userSearch )
    {
        this.userSearch = userSearch;
    }

    public List<UserSearchDTO> getResults()
    {
        return results;
    }

    public void setResults( List<UserSearchDTO> results )
    {
        this.results = results;
    }

    public boolean isShowResults()
    {
        return showResults;
    }

    public String getResultsUrl()
    {
        UrlBuilder builder = new UrlBuilder( getContext().getLocale(), getClass(), false );
        builder.setEvent( "results" );
        builder.addParameter( "userSearch.firstName", getNotNullParameterValue( userSearch.getFirstName() ) );
        builder.addParameter( "userSearch.lastName", getNotNullParameterValue( userSearch.getLastName() ) );
        builder.addParameter( "userSearch.email", getNotNullParameterValue( userSearch.getEmail() ) );
        builder.addParameter( "userSearch.roles", getNotNullParameterValues( userSearch.getRoles() ) );
        builder.addParameter( "userSearch.statuses", getNotNullParameterValues( userSearch.getStatuses() ) );
        builder.addParameter( "filterDataTablesCall", true );
        return builder.toString();
    }

    public List<StripesDropDownOption> getDefaultRoles()
    {
        List<StripesDropDownOption> roles = new ArrayList<StripesDropDownOption>();
        UserRole[] orderedRoles =
            new UserRole[] { UserRole.ADMIN, UserRole.PROJECT_ADMIN, UserRole.PRODUCT_OWNER, UserRole.SCRUM_MASTER,
                UserRole.TEAM, UserRole.STATISTICAL };
        for ( UserRole role : orderedRoles )
        {
            roles.add( new StripesDropDownOption( role, getEnumMessage( role ) ) );
        }
        return roles;
    }

    public List<StripesDropDownOption> getDefaultStatuses()
    {
        List<StripesDropDownOption> statuses = new ArrayList<StripesDropDownOption>();
        UserStatus[] orderedStatuses = new UserStatus[] { UserStatus.NEW, UserStatus.ACTIVE, UserStatus.DISABLED };
        for ( UserStatus status : orderedStatuses )
        {
            statuses.add( new StripesDropDownOption( status, getEnumMessage( status ) ) );
        }
        return statuses;
    }

    /**
     * Encode the value into not-null parameter value.
     * 
     * @param value the value to encode
     * @return the value itself UTF-8 encoded OR empty string if value is null
     */
    private static String getNotNullParameterValue( String value )
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
    private static Object[] getNotNullParameterValues( String[] values )
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

    public void setDraw( int draw )
    {
        this.draw = draw;
    }

    /**
     * Used in search result, this is a flag telling us the index of the item is search start offset.
     * 
     * @param the start index
     */
    public void setStart( int start )
    {
        userSearch.setStartIndex( start );
    }

    /**
     * Used in search result, this is a flag telling us expected items per page.
     * 
     * @param length the items per page
     */
    public void setLength( int length )
    {
        userSearch.setItemsPerPage( length );
    }

    public DatatablesOrder getOrder0()
    {
        return order0;
    }

    public String getLocalizationString()
    {
        JSONObject root = new JSONObject();
        for ( UserStatus userStatus : UserStatus.values() )
        {
            root.put( userStatus.name(), getEnumMessage( userStatus ) );
        }
        for ( UserRole role : UserRole.values() )
        {
            root.put( role.name(), getEnumMessage( role ) );
        }
        return root.toString();
    }

    /**
     * "Transfer" ordering data from {@link #order0} into {@link #userSearch}.
     */
    private void fillInOrdering()
    {
        // any value that is not descending will mean ascending
        userSearch.setAscending( !"desc".equalsIgnoreCase( order0.getDir() ) );
        String column = null;
        switch ( order0.getColumn() )
        {
            case 2:
                column = "firstName";
                break;
            case 3:
                column = "lastName";
                break;
            case 4:
                column = "email";
                break;
            default:
                LOG.warn( "Wrong ordering column value (it will use default ordering)): {}", order0.getColumn() );
                break;
        }
        userSearch.setOrderBy( column );
    }
}
