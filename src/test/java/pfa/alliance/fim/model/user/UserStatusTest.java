/**
 * 
 */
package pfa.alliance.fim.model.user;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is used for testing {@link UserStatus}.
 * 
 * @author Nemeti
 */
public class UserStatusTest
{
    @Test
    public void testUserStatues()
    {
        // If this is changed, do not forget to update the Db table: enum_user_status
        Assert.assertEquals( "The UserStatus number is incorrect", 3, UserStatus.values().length );
    }

    @Test
    public void test_valueOf_validStatus()
    {
        Assert.assertEquals( "ACTIVE status issue", UserStatus.ACTIVE, UserStatus.valueOf( "ACTIVE" ) );
    }

    @Test(expected=RuntimeException.class)
    public void test_valueOf_invalidStatus()
    {
        UserStatus.valueOf( "INVALID" );
    }
}
