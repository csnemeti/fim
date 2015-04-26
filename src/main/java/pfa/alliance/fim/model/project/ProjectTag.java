/**
 * 
 */
package pfa.alliance.fim.model.project;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

/**
 * This class represents base for tags that are applied on Issues belonging to a specific project.
 * 
 * @author Csaba
 */
@MappedSuperclass
public abstract class ProjectTag
    extends GenericModel
    implements Identifiable<Long>
{

    private static final long serialVersionUID = 3375634485765398011L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "project_id" )
    private Project project;

    @Column( name = "background_color", nullable = true, length = 10 )
    private String backgroundColor;

    @Column( name = "text_color", nullable = true, length = 10 )
    private String textColor;

    /**
     * Gets the value of the tag (component / label / etc).
     * 
     * @return the value of the tag
     */
    public abstract String getTagValue();

    /**
     * Sets the value of the tag (component / label / etc).
     * 
     * @param tagValue the new value tag should have
     */
    public abstract void setTagValue( String tagValue );

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

    public Project getProject()
    {
        return project;
    }

    public void setProject( Project project )
    {
        this.project = project;
    }

    public String getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor( String backgroundColor )
    {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor()
    {
        return textColor;
    }

    public void setTextColor( String textColor )
    {
        this.textColor = textColor;
    }

    /**
     * Gets the ID of the project.
     * 
     * @return the project id
     */
    protected Integer getProjectId()
    {
        return ( project != null ) ? project.getId() : null;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( getTagValue(), getProjectId() );
    }

    /**
     * Base class for equals().
     * 
     * @param projectTag the object to check
     * @return true if the object is equals with this one
     */
    protected boolean eqauls( ProjectTag projectTag )
    {
        return Objects.equals( getTagValue(), projectTag.getTagValue() )
            && Objects.equals( getProjectId(), projectTag.getProjectId() );
    }
}
