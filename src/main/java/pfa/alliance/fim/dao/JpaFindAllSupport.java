/**
 * 
 */
package pfa.alliance.fim.dao;

import java.io.Serializable;
import java.util.List;

import pfa.alliance.fim.model.Identifiable;

/**
 * This interface defines the method(s) necessary for retrieving all the entities from the database.
 * 
 * @author Nemeti Csaba
 */
public interface JpaFindAllSupport<T extends Identifiable<ID>, ID extends Serializable>
{

    /**
     * Gets all the records form the given object. Pay attention to memory usage!
     * 
     * @return the list of objects as result.
     */
    List<T> findAll();

    /**
     * Gets all the records form the given object. Pay attention to memory usage!
     * 
     * @param sort any ordering criteria
     * @return the list or records
     */
    List<T> findAll( Sort sort );

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
