/**
 * 
 */
package pfa.alliance.fim.web.util;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserLogin;
import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.service.LoggedInUserDTO;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.Permission;

/**
 * Utility class for login operation.
 * 
 * @author Csaba
 */
public final class LoginUtil
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( LoginUtil.class );

    /**
     * Gets the last login information from a given set.
     * 
     * @param logins the list of last logins
     * @return the last login or the current time if list is empty
     */
    public static Timestamp getUserLastLogin( final Set<UserLogin> logins )
    {
        Timestamp lastLogin = null;
        for ( UserLogin login : logins )
        {
            if ( lastLogin == null )
            {
                lastLogin = login.getCreatedAt();
            }
            else
            {
                if ( lastLogin.before( login.getCreatedAt() ) )
                {
                    lastLogin = login.getCreatedAt();
                }
            }
        }
        // for small memory footprint we clear the logins
        logins.clear();
        // theoretical null guard
        if ( lastLogin == null )
        {
            lastLogin = new Timestamp( System.currentTimeMillis() );
        }
        return lastLogin;
    }

    /**
     * Convert the existing user permission into expected format.
     * 
     * @param userDTO the {@link LoggedInUserDTO} containing the {@link User} with it's own permission system
     * @return the expected format for {@link AuthenticatedUserDTO}. Key is a Project ID, value is list or
     *         {@link Permission}s from that Project. null key keeps the default Permissions.
     */
    public static Map<Integer, Set<Permission>> convertPermissions( LoggedInUserDTO userDTO )
    {
        // we convert the map with permissions
        Map<UserRole, Set<Permission>> rolePermissionsMap = new HashMap<>();
        Map<UserRole, List<UserPermission>> userDtoPermissions = userDTO.getPermissions();
        for ( Map.Entry<UserRole, List<UserPermission>> permissionEntry : userDtoPermissions.entrySet() )
        {
            Set<Permission> permissionsForRole = new HashSet<>();
            rolePermissionsMap.put( permissionEntry.getKey(), permissionsForRole );
            for ( UserPermission userPermission : permissionEntry.getValue() )
            {
                permissionsForRole.add( Permission.valueOf( userPermission.name() ) );
            }
        }
        // for small memory footprint we clear the OLD permissions
        userDtoPermissions.clear();

        User user = userDTO.getUser();
        Map<Integer, Set<Permission>> permissionsMap = new HashMap<>();
        // we add the default mapping
        permissionsMap.put( null, rolePermissionsMap.get( user.getDefaultRole() ) );
        // we add the mapping based on project ID, null means default set
        Set<UserProjectRelation> userProjectRelation = user.getUserProjectRelation();
        if ( CollectionUtils.isNotEmpty( userProjectRelation ) )
        {
            for ( UserProjectRelation relation : userProjectRelation )
            {
                UserRole role = relation.getUserRole().getCorrespondingUserRole();
                permissionsMap.put( relation.getProject().getId(), rolePermissionsMap.get( role ) );
            }
            // for small memory footprint we clear the userProjectRelation
            userProjectRelation.clear();
        }

        LOG.debug( "Permissions for user ID = {} : {}", user.getId(), permissionsMap );
        return permissionsMap;
    }

}
