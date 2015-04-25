/**
 * 
 */
package pfa.alliance.fim.dto;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserRoleInsideProject;

/**
 * This class represents a record about a specific user assigned to a project.
 * 
 * @author Csaba
 */
public class UserAssignedProjectDTO
    implements Serializable
{
    private static final long serialVersionUID = 154942171844443294L;

    private int projectId;

    private String name;

    private String code;

    private ProjectState state;

    private String stateName;

    private UserRoleInsideProject role;

    private String roleName;

    public int getProjectId()
    {
        return projectId;
    }

    public void setProjectId( int id )
    {
        this.projectId = id;
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

    public ProjectState getState()
    {
        return state;
    }

    public void setState( ProjectState state )
    {
        this.state = state;
    }

    public String getStateIcon()
    {
        return state.getStateClass();
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName( String stateName )
    {
        this.stateName = stateName;
    }

    public UserRoleInsideProject getRole()
    {
        return role;
    }

    public void setRole( UserRoleInsideProject role )
    {
        this.role = role;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName( String roleName )
    {
        this.roleName = roleName;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( projectId, role );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof UserAssignedProjectDTO ) )
        {
            return false;
        }
        UserAssignedProjectDTO userProjectDTO = (UserAssignedProjectDTO) obj;
        return Objects.equals( projectId, userProjectDTO.projectId ) && Objects.equals( role, userProjectDTO.role );
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "projectId", projectId );
        tsb.append( "code", code );
        tsb.append( "state", state );
        tsb.append( "role", role );
        return tsb.toString();
    }
}
