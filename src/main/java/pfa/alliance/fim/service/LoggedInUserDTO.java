/**
 * 
 */
package pfa.alliance.fim.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;

/**
 * This class defines a logged in {@link User} with all permissions the user has.
 * 
 * @author Csaba
 */
public class LoggedInUserDTO
    implements Serializable
{
    private static final long serialVersionUID = -6210021392560869049L;

    /** The logged in user. */
    private final User user;

    /** The mapped permissions for each user role. */
    private final Map<UserRole, List<UserPermission>> permissions;

    /**
     * Called when instance of this class is created.
     * 
     * @param user the logged in user
     * @param permissions the mapped permissions for each role
     */
    public LoggedInUserDTO( User user, Map<UserRole, List<UserPermission>> permissions )
    {
        this.user = user;
        this.permissions = permissions;
    }

    public User getUser()
    {
        return user;
    }

    public Map<UserRole, List<UserPermission>> getPermissions()
    {
        return permissions;
    }

}
