/**
 * 
 */
package pfa.alliance.fim.dao;

import java.io.Serializable;
import java.util.List;

import pfa.alliance.fim.model.Identifiable;

/**
 * This interface defines methods for retrieving a subset from the list of entities.
 * 
 * @author Nemeti Csaba
 *
 */
public interface JpaFindAllWithPaginationSupport<T extends Identifiable<ID>, ID extends Serializable>
{

    /**
     * Gets a subset of the records form the given object. Pay attention to memory usage!
     * 
     * @param sort any ordering criteria
     * @param startIndex the start index
     * @param maxItems the maximum number of items to retrieve
     * @return the list or records
     */
    List<T> findAll( Sort sort, int startIndex, int maxItems);


    /**
     * Gets a subset of the records form the given object. Pay attention to memory usage!
     * 
     * @param page any ordering and pagination criteria
     * @return the list or records
     */
    List<T> findAll( SortAndPage page);
}
