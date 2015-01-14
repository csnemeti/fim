/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.OneTimeLinkType;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * This class is used for testing {@link UserRepositoryImpl}.
 * 
 * @author Csaba
 */
public class UserRepositoryImplTest
    extends BaseDbUnitTest
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserRepositoryImplTest.class );

    private static UserRepositoryImpl userRepositoryImpl;

    @BeforeClass
    public static void init()
    {
        userRepositoryImpl = new UserRepositoryImpl();
        getInjector().injectMembers( userRepositoryImpl );
    }

    @Test
    public void test_findBy_noUser()
    {
        User user = userRepositoryImpl.findBy( "username", "password" );
        Assert.assertNull( "User should not be found", user );
    }

    @Test
    public void test_findBy_validUsernameWrongPassword()
    {
        User user = userRepositoryImpl.findBy( "user2", "password" );
        Assert.assertNull( "User should not be found", user );
    }

    @Test
    public void test_findBy_validUsernameAndPassword()
    {
        User user = userRepositoryImpl.findBy( "user2", "pass2" );
        Assert.assertNotNull( "User should NOT be null", user );
    }

    @Test
    public void test_findByUsername_noUser()
    {
        User user = userRepositoryImpl.findByUsername( "username" );
        Assert.assertNull( "User should not be found", user );
    }

    @Test
    public void test_findByUsername_activeUser()
    {
        User user = userRepositoryImpl.findByUsername( "user1" );
        Assert.assertNotNull( "User should NOT be null", user );
        Assert.assertEquals( "UserStatus issue", UserStatus.ACTIVE, user.getStatus() );
    }

    @Test
    public void test_findByUsername_invalidUser()
    {
        User user = userRepositoryImpl.findByUsername( "user5" );
        Assert.assertNotNull( "User should NOT be null", user );
        Assert.assertEquals( "UserStatus issue", UserStatus.DISABLED, user.getStatus() );
    }

    @Test
    public void test_findByEmail_noUser()
    {
        User user = userRepositoryImpl.findByEmail( "my@email.com" );
        Assert.assertNull( "User should not be found", user );
    }

    @Test
    public void test_findByEmail_activeUser()
    {
        User user = userRepositoryImpl.findByEmail( "user3@email.com" );
        Assert.assertNotNull( "User should NOT be null", user );
        Assert.assertEquals( "UserStatus issue", UserStatus.ACTIVE, user.getStatus() );
    }

    @Test
    public void test_findByEmail_newUser()
    {
        User user = userRepositoryImpl.findByEmail( "user6@email.com" );
        Assert.assertNotNull( "User should NOT be null", user );
        Assert.assertEquals( "UserStatus issue", UserStatus.NEW, user.getStatus() );
    }

    @Test
    public void test_uniqueResult_noResults()
    {
        List<User> users = new ArrayList<User>();
        User user = DaoUtil.uniqueResult( users );
        Assert.assertNull( "Should be null", user );
    }

    @Test
    public void test_uniqueResult_oneResult()
    {
        List<User> users = new ArrayList<User>();
        User dummy = new User();
        users.add( dummy );

        User user = DaoUtil.uniqueResult( users );

        Assert.assertNotNull( "Should NOT be null", user );
        Assert.assertSame( "Wrong instance", dummy, user );
    }

    @Test( expected = NonUniqueResultException.class )
    public void test_uniqueResult_twoResults()
    {
        List<User> users = new ArrayList<User>();
        User dummy = new User();
        users.add( dummy );
        dummy = new User();
        users.add( dummy );

        DaoUtil.uniqueResult( users );
    }

    @Test
    public void test_getOneTimeLinkBy_invalidUuidNoDesignation()
    {
        UserOneTimeLink link = userRepositoryImpl.getOneTimeLinkBy( "123", null );
        Assert.assertNull( "This link should be NULL", link );
    }

    @Test
    public void test_getOneTimeLinkBy_invalidUuidWithDesignation()
    {
        UserOneTimeLink link = userRepositoryImpl.getOneTimeLinkBy( "123", OneTimeLinkType.FORGOT_PASWORD );
        Assert.assertNull( "This link should be NULL", link );
    }

    @Test
    public void test_getOneTimeLinkBy_validUuidNoDesignation()
    {
        UserOneTimeLink link = userRepositoryImpl.getOneTimeLinkBy( "uid3", null );
        Assert.assertNotNull( "This link should NOT be NULL", link );
        Assert.assertEquals( "Wrong UUID", "uid3", link.getUuid() );
    }

    @Test
    public void test_getOneTimeLinkBy_validUuidWrongDesignation()
    {
        UserOneTimeLink link = userRepositoryImpl.getOneTimeLinkBy( "uid3", OneTimeLinkType.USER_REGISTRATION );
        Assert.assertNull( "This link should be NULL", link );
    }

    @Test
    public void test_getOneTimeLinkBy_validUuidCorrectDesignation()
    {
        UserOneTimeLink link = userRepositoryImpl.getOneTimeLinkBy( "uid3", OneTimeLinkType.FORGOT_PASWORD );
        Assert.assertNotNull( "This link should NOT be NULL", link );
        Assert.assertEquals( "Wrong UUID", "uid3", link.getUuid() );
    }

    @Test
    public void test_getOneTimeLinkBy_validUuidNoDesignationAndExpired()
    {
        UserOneTimeLink link = userRepositoryImpl.getOneTimeLinkBy( "uid1", null );
        // expiration check is made on business level in order to return correct error message
        // so we should have the link, regardless of the fact that is expired
        Assert.assertNotNull( "This link should NOT be NULL", link );
        Assert.assertEquals( "Wrong UUID", "uid1", link.getUuid() );
        Assert.assertTrue( "This should be expired",
                           link.getExpiresAt().before( new Timestamp( System.currentTimeMillis() ) ) );
    }

    @AfterClass
    public static void finish()
    {
        userRepositoryImpl = null;
    }

}
