/**
 * 
 */
package pfa.alliance.fim.model.project;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.batoo.common.log.ToStringBuilder;

/**
 * This class represents a {@link Project} label.
 * 
 * @author Csaba
 */
@Entity( name = "project_label" )
public class ProjectLabel
    extends ProjectTag
{
    private static final long serialVersionUID = 5481816108523144425L;

    /** The name of the label. */
    @Column( name = "label", nullable = false, length = 50 )
    private String label;

    /**
     * {@inheritDoc}
     * 
     * @see #getLabel()
     */
    @Override
    public String getTagValue()
    {
        return label;
    }

    /**
     * {@inheritDoc}
     * 
     * @see #setLabel(String)
     */
    @Override
    public void setTagValue( String tagValue )
    {
        this.label = tagValue;
    }

    /**
     * Gets the name of this project label.
     * 
     * @return the label name
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Sets the project's label name.
     * 
     * @param label the name of the label
     */
    public void setLabel( String label )
    {
        this.label = label;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof ProjectLabel ) )
        {
            return false;
        }
        ProjectLabel label = (ProjectLabel) obj;
        return super.eqauls( label );
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "id", getId() );
        tsb.append( "label", getTagValue() );
        tsb.append( "project_id", getProjectId() );
        return tsb.toString();
    }

}
