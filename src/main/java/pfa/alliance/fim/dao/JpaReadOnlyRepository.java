/**
 * 
 */
package pfa.alliance.fim.dao;

import java.io.Serializable;

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
    // long count();

}
