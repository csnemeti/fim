/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
        List<UserPermission> result = new ArrayList<UserPermission>();
        if ( CollectionUtils.isNotEmpty( list ) )
        {
            for ( PermissionToRole permissionToRole : list )
            {
                result.add( permissionToRole.getUserPermission() );
            }
        }
        return result;
    }

}
