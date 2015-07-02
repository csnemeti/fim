package pfa.alliance.fim.model.issue.states;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

@Entity(name = "issue_state")
public class IssueState extends GenericModel implements Identifiable<Integer> {
    private static final long serialVersionUID = -6815173726653278341L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id" )
	private Integer id;
	
	@Column(name = "code")
	private String code;

    @Column( name = "state_name", nullable = false, length = 50 )
    private String name;

    @Column( name = "initial_state" )
    private boolean initialState;
    
    @Column( name = "final_state")
    private boolean finalState;

    @Override
    public Integer getId()
    {
        return id;
    }

    @Override
    public void setId( Integer id )
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
}
