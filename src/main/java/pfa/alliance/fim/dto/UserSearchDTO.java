/**
 * 
 */
package pfa.alliance.fim.dto;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**
 * This DTO is used to fill in search criteria for users.
 * 
 * @author Csaba
 */
public class UserSearchDTO
    extends AbstractSearchDTO
{
    
    private static final long serialVersionUID = 2993912235849374701L;

    private String firstName;

    private String lastName;

    private String email;

    private String[] roles;

    private String[] statuses;

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

    @Override
    public void setOrderBy( final String orderBy )
    {
        String oderByValue = orderBy;
        if ( StringUtils.isBlank( orderBy ) )
        {
            oderByValue = "lastName";
        }
        super.setOrderBy( oderByValue );
    }

    @Override
    public String toString()
    {
        return "UserSearchDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
            + ", startIndex=" + getStartIndex() + ", itemsPerPage=" + getItemsPerPage() + ", orderBy=" + getOrderBy()
            + ", ascending=" + isAscending() + ", statuses=" + Arrays.toString( statuses ) + ", roles="
            + Arrays.toString( roles ) + "]";
    }

}
