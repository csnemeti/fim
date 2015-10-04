/**
 * 
 */
package pfa.alliance.fim.dto.issue;

import java.util.Locale;

import pfa.alliance.fim.model.issue.IssueType;

/**
 * This class represents an issue detail.
 * 
 * @author Csaba
 */
public class IssueDTO
    extends IssueBaseDTO
{

    /** The ID of a State. */
    private Long stateId;

    /** The name of the state in a specific {@link Locale}. */
    private String stateName;

    /** The ID of a Priority. */
    private Long priorityId;

    /** The name of the priority in a specific {@link Locale}. */
    private String priorityName;

    /**
     * Called when instance of this class is created.
     */
    public IssueDTO()
    {
        super();
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param id the ID of the issue
     * @param parentId the ID of the parent issue
     * @param projectId the ID of the proje3ct where the issue belongs
     * @param code the Code of the issue
     * @param title the issue title
     * @param type the issue type
     */
    public IssueDTO( long id, Long parentId, int projectId, String code, String title, IssueType type )
    {
        super( id, parentId, projectId, code, title, type );
    }

    public Long getStateId()
    {
        return stateId;
    }

    public void setState( Long stateId, String stateName )
    {
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public Long getPriorityId()
    {
        return priorityId;
    }

    public void setPriority( Long priorityId, String priorityName )
    {
        this.priorityId = priorityId;
        this.priorityName = priorityName;
    }

    public String getStateName()
    {
        return stateName;
    }

    public String getPriorityName()
    {
        return priorityName;
    }

}
