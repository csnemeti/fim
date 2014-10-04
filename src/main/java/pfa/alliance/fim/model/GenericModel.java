/**
 * 
 */
package pfa.alliance.fim.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
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

    @Column( name = "created_at", insertable = false, updatable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Timestamp createdAt;

    @Column( name = "modified_at", insertable = false, updatable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Timestamp lastModified;

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
}
