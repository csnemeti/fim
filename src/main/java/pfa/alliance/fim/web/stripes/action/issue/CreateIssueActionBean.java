/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.issue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.model.issue.IssuePriority;
import pfa.alliance.fim.model.issue.IssueType;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.service.IssueManagerService;
import pfa.alliance.fim.service.IssuePriorityManagementService;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;

/**
 * This class is used for Creating an {@link Issue}.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/issue/create/{projectId}" )
@FimSecurity( )
public class CreateIssueActionBean
    extends BasePageActionBean
{
    private static final Logger LOG = LoggerFactory.getLogger( CreateIssueActionBean.class );

    private static final UserRoleInsideProject[] ALLOWED_ROLES_FOR_ISSUE_CREATE = { UserRoleInsideProject.SCRUM_MASTER,
        UserRoleInsideProject.PRODUCT_OWNER, UserRoleInsideProject.TEAM, UserRoleInsideProject.OWNER };

    private static final ProjectState[] ALLOWED_STATES_FOR_ISSUE_CREATE = { ProjectState.ACTIVE };

    private final ProjectManagementService projectManagementService;

    private final IssuePriorityManagementService issuePriorityManagementService;

    private final IssueManagerService issueManagerService;

    /** The ID of the project where the issue must be created. */
    private Integer projectId;

    /** Keeps the {@link IssueType} to be created. */
    private IssueType issueType = IssueType.TASK;

    private Long issuePriority;

    private String issueTitle;

    private String issueDescription;

    private String issueEnvironment;

    private List<StripesDropDownOption> candidateProjects;

    private List<IssuePriority> priorities;

    /**
     * Called when instance of this class is created.
     * 
     * @param projectManagementService the {@link ProjectManagementService} instance to be used in this class
     */
    @Inject
    public CreateIssueActionBean( ProjectManagementService projectManagementService,
                                  IssueManagerService issueManagerService,
                                  IssuePriorityManagementService issuePriorityManagementService )
    {
        this.projectManagementService = projectManagementService;
        this.issuePriorityManagementService = issuePriorityManagementService;
        this.issueManagerService = issueManagerService;
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        // load candidate projects, pick first project if nothing is selected, load the priorities for the given project
        loadCandidateProjects();
        getProjectId();
        loadIssuePriorities();
        // redirect to JSP
        return new ForwardResolution( FimPageURLs.CREATE_ISSUE_JSP.getURL() );
    }

    public Resolution changeProject()
    {
        LOG.debug( "Change Project: Show page..." );
        this.issuePriority = null;
        return goToHomePage();
    }

    public Resolution create()
    {
        AuthenticatedUserDTO userDTO = SecurityUtil.getUserFromSession( getSession() );
        Issue issue =
            issueManagerService.create( null, issueType, projectId, userDTO.getId(), null, issuePriority, issueTitle,
                                        issueDescription, issueEnvironment );

        return new RedirectResolution( FimPageURLs.CREATE_ISSUE_JSP.getURL() );
    }

    public boolean isCreateIssueAllowed()
    {
        return !getProjects().isEmpty();
    }

    public List<StripesDropDownOption> getProjects()
    {
        return candidateProjects;
    }

    private void loadCandidateProjects()
    {
        if ( candidateProjects == null )
        {
            AuthenticatedUserDTO userDto = SecurityUtil.getUserFromSession( getSession() );
            List<? extends ProjectDTO> projects =
                projectManagementService.getProjectsSummary( userDto.getId(), ALLOWED_ROLES_FOR_ISSUE_CREATE,
                                                             ALLOWED_STATES_FOR_ISSUE_CREATE );
            candidateProjects = new ArrayList<>();
            for ( ProjectDTO projectDTO : projects )
            {
                candidateProjects.add( new StripesDropDownOption( Integer.toString( projectDTO.getId() ),
                                                                  projectDTO.getCode() + " : " + projectDTO.getName() ) );
            }
        }
    }

    @Override
    public String getTitle()
    {
        return getMessage( "issue.createTitle" );
    }

    public Integer getProjectId()
    {
        if ( projectId == null )
        {
            if ( candidateProjects != null && candidateProjects.size() > 0 )
            {
                projectId = Integer.valueOf( candidateProjects.get( 0 ).getId() );
            }
        }
        return projectId;
    }

    public void setProjectId( Integer projectId )
    {
        this.projectId = projectId;
    }

    public List<StripesDropDownOption> getIssueTypes()
    {
        List<StripesDropDownOption> options = new ArrayList<>();
        IssueType[] types = new IssueType[] { IssueType.TASK, IssueType.BUG, IssueType.ENHANCEMENT };
        for ( IssueType type : types )
        {
            options.add( new StripesDropDownOption( type, getEnumMessage( type ) ) );
        }
        return options;
    }

    public List<StripesDropDownOption> getIssuePriorities()
    {
        List<StripesDropDownOption> options = new ArrayList<>();
        if ( priorities != null )
        {
            for ( IssuePriority priority : priorities )
            {
                options.add( new StripesDropDownOption( priority.getId().toString(), priority.getName() ) );
            }
        }
        return options;
    }

    private void loadIssuePriorities()
    {
        // this triggers retrieving the project also if nothing is set
        if ( priorities == null && getProjectId() != null )
        {
            priorities = issuePriorityManagementService.getPrioritiesForProject( projectId );
        }
    }

    public String getIssueType()
    {
        return issueType.name();
    }

    public void setIssueType( String issueType )
    {
        this.issueType = IssueType.valueOf( issueType );
    }

    public Long getIssuePriority()
    {
        if ( issuePriority == null )
        {
            if ( priorities != null )
            {
                for ( IssuePriority priority : priorities )
                {
                    if ( priority.isDefaultOption() )
                    {
                        issuePriority = priority.getId();
                        break;
                    }
                }
            }
        }
        return issuePriority;
    }

    public void setIssuePriority( Long issuePriority )
    {
        this.issuePriority = issuePriority;
    }

    public String getIssueTitle()
    {
        return issueTitle;
    }

    public void setIssueTitle( String issueTitle )
    {
        this.issueTitle = issueTitle;
    }

    public String getIssueDescription()
    {
        return issueDescription;
    }

    public void setIssueDescription( String issueDescription )
    {
        this.issueDescription = issueDescription;
    }

    public String getIssueEnvironment()
    {
        return issueEnvironment;
    }

    public void setIssueEnvironment( String issueEnvironment )
    {
        this.issueEnvironment = issueEnvironment;
    }

}
