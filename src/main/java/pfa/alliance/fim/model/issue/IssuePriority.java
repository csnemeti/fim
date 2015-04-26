/**
 * 
 */
package pfa.alliance.fim.model.issue;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import pfa.alliance.fim.model.Identifiable;

/**
 * This enumeration defines the issue priority.
 * 
 * @author Csaba
 */
@Entity( name = "issue_priority" )
public class IssuePriority
    implements Identifiable<Long>
{
    /** The name of the priority. */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;

    @Column( name = "priority_name", nullable = false, length = 50 )
    private String name;

    /** The ordering value. */
    @Column( name = "order_value", nullable = false )
    private int order;

    @Column( name = "default_option", nullable = false )
    private boolean defaultOption;

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

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public boolean isDefaultOption()
    {
        return defaultOption;
    }

    public void setDefaultOption( boolean defaultOption )
    {
        this.defaultOption = defaultOption;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( name );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof IssuePriority ) )
        {
            return false;
        }
        IssuePriority priority = (IssuePriority) obj;
        return Objects.equals( name, priority.name );
    }

    @Override
    public String toString()
    {
        return "IssuePriority[" + id + ", " + name + "]";
    }
}
