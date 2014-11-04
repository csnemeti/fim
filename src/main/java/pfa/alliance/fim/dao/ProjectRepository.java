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
     * This method is used for search for users with a given exact username. Since username is unique, only one result
     * will be returned.
     * 
     * @param username the login name of the user, no wild chars are allowed
     * @return the {@link User} with the given login name or null if no such user is found
     */
    User findByName( String username );

}
