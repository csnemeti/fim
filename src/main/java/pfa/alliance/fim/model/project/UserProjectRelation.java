package pfa.alliance.fim.model.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;
import pfa.alliance.fim.model.user.User;

/**
 * The entity that contains the mapping between the Board and User . This entity contains the key (UserBoardDataPk ) and
 * the common data that is shared between this two entities.
 * 
 * @author Dennis
 */
@Entity( name = "user_project_relation" )
@Table( uniqueConstraints = @UniqueConstraint( name = "user_project_relation_unq", columnNames = { "user_id",
    "project_id" } ) )
public class UserProjectRelation
    extends GenericModel
    implements Identifiable<Long>
{
    private static final long serialVersionUID = 2467663500482260838L;

    /** The relation ID. */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;

    /** The user that is mapped to project. */
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "user_id" )
    private User user;

    /** The owner project. */
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "project_id" )
    private Project project;

    /** The role of the user inside the project. */
    @Enumerated( EnumType.STRING )
    @Column( name = "user_role", length = 20, nullable = false )
    private UserRoleInsideProject userRole;

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId( Long id )
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject( Project project )
    {
        this.project = project;
    }

    public UserRoleInsideProject getUserRole()
    {
        return userRole;
    }

    public void setUserRole( UserRoleInsideProject role )
    {
        this.userRole = role;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();

        hcb.append( getIdentifiableId( user ) );
        hcb.append( getIdentifiableId( project ) );
        hcb.append( userRole );

        return hcb.toHashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( !( obj instanceof UserProjectRelation ) )
        {
            return false;
        }

        UserProjectRelation that = (UserProjectRelation) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append( getIdentifiableId( user ), getIdentifiableId( that.user ) );
        eb.append( getIdentifiableId( project ), getIdentifiableId( that.project ) );
        eb.append( userRole, that.userRole );

        return eb.isEquals();
    }

    /**
     * Gets the ID of an object.
     * 
     * @param object the object
     * @return the ID of the object
     */
    private static Integer getIdentifiableId( Identifiable<Integer> object )
    {
        return ( object == null ) ? 0 : object.getId();
    }

}
