/**
 * 
 */
package pfa.alliance.fim.dao;

import java.util.List;
import java.util.Map;

import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;

/**
 * This interface defines the {@link Project} repository methods.
 * 
 * @author Balaceanu Sergiu-Denis
 */
public interface ProjectRepository
    extends JpaRepository<Project, Integer>
{
    /**
     * Gets a project based on it's code.
     * 
     * @param code the code of the project
     * @return the project or null if code is not valid
     */
    Project findByCode( String code );

    /**
     * Gets the owner of the {@link Project}.
     * 
     * @param id the ID of the {@link Project}
     * @return the {@link User} representing project owner
     */
    User findOwnerForProject( int id );

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

    /**
     * Gets a set of users that are assigned to a {@link Project} based on their role in the project.
     * 
     * @param projectId the ID of the project
     * @param roles the roles we're interested in
     * @param maxUsersPerRole total amount of users that will be retrieved for a role (max 100)
     * @return the map with data
     */
    Map<UserRoleInsideProject, List<User>> findUsersOnProject( int projectId, int maxUsersPerRole,
                                                               UserRoleInsideProject... roles );

    /**
     * Find all active users that are assigned to a {@link Project}.
     * 
     * @param projectID the ID of the {@link Project}
     * @return a non-null list of users and their role in the project
     */
    List<UserProjectRelation> findActiveUsersFor( int projectID );
}
