package pfa.alliance.fim.dto.issue;

public class IssueStateRelationDTO {

	private IssueStateDTO fromState;

	private IssueStateDTO nextState;

	private boolean bidirectional;

	public IssueStateDTO getFromState() {
		return fromState;
	}

	public void setFromState(IssueStateDTO fromStateDTO) {
		this.fromState = fromStateDTO;
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
