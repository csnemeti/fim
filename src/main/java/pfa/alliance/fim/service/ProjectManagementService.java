/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.Map;

import pfa.alliance.fim.model.project.Project;
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
     * @param creatorUserId the ID of the user who created the project
     * @param additionalUsers additional users expressed as UserId - role on the project
     * @return the created project
     */
    Project create( String name, String code, String description, int creatorUserId,
                    Map<Integer, UserRoleInsideProject> additionalUsers );
}
