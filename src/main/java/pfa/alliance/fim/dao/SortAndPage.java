/**
 * 
 */
package pfa.alliance.fim.dao;

/**
 * This class extends {@link Sort} in order to add pagination related data.
 * 
 * @author Nemeti Csaba
 */
public class SortAndPage
    extends Sort
{
    private static final long serialVersionUID = 8107023985826649620L;

    /** The maximum number of items to retrieve at once. */
    public final static int MAX_ITEMS_TO_RETRIEVE = 1000;

    /** The default value for maximum number of items to retrieve. */
    public final static int DEFAULT_MAX_ITEMS = 50;

    /** The index of the first element to be retrieved. Default value will be for first item to be retrieved. */
    private int startIndex;

    /** The maximum number of items to be retrieved. */
    private int maxItems = DEFAULT_MAX_ITEMS;

    /**
     * Gets the start index value.
     * 
     * @return the index of the first item to be retrieved (starting from 0)
     */
    public int getStartIndex()
    {
        return startIndex;
    }

    /**
     * Sets the start index value.
     * 
     * @param startIndex the start value position of the first result, numbered from 0
     * @throws IllegalArgumentException if the value is negative
     */
    public void setStartIndex( int startIndex )
    {

        this.startIndex = startIndex;
    }

    /**
     * Gets the maximum number of items to retrieve.
     * 
     * @return the maximum number of items (should be a value between 1 and {@value #MAX_ITEMS_TO_RETRIEVE})
     */
    public int getMaxItems()
    {
        return maxItems;
    }

    /**
     * Sets the maximum number oof items to retrieve.
     * 
     * @param maxItems the maximum number of items (should be a value between 1 and {@value #MAX_ITEMS_TO_RETRIEVE})
     * @throws IllegalArgumentException if the valuue is invalid
     */
    public void setMaxItems( int maxItems )
    {
        if ( maxItems < 1 || maxItems > MAX_ITEMS_TO_RETRIEVE )
        {
            throw new IllegalArgumentException( "Value " + maxItems + " is invalid" );
        }
        this.maxItems = maxItems;
    }

    /**
     * Configure this class for retrieving the next page (set of data). This means increasing the start index value with
     * value for max items to retrieve.
     * 
     * @see #setStartIndex(int)
     * @see #getMaxItems()
     */
    public void nextPage()
    {
        setStartIndex( getStartIndex() + getMaxItems() );
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder( super.toString() );
        sb.append( " OFFSET " ).append( startIndex );
        sb.append( " LIMIT " ).append( maxItems );
        return sb.toString();
    }
}
