/**
 * 
 */
package pfa.alliance.fim.model.user;

import java.sql.Timestamp;

import org.junit.Test;

import pfa.alliance.fim.EqualsHashCodeAndToStringTester;

/**
 * This class is used for testing {@link UserLogin}
 * 
 * @author Nemeti
 *
 */
public class UserLoginTest
{
    
    @Test
    public void testEquals_hashCode_toString(){
        User u1 = new User();
        u1.setId( 1 );
        
        User u2 = new User();
        u2.setId( 2 );
        
        Timestamp timestamp1 = new Timestamp( System.currentTimeMillis() - 10000L);
        Timestamp timestamp2 = new Timestamp( System.currentTimeMillis() );
        
        UserLogin o1 = new UserLogin();
        o1.setCreatedAt( timestamp1 );
        o1.setUser( u1 );

        UserLogin o2 = new UserLogin();
        o2.setCreatedAt( timestamp1 );
        o2.setUser( u1 );
        // these are not part of equals or hashCode
        o2.setId( 2L );
        
        UserLogin o3 = new UserLogin();
        o3.setCreatedAt( timestamp2 );
        o3.setUser( u2 );
        
        EqualsHashCodeAndToStringTester.testInstances( o1, o2, o3 );
    }

}
