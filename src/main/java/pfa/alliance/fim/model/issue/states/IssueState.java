package pfa.alliance.fim.model.issue.states;

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

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

@Entity(name = "issue_state")
public class IssueState
    extends GenericModel
    implements Identifiable<Long>
{
    private static final long serialVersionUID = -6815173726653278343L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id" )
    private Long id;
	
    @Column( name = "code", nullable = false, length = 20 )
	private String code;

    @Column( name = "state_name", nullable = false, length = 50 )
    private String name;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "flow_id" )
    private IssueFlow flow;

    @Column( name = "start_state", nullable = false )
    private boolean startState;

    @Column( name = "end_state", nullable = false )
    private boolean endState;

    @OneToMany( mappedBy = "record", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
    private Set<IssueStateLocalized> localizations;

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

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public Set<IssueStateLocalized> getLocalizations()
    {
        return localizations;
    }

    public void setLocalizations( Set<IssueStateLocalized> localizations )
    {
        this.localizations = localizations;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public IssueFlow getFlow()
    {
        return flow;
    }

    public void setFlow( IssueFlow flow )
    {
        this.flow = flow;
    }

    public boolean isStartState()
    {
        return startState;
    }

    public void setStartState( boolean startState )
    {
        this.startState = startState;
    }

    public boolean isEndState()
    {
        return endState;
    }

    public void setEndState( boolean endState )
    {
        this.endState = endState;
    }
	
}
