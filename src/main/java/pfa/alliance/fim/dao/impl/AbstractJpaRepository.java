/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import pfa.alliance.fim.dao.JpaFindAllSupport;
import pfa.alliance.fim.dao.JpaFindAllWithPaginationSupport;
import pfa.alliance.fim.dao.JpaRepository;
import pfa.alliance.fim.dao.Sort;
import pfa.alliance.fim.dao.SortAndPage;
import pfa.alliance.fim.model.Identifiable;

/**
 * This class serves as base class for repository classes.
 * 
 * @author Csaba
 */
abstract class AbstractJpaRepository<T extends Identifiable<ID>, ID extends Serializable>
    extends BaseRepository
    implements JpaRepository<T, ID>
{
    @Override
    public boolean exists( ID id )
    {
        return findOne( id ) != null;
    }

    @Override
    public T findOne( ID id )
    {
        return getEntityManager().find( getEntityClass(), id );
    }

    @Override
    public long count()
    {
        String sql = "SELECT COUNT(f." + getIdColumnName() + ") FROM " + getEntityClass().getCanonicalName() + " f";
        TypedQuery<Long> countQuery = getEntityManager().createQuery( sql, Long.class );
        return countQuery.getSingleResult();
    }

    @Override
    public List<ID> findAllIds()
    {
        return findAllIds( null );
    }

    @Override
    public List<ID> findAllIds( Sort sort )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ID> criteriaQuery = criteriaBuilder.createQuery( getIdClass() );
        Root<T> from = criteriaQuery.from( getEntityClass() );
        criteriaQuery.select( from.get( getIdColumnName() ) );
        if ( sort != null && !sort.isEmpty() )
        {
            List<Order> orders = new ArrayList<Order>();
            for ( Entry<String, Boolean> sortEntry : sort.getSorting().entrySet() )
            {
                Path<?> path = from.get( sortEntry.getKey() );
                Order order = ( sortEntry.getValue() ) ? criteriaBuilder.asc( path ) : criteriaBuilder.desc( path );
                orders.add( order );
            }
            criteriaQuery.orderBy( orders );
        }
        TypedQuery<ID> query = em.createQuery( criteriaQuery );
        return query.getResultList();
    }

    @Override
    public List<T> save( Collection<T> objects )
    {
        List<T> saved = new ArrayList<T>();
        for ( T object : objects )
        {
            saved.add( save( object ) );
        }
        return saved;
    }

    @Override
    public T save( T obj )
    {
        if ( isNewEntity( obj ) )
        {
            return saveNewEntity( obj );
        }
        else
        {
            return updateExistingEntity( obj );
        }
    }

    /**
     * Saves a new entity. Do not call {@link #save(Identifiable)} or {@link #save(Collection)} from this method. It
     * will cause infinite loop.
     * 
     * @param obj the entity to be saved (inserted)
     * @return the saved object (might be the same or a different one)
     */
    protected T saveNewEntity( T obj )
    {
        getEntityManager().persist( obj );
        return obj;
    }

    /**
     * Updates an existing entity. Do not call {@link #save(Identifiable)} or {@link #save(Collection)} from this
     * method. It will cause infinite loop.
     * 
     * @param obj the entity to be saved (updated)
     * @return the saved object (might be the same or a different one)
     */
    protected T updateExistingEntity( T obj )
    {
        return getEntityManager().merge( obj );
    }

    @Override
    public void delete( ID id )
    {
        T obj = findOne( id );
        if ( obj != null )
        {
            delete( obj );
        }
    }

    @Override
    public void delete( T entity )
    {
        getEntityManager().remove( entity );
    }

    @Override
    public void delete( Collection<? extends T> entities )
    {
        for ( T obj : entities )
        {
            delete( obj );
        }
    }

    /**
     * Gets all the records form the given object. Pay attention to memory usage!
     * 
     * @return the list of objects as result.
     */
    public List<T> findAll()
    {
        return findAll( (Sort) null );
    }

    /**
     * Gets all the records form the given object. Pay attention to memory usage!
     * 
     * @param sort any ordering criteria
     * @return the list or records
     */
    public List<T> findAll( Sort sort )
    {
        // IMPORTANT: in order to prevent calling findAll..() methods (especially findAll() without pagination) we
        // enforce an interface to be implemented. We can provide a default implementation but we need to constrain the
        // possibility to call any time these methods.
        // What we did to make it work is that we provide a default implementation calling it is conditioned by the
        // repository class should implement JpaFindAllSupport.
        if ( !( this instanceof JpaFindAllSupport ) )
        {
            throw new IllegalStateException(
                                             "In order to call this method your reporsitory must implement JpaFindAllSupport" );
        }

        TypedQuery<T> query = createFindAllQuery( sort );
        return query.getResultList();
    }

    /**
     * Gets a subset of the records form the given object. Pay attention to memory usage!
     * 
     * @param page any ordering and pagination criteria
     * @return the list or records
     */
    public List<T> findAll( SortAndPage page )
    {
        if ( page == null )
        {
            throw new IllegalArgumentException( "SortAndPage should not be null" );
        }
        return findAll( page, page.getStartIndex(), page.getMaxItems() );
    }

    /**
     * Gets a subset of the records form the given object. Pay attention to memory usage!
     * 
     * @param sort any ordering criteria
     * @param startIndex the start index
     * @param maxItems the maximum number of items to retrieve
     * @return the list or records
     */
    public List<T> findAll( Sort sort, int startIndex, int maxItems )
    {
        // IMPORTANT: in order to prevent calling findAll..() methods (especially findAll() without pagination) we
        // enforce an interface to be implemented. We can provide a default implementation but we need to constrain the
        // possibility to call any time these methods.
        // What we did to make it work is that we provide a default implementation calling it is conditioned by the
        // repository class should implement JpaFindAllWithPaginationSupport.
        if ( sort == null )
        {
            throw new IllegalArgumentException( "Sort should not be null" );
        }
        if ( !( this instanceof JpaFindAllWithPaginationSupport ) )
        {
            throw new IllegalStateException(
                                             "In order to call this method your reporsitory must implement JpaFindAllWithPaginationSupport" );
        }
        TypedQuery<T> query = createFindAllQuery( sort );
        query.setFirstResult( startIndex );
        query.setMaxResults( maxItems );
        return query.getResultList();
    }

    /**
     * Builds a query for findAll with sorting condition.
     * 
     * @param sort the sorting condition (if null, it will not be added any ORDER BY to query)
     * @return the built query
     */
    private TypedQuery<T> createFindAllQuery( Sort sort )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery( getEntityClass() );
        Root<T> root = criteria.from( getEntityClass() );
        if ( sort != null && !sort.isEmpty() )
        {
            List<Order> order = new ArrayList<Order>();
            for ( Map.Entry<String, Boolean> entry : sort )
            {
                Path<?> path = root.get( entry.getKey() );
                order.add( ( entry.getValue() ) ? cb.asc( path ) : cb.desc( path ) );
            }
            criteria.orderBy( order );
        }
        return em.createQuery( criteria );
    }

    /**
     * Gets the {@link Class} of the entity that this repository is managing.
     * 
     * @return the entity {@link Class}
     */
    protected abstract Class<T> getEntityClass();

    /**
     * Gets the {@link Class} of the ID that this repository is managing.
     * 
     * @return the Id {@link Class}
     */
    protected abstract Class<ID> getIdClass();

    /**
     * Gets the name of the ID column.
     * 
     * @return this implementation return "id"
     */
    protected String getIdColumnName()
    {
        return "id";
    }

    /**
     * Find if this object is new. In order to do that, it checks if object ID is null.
     * 
     * @param obj the object to check
     * @return true if object is new
     */
    protected boolean isNewEntity( T obj )
    {
        ID id = obj.getId();
        return id == null;
    }
}
