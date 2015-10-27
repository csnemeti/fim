package pfa.alliance.fim.web.stripes.action.project;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.issue.states.IssueFlow;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.LoggedInUserDTO;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.service.impl.DuplicateDataException;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;
import pfa.alliance.fim.web.util.LoginUtil;

/**
 * This class is used for creating a {@link Project}.
 * 
 * @author Denis
 * @author Csaba
 */
@UrlBinding( value = "/project/create" )
@FimSecurity( checkIfAll = { Permission.PROJECT_CREATE_PROJECT } )
public class CreateProjectActionBean
    extends BasePageActionBean
{
    private static final Logger LOG = LoggerFactory.getLogger( CreateProjectActionBean.class );

    /** The name of the project. */
    @Validate( required = true, trim = true, on = "create", maxlength = 50 )
    private String projectName;

    /** The code of the project. */
    @Validate( required = true, trim = true, on = "create", maxlength = 20, minlength = 2 )
    private String projectCode;

    /** The description of the project. */
    @Validate( trim = true, on = "create", maxlength = 2000 )
    private String projectDescription;

    /** The User ID that will be the owner. */
    private Integer ownerId;

    /** The User name that will be the owner. */
    @Validate( required = true, trim = true, on = "create" )
    private String ownerName;

    @Validate( required = true, on = { "create" } )
    private Integer flowId;

    /** Message key regarding Database operation */
    private String dbOperationResult;

    /** Flag to indicate if project is hidden or not. */
    private Boolean hidden;

    /** Flag to indicate that project should be automatically set to ProjectState#ACTIVE. */
    private Boolean activate;

    private final ProjectManagementService projectManagementService;

    private final UserManagerService userManagerService;

    /** Service used for generating URLs inside FIM. */
    private final FimUrlGeneratorService fimUrlGeneratorService;

    private final static String PROJECT_CREATED_RESPONSE = "project.projectCreated";

    private final static String DUPLICATE_PROJECT_DATA_RESPONSE = "project.duplicateProjectData";

    /**
     * Called when instance of this class is created.
     * 
     * @param projectManagementService the {@link ProjectManagementService} instance to be used in this class
     * @param userManagerService the {@link UserManagerService} instance to be used in this class
     * @param fimUrlGeneratorService the instance of service used for generating full URLs inside FIM
     */
    @Inject
    public CreateProjectActionBean( ProjectManagementService projectManagementService,
                                    UserManagerService userManagerService,
                                    FimUrlGeneratorService fimUrlGeneratorService )
    {
        this.projectManagementService = projectManagementService;
        this.userManagerService = userManagerService;
        this.fimUrlGeneratorService = fimUrlGeneratorService;
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        // get the current user form session and configure values
        AuthenticatedUserDTO user = SecurityUtil.getUserFromSession( getSession() );
        ownerId = user.getId();
        ownerName = user.getName();
        // redirect to JSP
        return new ForwardResolution( FimPageURLs.CREATE_PROJECT_JSP.getURL() );
    }

    /**
     * Method called when authentication form Submit button is pressed.
     * 
     * @return where to go next
     */
    public Resolution create()
    {
        LOG.debug( "Trying to create project: projectName = {}, projectCode = {}, ownerId = {}", projectName,
                   projectCode, ownerId );
        try
        {
            AuthenticatedUserDTO user = SecurityUtil.getUserFromSession( getSession() );
            ownerId = user.getId();
            ProjectState state = ( activate != null && activate ) ? ProjectState.ACTIVE : ProjectState.IN_PREPARATION;
            Project project =
                projectManagementService.create( projectName, projectCode, projectDescription, isHiddenSet(), state,
                                                 flowId, ownerId, null, user, getContext().getLocale() );
            dbOperationResult = PROJECT_CREATED_RESPONSE;

            // modify the user permissions so that newly created Project is visible
            LoggedInUserDTO loggedInUser = userManagerService.refreshUser( ownerId );
            if ( loggedInUser != null )
            {
                updateUserFromSession( loggedInUser, user );
            }

            // create URL to project
            String url = fimUrlGeneratorService.getProjectLink( project.getCode() );
            return new RedirectResolution( url, false );
        }
        catch ( DuplicateDataException e )
        {
            dbOperationResult = DUPLICATE_PROJECT_DATA_RESPONSE;
            return new ForwardResolution( FimPageURLs.CREATE_PROJECT_JSP.getURL() );
        }
    }

    /**
     * Sets the user on the session.
     * 
     * @param user the user to set on session
     */
    private void updateUserFromSession( LoggedInUserDTO userDTO, AuthenticatedUserDTO user )
    {
        AuthenticatedUserDTO authenticatedUserDTO =
            new AuthenticatedUserDTO( user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                                      user.getUsername(), user.getLastLogin(), LoginUtil.convertPermissions( userDTO ) );
        SecurityUtil.putUserIntoSession( authenticatedUserDTO, getContext().getRequest().getSession( true ) );
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.project.create" );
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName( String projectName )
    {
        this.projectName = projectName;
    }

    public String getProjectCode()
    {
        return projectCode;
    }

    public void setProjectCode( String projectCode )
    {
        this.projectCode = projectCode;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }

    public void setProjectDescription( String projectDescription )
    {
        this.projectDescription = projectDescription;
    }

    public Integer getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( Integer ownerId )
    {
        this.ownerId = ownerId;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName( String ownerName )
    {
        this.ownerName = ownerName;
    }

    public Boolean getHidden()
    {
        return hidden;
    }

    public boolean isHiddenSet()
    {
        return hidden != null && hidden;
    }

    public void setHidden( Boolean hidden )
    {
        this.hidden = hidden;
    }

    public Boolean getActivate()
    {
        return activate;
    }

    public void setActivate( Boolean activate )
    {
        this.activate = activate;
    }

    public List<StripesDropDownOption> getIssueFlows()
    {
        List<StripesDropDownOption> states = new ArrayList<>();
        List<IssueFlow> flows = projectManagementService.getAllValidFlows();
        for ( IssueFlow flow : flows )
        {
            states.add( new StripesDropDownOption( flow.getId().toString(), flow.getName() ) );
        }
        return states;
    }

    public void setIssueFlow( Integer flowId )
    {
        this.flowId = flowId;
    }

    public String getDbOperationResult()
    {
        String result = null;

        if ( dbOperationResult != null )
        {
            result = getMessage( dbOperationResult );
        }
        return result;
    }

}
