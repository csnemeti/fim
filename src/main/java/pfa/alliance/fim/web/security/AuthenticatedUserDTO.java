/**
 * 
 */
package pfa.alliance.fim.web.security;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This DTO is kept on session and stores the logged in user and some of the relevant data it has.
 * 
 * @author Nemeti
 */
public class AuthenticatedUserDTO
    implements Serializable
{
    private static final long serialVersionUID = -8177414215766169468L;

    /** The user ID in database. */
    private final int id;

    /** The user first name (might be null). */
    private final String firstName;

    /** The user last name (might be null). */
    private final String lastName;

    /** The user e-mail address. */
    private final String email;

    /** The user login name. */
    private final String username;

    /** The time when user logged in. */
    private final Timestamp lastLogin;

    /**
     * Called when instance of this class is created.
     * 
     * @param id the User ID in database
     * @param firstName the user first name
     * @param lastName the user last name
     * @param email the user e-mail address
     * @param username the user login name
     * @param lastLogin the time when user last logged in
     */
    public AuthenticatedUserDTO( int id, String firstName, String lastName, String email, String username,
                                 Timestamp lastLogin )
    {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.lastLogin = lastLogin;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUsername()
    {
        return username;
    }

    public Timestamp getLastLogin()
    {
        return lastLogin;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append( email );
        hcb.append( username );
        return hcb.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof AuthenticatedUserDTO ) )
        {
            return false;
        }
        AuthenticatedUserDTO user = (AuthenticatedUserDTO) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append( email, user.email );
        eb.append( username, user.username );
        return eb.isEquals();
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "id", id );
        tsb.append( "first name", firstName );
        tsb.append( "last name", lastName );
        tsb.append( "username", username );
        tsb.append( "email", email );
        tsb.append( "last login", lastLogin );
        return tsb.toString();
    }
}
