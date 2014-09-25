/**
 * 
 */
package pfa.alliance.fim.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import pfa.alliance.fim.model.Identifiable;

/**
 * This class serves as base class for repository classes.
 * 
 * @author Csaba
 */
public abstract class AbstractJpaRepository<T extends Identifiable<ID>, ID extends Serializable>
{
    /** The entity manager used in the application. */
    private EntityManager entityManager;

    /**
     * Check if a record with the given ID exists in database.
     * 
     * @param id the ID to check
     * @return true if the ID was found, false otherwise
     */
    public boolean exists( ID id )
    {
        return findOne( id ) != null;
    }

    /**
     * Finds the object with given ID as primary key.
     * 
     * @param id the primary key of the record
     * @return the corresponding object or null if object is not found
     */
    public T findOne( ID id )
    {
        return entityManager.find( getEntityClass(), id );
    }

    /**
     * Gets all the records form the given object. Pay attention to memory usage!
     * 
     * @return the list of objects as result.
     */
    // public abstract List<T> findAll();

    // public abstract List<T> findAll(Sort sort);
    // public abstract List<T> findAll(Pageable pageable);
    /**
     * Gets the number of total objects of this type from database.
     * 
     * @return the total number of results
     */
    // public abstract long count();

    /**
     * Save all the objects sent as parameters.
     * 
     * @param objects the objects to save
     * @return the saved objects
     */
    public List<T> save( Collection<T> objects )
    {
        List<T> saved = new ArrayList<T>();
        for ( T object : objects )
        {
            saved.add( save( object ) );
        }
        return saved;
    }

    /**
     * Saves the given object.
     * 
     * @param obj the object to save
     * @return the saved object
     */
    public T save( T obj )
    {
        if ( isNewEntity( obj ) )
        {
            entityManager.persist( obj );
            return obj;
        }
        else
        {
            return entityManager.merge( obj );
        }
    }

    /**
     * Deletes an object with a given ID.
     * 
     * @param id the ID of the object to delete
     */
    public void delete( ID id )
    {
        T obj = findOne( id );
        if ( obj != null )
        {
            delete( obj );
        }
    }

    /**
     * Deletes a given object.
     * 
     * @param entity the object to delete
     */
    public void delete( T entity )
    {
        entityManager.remove( entity );
    }

    /**
     * Delete a collection of objects.
     * 
     * @param entities the objects to be deleted
     */
    public void delete( Collection<? extends T> entities )
    {
        for ( T obj : entities )
        {
            delete( obj );
        }
    }

    /**
     * Gets the {@link Class} of the entity that this repository is managing.
     * 
     * @return the entity {@link Class}
     */
    protected abstract Class<T> getEntityClass();

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
