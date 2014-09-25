/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.batoo.jpa.BJPASettings;
import org.batoo.jpa.JPASettings;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * This class is used for testing {@link UserRepositoryImpl}.
 * 
 * @author Csaba
 */
public class UserRepositoryImplTest
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserRepositoryImplTest.class );

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    private static IDatabaseConnection connection;

    private static IDataSet dataset;

    private static UserRepositoryImpl userRepositoryImpl;

    @BeforeClass
    public static void initEntityManager()
        throws Exception
    {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put( JPASettings.JDBC_URL, "jdbc:derby:memory:unit-testing-jpa;create=true" );
        properties.put( BJPASettings.DDL, "CREATE" );
        try
        {
            LOG.info( "BuildingEntityManager for unit tests: {}", properties );

            entityManagerFactory = Persistence.createEntityManagerFactory( "fimJpaUnit", properties );
            entityManager = entityManagerFactory.createEntityManager();

            Connection conn = DriverManager.getConnection( "jdbc:derby:memory:unit-testing-jpa" );
            connection = new DatabaseConnection( conn );

            FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
            flatXmlDataSetBuilder.setColumnSensing( true );
            dataset =
                flatXmlDataSetBuilder.build( Thread.currentThread().getContextClassLoader().getResourceAsStream( "dbunit/user/users.xml" ) );

            DatabaseOperation.CLEAN_INSERT.execute( connection, dataset );

            userRepositoryImpl = new UserRepositoryImpl();
            userRepositoryImpl.setEntityManager( entityManager );
        }
        catch ( Exception ex )
        {
            LOG.error( "Error while EntityManager init", ex );
            throw ex;
        }
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
        User user = userRepositoryImpl.findByEmail( "user4@email.com" );
        Assert.assertNotNull( "User should NOT be null", user );
        Assert.assertEquals( "UserStatus issue", UserStatus.NEW, user.getStatus() );
    }

    @AfterClass
    public static void closeEntityManager()
    {
        entityManager.close();
        entityManagerFactory.close();
    }

}
