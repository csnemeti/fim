/**
 * 
 */
package pfa.alliance.fim.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class describes a User.
 * 
 * @author Csaba
 */
public class UserDTO
    implements Serializable
{
    private static final long serialVersionUID = -7741231246839007274L;

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstname )
    {
        this.firstName = firstname;
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

    public String getName()
    {
        return StringUtils.join( new String[] { firstName, lastName }, " " );
    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf( id ).hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof UserDTO ) )
        {
            return false;
        }
        UserDTO userDTO = (UserDTO) obj;
        return id == userDTO.id;
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "id", id );
        tsb.append( "first name", firstName );
        tsb.append( "last name", lastName );
        tsb.append( "e-mail", email );
        return tsb.toString();
    }
}
