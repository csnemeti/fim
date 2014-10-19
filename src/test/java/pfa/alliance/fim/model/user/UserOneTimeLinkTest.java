/**
 * 
 */
package pfa.alliance.fim.model.user;

import java.sql.Timestamp;

import org.junit.Test;

import pfa.alliance.fim.EqualsHashCodeAndToStringTester;
import pfa.alliance.fim.GetterAndSetterTester;

/**
 * This class is used for testing {@link UserOneTimeLink}.
 * 
 * @author Csaba
 */
public class UserOneTimeLinkTest
{

    @Test
    public void testGettersAndSetters()
    {
        GetterAndSetterTester tester = new GetterAndSetterTester();
        tester.addDefaultInstance( OneTimeLinkType.class, OneTimeLinkType.FORGOT_PASWORD );
        tester.addDefaultInstance( User.class, new User() );
        tester.testClass( UserOneTimeLink.class );
    }

    @Test
    public void testEquals_hashCode_toString()
    {
        Timestamp common = new Timestamp( System.currentTimeMillis() );

        UserOneTimeLink o1 = new UserOneTimeLink();
        o1.setUuid( "uuid1" );
        o1.setExpiresAt( common );
        o1.setDesignation( OneTimeLinkType.FORGOT_PASWORD );

        UserOneTimeLink o2 = new UserOneTimeLink();
        o2.setUuid( "uuid1" );
        o2.setExpiresAt( common );
        o2.setDesignation( OneTimeLinkType.FORGOT_PASWORD );
        // these are not part of equals or hashCode
        o2.setId( 2L );
        o2.setUser( new User() );

        UserOneTimeLink o3 = new UserOneTimeLink();
        o3.setUuid( "uuid3" );
        o3.setExpiresAt( common );
        o3.setDesignation( OneTimeLinkType.USER_INVITE );

        EqualsHashCodeAndToStringTester.testInstances( o1, o2, o3 );
    }

}
