/**
 * 
 */
package pfa.alliance.fim.dto;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.dto.issue.IssueFlowDTO;
import pfa.alliance.fim.model.project.ProjectState;

/**
 * DTO describing a Project.
 * 
 * @author Csaba
 */
public class ProjectDTO
{
    private int id;

    private String name;

    private String code;

    private ProjectState state;

    private Timestamp stateChangedAt;

    private boolean hidden;


    private String description;
    
    private IssueFlowDTO issueFlow;

    private UserDTO owner;

    private Timestamp createAt;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public UserDTO getOwner()
    {
        return owner;
    }

    public void setOwner( UserDTO owner )
    {
        this.owner = owner;
    }

    public ProjectState getState()
    {
        return state;
    }

    public void setState( ProjectState state )
    {
        this.state = state;
    }

    public Timestamp getStateChangedAt()
    {
        return stateChangedAt;
    }

    public void setStateChangedAt( Timestamp stateChangedAt )
    {
        this.stateChangedAt = stateChangedAt;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public void setHidden( boolean hidden )
    {
        this.hidden = hidden;
    }

    public Timestamp getCreateAt()
    {
        return createAt;
    }

    public void setCreateAt( Timestamp createAt )
    {
        this.createAt = createAt;
    }

    public IssueFlowDTO getIssueFlow() {
		return issueFlow;
	}

	public void setIssueFlow(IssueFlowDTO issueFlow) {
		this.issueFlow = issueFlow;
	}

	public String getOwnerInfo()
    {
        StringBuilder sb = new StringBuilder();
        if ( owner != null )
        {
            String name = owner.getName();
            String separatorLeft = "";
            String separatorRight = "";
            if ( StringUtils.isNotBlank( name ) )
            {
                separatorLeft = " ( ";
                separatorRight = " )";
            }
            else
            {
                name = "";
            }
            sb.append( name ).append( separatorLeft ).append( owner.getEmail() ).append( separatorRight );
        }
        else
        {
            sb.append( "<< unknown >>" );
        }
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf( id ).hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof ProjectDTO ) )
        {
            return false;
        }
        ProjectDTO projectDto = (ProjectDTO) obj;
        return id == projectDto.id;
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "id", id );
        tsb.append( "code", code );
        tsb.append( "name", name );
        return tsb.toString();
    }

}
