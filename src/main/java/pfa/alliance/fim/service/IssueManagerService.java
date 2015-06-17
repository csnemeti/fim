/**
 * 
 */
package pfa.alliance.fim.service;

import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.model.issue.IssuePriority;
import pfa.alliance.fim.model.issue.IssueType;

/**
 * This interface is used for managing issues.
 * 
 * @author Csaba
 */
public interface IssueManagerService
{
    /**
     * Creates an {@link Issue}.
     * 
     * @param parentId the ID of the parent issue (if any)
     * @param type the type of the issue that is created
     * @param projectId the {@link pfa.alliance.fim.model.project.Project} where the issue belongs to
     * @param reportedUserId the {@link pfa.alliance.fim.model.user.User} id that reported the issue
     * @param assignedUserId the assigned {@link pfa.alliance.fim.model.user.User} to issue
     * @param priority the priority of the issue
     * @param title the issue title
     * @param description the issue description
     * @param environment the environment necessary to reproduce the issue
     * @return the created issue
     */
    Issue create( Long parentId, IssueType type, int projectId, int reportedUserId, Integer assignedUserId,
                  IssuePriority priority, String title, String description, String environment );
}
