/**
 * 
 */
package pfa.alliance.fim.dto;

import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * This class defines a search result for user.
 * 
 * @author Csaba
 */
public class UserSearchResultDTO
    extends UserDTO
{
    private UserRole defaultRole;

    private UserStatus userStatus;

    /**
     * Called when instance of this class is created.
     */
    public UserSearchResultDTO()
    {
        super();
    }
    /**
     * Called when instance of this class is created.
     * 
     * @param id the User ID
     * @param firstName the User first name
     * @param lastName the user last name
     * @param email the user e-mail address
     * @param defaultRole the suer default role
     */
    public UserSearchResultDTO( Integer id, String firstName, String lastName, String email, UserStatus userStatus,
                                UserRole defaultRole )
    {
        super( id, firstName, lastName, email );
        this.userStatus = userStatus;
        this.defaultRole = defaultRole;
    }

    public UserRole getDefaultRole()
    {
        return defaultRole;
    }

    public void setDefaultRole( UserRole defaultRole )
    {
        this.defaultRole = defaultRole;
    }

    public UserStatus getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus( UserStatus userStatus )
    {
        this.userStatus = userStatus;
    }

}
