/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import pfa.alliance.fim.dao.impl.UserRepositoryImpl.UserLoginComparator;
import pfa.alliance.fim.model.user.UserLogin;

/**
 * This class is used for testing {@link UserLoginComparator}.
 * 
 * @author Nemeti
 */
public class UserLoginComparatorTest
{
    @Test
    public void testComparator()
    {
        final long now = System.currentTimeMillis();
        List<UserLogin> logins = new ArrayList<UserLogin>();
        UserLogin ul1 = new UserLogin();
        ul1.setCreatedAt( new Timestamp( now ) );
        logins.add( ul1 );
        
        UserLogin ul2 = new UserLogin();
        ul2.setCreatedAt( new Timestamp( now - 1000000L ) );
        logins.add( ul2 );

        UserLogin ul3 = new UserLogin();
        ul3.setCreatedAt( new Timestamp( now - 1L ) );
        logins.add( ul3 );
        
        Collections.sort( logins, new UserLoginComparator() );
        
        Assert.assertSame( "Instance compparation 1. failed", ul2, logins.get( 0 ) );
        Assert.assertSame( "Instance compparation 2. failed", ul3, logins.get( 1 ) );
        Assert.assertSame( "Instance compparation 3. failed", ul1, logins.get( 2 ) );
    }
}
