/**
 * 
 */
package pfa.alliance.fim.model.user;

import java.sql.Timestamp;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

/**
 * This class is used for defining a user One time link.
 * 
 * @author Csaba
 */
@Entity( name = "user_one_time_link" )
public class UserOneTimeLink
    extends GenericModel
    implements Identifiable<Long>
{
    private static final long serialVersionUID = -8473416614781695814L;

    /** The link ID. */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    /** The unique identifier of link. */
    @Column( name = "uuid", length = 200, nullable = false )
    private String uuid;

    /** The time when the link expires. */
    @Column( name = "expires_at", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Timestamp expiresAt;

    /** The user that this link is bound to. */
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "user_id" )
    private User user;

    /** The type of the one time link. */
    @Enumerated( EnumType.STRING )
    @Column( name = "designation", length = 30, nullable = false )
    private OneTimeLinkType designation;

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

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid( String uuid )
    {
        this.uuid = uuid;
    }

    public Timestamp getExpiresAt()
    {
        return expiresAt;
    }

    public void setExpiresAt( Timestamp expiresAt )
    {
        this.expiresAt = expiresAt;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public OneTimeLinkType getDesignation()
    {
        return designation;
    }

    public void setDesignation( OneTimeLinkType designation )
    {
        this.designation = designation;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append( uuid ).append( expiresAt ).append( designation );
        return hcb.toHashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof UserOneTimeLink ) )
        {
            return false;
        }
        UserOneTimeLink link = (UserOneTimeLink) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append( uuid, link.uuid );
        eb.append( expiresAt, link.expiresAt );
        eb.append( designation, link.designation );
        return eb.isEquals();
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "id", id ).append( "uuid", uuid );
        tsb.append( "designation", designation ).append( "expiresAt", expiresAt );
        return tsb.toString();
    }
}
