/**
 * 
 */
package pfa.alliance.fim.model.project;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.batoo.common.log.ToStringBuilder;

/**
 * This class defines a {@link Project} component.
 * 
 * @author Csaba
 */
@Entity( name = "project_component" )
public class ProjectComponent
    extends ProjectTag
{
    private static final long serialVersionUID = 4505175118389976032L;

    /** The name of the component. */
    @Column( name = "component_name", nullable = false, length = 50 )
    private String componentName;

    /**
     * {@inheritDoc}
     * 
     * @see #getComponentName()
     */
    @Override
    public String getTagValue()
    {
        return componentName;
    }

    /**
     * {@inheritDoc}
     * 
     * @see #setComponentName(String)
     */
    @Override
    public void setTagValue( String tagValue )
    {
        this.componentName = tagValue;
    }

    /**
     * Gets the name of the component.
     * 
     * @return the component name
     */
    public String getComponentName()
    {
        return componentName;
    }

    /**
     * Sets the name of the component.
     * 
     * @param componentName the name of the component
     */
    public void setComponentName( String componentName )
    {
        this.componentName = componentName;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof ProjectComponent ) )
        {
            return false;
        }
        ProjectComponent component = (ProjectComponent) obj;
        return super.eqauls( component );
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "id", getId() );
        tsb.append( "component", getTagValue() );
        tsb.append( "project_id", getProjectId() );
        return tsb.toString();
    }
}
