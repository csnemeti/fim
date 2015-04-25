/**
 * 
 */
package pfa.alliance.fim.model.issue;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import pfa.alliance.fim.model.Identifiable;

/**
 * This enumeration defines the issue priority.
 * 
 * @author Csaba
 */
@Entity( name = "issue_priority" )
public class IssuePriority
    implements Identifiable<String>
{
    /** The name of the priority. */
    @Id
    @Column( name = "id" )
    private String id;

    /** The ordering value. */
    @Column( name = "order_value", nullable = false, length = 50 )
    private int order;

    @Column( name = "default_option", nullable = false )
    private boolean defaultOption;

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public void setId( String id )
    {
        this.id = id;
    }

    /**
     * Gets the value used for ordering.
     * 
     * @return the value used for ordering
     */
    public int getOrder()
    {
        return order;
    }

    public void setOrder( int order )
    {
        this.order = order;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof IssuePriority ) )
        {
            return false;
        }
        IssuePriority priority = (IssuePriority) obj;
        return Objects.equals( id, priority.id );
    }

    @Override
    public String toString()
    {
        return "IssuePriority[" + id + "]";
    }
}
