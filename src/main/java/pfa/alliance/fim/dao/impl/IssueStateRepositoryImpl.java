package pfa.alliance.fim.dao.impl;

import javax.inject.Singleton;

import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.model.issue.states.IssueState;

@Singleton
public class IssueStateRepositoryImpl extends AbstractJpaRepository<IssueState, Integer> implements IssueStateRepository{

	@Override
	protected Class<IssueState> getEntityClass() {
		return IssueState.class;
	}

	@Override
	protected Class<Integer> getIdClass() {
		return Integer.class;
	}

}
