package pfa.alliance.fim.dao.impl;

import javax.inject.Singleton;

import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;

@Singleton
public class IssueStateRepositoryImpl
    extends AbstractJpaRepository<IssueState, Long>
    implements IssueStateRepository
{

    @Override
    protected Class<IssueState> getEntityClass()
    {
        return IssueState.class;
    }

    @Override
    protected Class<Long> getIdClass()
    {
        return Long.class;
    }

    @Override
    public IssueState findStartStateForProject( Project project )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
