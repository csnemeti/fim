package pfa.alliance.fim.dao.impl;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.model.issue.states.IssueFlow;
import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;

/**
 * Repository for {@link IssueFlow}.
 * 
 * @author Denis
 * @author Csaba
 */
@Singleton
public class IssueStateRepositoryImpl
    extends AbstractJpaRepository<IssueState, Long>
    implements IssueStateRepository
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( IssueStateRepositoryImpl.class );

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
    public List<IssueFlow> findAllFlows()
    {
        LOG.debug( "Getting all usable IssueFlows" );
        EntityManager em = getEntityManager();
        TypedQuery<IssueFlow> query =
            em.createQuery( "SELECT p FROM pfa.alliance.fim.model.issue.states.IssueFlow p ORDER BY p.name",
                            IssueFlow.class );
        List<IssueFlow> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        return results;
    }

    @Override
    public IssueFlow findFlowById( final long id )
    {
        LOG.debug( "Getting IssueFlow with ID = {}", id );
        return getEntityManager().find( IssueFlow.class, id );
    }

    @Override
    public IssueState findStartStateForProject( Project project )
    {
        LOG.debug( "Finding IssueFlow used by Project with ID = {}", project.getId() );
        IssueFlow flow = project.getIssueFlow();
        LOG.debug( "Finding initial IssueState used by IssueFlow with ID = {}", flow.getId() );
        EntityManager em = getEntityManager();
        TypedQuery<IssueState> query =
            em.createQuery( "SELECT s FROM pfa.alliance.fim.model.issue.states.IssueState s "
                + "INNER JOIN s.flow f WHERE f.id = ? AND s.initialState = true", IssueState.class );
        query.setParameter( 1, flow.getId() );
        List<IssueState> results = query.getResultList();
        LOG.debug( "Initial IssueState(s) used by IssueFlow with ID = {} : {}", flow.getId(), results );
        return DaoUtil.uniqueResult( results );
    }

}
