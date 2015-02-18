/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.RoleAndPermissionRepository;
import pfa.alliance.fim.model.user.PermissionToRole;
import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;

/**
 * This is an implementation for {@link RoleAndPermissionRepository}
 * 
 * @author Csaba
 */
@Singleton
public class RoleAndPermissionRepositoryImpl
    extends BaseRepository
    implements RoleAndPermissionRepository
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( RoleAndPermissionRepositoryImpl.class );

    @Override
    public List<UserPermission> findPermissionsFor( UserRole role )
    {
        LOG.debug( "Getting permissions for role = {}", role );
        EntityManager em = getEntityManager();
        TypedQuery<PermissionToRole> query =
            em.createQuery( "SELECT p FROM pfa.alliance.fim.model.user.PermissionToRole p WHERE p.userRole = :role",
                            PermissionToRole.class );
        query.setParameter( "role", role.name() );
        List<PermissionToRole> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        return toPermissionList( results );
    }

    /**
     * Converts the input entity list into a list of enumeration.
     * 
     * @param list the input list
     * @return the {@link List} of {@link UserPermission}
     */
    private static List<UserPermission> toPermissionList( List<PermissionToRole> list )
    {
        List<UserPermission> result = new ArrayList<>();
        if ( CollectionUtils.isNotEmpty( list ) )
        {
            for ( PermissionToRole permissionToRole : list )
            {
                result.add( permissionToRole.getUserPermission() );
            }
        }
        return result;
    }

    @Override
    public Map<UserRole, List<UserPermission>> findPermissionsFor( Collection<UserRole> roles )
    {
        LOG.debug( "Getting permissions for roles = {}", roles );
        EntityManager em = getEntityManager();
        TypedQuery<PermissionToRole> query =
            em.createQuery( "SELECT p FROM pfa.alliance.fim.model.user.PermissionToRole p WHERE p.userRole IN :roles",
                            PermissionToRole.class );
        query.setParameter( "roles", extractRolesNames( roles ) );
        List<PermissionToRole> results = query.getResultList();
        LOG.debug( "Returned results: {}", results );
        return toPermissionMap( results );
    }

    /**
     * Creates a {@link List} role names based on a {@link Collection} of {@link UserRole}s.
     * 
     * @param roles the {@link Collection} or roles
     * @return a not null {@link List} with {@link UserRole} names
     */
    private static List<String> extractRolesNames( Collection<UserRole> roles )
    {
        List<String> rolesNames = new ArrayList<>();
        for ( UserRole role : roles )
        {
            rolesNames.add( role.name() );
        }
        return rolesNames;
    }

    /**
     * Converts the input entity list into a list of enumeration.
     * 
     * @param list the input list
     * @return the {@link List} of {@link UserPermission}
     */
    private static Map<UserRole, List<UserPermission>> toPermissionMap( List<PermissionToRole> list )
    {
        Map<UserRole, List<UserPermission>> result = new HashMap<>();
        if ( CollectionUtils.isNotEmpty( list ) )
        {
            for ( PermissionToRole permissionToRole : list )
            {
                UserRole role = permissionToRole.getUserRole();
                List<UserPermission> rolePermissions = result.get( role );
                if ( rolePermissions == null )
                {
                    rolePermissions = new ArrayList<>();
                    result.put( role, rolePermissions );
                }
                rolePermissions.add( permissionToRole.getUserPermission() );
            }
        }
        return result;
    }
}
