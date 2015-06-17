/**
 * 
 */
package pfa.alliance.fim.dao;

import pfa.alliance.fim.model.issue.Issue;

/**
 * This interface defines method for managing {@link Issue}s.
 * 
 * @author Csaba
 */
public interface IssueRepository
    extends JpaRepository<Issue, Long>
{

}
