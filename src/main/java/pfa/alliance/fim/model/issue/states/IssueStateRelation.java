package pfa.alliance.fim.model.issue.states;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pfa.alliance.fim.model.Identifiable;

@Entity(name = "issue_state_relation")
public class IssueStateRelation implements Identifiable<Integer> {

	@Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_flow")
	private IssueFlow flow;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "initial_state")
	private IssueState initialState;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "next_state")
	private IssueState nextState;

	@Column(name = "bidirectional_flag")
	private boolean bidirectional;

	public IssueState getInitialState() {
		return initialState;
	}

	public void setInitialState(IssueState initialState) {
		this.initialState = initialState;
	}

	public IssueState getNextState() {
		return nextState;
	}

	public void setNextState(IssueState nextState) {
		this.nextState = nextState;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
