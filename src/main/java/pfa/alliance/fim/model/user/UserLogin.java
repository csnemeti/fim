/**
 * 
 */
package pfa.alliance.fim.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import pfa.alliance.fim.model.Identifiable;

/**
 * This class keeps when the user logged in.
 * 
 * @author Nemeti
 */
@Entity( name = "user_login" )
public class UserLogin
    implements Serializable, Identifiable<Long>
{
    private static final long serialVersionUID = -3911814345921477177L;

    /** The ID of a user login record. */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    /** The timestamp when this occurred. */
    @Column( name = "created_at", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Timestamp createdAt;

    /** The user to whom this record belongs. */
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "user_id" )
    private User user;

    /** For JPA version. */
    @Column( name = "version" )
    @Version
    private int version;

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

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt( Timestamp createdAt )
    {
        this.createdAt = createdAt;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion( int version )
    {
        this.version = version;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append( user.getId() );
        hcb.append( createdAt );
        return hcb.toHashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof UserLogin ) )
        {
            return false;
        }
        UserLogin userLogin = (UserLogin) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append( user.getId(), userLogin.user.getId() );
        eb.append( createdAt, userLogin.createdAt );
        return eb.isEquals();
    }

    @Override
    public String toString()
    {
        return "UserLogin[user id = " + user.getId() + ", login at = " + createdAt + "]";
    }
}
