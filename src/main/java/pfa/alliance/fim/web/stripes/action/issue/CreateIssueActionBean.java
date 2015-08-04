/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.issue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
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

    /** The ID of the project where the issue must be created. */
    private Integer projectId;

    private List<StripesDropDownOption> candidateProjects;

    private final ProjectManagementService projectManagementService;

    /**
     * Called when instance of this class is created.
     * 
     * @param projectManagementService the {@link ProjectManagementService} instance to be used in this class
     */
    @Inject
    public CreateIssueActionBean( ProjectManagementService projectManagementService )
    {
        this.projectManagementService = projectManagementService;
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        // redirect to JSP
        return new ForwardResolution( FimPageURLs.CREATE_ISSUE_JSP.getURL() );
    }

    public boolean isCreateIssueAllowed()
    {
        return !getProjects().isEmpty();
    }

    public List<StripesDropDownOption> getProjects()
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
        return candidateProjects;
    }

    @Override
    public String getTitle()
    {
        return getMessage( "issue.createTitle" );
    }

    public Integer getProjectId()
    {
        return projectId;
    }

    public void setProjectId( Integer projectId )
    {
        this.projectId = projectId;
    }

}
