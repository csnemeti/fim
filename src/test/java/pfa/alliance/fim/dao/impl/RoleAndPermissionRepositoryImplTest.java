/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.UserPermission;
import pfa.alliance.fim.model.user.UserRole;

/**
 * This class is used for testing {@link RoleAndPermissionRepositoryImpl}.
 * 
 * @author Csaba
 */
public class RoleAndPermissionRepositoryImplTest
    extends BaseDbUnitTest
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( RoleAndPermissionRepositoryImplTest.class );

    private static RoleAndPermissionRepositoryImpl roleAndPermissionRepositoryImpl;

    @BeforeClass
    public static void init()
    {
        roleAndPermissionRepositoryImpl = new RoleAndPermissionRepositoryImpl();
        getInjector().injectMembers( roleAndPermissionRepositoryImpl );
    }

    @Test
    public void test_findPermissionsFor_Admin()
    {
        List<UserPermission> permissions = roleAndPermissionRepositoryImpl.findPermissionsFor( UserRole.ADMIN );
        Assert.assertNotNull( "Permissions sould NOT be null", permissions );
        Assert.assertFalse( "Permissions sould NOT be empty", permissions.isEmpty() );
    }

    @AfterClass
    public static void finish()
    {
        roleAndPermissionRepositoryImpl = null;
    }

}
