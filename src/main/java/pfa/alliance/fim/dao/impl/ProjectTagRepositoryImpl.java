/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import pfa.alliance.fim.dao.Sort;
import pfa.alliance.fim.model.project.ProjectTag;

/**
 * This class represents the abstract implementation for {@link ProjectTag} repository.
 * 
 * @author Csaba
 */
abstract class ProjectTagRepositoryImpl<T extends ProjectTag>
    extends AbstractJpaRepository<T, Long>
{
    /**
     * Gets the default ordering criteria.
     * 
     * @return the default ordering criteria
     */
    protected abstract Sort createDefaultSortCriteria();

    /**
     * Gets all the {@link ProjectTag}s belonging to a Project with given ID, ordered based on default ordering
     * criteria.
     * 
     * @param projectId the ID of the project
     * @return the list of {@link ProjectTag}s
     * @see #findAllByProject(int, Sort)
     * @see #createDefaultSortCriteria()
     */
    public List<T> findAllByProject( final int projectId )
    {
        return findAllByProject( projectId, null );
    }

    /**
     * Gets all the {@link ProjectTag}s belonging to a Project with given ID, ordered based on user choice.
     * 
     * @param projectId the ID of the project
     * @param sort the ordering criteria, default ordering if null
     * @return the list of {@link ProjectTag}s
     * @see #createDefaultSortCriteria()
     */
    public List<T> findAllByProject( final int projectId, final Sort sort )
    {
        Sort ourSort = ( sort != null ) ? sort : createDefaultSortCriteria();
        // build criteria
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery( getEntityClass() );
        Root<T> root = criteria.from( getEntityClass() );
        // add where constraint
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
        TypedQuery<T> query = em.createQuery( criteria );
        return query.getResultList();
    }

    @Override
    protected Class<Long> getIdClass()
    {
        return Long.class;
    }
}
