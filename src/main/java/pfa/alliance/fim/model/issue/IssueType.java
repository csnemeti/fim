/**
 * 
 */
package pfa.alliance.fim.model.issue;

import java.util.Arrays;
import java.util.List;

/**
 * Defines the possible types of an issue and allowed types for a child issue.
 * 
 * @author Csaba
 */
public enum IssueType
{
    SUB_TASK, TASK( SUB_TASK ), ENHANCEMENT( TASK ), BUG( TASK ), SUB_STORY( ENHANCEMENT, TASK ), STORY( SUB_STORY,
        ENHANCEMENT, TASK ), FEATURE( STORY, ENHANCEMENT, TASK ), EPIC( FEATURE, STORY, ENHANCEMENT, TASK );
    /** The types allowed for a child issue. */
    private final IssueType[] subTypes;
    
    /**
     * Called when instance of this class is created.
     * 
     * @param children the allowed types of a child issue
     */
    private IssueType( IssueType... children )
    {
        this.subTypes = children;
    }

    /**
     * Gets the list of types a child issue may haver
     * 
     * @return the list of child issue types
     */
    public List<IssueType> getSubTypes()
    {
        return Arrays.asList( subTypes );
    }
}
