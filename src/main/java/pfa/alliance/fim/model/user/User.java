/**
 * 
 */
package pfa.alliance.fim.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

/**
 * This class defines a user.
 * 
 * @author Nemeti
 */
@Entity(name="fim_user")
public class User
    extends GenericModel implements Identifiable<Integer>
{
    private static final long serialVersionUID = 4717969508781933217L;

    /** The user ID. */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    /** The first name of the user. */
    @Column( name = "first_name", length = 100, nullable = true )
    private String firstName;

    /** The last name of the user. */
    @Column( name = "last_name", length = 100, nullable = true )
    private String lastName;

    /** The user e-mail address. */
    @Column( name = "email", length = 200, nullable = false, unique = true )
    private String email;

    /** The user login name. */
    @Column( name = "login_name", length = 200, nullable = false, unique = true )
    private String login;

    /** The password of the user. */
    @Column( name = "user_password", length = 250, nullable = false )
    private String password;

    /** The user status. */
    @Enumerated( EnumType.STRING )
    @Column( name = "status", length = 20, nullable = false )
    private UserStatus status;

    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL )
    @OrderBy( "id DESC" )
    private Set<UserLogin> logins;

    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL )
    private Set<UserOneTimeLink> oneTimeLinks;

    @Override
    public Integer getId()
    {
        return id;
    }

    @Override
    public void setId( Integer id )
    {
        this.id = id;
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

    public String getLogin()
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public UserStatus getStatus()
    {
        return status;
    }

    public void setStatus( UserStatus status )
    {
        this.status = status;
    }

    public Set<UserLogin> getLogins()
    {
        return logins;
    }

    public void setLogins( Set<UserLogin> logins )
    {
        this.logins = logins;
    }

    public Set<UserOneTimeLink> getOneTimeLinks()
    {
        return oneTimeLinks;
    }

    public void setOneTimeLinks( Set<UserOneTimeLink> oneTimeLinks )
    {
        this.oneTimeLinks = oneTimeLinks;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append( email );
        hcb.append( login );
        hcb.append( status );
        return hcb.toHashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof User ) )
        {
            return false;
        }
        User user = (User) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append( email, user.email );
        eb.append( login, user.login );
        eb.append( status, user.status );
        return eb.isEquals();
    }

    @Override
    public String toString()
    {
        ToStringBuilder tsb = new ToStringBuilder( this );
        tsb.append( "username", login ).append( "email", email ).append( "status", status );
        tsb.append( "first name", firstName ).append( "last name", lastName );
        return tsb.toString();
    }
}
