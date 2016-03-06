/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;
import net.sourceforge.stripes.validation.Validate;
import pfa.alliance.fim.dto.UserSearchResultDTO;
import pfa.alliance.fim.model.issue.IssuePriority;
import pfa.alliance.fim.model.issue.states.IssueFlow;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectComponent;
import pfa.alliance.fim.model.project.ProjectLabel;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.util.ColorUtils;
import pfa.alliance.fim.util.ColorUtils.ColorWithName;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.security.ProjectSensibleActionBean;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;
import pfa.alliance.fim.web.stripes.action.user.UserAutocompleteActionBean;

/**
 * This is the base class for editing a specific project.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/project/edit/{code}/{focus}" )
@FimSecurity( checkIfAny = Permission.PROJECT_EDIT_PROJECT )
public class EditProjectActionBean
    extends BasePageActionBean
    implements ProjectSensibleActionBean

{
    private static final Logger LOG = LoggerFactory.getLogger( EditProjectActionBean.class );

    /** Error code returned when project is not found. */
    private static final int PROJECT_NOT_FOUND = 410;

    /** The code of the project. */
    @Validate( required = true, trim = true )
    private String code;

    /** Tells us what tab to focus on. */
    private String focus = "basic";

    /** The name of a new Label. */
    @Validate( required = true, trim = true, on = { "createLabel", "createComponent", "editLabel" }, maxlength = 40 )
    private String labelName;

    /** The chosen color for a new Label. */
    @Validate( required = true, trim = true, on = { "createLabel", "createComponent", "editLabel" }, maxlength = 40 )
    private String labelColor;

    @Validate( required = true, trim = true, on = { "editLabel", "deleteLabel" }, maxlength = 20 )
    private String labelType;

    @Validate( required = true, on = { "editLabel", "deleteLabel" } )
    private Long labelId;

    @Validate( required = true, on = { "updateFlow" } )
    private Long flowId;

    @Validate( required = true, on = { "changeOwner" } )
    private Integer newOwner;

    /**
     * The ID of the user to be added to project. In theory this should be required butit's difficult to control from
     * front-end. This means you should expect null values and treat them nicely in back-end. <br />
     * Also, it can be the ID of a user who will have his / hers role modified OR id of a user who is unassigned from
     * Project.
     */
    @Validate( required = true, on = { "addUser", "deleteUser" } )
    private Integer userId;

    private final ProjectManagementService projectManagementService;

    private Project project = null;

    private List<ProjectLabel> labelList;

    private List<ProjectComponent> componentList;

    /** The role a user should have. For example role of a new user, new role of an existing user, etc. */
    private UserRole newUserRole = UserRole.TEAM;

    /** The list of all supported colors. */
    private List<StripesDropDownOption> colors = new ArrayList<>();

    /** The list of roles user can assign. */
    private List<StripesDropDownOption> roles = null;

    /** The list of assigned users to this project. */
    private List<UserSearchResultDTO> assignedUsers = null;

    /** Gets the project owner information. */
    private User owner;

    /**
     * Called when instance of this class is created.
     * 
     * @param projectManagementService the {@link ProjectManagementService} instance to be used in this class
     */
    @Inject
    public EditProjectActionBean( ProjectManagementService projectManagementService )
    {
        this.projectManagementService = projectManagementService;
    }

    @Override
    public Integer getProjectId()
    {
        loadProjectFromDb();
        return ( project == null ) ? null : project.getId();
    }

    @Override
    protected Integer getSelectedProjectId()
    {
        return getProjectId();
    }

    @DefaultHandler
    public Resolution goToPage()
    {
        LOG.debug( "Show project with code: {}", code );
        loadProjectFromDb();
        if ( project == null )
        {
            // redirect to error page
            return new ErrorResolution( PROJECT_NOT_FOUND, "Project with code (" + code + ") was not found" );
        }
        else
        {
            // redirect to JSP
            return new ForwardResolution( FimPageURLs.EDIT_PROJECT_JSP.getURL() );
        }
    }

    public Resolution update()
    {
        LOG.debug( "Updating project with code = {}: {}", code, project );
        projectManagementService.update( code, project.getName(), project.getCode(), project.getDescription(),
                                         project.isHidden(), project.getState(), getLocale() );
        // if update succeeds project code might be changed
        if ( StringUtils.isNotBlank( project.getCode() ) )
        {
            this.code = project.getCode();
        }

        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution editLabel()
    {
        switch ( labelType )
        {
            case "label":
                projectManagementService.updateLabel( labelId, code, labelName, labelColor );
                break;
            case "component":
                projectManagementService.updateComponent( labelId, code, labelName, labelColor );
                break;
            default:
                LOG.warn( "Unknown label type: {}, nothing will be updated", labelType );
                break;
        }
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution deleteLabel()
    {
        LOG.debug( "Deleting {} with ID = {} for project with code = {}", labelType, labelId, code );
        switch ( labelType )
        {
            case "label":
                projectManagementService.deleteLabel( labelId, code );
                break;
            case "component":
                projectManagementService.deleteComponent( labelId, code );
                break;
            default:
                LOG.warn( "Unknown label type: {}, nothing will be deleted", labelType );
                break;
        }
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution createLabel()
    {
        // process
        projectManagementService.createLabel( code, labelName, labelColor );
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution createComponent()
    {
        // process
        projectManagementService.createComponent( code, labelName, labelColor );
        // redirect to this page again
        return redirectBackHere();
    }

    @FimSecurity( checkIfAll = Permission.PROJECT_EDIT_PROJECT_CHANGE_OWNER )
    public Resolution changeOwner()
    {
        // process
        projectManagementService.changeOwner( getProjectId(), newOwner, getLocale() );
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution updateFlow()
    {
        // process
        projectManagementService.updateProjectFlow( code, flowId );
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution addUser()
    {
        LOG.debug( "Assignning user: {} in role {} to project {}", userId, newUserRole, code );
        UserRoleInsideProject userRole = UserRoleInsideProject.findByUserRole( newUserRole );
        if ( userId != null && userRole != null )
        {
            projectManagementService.assignUser( userId, code, userRole,
                                                 SecurityUtil.getUserFromSession( getSession() ), getLocale() );
        }
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution editeUser()
    {
        LOG.debug( "Update user: {} role from project {} to {}", userId, code, newUserRole );
        UserRoleInsideProject userRole = UserRoleInsideProject.findByUserRole( newUserRole );
        if ( userId != null && userRole != null )
        {
            projectManagementService.updateUserAssignment( userId, code, userRole,
                                                           SecurityUtil.getUserFromSession( getSession() ),
                                                           getLocale() );
        }
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution deleteUser()
    {
        LOG.debug( "Remove user: {} from project {}", userId, code );
        if ( userId != null )
        {
            projectManagementService.removeUserAssignment( userId, code,
                                                           SecurityUtil.getUserFromSession( getSession() ),
                                                           getLocale() );
        }
        // redirect to this page again
        return redirectBackHere();
    }

    private Resolution redirectBackHere()
    {
        RedirectResolution resolution = new RedirectResolution( getClass() );
        resolution.addParameter( "code", code );
        resolution.addParameter( "focus", focus );
        return resolution;
    }

    public int getLabelsNumber()
    {
        return getLabels().size();
    }

    public synchronized List<ProjectLabel> getLabels()
    {
        if ( labelList == null )
        {
            // we might not have project id so we load the project from DB
            loadProjectFromDb();
            labelList = projectManagementService.findLabelsByProjectId( getProject().getId() );
        }
        return labelList;
    }

    public int getComponentsNumber()
    {
        return getComponents().size();
    }

    public synchronized List<ProjectComponent> getComponents()
    {
        if ( componentList == null )
        {
            // we might not have project id so we load the project from DB
            loadProjectFromDb();
            componentList = projectManagementService.findComponentsByProjectCode( getProject().getId() );
        }
        return componentList;
    }

    public List<StripesDropDownOption> getColors()
    {
        synchronized ( colors )
        {
            if ( colors.isEmpty() )
            {
                for ( ColorWithName color : ColorUtils.getColors() )
                {
                    colors.add( new StripesDropDownOption( color.getHexCode(),
                                                           getMessage( "color." + color.getName() ) ) );
                }
            }
        }
        return colors;
    }

    public String getBasicLink()
    {
        return buildTabLink( "basic" );
    }

    public String getBasicClass()
    {
        return buildTabCssClass( "basic" );
    }

    public String getStatesLink()
    {
        return buildTabLink( "states" );
    }

    public String getStatesClass()
    {
        return buildTabCssClass( "states" );
    }

    public String getLabelsLink()
    {
        return buildTabLink( "labels" );
    }

    public String getLabelsClass()
    {
        return buildTabCssClass( "labels" );
    }

    public String getUsersLink()
    {
        return buildTabLink( "users" );
    }

    public String getUsersClass()
    {
        return buildTabCssClass( "users" );
    }

    public String getOwnerClass()
    {
        return buildTabCssClass( "owner" );
    }

    public String getOwnerLink()
    {
        return buildTabLink( "owner" );
    }

    private String buildTabLink( final String expectedTab )
    {
        UrlBuilder builder = new UrlBuilder( getLocale(), getClass(), true );
        builder.addParameter( "code", code );
        builder.addParameter( "focus", expectedTab );
        String url = builder.toString();
        String contextPath = getContext().getServletContext().getContextPath();
        if ( contextPath != null )
        {
            url = contextPath + url;
        }
        return url;
    }

    /**
     * Verifies if the tab name given as parameter is the selected one and returns corresponding CSS class
     * 
     * @param expectedTab the expected tab name
     * @return the CSS class or empty string if class should not be applied
     */
    private String buildTabCssClass( final String expectedTab )
    {
        return expectedTab.equalsIgnoreCase( focus ) ? " class=\"active\"" : "";
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getFocus()
    {
        return focus;
    }

    public void setFocus( String focus )
    {
        this.focus = focus;
    }

    public String getLabelName()
    {
        return labelName;
    }

    public void setLabelName( String labelName )
    {
        this.labelName = labelName;
    }

    public String getLabelColor()
    {
        return labelColor;
    }

    public void setLabelColor( String labelColor )
    {
        this.labelColor = labelColor;
    }

    public String getLabelType()
    {
        return labelType;
    }

    public void setLabelType( String labelType )
    {
        this.labelType = labelType;
    }

    public Long getLabelId()
    {
        return labelId;
    }

    public void setLabelId( Long labelId )
    {
        this.labelId = labelId;
    }

    /**
     * Loads the {@link Project} from DB.
     */
    private void loadProjectFromDb()
    {
        if ( project == null && StringUtils.isNotBlank( code ) )
        {
            project = projectManagementService.findByCode( code );
        }
    }

    public Project getProject()
    {
        if ( project == null )
        {
            project = new Project();
        }
        return project;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public List<StripesDropDownOption> getStates()
    {
        boolean isColosed = ProjectState.CLOSED.equals( getProject().getState() );
        List<StripesDropDownOption> states = new ArrayList<>();
        for ( ProjectState state : ProjectState.values() )
        {
            if ( ProjectState.SCHEDULED_FOR_DELETE != state || isColosed )
            {
                states.add( new StripesDropDownOption( state, getEnumMessage( state ) ) );
            }
        }
        return states;
    }

    public String getProjectState()
    {
        Project project = getProject();
        return project.getState().name();
    }

    public void setProjectState( String stateName )
    {
        Project project = getProject();
        ProjectState state = ProjectState.valueOf( stateName );
        project.setState( state );
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

    public Long getIssueFlow()
    {
        IssueFlow flow = project.getIssueFlow();
        return ( flow != null ) ? flow.getId() : null;
    }

    public void setIssueFlow( Long flowId )
    {
        this.flowId = flowId;
    }

    public List<IssuePriority> getPriorities()
    {
        List<IssuePriority> priorities = new ArrayList<>( project.getPriorities() );
        Collections.sort( priorities );
        return priorities;
    }

    public String getNewUserRole()
    {
        return newUserRole.name();
    }

    public void setNewUserRole( String defaultRole )
    {
        this.newUserRole = UserRole.valueOf( defaultRole );
    }

    public List<StripesDropDownOption> getUserRoles()
    {
        if ( roles == null )
        {
            roles = new ArrayList<StripesDropDownOption>();

            List<UserRole> orderedRoles = new ArrayList<>();
            HttpSession session = getSession();
            if ( SecurityUtil.hasPermission( Permission.PROJECT_EDIT_PROJECT_ASSIGN_PA, getProjectId(), session ) )
            {
                orderedRoles.add( UserRole.PROJECT_ADMIN );
            }
            if ( SecurityUtil.hasPermission( Permission.PROJECT_EDIT_PROJECT_ASSIGN_PO, getProjectId(), session ) )
            {
                orderedRoles.add( UserRole.PRODUCT_OWNER );
            }
            if ( SecurityUtil.hasPermission( Permission.PROJECT_EDIT_PROJECT_ASSIGN_SM, getProjectId(), session ) )
            {
                orderedRoles.add( UserRole.SCRUM_MASTER );
            }
            orderedRoles.add( UserRole.TEAM );
            orderedRoles.add( UserRole.STATISTICAL );

            for ( UserRole role : orderedRoles )
            {
                roles.add( new StripesDropDownOption( role, getMessage( role.getDeclaringClass().getName() + "."
                    + role.name() ) ) );
            }
        }
        return roles;
    }

    public String getUsersAutocompleteUrl()
    {
        Project project = getProject();
        UrlBuilder builder = new UrlBuilder( getLocale(), UserAutocompleteActionBean.class, false );
        builder.addParameter( "id", project.getId() );
        builder.addParameter( "query", "abc" );
        builder.setEvent( "project" );
        String url = builder.toString();
        String contextPath = getContext().getServletContext().getContextPath();
        if ( contextPath != null )
        {
            url = contextPath + url;
        }
        return url;
    }

    public int getAssignedUsersNumber()
    {
        return getAssignedUsers().size();
    }

    public List<UserSearchResultDTO> getAssignedUsers()
    {
        if ( assignedUsers == null )
        {
            assignedUsers = projectManagementService.findActiveUsersAssignedToProject( getProjectId(), getLocale() );
        }
        return assignedUsers;
    }

    public User getProjectOwner()
    {
        if ( owner == null )
        {
            owner = projectManagementService.getProjectOwner( getProjectId() );
        }
        return owner;
    }

    public Integer getNewOwner()
    {
        User owner = getProjectOwner();
        if ( newOwner == null && owner != null )
        {
            newOwner = owner.getId();
        }
        return newOwner;
    }

    public void setNewOwner( Integer newOwner )
    {
        this.newOwner = newOwner;
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.project.edit", code );
    }
}
