/**
 * 
 */
package pfa.alliance.fim.dao;

import java.util.List;

import pfa.alliance.fim.model.project.ProjectComponent;

/**
 * This interface defines methods for managing {@link ProjectComponent}s.
 * 
 * @author Csaba
 */
public interface ProjectComponentRepository
    extends JpaRepository<ProjectComponent, Long>
{
    /**
     * Gets all the {@link ProjectComponent}s belonging to a Project with given ID, ordered by component name.
     * 
     * @param projectId the ID of the project
     * @return the list of {@link ProjectComponent}s
     * @see #findAllByProject(int, Sort)
     */
    List<ProjectComponent> findAllByProject( int projectId );

    /**
     * Gets all the {@link ProjectComponent}s belonging to a Project with given ID, ordered based on user choice.
     * 
     * @param projectId the ID of the project
     * @param sort the ordering criteria
     * @return the list of {@link ProjectComponent}s
     */
    List<ProjectComponent> findAllByProject( int projectId, Sort sort );

    /**
     * Deletes a {@link ProjectComponent} if belongs to a given Project.
     * 
     * @param id the id of the component
     * @param projectId the id of the project
     * @return true if component was deleted
     */
    boolean deleteComponentBy( final long id, final int projectId );
}
