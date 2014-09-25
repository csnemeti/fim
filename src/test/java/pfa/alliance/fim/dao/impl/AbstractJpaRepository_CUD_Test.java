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
import org.junit.Before;
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
public class AbstractJpaRepository_CUD_Test
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractJpaRepository_CUD_Test.class );

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

    @Before
    public void dbSetup()
        throws Exception
    {
        DatabaseOperation.CLEAN_INSERT.execute( connection, dataset );
    }

    // @Test
    // public void test_deleteId_noUser()
    // {
    // userRepositoryImpl.delete( 1000 );
    // }

    @Test
    public void test_deleteId_validUser()
    {
        User user = userRepositoryImpl.findByUsername( "user1" );
        userRepositoryImpl.delete( user );
        entityManager.flush();
        Assert.assertFalse( "User should not be found", userRepositoryImpl.exists( user.getId() ) );
    }

    @AfterClass
    public static void closeEntityManager()
    {
        entityManager.close();
        entityManagerFactory.close();
    }


}
