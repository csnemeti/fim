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
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractJpaRepository_Read_Test.class );

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
    public static void closeEntityManager()
    {
        entityManager.close();
        entityManagerFactory.close();
    }
}
