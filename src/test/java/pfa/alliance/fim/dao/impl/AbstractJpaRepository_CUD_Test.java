/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * This class is used for testing reader methods from {@link AbstractJpaRepository}.
 * 
 * @author Csaba
 */
public class AbstractJpaRepository_CUD_Test
    extends BaseDbUnitTest
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractJpaRepository_CUD_Test.class );

    private EntityTransaction transaction;

    private static UserRepositoryImpl userRepositoryImpl;

    @BeforeClass
    public static void init()
    {
        userRepositoryImpl = new UserRepositoryImpl();
        getInjector().injectMembers( userRepositoryImpl );
    }

    @Before
    public void dbSetup()
        throws Exception
    {
        transaction = getEntityManager().getTransaction();
        transaction.begin();
    }

    @Test
    public void test_deleteId_noUser()
    {
        userRepositoryImpl.delete( 100000 );
    }

    @Test
    public void test_deleteId_validUser()
    {
        User user = userRepositoryImpl.findByUsername( "user2" );
        userRepositoryImpl.delete( user.getId() );
        // TODO test this somehow
    }

    @Test
    public void test_delete_validUser()
    {
        User user = userRepositoryImpl.findByUsername( "user1" );
        LOG.debug( "User: {}, version = {}, created = {}", user, user.getVersion(), user.getCreatedAt() );
        userRepositoryImpl.delete( user );
        getEntityManager().flush();
        // TODO test this somehow
        // Assert.assertFalse( "User should not be found", userRepositoryImpl.exists( user.getId() ) );
    }

    @Test
    public void test_save_newObject()
    {
        User user = new User();
        user.setEmail( "csaba@csaba.ro" );
        user.setLogin( "csaba@csaba.ro" );
        user.setPassword( "abc" );
        user.setStatus( UserStatus.NEW );

        userRepositoryImpl.save( user );
    }

    @Test
    public void test_save_existingObject()
    {
        User user = userRepositoryImpl.findByUsername( "user3" );
        user.setEmail( "csaba@nemeti.ro" );
        user.setLogin( "csaba@nemeti.ro" );
        user.setPassword( "abc" );
        user.setStatus( UserStatus.ACTIVE );

        userRepositoryImpl.save( user );
    }

    @Test
    public void test_delete_emptyCollection()
    {
        userRepositoryImpl.delete( new ArrayList<User>() );
    }

    @Test
    public void test_delete_withCollection()
    {
        List<User> toDelete = new ArrayList<User>();
        toDelete.add( userRepositoryImpl.findByUsername( "user5" ) );
        userRepositoryImpl.delete( toDelete );
    }

    @Test
    public void test_save_emptyCollection()
    {
        userRepositoryImpl.save( new ArrayList<User>() );
    }

    @Test
    public void test_save_withCollection()
    {
        List<User> toSave = new ArrayList<User>();
        User user4 = userRepositoryImpl.findByUsername( "user4" );
        user4.setStatus( UserStatus.ACTIVE );
        toSave.add( user4 );
        User user26 = new User();
        user26.setEmail( "user26@company.com" );
        user26.setLogin( "user26" );
        userRepositoryImpl.save( toSave );
    }

    @After
    public void finishTest()
    {
        if ( transaction != null )
        {
            transaction.rollback();
        }
    }

    @AfterClass
    public static void destroy()
        throws Exception
    {
        userRepositoryImpl = null;
        reloadDataFromScratch();
    }
}
