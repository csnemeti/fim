/**
 * 
 */
package pfa.alliance.fim.dto.issue;

import java.io.Serializable;

import pfa.alliance.fim.model.issue.IssueType;

/**
 * This class represents an issue summary.
 * 
 * @author Csaba
 */
public class IssueBaseDTO
    implements Serializable, IssueIdentifier
{
    /** The ID of this issue. */
    private long id;

    /** The ID of the parent issue. */
    private Long parentId;

    /** The ID of the project. */
    private int projectId;

    /** This is the issue code Project + Issue code. */
    private String code;

    /** The title of the issue. */
    private String title;

    /** The type of the issue. */
    private IssueType type;

    /** The URL to this issue. */
    private String url;

    /** The user who created the issue. */
    private String createdBy;

    /** The user ID who created the issue. */
    private Integer createdById;

    /** The user who has assigned the issue. */
    private String assignedTo;

    /** The user ID who has assigned the issue. */
    private Integer assignedToId;

    /**
     * Called when instance of this class is created.
     */
    public IssueBaseDTO()
    {
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
    public IssueBaseDTO( long id, Long parentId, int projectId, String code, String title, IssueType type )
    {
        this.id = id;
        this.parentId = parentId;
        this.projectId = projectId;
        this.code = code;
        this.title = title;
        this.type = type;
    }

    @Override
    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId( Long parentId )
    {
        this.parentId = parentId;
    }

    public int getProjectId()
    {
        return projectId;
    }

    public void setProjectId( int projectId )
    {
        this.projectId = projectId;
    }

    @Override
    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public IssueType getType()
    {
        return type;
    }

    public void setType( IssueType type )
    {
        this.type = type;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy( String createdBy, Integer createdById )
    {
        this.createdBy = createdBy;
        this.createdById = createdById;
    }

    public String getAssignedTo()
    {
        return assignedTo;
    }

    public void setAssignedTo( String assignedTo, Integer assignedToId )
    {
        this.assignedTo = assignedTo;
        this.assignedToId = assignedToId;
    }

    public Integer getCreatedById()
    {
        return createdById;
    }

    public Integer getAssignedToId()
    {
        return assignedToId;
    }

}
