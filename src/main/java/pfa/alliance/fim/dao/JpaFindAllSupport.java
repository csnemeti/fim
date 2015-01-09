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
}
