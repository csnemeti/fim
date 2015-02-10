package pfa.alliance.fim.web.stripes.action.project;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for displaying the project dashboard.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/project/show/{code}" )
@FimSecurity
public class ProjectDashboardActionBean
    extends BasePageActionBean
{
    private static final Logger LOG = LoggerFactory.getLogger( ProjectDashboardActionBean.class );

    /** Error code returned when project is not found. */
    private static final int PROJECT_NOT_FOUND = 410;

    /** The code of the project. */
    private String code;

    private final ProjectManagementService projectManagementService;

    private ProjectDTO project = null;

    /**
     * Called when instance of this class is created.
     * 
     * @param projectManagementService the {@link ProjectManagementService} instance to be used in this class
     */
    @Inject
    public ProjectDashboardActionBean( ProjectManagementService projectManagementService )
    {
        this.projectManagementService = projectManagementService;
    }

    @DefaultHandler
    public Resolution goToPage()
    {
        LOG.debug( "Show project with code: {}", code );
        if ( StringUtils.isNotBlank( code ) )
        {
            project = projectManagementService.getProjectDetails( code );
        }
        if ( project == null )
        {
            // redirect to error page
            return new ErrorResolution( PROJECT_NOT_FOUND, "Project with code (" + code + ") was not found" );
        }
        else
        {
            // redirect to JSP
            return new ForwardResolution( FimPageURLs.PROJECT_DASHBOARD_JSP.getURL() );
        }
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public ProjectDTO getProject()
    {
        return project;
    }

    /**
     * Gets the project state localized name.
     * 
     * @return the localized name
     */
    public String getStateTitle()
    {
        ProjectState state = project.getState();
        return getMessage( state.getDeclaringClass().getCanonicalName() + "." + state.name() );
    }

    /**
     * Gets the formated time when project was created.
     * 
     * @return the formated time when project was created
     */
    public String getFormatedCreateAt()
    {
        return DateUtils.formatDate( project.getCreateAt(), DateUtils.DATETIME_FORMAT_DAY_FIRST );
    }

    /**
     * Gets the formated time period since project was created.
     * 
     * @return the formated period
     */
    public String getFormatedCreatePeriod()
    {
        return getFormatedUntilNowPeriod( project.getCreateAt() );
    }

    /**
     * Gets the formated time when last state change was made.
     * 
     * @return the formated time when last state change was made
     */
    public String getFormatedLastStateChange()
    {
        return DateUtils.formatDate( project.getStateChangedAt(), DateUtils.DATETIME_FORMAT_DAY_FIRST );
    }

    /**
     * Gets the formated time period since last state change was made.
     * 
     * @return the formated period
     */
    public String getFormatedLastStateChangePeriod()
    {
        return getFormatedUntilNowPeriod( project.getStateChangedAt() );
    }
}