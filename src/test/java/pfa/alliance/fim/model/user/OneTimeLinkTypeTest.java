/**
 * 
 */
package pfa.alliance.fim.model.user;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is used for testing {@link OneTimeLinkType}.
 * 
 * @author Csaba
 */
public class OneTimeLinkTypeTest
{
    @Test
    public void testValuess()
    {
        // If this is changed, do not forget to update the Db table: enum_one_time_link_type
        Assert.assertEquals( "The number is incorrect", 3, OneTimeLinkType.values().length );
    }

    @Test
    public void test_valueOf_valid()
    {
        Assert.assertEquals( "Valid status issue", OneTimeLinkType.FORGOT_PASWORD,
                             OneTimeLinkType.valueOf( "FORGOT_PASWORD" ) );
    }

    @Test( expected = RuntimeException.class )
    public void test_valueOf_invalid()
    {
        OneTimeLinkType.valueOf( "INVALID" );
    }

}
