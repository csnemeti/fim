/**
 * 
 */
package pfa.alliance.fim.model.issue;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import pfa.alliance.fim.model.Identifiable;
import pfa.alliance.fim.model.project.Project;

/**
 * This enumeration defines the issue priority.
 * 
 * @author Csaba
 */
@Entity( name = "issue_priority" )
public class IssuePriority
    implements Identifiable<Long>, Comparable<IssuePriority>
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

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "project_id" )
    private Project project;

    @OneToMany( mappedBy = "record", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
    private Set<IssuePriorityLocalized> localizations;

    /**
     * Called when instance of this class is created.
     */
    public IssuePriority()
    {
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param name the name of the priority
     * @param order the order value
     * @param defaultOption true if this is the default priority
     * @param project the project where this priority belongs to
     */
    public IssuePriority( String name, int order, boolean defaultOption, Project project )
    {
        this.name = name;
        this.order = order;
        this.defaultOption = defaultOption;
        this.project = project;
    }

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

    public Set<IssuePriorityLocalized> getLocalizations()
    {
        return localizations;
    }

    public void setLocalizations( Set<IssuePriorityLocalized> localizations )
    {
        this.localizations = localizations;
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

    @Override
    public int compareTo( IssuePriority o )
    {
        return order - o.getOrder();
    }
}
