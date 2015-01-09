/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.JpaFindAllSupport;
import pfa.alliance.fim.dao.JpaFindAllWithPaginationSupport;
import pfa.alliance.fim.dao.Sort;
import pfa.alliance.fim.dao.SortAndPage;
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

    private static UserRepositoryImplWithFind userRepositoryImpl;

    private static UserRepositoryImplNoFind userRepositoryImplSimple;

    @BeforeClass
    public static void init()
    {
        userRepositoryImpl = new UserRepositoryImplWithFind();
        getInjector().injectMembers( userRepositoryImpl );

        userRepositoryImplSimple = new UserRepositoryImplNoFind();
        getInjector().injectMembers( userRepositoryImplSimple );
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

    @Test
    public void test_findAll()
    {
        List<User> users = userRepositoryImpl.findAll();
        Assert.assertNotNull( "Users list should not be null", users );
        Assert.assertEquals( "Wrong number of users found", TOTAL_USERS_IN_DB, users.size() );
        Set<Integer> distinnctIds = new HashSet<Integer>();
        for ( User user : users )
        {
            distinnctIds.add( user.getId() );
        }
        Assert.assertEquals( "Wrong number of users found (2)", TOTAL_USERS_IN_DB, distinnctIds.size() );

    }

    @Test
    public void test_findAll_orderByIdAsc()
    {
        Sort sort = new Sort();
        sort.add( "id", true );
        List<User> users = userRepositoryImpl.findAll( sort );
        Assert.assertNotNull( "User list should not be null", users );
        Assert.assertEquals( "Wrong number of users found", TOTAL_USERS_IN_DB, users.size() );
        LinkedHashSet<Integer> distinnctIds = new LinkedHashSet<Integer>();
        for ( User user : users )
        {
            distinnctIds.add( user.getId() );
        }
        Assert.assertEquals( "Wrong number of users found (2)", TOTAL_USERS_IN_DB, distinnctIds.size() );

        // IDs should be in ascending order
        Integer previous = users.get( 0 ).getId();
        for ( int i = 1; i < users.size(); i++ )
        {
            Integer curent = users.get( i ).getId();
            if ( curent <= previous )
            {
                Assert.fail( "Ids are not orderred ascending: " + distinnctIds );
            }
        }
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_findAll_withNullPagination()
    {
        userRepositoryImpl.findAll( (SortAndPage) null );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_findAll_withNullPagination2()
    {
        userRepositoryImpl.findAll( (Sort) null, 1, 3 );
    }

    @Test
    public void test_findAll_orderByCreationTimeDesc()
    {
        SortAndPage sort = new SortAndPage();
        sort.add( "createdAt", false );
        sort.setStartIndex( 3 );
        sort.setMaxItems( 2 );
        List<User> users = userRepositoryImpl.findAll( sort );
        Assert.assertNotNull( "User list should not be null", users );
        Assert.assertEquals( "Wrong number of users found", 2, users.size() );

        Integer id1 = users.get( 0 ).getId();
        Integer id2 = users.get( 1 ).getId();
        Assert.assertNotEquals( "Wrong number of users found (2)", id1, id2 );
        Assert.assertTrue( "Order is wrong: " + id1 + " > " + id2, id1 > id2 );
    }

    @Test( expected = IllegalStateException.class )
    public void test_findAll_noInterface1()
    {
        SortAndPage sort = new SortAndPage();
        userRepositoryImplSimple.findAll( sort );
    }

    @Test( expected = IllegalStateException.class )
    public void test_findAll_noInterface2()
    {
        userRepositoryImplSimple.findAll();
    }

    @AfterClass
    public static void destroy()
    {
        userRepositoryImpl = null;
    }

    /**
     * This is an extension that implements all findAll(...) methods. We allow ourself to do this since we're in unit
     * test and operate with small amount of data. Both interface should not be implemented in real world unless small
     * amount of data is processed (so that pagination is not mandatory).
     */
    private static class UserRepositoryImplNoFind
        extends AbstractJpaRepository<User, Integer>
    {

        @Override
        protected Class<User> getEntityClass()
        {
            return User.class;
        }

        @Override
        protected Class<Integer> getIdClass()
        {
            return Integer.class;
        }
    }

    /**
     * This is an extension that implements all findAll(...) methods. We allow ourself to do this since we're in unit
     * test and operate with small amount of data. Both interface should not be implemented in real world unless small
     * amount of data is processed (so that pagination is not mandatory).
     */
    private static class UserRepositoryImplWithFind
        extends UserRepositoryImplNoFind
        implements JpaFindAllSupport, JpaFindAllWithPaginationSupport
    {

    }
}
