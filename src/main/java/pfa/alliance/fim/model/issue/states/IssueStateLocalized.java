package pfa.alliance.fim.model.issue.states;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pfa.alliance.fim.model.localized.AbstractLocalizedModel;

@Entity(name = "issue_state_ml")
public class IssueStateLocalized extends AbstractLocalizedModel<IssueStateLocalizedPk>{
	private static final long serialVersionUID = 8740714265309750234L;
	
	@Column(name = "id_issue_state")
	private Integer issueStateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue_state")
	private IssueState issueState;
	
	@Column(name = "name")
	private String issueStateName;
	
	@Override
	public String getName() {
		return issueStateName;
	}

	@Override
	public void setName(String name) {
		issueStateName = name;
	}

}
