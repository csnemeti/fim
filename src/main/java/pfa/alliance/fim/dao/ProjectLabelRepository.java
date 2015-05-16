/**
 * 
 */
package pfa.alliance.fim.dao;

import java.util.List;

import pfa.alliance.fim.model.project.ProjectLabel;

/**
 * This interface defines methods for managing {@link ProjectLabel}s.
 * 
 * @author Csaba
 */
public interface ProjectLabelRepository
    extends JpaRepository<ProjectLabel, Long>
{
    /**
     * Gets all the {@link ProjectLabel}s belonging to a Project with given ID, ordered by component name.
     * 
     * @param projectId the ID of the project
     * @return the list of {@link ProjectLabel}s
     * @see #findAllByProject(int, Sort)
     */
    List<ProjectLabel> findAllByProject( int projectId );

    /**
     * Gets all the {@link ProjectLabel}s belonging to a Project with given ID, ordered based on user choice.
     * 
     * @param projectId the ID of the project
     * @param sort the ordering criteria
     * @return the list of {@link ProjectLabel}s
     */
    List<ProjectLabel> findAllByProject( int projectId, Sort sort );

    /**
     * Deletes a {@link ProjectLabel} if belongs to a given Project.
     * 
     * @param id the id of the label
     * @param projectId the id of the project
     * @return true if label was deleted
     */
    boolean deleteLabelBy( final long id, final int projectId );
}
