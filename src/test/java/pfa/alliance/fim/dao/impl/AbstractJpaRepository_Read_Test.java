/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;

/**
 * This class is used for testing reader methods from {@link AbstractJpaRepository}.
 * 
 * @author Csaba
 */
public class AbstractJpaRepository_Read_Test
    extends BaseDbUnitTest
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractJpaRepository_Read_Test.class );

    private static UserRepositoryImpl userRepositoryImpl;

    @BeforeClass
    public static void init()
    {
        userRepositoryImpl = new UserRepositoryImpl();
        getInjector().injectMembers( userRepositoryImpl );
    }

    @Test
    public void test_exists_noUser()
    {
        boolean exists = userRepositoryImpl.exists( 100 );
        Assert.assertFalse( "User should not be found", exists );
    }

    @Test
    public void test_exists_validUser()
    {
        boolean exists = userRepositoryImpl.exists( 1 );
        Assert.assertTrue( "User should be found", exists );
    }

    @Test
    public void test_findOne_noUser()
    {
        User user = userRepositoryImpl.findOne( 100 );
        Assert.assertNull( "User should not be found", user );
    }

    @Test
    public void test_findOne_validUser()
    {
        User user = userRepositoryImpl.findOne( 2 );
        Assert.assertNotNull( "User should be found", user );
    }

    @AfterClass
    public static void destroy()
    {
        userRepositoryImpl = null;
    }
}
