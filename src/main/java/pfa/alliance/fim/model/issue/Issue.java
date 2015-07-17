/**
 * 
 */
package pfa.alliance.fim.model.issue;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;
import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectComponent;
import pfa.alliance.fim.model.project.ProjectLabel;
import pfa.alliance.fim.model.user.User;

/**
 * This class represents an issue.
 * 
 * @author Csaba
 */
@Entity( name = "issue" )
public class Issue
    extends GenericModel
    implements Identifiable<Long>
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY, optional = true )
    @JoinColumn( name = "parent_id" )
    private Issue parent;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "project_id" )
    private Project project;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "reported_by" )
    private User reporter;

    @ManyToOne( fetch = FetchType.EAGER, optional = true )
    @JoinColumn( name = "assigned_to" )
    private User assigned;

    @Column( name = "title", nullable = false, length = 250 )
    private String title;

    @Column( name = "description", nullable = true, length = 4000 )
    private String description;

    @Column( name = "environment", nullable = true, length = 1000 )
    private String environment;

    @Enumerated( EnumType.STRING )
    @Column( name = "type", length = 20, nullable = false )
    private IssueType type;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "state_id" )
    private IssueState state;

    @Enumerated( EnumType.STRING )
    @Column( name = "resolution", length = 20, nullable = false )
    private IssueResolution resolution;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "priority_id" )
    private IssuePriority priority;

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "issue_components_map", joinColumns = @JoinColumn( name = "issue_id", referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn( name = "component_id", referencedColumnName = "id" ) )
    private Set<ProjectComponent> components;

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "issue_labels_map", joinColumns = @JoinColumn( name = "issue_id", referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn( name = "label_id", referencedColumnName = "id" ) )
    private Set<ProjectLabel> labels;

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

    public Issue getParent()
    {
        return parent;
    }

    public void setParent( Issue parent )
    {
        this.parent = parent;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject( Project project )
    {
        this.project = project;
    }

    public User getReporter()
    {
        return reporter;
    }

    public void setReporter( User reporter )
    {
        this.reporter = reporter;
    }

    public User getAssigned()
    {
        return assigned;
    }

    public void setAssigned( User assigned )
    {
        this.assigned = assigned;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getEnvironment()
    {
        return environment;
    }

    public void setEnvironment( String environment )
    {
        this.environment = environment;
    }

    public IssueType getType()
    {
        return type;
    }

    public void setType( IssueType type )
    {
        this.type = type;
    }

    public IssueState getState()
    {
        return state;
    }

    public void setState( IssueState state )
    {
        this.state = state;
    }

    public IssueResolution getResolution()
    {
        return resolution;
    }

    public void setResolution( IssueResolution resolution )
    {
        this.resolution = resolution;
    }

    public IssuePriority getPriority()
    {
        return priority;
    }

    public void setPriority( IssuePriority priority )
    {
        this.priority = priority;
    }

    public Set<ProjectComponent> getComponents()
    {
        return components;
    }

    public void setComponents( Set<ProjectComponent> components )
    {
        this.components = components;
    }

    public Set<ProjectLabel> getLabels()
    {
        return labels;
    }

    public void setLabels( Set<ProjectLabel> labels )
    {
        this.labels = labels;
    }

}
