/**
 * 
 */
package pfa.alliance.fim.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import pfa.alliance.fim.model.Identifiable;

/**
 * This is a base interface for repositories.
 * 
 * @author Csaba
 *
 */
public interface JpaRepository<T extends Identifiable<ID>, ID extends Serializable> extends JpaReadOnlyRepository<T, ID>
{
    /**
     * Save all the objects sent as parameters.
     * 
     * @param objects the objects to save
     * @return the saved objects
     */
    List<T> save( Collection<T> objects );

    /**
     * Saves the given object.
     * 
     * @param obj the object to save
     * @return the saved object
     */
    T save( T obj );

    /**
     * Deletes an object with a given ID.
     * 
     * @param id the ID of the object to delete
     */
    void delete( ID id );

    /**
     * Deletes a given object.
     * 
     * @param entity the object to delete
     */
    void delete( T entity );

    /**
     * Delete a collection of objects.
     * 
     * @param entities the objects to be deleted
     */
    void delete( Collection<? extends T> entities );
}
