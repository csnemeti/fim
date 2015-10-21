/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
import pfa.alliance.fim.model.issue.states.IssueFlow;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectComponent;
import pfa.alliance.fim.model.project.ProjectLabel;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserRoleInsideProject;

/**
 * This interface handles all operations regarding {@link Project}.
 * 
 * @author Csaba
 */
public interface ProjectManagementService
{
    /**
     * Creates a new {@link Project} from scratch.
     * 
     * @param name the name of the project
     * @param code the code of the project
     * @param description project description
     * @param hidden flag indicating project is hidden (not visible on search)
     * @param state the initial state of the project (Accepted values are {@link ProjectState#ACTIVE} and the default
     *            value {@link ProjectState#IN_PREPARATION}.)
     * @param flowId the chosen Issue flow ID for the project
     * @param creatorUserId the ID of the user who created the project
     * @param additionalUsers additional users expressed as UserId - role on the project
     * @param locale the current request {@link Locale} (for e-mail sending)
     * @return the created project
     */
    Project create( String name, String code, String description, boolean hidden, ProjectState state, final int flowId,
                    int creatorUserId, Map<Integer, UserRoleInsideProject> additionalUsers, Locale locale );

    /**
     * Updates a existing {@link Project}.
     * 
     * @param oldCode the old code of the Project
     * @param name the name of the project
     * @param code the code of the project
     * @param description project description
     * @param hidden flag indicating project is hidden (not visible on search)
     * @param state the initial state of the project (Accepted values are {@link ProjectState#ACTIVE} and the default
     *            value {@link ProjectState#IN_PREPARATION}.)
     * @param locale the current request {@link Locale} (for e-mail sending)
     * @return the created project
     */
    Project update( String oldCode, String name, String code, String description, boolean hidden, ProjectState state,
                    Locale locale );

    /**
     * Gets a project based on the given code.
     * 
     * @param code the code of the project
     * @return the Project if found, null if not
     */
    Project findByCode( String code );

    /**
     * Gets a project details based on the given code.
     * 
     * @param code the code of the project
     * @return the Project if found, null if not
     */
    ProjectDTO getProjectDetails( String code );

    /**
     * Counts the number of results based on the given search criteria.
     * 
     * @param criteriaCriteria the search criteria
     * @return the number of results
     */
    long count( ProjectSearchDTO criteriaCriteria );

    /**
     * Search for projects matching the given search criteria
     * 
     * @param criteria the search criteria
     * @return the results
     */
    List<ProjectSearchResultDTO> search( ProjectSearchDTO criteria );

    /**
     * Creates a {@link ProjectComponent}.
     * 
     * @param projectCode the {@link Project} that will use this component.
     * @param component the name of the component
     * @param textColor the HEXA color of the text
     * @return the created component
     */
    ProjectComponent createComponent( final String projectCode, final String component, final String textColor );

    /**
     * Gets the list of {@link ProjectComponent}s belonging to project with given code.
     * 
     * @param projectId the ID of the {@link Project}
     * @return the list of {@link ProjectComponent}s ordered by name
     */
    List<ProjectComponent> findComponentsByProjectCode( final int projectId );

    /**
     * Creates a {@link ProjectLabel}.
     * 
     * @param projectCode the {@link Project} that will use this label.
     * @param label the name of the label
     * @param bgColor the HEXA color of the background
     * @return the created label
     */
    ProjectLabel createLabel( final String projectCode, final String label, final String bgColor );

    /**
     * Gets the list of {@link ProjectLabel}s belonging to project with given code.
     * 
     * @param projectId the ID of the {@link Project}
     * @return the list of {@link ProjectLabel}s ordered by name
     */
    List<ProjectLabel> findLabelsByProjectId( final int projectId );

    /**
     * Updates a {@link ProjectComponent}.
     * 
     * @param id the component ID
     * @param projectCode the code of the project
     * @param labelName the name of the component
     * @param labelColor the color to be used
     * @return true if update was successful
     */
    boolean updateComponent( long id, String projectCode, String labelName, String labelColor );

    /**
     * Updates a {@link ProjectLabel}.
     * 
     * @param id the label ID
     * @param projectCode the code of the project
     * @param labelName the name of the label
     * @param labelColor the color to be used
     * @return true if update was successful
     */
    boolean updateLabel( long id, String projectCode, String labelName, String labelColor );
    /**
     * Deletes the {@link ProjectComponent} with given ID if it belongs to given project.
     * 
     * @param id the ID of the component
     * @param projectCode the code of the project
     * @return true if component was deleted
     */
    boolean deleteComponent( final long id, final String projectCode );

    /**
     * Deletes the {@link ProjectLabel} with given ID if it belongs to given project.
     * 
     * @param id the ID of the label
     * @param projectCode the code of the project
     * @return true if label was deleted
     */
    boolean deleteLabel( final long id, final String projectCode );

    /**
     * Gets all the flows that can be assigned to a {@link Project}.
     * 
     * @return all valid flows
     */
    List<IssueFlow> getAllValidFlows();

    /**
     * Change the {@link IssueFlow} of a {@link Project}
     * 
     * @param projectCode the code of the {@link Project}
     * @param flowId the ID of the new {@link IssueFlow} to set
     */
    void updateProjectFlow( final String projectCode, final long flowId );

    /**
     * Assign a user to a project.
     * 
     * @param userId the ID of the user to assign
     * @param projectCode the project code
     * @param role the role the user will have inside the project
     * @return true if assignment was successful
     */
    boolean assignUser( final int userId, final String projectCode, UserRoleInsideProject role );

    /**
     * Gets a summary about the {@link Project}s where we have the given user assigned with given roles and projects is
     * with given states.
     * 
     * @param assignedUserId the ID of the user who must be assigned to those projects
     * @param roles the roles that are allowed the user to have
     * @param allowedStates the allowed project states
     * @return the list of projects or empty list if nothing matches
     */
    List<? extends ProjectDTO> getProjectsSummary( int assignedUserId, UserRoleInsideProject[] roles,
                                                   ProjectState[] allowedStates );
}
