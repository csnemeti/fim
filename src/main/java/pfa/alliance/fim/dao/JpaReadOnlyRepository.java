/**
 * 
 */
package pfa.alliance.fim.dao;

import java.io.Serializable;
import java.util.List;

import pfa.alliance.fim.model.Identifiable;

/**
 * This is a base interface for repositories.
 * 
 * @author Csaba
 *
 */
public interface JpaReadOnlyRepository<T extends Identifiable<ID>, ID extends Serializable>
{
    /**
     * Check if a record with the given ID exists in database.
     * 
     * @param id the ID to check
     * @return true if the ID was found, false otherwise
     */
    boolean exists( ID id );    

    /**
     * Finds the object with given ID as primary key.
     * 
     * @param id the primary key of the record
     * @return the corresponding object or null if object is not found
     */
    T findOne( ID id );

    /**
     * Gets the number of total objects of this type from database.
     * 
     * @return the total number of results
     */
    long count();

    /**
     * Gets the list of all IDs for the records defined in the database.
     * @return the list of all IDs
     * @see #findAllIds(Sort)
     */
    List<ID> findAllIds();

    /**
     * Gets the list of all IDs for the records defined in the database in the given order.
     * @param sort the ordering criteria. If null no ordering criteria is defined
     * @return the list of all IDs
     * @see #findAllIds()
     */
    List<ID> findAllIds(Sort sort);
}
