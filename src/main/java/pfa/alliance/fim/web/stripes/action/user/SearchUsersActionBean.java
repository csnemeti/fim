/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;
import pfa.alliance.fim.dto.AbstractSearchDTO;
import pfa.alliance.fim.dto.UserSearchDTO;
import pfa.alliance.fim.dto.UserSearchResultDTO;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.datatables.DatatablesOrder;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.AbstractDatatablesSearchActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;

/**
 * User search action bean.
 * 
 * @author Csaba
 */
@UrlBinding( "/user/search" )
@FimSecurity( checkIfAny = Permission.ADMIN_SEARCH_USERS )
public class SearchUsersActionBean
    extends AbstractDatatablesSearchActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( SearchUsersActionBean.class );

    private UserSearchDTO userSearch = new UserSearchDTO();

    private List<UserSearchDTO> results;

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

    @Inject
    public SearchUsersActionBean( UserManagerService userManagerService )
    {
        super( FimPageURLs.USER_SEARCH_JSP );

        this.userManagerService = userManagerService;
        LOG.debug( "New instance created..." );
    }

    /**
     * This method is called through AJAX from DataTables in order to fill in the results.
     * 
     * @return a JSon with results
     */
    public Resolution results()
    {
        fillInOrdering();
        LOG.debug( "Results for: {}, {}", userSearch, getOrder0() );
        JSONObject result = new JSONObject();
        result.put( "draw", getDraw() );
        long resultsNumber = userManagerService.count( userSearch );
        result.put( "recordsTotal", resultsNumber );
        List<UserSearchResultDTO> filteredResults;
        if ( resultsNumber != 0L )
        {
            filteredResults = process( userManagerService.search( userSearch ) );
        }
        else
        {
            filteredResults = new ArrayList<>();
        }
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
        UserRole[] orderedRoles = new UserRole[] { UserRole.ADMIN, UserRole.PROJECT_ADMIN, UserRole.PRODUCT_OWNER,
            UserRole.SCRUM_MASTER, UserRole.TEAM, UserRole.STATISTICAL };
        for ( UserRole role : orderedRoles )
        {
            roles.add( new StripesDropDownOption( role, getEnumMessage( role ) ) );
        }
        return roles;
    }

    /**
     * Gets the localized {@link UserStatus} values for a drop-down.
     * 
     * @return a list of values to display with status name and corresponding localized value
     */
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

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.user.search" );
    }

    /**
     * "Transfer" ordering data from {@link #order0} into {@link #userSearch}.
     */
    private void fillInOrdering()
    {
        DatatablesOrder order0 = getOrder0();
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

    private List<UserSearchResultDTO> process( List<UserSearchResultDTO> result )
    {
        final String contextPath = findContextPath();
        final HttpSession session = getSession();
        final boolean canEditUser =
            SecurityUtil.hasPermission( Permission.ADMIN_EDIT_OTHER_USER_PROFILE, null, session );
        final boolean canModifyStatus =
            SecurityUtil.hasPermission( Permission.ADMIN_MODIFY_USER_STATUS, null, session );
        final boolean canResetPassword =
            SecurityUtil.hasPermission( Permission.ADMIN_RESET_USER_PASSWORD, null, session );
        for ( UserSearchResultDTO dto : result )
        {
            final boolean isActive = UserStatus.ACTIVE.equals( dto.getUserStatus() );
            final boolean isScheduledForDelete = UserStatus.SCHEDULED_FOR_DELETE.equals( dto.getUserStatus() );
            StringBuilder sb = new StringBuilder();
            sb.append( "<table><tr>" );
            // view user link
            UrlBuilder builder = new UrlBuilder( getContext().getLocale(), ShowUserProfileActionBean.class, false );
            builder.addParameter( "userId", dto.getId() );
            String url = contextPath + builder.toString();
            sb.append( "<td class='noSpacing' style='width: 25%'><a href='" ).append( url ).append( "' title='" ).append( getMessage( "action.view" ) ).append( "'><i class='fa fa-eye fa-2x'></i></a></td>" );
            if ( canEditUser && !isScheduledForDelete )
            {
                sb.append( "<td class='noSpacing' style='width: 25%'><a href='#' title='" ).append( getMessage( "action.edit" ) ).append( "'><i class='fa fa-pencil-square fa-2x'></i></a></td>" );
            }
            else
            {
                sb.append( "<td class='noSpacing' style='width: 25%'>&nbsp;</td>" );
            }
            boolean statusAdded = false;
            if ( isActive )
            {
                if ( canModifyStatus )
                {
                    sb.append( "<td class='noSpacing' style='width: 25%'><a href='#' title='" ).append( getMessage( "action.disable" ) ).append( "' onclick='disableUser(" ).append( dto.getId() ).append( ")'><i class='fa fa-minus-circle fa-2x'></i></a></td>" );
                    statusAdded = true;
                }
            }
            else if ( !isScheduledForDelete )
            {
                if ( canModifyStatus )
                {
                    sb.append( "<td class='noSpacing' style='width: 25%'><a href='#' title='" ).append( getMessage( "action.activate" ) ).append( "' onclick='enableUser(" ).append( dto.getId() ).append( ")'><i class='fa fa-plus-circle fa-2x'></i></a></td>" );
                    statusAdded = true;
                }
            }
            if ( !statusAdded )
            {
                sb.append( "<td class='noSpacing' style='width: 25%'>&nbsp;</td>" );
            }
            if ( canResetPassword && isActive )
            {
                sb.append( "<td class='noSpacing' style='width: 25%'><a href='javascript: void(0)' title='" ).append( getMessage( "action.reset.password" ) ).append( "' onclick='resetPassword(" ).append( dto.getId() ).append( ")'><i class='fa fa-unlock-alt fa-2x'></i></a></td>" );
            }
            else
            {
                sb.append( "<td class='noSpacing' style='width: 25%'>&nbsp;</td>" );
            }

            sb.append( "</tr></table>" );
            dto.setActions( sb.toString() );
        }
        return result;
    }

    @Override
    protected AbstractSearchDTO getSearchObject()
    {
        return userSearch;
    }
}
