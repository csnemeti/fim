/**
 * 
 */
package pfa.alliance.fim.model.user;

import org.junit.Test;

import pfa.alliance.fim.EqualsHashCodeAndToStringTester;
import pfa.alliance.fim.GetterAndSetterTester;

/**
 * This class is used for testing the {@link User} entity.
 * 
 * @author Nemeti
 *
 */
public class UserTest
{
    
    @Test
    public void testGettersAndSetters(){
        GetterAndSetterTester tester = new GetterAndSetterTester();
        tester.addDefaultInstance( UserStatus.class, UserStatus.ACTIVE );
        tester.testClass( User.class );
    }
    
    @Test
    public void testEquals_hashCode_toString(){
        User o1 = new User();
        o1.setEmail( "e1@mail.com" );
        o1.setLogin( "e1@mail.com" );
        o1.setStatus( UserStatus.ACTIVE );

        User o2 = new User();
        o2.setEmail( "e1@mail.com" );
        o2.setLogin( "e1@mail.com" );
        o2.setStatus( UserStatus.ACTIVE );
        // these are not part of equals or hashCode
        o2.setId( 2 );
        o2.setFirstName( "First" );
        o2.setLastName( "Last" );
        
        User o3 = new User();
        o3.setEmail( "e3@mail.com" );
        o3.setLogin( "e3@mail.com" );
        o3.setStatus( UserStatus.ACTIVE );
        
        EqualsHashCodeAndToStringTester.testInstances( o1, o2, o3 );
    }
}
