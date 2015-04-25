/**
 * 
 */
package pfa.alliance.fim.dao;

import pfa.alliance.fim.model.issue.IssuePriority;
import pfa.alliance.fim.model.issue.IssueType;

/**
 * This interface defines the {@link IssueType} repository methods.
 * 
 * @author Csaba
 */
public interface IssuePriorityRepository
    extends JpaReadOnlyRepository<IssuePriority, String>, JpaFindAllSupport<IssuePriority, String>
{

}
