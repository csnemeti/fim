/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
@Ignore
public class UserRepositoryImpl_Transaction_Test
    extends BaseDbUnitTest
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserRepositoryImpl_Transaction_Test.class );

    private static UserRepositoryImpl userRepositoryImpl;
    private EntityTransaction transaction;

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
    public void test_addNewLoginInfoAndLimitLogs_NoPreviousRecords()
    {
        User user3 = userRepositoryImpl.findBy( "user3", "pass3" );
        Assert.assertNotNull( "User should not be null", user3 );
        Assert.assertTrue( "No previous login records...", CollectionUtils.isEmpty( user3.getLogins() ) );
        
        userRepositoryImpl.addNewLoginInfoAndLimitLogs( user3 );
        Assert.assertEquals( "Previous login records error", 1, user3.getLogins().size() );
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
    public static void finish()
    {
        userRepositoryImpl = null;
    }

}
