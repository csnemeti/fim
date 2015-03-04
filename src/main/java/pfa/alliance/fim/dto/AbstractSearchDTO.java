/**
 * 
 */
package pfa.alliance.fim.dto;

import java.io.Serializable;

/**
 * Base class for search DTOs.
 * 
 * @author Csaba
 */
public class AbstractSearchDTO
    implements Serializable
{
    private static final long serialVersionUID = 6615786471037531990L;

    private int startIndex;

    private int itemsPerPage;

    private String orderBy;

    private boolean ascending = true;

    public int getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex( int startIndex )
    {
        this.startIndex = startIndex;
    }

    public int getItemsPerPage()
    {
        return itemsPerPage;
    }

    public void setItemsPerPage( int itemsPerPage )
    {
        this.itemsPerPage = itemsPerPage;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy( String orderBy )
    {
        this.orderBy = orderBy;
    }

    public boolean isAscending()
    {
        return ascending;
    }

    public void setAscending( boolean ascending )
    {
        this.ascending = ascending;
    }

}
