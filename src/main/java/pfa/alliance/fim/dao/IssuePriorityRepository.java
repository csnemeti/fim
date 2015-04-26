/**
 * 
 */
package pfa.alliance.fim.dao;

import java.util.List;

import pfa.alliance.fim.model.issue.IssuePriority;
import pfa.alliance.fim.model.issue.IssueType;

/**
 * This interface defines the {@link IssueType} repository methods.
 * 
 * @author Csaba
 */
public interface IssuePriorityRepository
    extends JpaReadOnlyRepository<IssuePriority, Long>
{
    /**
     * Find all {@link IssuePriority}s belonging to a given Project.
     * @param projectId the ID of the project
     * @return the list of priorities ordered by their importance descending
     */
    List<IssuePriority> findAll( int projectId );

    /**
     * Find all {@link IssuePriority}s belonging to a given Project.
     * 
     * @param projectId the ID of the project
     * @param the ordering criteria
     * @return the list of priorities
     */
    List<IssuePriority> findAll( int projectId, Sort sort );
}
