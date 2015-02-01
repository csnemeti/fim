/**
 * 
 */
package pfa.alliance.fim.dto;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**
 * This DTO is used to fill in search criteria for users.
 * 
 * @author Csaba
 */
public class UserSearchDTO
    implements Serializable
{
    private static final long serialVersionUID = -6241852053045832667L;

    private String firstName;

    private String lastName;

    private String email;

    private String[] roles;

    private String[] statuses;

    private int startIndex;

    private int itemsPerPage;

    private String orderBy;

    private boolean ascending = true;

    public UserSearchDTO()
    {
        setOrderBy( null );
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String[] getRoles()
    {
        return roles;
    }

    public void setRoles( String[] roles )
    {
        this.roles = roles;
    }

    public String[] getStatuses()
    {
        return statuses;
    }

    public void setStatuses( String[] statuses )
    {
        this.statuses = statuses;
    }

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
        if ( StringUtils.isBlank( orderBy ) )
        {
            this.orderBy = "lastName";
        }
        else
        {
            this.orderBy = orderBy;
        }
    }

    public boolean isAscending()
    {
        return ascending;
    }

    public void setAscending( boolean ascending )
    {
        this.ascending = ascending;
    }

    @Override
    public String toString()
    {
        return "UserSearchDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
            + ", startIndex=" + startIndex + ", itemsPerPage=" + itemsPerPage + ", orderBy=" + orderBy + ", ascending="
            + ascending + ", statuses=" + Arrays.toString( statuses ) + ", roles=" + Arrays.toString( roles ) + "]";
    }

}
