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
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

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

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

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

    public Resolution search()
    {
        LOG.debug( "Searching for: {}", userSearch );
        showResults = true;
        return new ForwardResolution( FimPageURLs.USER_SEARCH_JSP.getURL() );
    }

    public Resolution results()
    {
        LOG.debug( "Results for: {}", userSearch );
        JSONObject result = new JSONObject();
        result.put( "recordsTotal", 0 );
        // result.put( "recordsTotal", 0 );
        result.put( "data", new ArrayList<UserSearchDTO>() );
        return new StreamingResolution( "application/json", new StringReader( result.toString() ) );
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
        builder.addParameter( "userSearch.defaultRole", getNotNullParameterValues( userSearch.getRoles() ) );
        return builder.toString();
    }
    public List<StripesUserRole> getDefaultRoles()
    {
        List<StripesUserRole> roles = new ArrayList<StripesUserRole>();
        UserRole[] orderedRoles =
            new UserRole[] { UserRole.ADMIN, UserRole.PROJECT_ADMIN, UserRole.PRODUCT_OWNER, UserRole.SCRUM_MASTER,
                UserRole.TEAM, UserRole.STATISTICAL };
        for ( UserRole role : orderedRoles )
        {
            roles.add( new StripesUserRole( role, getMessage( role.getDeclaringClass().getName() + "." + role.name() ) ) );
        }
        return roles;
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
        try{
            if(StringUtils.isNotBlank( value )){
                result = URLEncoder.encode( value, "UTF-8" );
            }
            else
            {
                result = "";
            }
        }catch(UnsupportedEncodingException e){
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
                if(StringUtils.isNotBlank( value )){
                    try{
                        results.add(URLEncoder.encode( value, "UTF-8" ) );
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
