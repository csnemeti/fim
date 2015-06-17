/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import javax.inject.Singleton;

import pfa.alliance.fim.dao.IssueRepository;
import pfa.alliance.fim.model.issue.Issue;

/**
 * This is the implementation of {@link IssueRepository}.
 * 
 * @author Csaba
 */
@Singleton
class IssueRepositoryImpl
    extends AbstractJpaRepository<Issue, Long>
    implements IssueRepository
{

    @Override
    protected Class<Issue> getEntityClass()
    {
        return Issue.class;
    }

    @Override
    protected Class<Long> getIdClass()
    {
        return Long.class;
    }

}
