/**
 * 
 */
package pfa.alliance.fim.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class defines sorting criteria.
 * 
 * @author Nemeti Csaba
 */
public class Sort
    implements Iterable<Map.Entry<String, Boolean>>, Serializable
{
    private static final long serialVersionUID = 6733540697122505732L;

    /**
     * This map keeps the sorting order (in the field name, should be ascending form). Instance of the class is very
     * important because beside the field name and sorting order it also keeps the order how those fields were set.
     */
    private LinkedHashMap<String, Boolean> sorting = new LinkedHashMap<String, Boolean>();

    /**
     * Adds a new sorting criteria.
     * 
     * @param filedName the name of the field that should be ordered
     * @param ascending true if ordering should be ascending
     * @return returns this object so that you can add another sorting criteria in chain
     */
    public Sort add( String filedName, boolean ascending )
    {
        sorting.put( filedName, ascending );
        return this;
    }

    /**
     * Gets a not null map with sorting order. In case the map is empty, no ordering criteria was defined.
     * 
     * @return a map having as key the field name and as value true if field should be ascending, false if descending.
     *         Null is never set as value
     */
    public LinkedHashMap<String, Boolean> getSorting()
    {
        return sorting;
    }

    /**
     * Checks if there are no sorting conditions defined.
     * 
     * @return true if there are no sorting conditions defined
     */
    public boolean isEmpty()
    {
        return sorting.isEmpty();
    }

    @Override
    public Iterator<Entry<String, Boolean>> iterator()
    {
        return sorting.entrySet().iterator();
    }

}
