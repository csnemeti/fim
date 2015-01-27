/**
 * 
 */
package pfa.alliance.fim.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This DTO is used to fill in search criteria for users.
 * 
 * @author Csaba
 */
public class UserSearchDTO
    implements Serializable
{
    private String firstName;

    private String lastName;

    private String email;

    private String[] roles;

    private int startIndex;

    private int itemsPerPage;

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

    @Override
    public String toString()
    {
        return "UserSearchDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
            + ", startIndex=" + startIndex + ", itemsPerPage=" + itemsPerPage + ", roles="
            + Arrays.toString( roles ) + "]";
    }

}
