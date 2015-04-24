package pfa.alliance.fim.model.issue.states;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import pfa.alliance.fim.model.localized.AbstractLocalizedPk;

@Embeddable
public class IssueStateLocalizedPk extends AbstractLocalizedPk<Integer>{
	private static final long serialVersionUID = 6937655471480130613L;
	
	@Column(name = "id_issue_state")
	private Integer issueStateId;
	
	@Override
	public Integer getEntityId() {
		return issueStateId;
	}

	@Override
	public void setEntityId(Integer id) {
		this.issueStateId = id;
	}

}
