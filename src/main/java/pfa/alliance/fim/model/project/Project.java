package pfa.alliance.fim.model.project;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

@Entity( name = "project" )
public class Project
    extends GenericModel
    implements Identifiable<Integer>
{
    private static final long serialVersionUID = 177356267632L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;

    @Column( name = "project_name", nullable = false, length = 50 )
    private String name;

    @Column( name = "code", nullable = false, unique = true, length = 20 )
    private String code;

    @Column( name = "description", length = 2000 )
    private String description;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL )
    private Set<UserProjectRelation> userBoardData;

    public Project()
    {
        super();
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Override
    public Integer getId()
    {
        return id;
    }

    @Override
    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public Set<UserProjectRelation> getUserBoardData()
    {
        return userBoardData;
    }

    public void setUserBoardData( Set<UserProjectRelation> userBoardData )
    {
        this.userBoardData = userBoardData;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();

        hcb.append( code );
        hcb.append( name );
        hcb.append( description );

        return hcb.toHashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( !( obj instanceof Project ) )
        {
            return false;
        }

        Project that = (Project) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append( code, that.code );
        eb.append( name, that.name );
        eb.append( description, that.description );

        return eb.isEquals();
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );

        tsb.append( "id", id );
        tsb.append( "name", name );
        tsb.append( "code", code );

        return tsb.toString();
    }

}