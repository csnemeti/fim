/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pfa.alliance.fim.dao.JpaRepository;
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

    /**
     * Check if a record with the given ID exists in database.
     * 
     * @param id the ID to check
     * @return true if the ID was found, false otherwise
     */
    @Override
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
    @Override
    public T findOne( ID id )
    {
        return getEntityManager().find( getEntityClass(), id );
    }

    /**
     * Save all the objects sent as parameters.
     * 
     * @param objects the objects to save
     * @return the saved objects
     */
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

    /**
     * Saves the given object.
     * 
     * @param obj the object to save
     * @return the saved object
     */
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

    /**
     * Deletes an object with a given ID.
     * 
     * @param id the ID of the object to delete
     */
    @Override
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
    @Override
    public void delete( T entity )
    {
        getEntityManager().remove( entity );
    }

    /**
     * Delete a collection of objects.
     * 
     * @param entities the objects to be deleted
     */
    @Override
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
