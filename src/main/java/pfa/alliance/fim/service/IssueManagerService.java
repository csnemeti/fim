/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.List;

import pfa.alliance.fim.dto.issue.IssueBaseDTO;
import pfa.alliance.fim.model.issue.Issue;
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
     * @param priorityId the priority ID of the issue
     * @param title the issue title
     * @param description the issue description
     * @param environment the environment necessary to reproduce the issue
     * @return the created issue
     */
    Issue create( Long parentId, IssueType type, int projectId, int reportedUserId, Integer assignedUserId,
                  Long priorityId, String title, String description, String environment );

    /**
     * Gets the {@link Issue} with corresponding ID.
     * 
     * @param id the ID of the issue
     * @return The {@link Issue} if found, null if not
     */
    Issue findById( long id );

    /**
     * Gets the list of {@link IssueBaseDTO}s representing the ancestors of the given issue. First element will be the
     * top level item and last the closest to our item or the item itself.
     * 
     * @param id the ID of the given issue
     * @param includeTarget in case of <code>true</code> this item will be the last in the lsit
     * @return the ancestor list of this issue starting with top-level issue
     */
    List<IssueBaseDTO> getAncestorsFor( long id, boolean includeTarget );

    /**
     * Gets first level child issues of the given issue.
     * 
     * @param id the ID of the issue that is the parent
     * @return a non-null list of children
     */
    List<IssueBaseDTO> getChildernFor( long id );
}
