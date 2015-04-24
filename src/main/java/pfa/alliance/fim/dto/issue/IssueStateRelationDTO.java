package pfa.alliance.fim.dto.issue;

public class IssueStateRelationDTO {

	private IssueStateDTO initialState;

	private IssueStateDTO nextState;

	private boolean bidirectional;

	public IssueStateDTO getInitialState() {
		return initialState;
	}

	public void setInitialState(IssueStateDTO initialState) {
		this.initialState = initialState;
	}

	public IssueStateDTO getNextState() {
		return nextState;
	}

	public void setNextState(IssueStateDTO nextState) {
		this.nextState = nextState;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

}
