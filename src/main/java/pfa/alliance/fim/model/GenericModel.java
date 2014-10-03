/**
 * 
 */
package pfa.alliance.fim.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * This class defines the base of an entity
 * 
 * @author Nemeti
 */
@MappedSuperclass
public class GenericModel
    implements Serializable
{
    private static final long serialVersionUID = 553540917593216250L;

    @Column( name = "version" )
    @Version
    private int version;

    @Column( name = "created_at", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    public Timestamp createdAt;

    @Column( name = "modified_at", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    public Timestamp lastModified;

    public int getVersion()
    {
        return version;
    }

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public Timestamp getLastModified()
    {
        return lastModified;
    }

    /**
     * Method called before object is persisted.
     */
    @PrePersist
    protected void beforePersist()
    {
        lastModified = new Timestamp( System.currentTimeMillis() );
        if ( createdAt == null )
        {
            createdAt = lastModified;
        }
    }
}
