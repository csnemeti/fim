/**
 * 
 */
package pfa.alliance.fim.dto;

import pfa.alliance.fim.model.user.UserRole;

/**
 * This class defines a search result for user.
 * 
 * @author Csaba
 */
public class UserSearchResultDTO
    extends UserDTO
{
    private UserRole defaultRole;

    public UserRole getDefaultRole()
    {
        return defaultRole;
    }

    public void setDefaultRole( UserRole defaultRole )
    {
        this.defaultRole = defaultRole;
    }

}
