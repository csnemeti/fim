/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.UserDTO;
import pfa.alliance.fim.service.SearchService;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.stripes.action.BaseActionBean;

/**
 * This class represents a user autocomplete.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/user/ac/{id}" )
@FimSecurity( checkIfAny = Permission.PROJECT_EDIT_PROJECT )
public class UserAutocompleteActionBean
    extends BaseActionBean
{
    private static final Logger LOG = LoggerFactory.getLogger( UserAutocompleteActionBean.class );

    /** The types text in autocomplete. */
    @Validate( required = true, trim = true )
    private String query;

    /** The ID of the project we're interested on. */
    @Validate( required = true )
    private Integer id;

    private SearchService searchService;

    private static final Map<Integer, String> names = new HashMap<Integer, String>();

    static
    {
        names.put( 1, "Csaba" );
        names.put( 2, "Monika" );
        names.put( 3, "Denis" );
        names.put( 4, "Sergiu" );
    }

    /**
     * Called when instance of this class is created.
     */
    @Inject
    public UserAutocompleteActionBean( SearchService searchService )
    {
        super();
        LOG.debug( "Create class instance..." );
        this.searchService = searchService;
    }

    /**
     * Returns the city's list for the autocomplete drop down.
     *
     * @return a StreamingResolution with the city's list
     */
    @DefaultHandler
    public Resolution project()
    {
        LOG.debug( "Get project autocomplete for id = {}, query = {}", id, query );
        List<UserDTO> searchResult = searchService.findActiveUsersNotAssignedToProject( id, query );
        LOG.debug( "Search results: {}", searchResult );
        StringBuilder sb = new StringBuilder( "autocompleteRes = [" );
        String separator = "";
        for ( UserDTO user : searchResult )
        {
            sb.append( separator ).append( "{id : " ).append( user.getId() ).append( ", name : \"" );
            appendNameAndEmailForUser( user, sb ).append( "\"}" );
            separator = ", ";
        }
        sb.append( "];" );
        return ( new StreamingResolution( "text/javascript", sb.toString() ) ).setFilename( "users.js" ).setLength( sb.length() );
    }

    private static StringBuilder appendNameAndEmailForUser( UserDTO user, StringBuilder sb )
    {
        String firstName = user.getFirstName();
        String separator = "";
        if ( StringUtils.isNotBlank( firstName ) )
        {
            sb.append( firstName );
            separator = " ";
        }

        String lastName = user.getLastName();
        if ( StringUtils.isNotBlank( firstName ) )
        {
            sb.append( separator ).append( lastName );
            separator = " ";
        }

        sb.append( separator ).append( "<" ).append( user.getEmail() ).append( ">" );

        return sb;
    }

    public void setQuery( String query )
    {
        this.query = query;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

}
