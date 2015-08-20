/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.dao.Sort;
import pfa.alliance.fim.model.issue.IssuePriority;

/**
 * This class is the implementation of {@link IssuePriorityRepository}.
 * 
 * @author Csaba
 */
@Singleton
class IssuePriorityRepositoryImpl
    extends AbstractJpaRepository<IssuePriority, Long>
    implements IssuePriorityRepository
{

    @Override
    public List<IssuePriority> findAll( int projectId )
    {
        return findAll( projectId, null );
    }

    @Override
    public List<IssuePriority> findAll( int projectId, Sort sort )
    {
        Sort ourSort = ( sort != null ) ? sort : createDefaultSorting();
        // build criteria
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<IssuePriority> criteria = cb.createQuery( getEntityClass() );
        Root<IssuePriority> root = criteria.from( getEntityClass() );
        criteria.where( cb.equal( root.get( "project" ).get( "id" ), projectId ) );
        // add ordering
        List<Order> order = new ArrayList<Order>();
        for ( Map.Entry<String, Boolean> entry : ourSort )
        {
            Path<?> path = root.get( entry.getKey() );
            order.add( ( entry.getValue() ) ? cb.asc( path ) : cb.desc( path ) );
        }
        criteria.orderBy( order );
        // build & run query
        TypedQuery<IssuePriority> query = em.createQuery( criteria );
        return query.getResultList();
    }

    @Override
    protected Class<IssuePriority> getEntityClass()
    {
        return IssuePriority.class;
    }

    @Override
    protected Class<Long> getIdClass()
    {
        return Long.class;
    }

    /**
     * Creates the default ordering that puts the {@link IssuePriority} in a natural order.
     * 
     * @return the default sorting order
     */
    private static Sort createDefaultSorting()
    {
        Sort sort = new Sort();
        sort.add( "order", false );
        return sort;
    }
}
