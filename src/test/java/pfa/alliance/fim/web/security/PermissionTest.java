/**
 * 
 */
package pfa.alliance.fim.web.security;

import org.junit.Assert;
import org.junit.Test;

import pfa.alliance.fim.model.user.UserPermission;

/**
 * This class is used for testing {@link Permission}.
 * 
 * @author Csaba
 */
public class PermissionTest
{
    @Test
    public void testPermissionsCount()
    {
        Assert.assertEquals( "Permissions size should match", Permission.values().length, UserPermission.values().length );
    }

    @Test
    public void testMatchingName()
    {
        // All values from Permission should have a corresponding name in UserPermission
        for ( Permission permission : Permission.values() )
        {
            UserPermission userPermission = UserPermission.valueOf( permission.name() );
            Assert.assertNotNull( "UserPermission should NOT be null for: " + permission.name(), userPermission );
        }
    }
}
