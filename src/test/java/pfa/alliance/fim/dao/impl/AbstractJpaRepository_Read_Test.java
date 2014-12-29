/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.Sort;
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
    
    private static final int TOTAL_USERS_IN_DB = 6;

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

    @Test
    public void test_getIdColumnName()
    {
        String columnName = userRepositoryImpl.getIdColumnName();
        Assert.assertNotNull( "ID column should not be null", columnName );
        Assert.assertEquals( "ID column name issues", "id", columnName );
    }

    @Test
    public void test_count()
    {
        long users = userRepositoryImpl.count();
        Assert.assertEquals( "Wrong number of users found", TOTAL_USERS_IN_DB, (int)users );
    }

    @Test
    public void test_findAllIds()
    {
        List<Integer> userIds = userRepositoryImpl.findAllIds();
        Assert.assertNotNull( "Ids list should not be null", userIds );
        Assert.assertEquals( "Wrong number of users found", TOTAL_USERS_IN_DB, userIds.size() );
        Set<Integer> distinnctIds = new HashSet<Integer>( userIds );
        Assert.assertEquals( "Wrong number of users found (2)", TOTAL_USERS_IN_DB, distinnctIds.size() );
    }

    @Test
    public void test_findAllIds_emptySort()
    {
        Sort sort = new Sort();
        List<Integer> userIds = userRepositoryImpl.findAllIds( sort );
        Assert.assertNotNull( "Ids list should not be null", userIds );
        Assert.assertEquals( "Wrong number of users found", TOTAL_USERS_IN_DB, userIds.size() );
        Set<Integer> distinnctIds = new HashSet<Integer>( userIds );
        Assert.assertEquals( "Wrong number of users found (2)", TOTAL_USERS_IN_DB, distinnctIds.size() );
    }

    @Test
    public void test_findAllIds_orderByIdAsc()
    {
        Sort sort = new Sort();
        sort.add( "id", true );
        List<Integer> userIds = userRepositoryImpl.findAllIds( sort );
        Assert.assertNotNull( "Ids list should not be null", userIds );
        Assert.assertEquals( "Wrong number of users found", TOTAL_USERS_IN_DB, userIds.size() );
        Set<Integer> distinnctIds = new HashSet<Integer>( userIds );
        Assert.assertEquals( "Wrong number of users found (2)", TOTAL_USERS_IN_DB, distinnctIds.size() );

        // IDs should be in ascending order
        Integer previous = userIds.get( 0 );
        for ( int i = 1; i < userIds.size(); i++ )
        {
            Integer curent = userIds.get( i );
            if ( curent <= previous )
            {
                Assert.fail( "Ids are not orderred ascending: " + userIds );
            }
        }
    }

    @Test
    public void test_findAllIds_orderByCreationTimeDesc()
    {
        Sort sort = new Sort();
        sort.add( "createdAt", false );
        List<Integer> userIds = userRepositoryImpl.findAllIds( sort );
        Assert.assertNotNull( "Ids list should not be null", userIds );
        Assert.assertEquals( "Wrong number of users found", TOTAL_USERS_IN_DB, userIds.size() );
        Set<Integer> distinnctIds = new HashSet<Integer>( userIds );
        Assert.assertEquals( "Wrong number of users found (2)", TOTAL_USERS_IN_DB, distinnctIds.size() );

        // IDs should be in descending order
        Integer previous = userIds.get( 0 );
        for ( int i = 1; i < userIds.size(); i++ )
        {
            Integer curent = userIds.get( i );
            if ( curent >= previous )
            {
                Assert.fail( "Ids are not orderred descending: " + userIds );
            }
        }
    }

    @AfterClass
    public static void destroy()
    {
        userRepositoryImpl = null;
    }
}
