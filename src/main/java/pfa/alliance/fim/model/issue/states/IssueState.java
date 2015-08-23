package pfa.alliance.fim.model.issue.states;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

@Entity(name = "issue_state")
public class IssueState
    extends GenericModel
    implements Identifiable<Long>
{
    private static final long serialVersionUID = -6815173726653278348L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id" )
    private Long id;
	
    @Column( name = "code", nullable = false, length = 20 )
	private String code;

    @Column( name = "state_name", nullable = false, length = 50 )
    private String name;

    @Column( name = "initial_state", nullable = false )
    private boolean initialState;
    
    @Column( name = "final_state", nullable = false )
    private boolean finalState;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "flow_id" )
    private IssueFlow flow;

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

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

	public boolean isInitialState() 
	{
		return initialState;
	}

	public void setInitialState(boolean initialState) 
	{
		this.initialState = initialState;
	}

	public boolean isFinalState() 
	{
		return finalState;
	}

	public void setFinalState(boolean finalState) 
	{
		this.finalState = finalState;
	}

    public IssueFlow getFlow()
    {
        return flow;
    }

    public void setFlow( IssueFlow flow )
    {
        this.flow = flow;
    }

    @Override
    public String toString()
    {
        return "IssueState [id=" + id + ", code=" + code + ", name=" + name + ", initialState=" + initialState
            + ", finalState=" + finalState + "]";
    }
}
