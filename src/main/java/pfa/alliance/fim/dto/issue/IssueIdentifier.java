/**
 * 
 */
package pfa.alliance.fim.dto.issue;

/**
 * This interface defines the methods necessary for creating links to issue.
 * 
 * @author Csaba
 */
public interface IssueIdentifier
{
    /**
     * Gets a code in the following format: Project_Code-Issue_Code.
     * 
     * @return a unique code identifying the issue
     */
    String getCode();

    /**
     * Gets the issue ID.
     * 
     * @return the ID of the issue
     */
    long getId();
}
