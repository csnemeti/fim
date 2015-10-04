/**
 * 
 */
package pfa.alliance.fim.dao;

import java.util.List;

import pfa.alliance.fim.dto.issue.IssueBaseDTO;
import pfa.alliance.fim.dto.issue.IssueDTO;
import pfa.alliance.fim.model.issue.Issue;

/**
 * This interface defines method for managing {@link Issue}s.
 * 
 * @author Csaba
 */
public interface IssueRepository
    extends JpaRepository<Issue, Long>
{
    /**
     * Gets the {@link IssueBaseDTO} instance based on the Issue ID.
     * 
     * @param id the ID of the issue
     * @return the corresponding {@link IssueBaseDTO} or null if ID is invalid
     */
    IssueBaseDTO findIssueBaseDtoById( long id );

    /**
     * Gets first level child issues of the given issue.
     * 
     * @param id the ID of the issue that is the parent
     * @return a non-null list of children
     */
    List<IssueDTO> getChildernFor( long id );

}
