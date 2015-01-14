/**
 * 
 */
package pfa.alliance.fim.dao;

import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;

/**
 * This interface defines the {@link Project} repository methods.
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Integer>
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
}
