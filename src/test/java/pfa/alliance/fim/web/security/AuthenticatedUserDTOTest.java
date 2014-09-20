/**
 * 
 */
package pfa.alliance.fim.web.security;

import java.sql.Timestamp;

import org.junit.Test;

import pfa.alliance.fim.EqualsHashCodeAndToStringTester;

/**
 * This class is used for testing {@link AuthenticatedUserDTO}
 * 
 * @author Nemeti
 */
public class AuthenticatedUserDTOTest
{
    @Test
    public void testEquals_hashCode_toString()
    {
        AuthenticatedUserDTO o1 =
            new AuthenticatedUserDTO( 1, "F1", "L1", "user@email.com", "username",
                                      new Timestamp( System.currentTimeMillis() ) );
        AuthenticatedUserDTO o2 =
            new AuthenticatedUserDTO( 2, "F2", "L2", "user@email.com", "username",
                                      new Timestamp( System.currentTimeMillis() ) );
        AuthenticatedUserDTO o3 =
            new AuthenticatedUserDTO( 3, "F3", "L3", "user@email.com", "login",
                                      new Timestamp( System.currentTimeMillis() ) );
        EqualsHashCodeAndToStringTester.testInstances( o1, o2, o3 );
    }
}
