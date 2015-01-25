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

    @Override
    public String toString()
    {
        return "UserSearchDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", roles="
            + Arrays.toString( roles ) + "]";
    }

}
