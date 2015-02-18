/**
 * 
 */
package pfa.alliance.fim.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;

/**
 * This interface defines methods used in {@link UserRole} and {@link UserPermission} management. This interface defines
 * methods for retrieving permission list for a role, assigning or removing a specific permission for a user.
 * 
 * @author Csaba
 */
public interface RoleAndPermissionRepository
{
    /**
     * Gets the list of all {@link UserPermission}s mapped to a given role.
     * 
     * @param role the given {@link UserRole}
     * @return the list of defined {@link UserPermission} for the given {@link UserRole}
     */
    List<UserPermission> findPermissionsFor( UserRole role );

    /**
     * Gets the list of all {@link UserPermission}s mapped to a given collection of roles.
     * 
     * @param roles the {@link Collection} of {@link UserRole}
     * @return the list of defined {@link UserPermission} for for each {@link UserRole}
     */
    Map<UserRole, List<UserPermission>> findPermissionsFor( Collection<UserRole> roles );
}
