/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.project;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.user.UserStatus;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;

/**
 * Project search action bean.
 * 
 * @author Csaba
 */
@UrlBinding( "/project/search" )
@FimSecurity( )
public class SearchProjectsActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( SearchProjectsActionBean.class );

    /** Flag indicating results table should be displayed. */
    private boolean showResults = false;

    /** The instance of {@link ProjectManagementService} to be used. */
    private ProjectManagementService projectManagementService;

    @Inject
    public SearchProjectsActionBean( ProjectManagementService projectManagementService )
    {
        this.projectManagementService = projectManagementService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution view()
    {
        // LOG.debug( "Show search page: {}", userSearch );
        return new ForwardResolution( FimPageURLs.PROJECT_SEARCH_JSP.getURL() );
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

    public boolean isShowResults()
    {
        return showResults;
    }

}
