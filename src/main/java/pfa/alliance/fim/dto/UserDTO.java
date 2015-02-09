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

    /**
     * Called when instance of this class is created.
     */
    public UserDTO()
    {
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param id the User ID
     * @param firstName the User first name
     * @param lastName the user last name
     * @param email the user e-mail address
     */
    public UserDTO( int id, String firstName, String lastName, String email )
    {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

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
