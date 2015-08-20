/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.List;

import pfa.alliance.fim.model.issue.IssuePriority;

/**
 * This service is used for managing {@link IssuePriority} objects.
 * 
 * @author Csaba
 */
public interface IssuePriorityManagementService
{
    /**
     * Gets the priorities of a Project in ordered form.
     * 
     * @param projectId the ID of the Project
     * @return the list of priorities
     */
    List<IssuePriority> getPrioritiesForProject( int projectId );
}
