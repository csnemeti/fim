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
import pfa.alliance.fim.model.project.Project;
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
     * @param creatorUserId the ID of the user who created the project
     * @param additionalUsers additional users expressed as UserId - role on the project
     * @param locale the current request {@link Locale} (for e-mail sending)
     * @return the created project
     */
    Project create( String name, String code, String description, boolean hidden, ProjectState state,
                    int creatorUserId, Map<Integer, UserRoleInsideProject> additionalUsers, Locale locale );

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
}
