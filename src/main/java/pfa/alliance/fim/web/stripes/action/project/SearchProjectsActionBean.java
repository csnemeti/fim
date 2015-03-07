/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.project;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.AbstractSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.datatables.DatatablesOrder;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.AbstractDatatablesSearchActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;

/**
 * Project search action bean.
 * 
 * @author Csaba
 */
@UrlBinding( "/project/search" )
@FimSecurity( checkIfAny = Permission.PROJECT_LIST_PROJECTS )
public class SearchProjectsActionBean
    extends AbstractDatatablesSearchActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( SearchProjectsActionBean.class );

    private ProjectSearchDTO projectSearch = new ProjectSearchDTO();

    /** The instance of {@link ProjectManagementService} to be used. */
    private ProjectManagementService projectManagementService;

    @Inject
    public SearchProjectsActionBean( ProjectManagementService projectManagementService )
    {
        super( FimPageURLs.PROJECT_SEARCH_JSP );

        this.projectManagementService = projectManagementService;
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
        LOG.debug( "Results for: {}, {}", projectSearch, getOrder0() );
        JSONObject result = new JSONObject();
        result.put( "draw", getDraw() );
        long resultsNumber = projectManagementService.count( projectSearch );
        result.put( "recordsTotal", resultsNumber );
        List<ProjectSearchResultDTO> filteredResults = null;
        if ( resultsNumber != 0L )
        {
            final boolean canEdit = SecurityUtil.hasPermission( Permission.PROJECT_EDIT_PROJECT, getSession() );
            String contextPath = getContext().getServletContext().getContextPath();
            if ( contextPath.equals( "/" ) )
            {
                contextPath = null;
            }
            filteredResults = process( projectManagementService.search( projectSearch ), canEdit, contextPath );
        }
        else
        {
            filteredResults = new ArrayList<>();
        }
        result.put( "recordsFiltered", resultsNumber );
        result.put( "data", filteredResults );
        return new StreamingResolution( "application/json", new StringReader( result.toString( 3 ) ) );
    }

    /**
     * Gets the localized {@link UserStatus} values for a drop-down.
     * 
     * @return a list of values to display with status name and corresponding localized value
     */
    public List<StripesDropDownOption> getDefaultStates()
    {
        List<StripesDropDownOption> statuses = new ArrayList<StripesDropDownOption>();
        ProjectState[] orderedStates =
            new ProjectState[] { ProjectState.IN_PREPARATION, ProjectState.ACTIVE, ProjectState.CLOSED };
        for ( ProjectState state : orderedStates )
        {
            statuses.add( new StripesDropDownOption( state, getEnumMessage( state ) ) );
        }
        return statuses;
    }

    public String getLocalizationString()
    {
        JSONObject root = new JSONObject();
        for ( ProjectState projectState : ProjectState.values() )
        {
            root.put( projectState.name(), getEnumMessage( projectState ) );
        }
        return root.toString();
    }

    public String getResultsUrl()
    {
        UrlBuilder builder = new UrlBuilder( getContext().getLocale(), getClass(), false );
        builder.setEvent( "results" );
        builder.addParameter( "projectSearch.code", getNotNullParameterValue( projectSearch.getCode() ) );
        builder.addParameter( "projectSearch.name", getNotNullParameterValue( projectSearch.getName() ) );
        builder.addParameter( "projectSearch.hidden", projectSearch.showHidden() );
        builder.addParameter( "projectSearch.states", getNotNullParameterValues( projectSearch.getStates() ) );
        builder.addParameter( "filterDataTablesCall", true );
        return builder.toString();
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.project.search" );
    }

    /**
     * "Transfer" ordering data from {@link #order0} into {@link #userSearch}.
     */
    private void fillInOrdering()
    {
        DatatablesOrder order0 = getOrder0();
        // any value that is not descending will mean ascending
        projectSearch.setAscending( !"desc".equalsIgnoreCase( order0.getDir() ) );
        String column = null;
        switch ( order0.getColumn() )
        {
            case 2:
                column = "code";
                break;
            case 3:
                column = "name";
                break;
            case 4:
                column = "createdAt";
                break;
            default:
                LOG.warn( "Wrong ordering column value (it will use default ordering)): {}", order0.getColumn() );
                break;
        }
        projectSearch.setOrderBy( column );
    }

    /**
     * Process the result list.
     * 
     * @param result the result elements
     * @param canEdit flag telling us us current user can edit Projects
     * @return the processed list
     */
    private List<ProjectSearchResultDTO> process( List<ProjectSearchResultDTO> result, final boolean canEdit,
                                                  final String contextPath )
    {
        for ( ProjectSearchResultDTO dto : result )
        {
            UrlBuilder builder = new UrlBuilder( getContext().getLocale(), ProjectDashboardActionBean.class, true );
            builder.addParameter( "code", dto.getCode() );
            String url = builder.toString();
            if ( contextPath != null )
            {
                url = contextPath + url;
            }

            StringBuilder sb = new StringBuilder();
            sb.append( "<table><tr>" );
            sb.append( "<td class='noSpacing'><a href='" ).append( url ).append( "' title='" ).append( getMessage( "action.view" ) ).append( "'><i class='fa fa-eye fa-2x'></i></a></td>" );
            if ( canEdit )
            {
                sb.append( "<td class='noSpacing'><a href='#' title='" ).append( getMessage( "action.edit" ) ).append( "'><i class='fa fa-pencil-square fa-2x'></i></a></td>" );
            }
            sb.append( "</tr></table>" );
            dto.setActions( sb.toString() );
        }
        return result;
    }

    public boolean isShowHiddenProjects()
    {
        return SecurityUtil.hasPermission( Permission.PROJECT_SHOW_HIDDEN_PROJECTS, getSession() );
    }

    public ProjectSearchDTO getProjectSearch()
    {
        return projectSearch;
    }

    public void setProjectSearch( ProjectSearchDTO projectSearch )
    {
        this.projectSearch = projectSearch;
    }

    @Override
    protected AbstractSearchDTO getSearchObject()
    {
        return projectSearch;
    }

}
