/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import pfa.alliance.fim.dao.IssueRepository;
import pfa.alliance.fim.dto.issue.IssueBaseDTO;
import pfa.alliance.fim.dto.issue.IssueDTO;
import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.model.issue.IssuePriority;
import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;

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

    @Override
    public IssueBaseDTO findIssueBaseDtoById( long id )
    {
        IssueBaseDTO dto = null;
        Issue issue = findOne( id );
        if ( issue != null )
        {
            dto = toIssueBaseDTO( issue, null );
        }
        return dto;
    }

    @Override
    public List<IssueDTO> getChildernFor( long id )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Issue> criteriaQuery = cb.createQuery( Issue.class );
        Root<Issue> root = criteriaQuery.from( Issue.class );
        // add where constraint
        criteriaQuery.where( cb.equal( root.get( "parent" ).get( "id" ), id ) );
        // add ordering
        Order order = cb.asc( root.get( "id" ) );
        criteriaQuery.orderBy( order );
        TypedQuery<Issue> query = em.createQuery( criteriaQuery );
        List<Issue> issues = query.getResultList();
        List<IssueDTO> result = new ArrayList<IssueDTO>();
        for ( Issue issue : issues )
        {
            result.add( toIssueDTO( issue, id ) );
        }
        return result;
    }

    private static IssueDTO toIssueDTO( Issue issue, Long parentId )
    {
        IssueDTO issueDto = new IssueDTO();
        populateIssueBaseDTO( issueDto, issue, parentId );
        IssueState state = issue.getState();
        if ( state != null )
        {
            issueDto.setState( state.getId(), state.getName() );
        }
        IssuePriority priority = issue.getPriority();
        if ( priority != null )
        {
            issueDto.setPriority( priority.getId(), priority.getName() );
        }
        return issueDto;
    }

    private static IssueBaseDTO toIssueBaseDTO( Issue issue, Long parentId )
    {
        IssueBaseDTO issueDto = new IssueBaseDTO();
        populateIssueBaseDTO( issueDto, issue, parentId );
        return issueDto;
    }

    private static void populateIssueBaseDTO( IssueBaseDTO issueDto, Issue issue, Long parentId )
    {
        issueDto.setId( issue.getId() );
        if ( parentId != null )
        {
            issueDto.setParentId( parentId );
        }
        else
        {
            Issue parent = issue.getParent();
            if ( parent != null )
            {
                issueDto.setParentId( parent.getId() );
            }
        }
        Project project = issue.getProject();
        issueDto.setCode( project.getCode() + "-" + issue.getCode() );
        issueDto.setTitle( issue.getTitle() );
        issueDto.setType( issue.getType() );
    }

}
