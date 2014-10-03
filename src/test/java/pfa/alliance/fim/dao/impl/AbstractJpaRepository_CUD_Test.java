/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserStatus;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * This class is used for testing reader methods from {@link AbstractJpaRepository}.
 * 
 * @author Csaba
 */
@Ignore
public class AbstractJpaRepository_CUD_Test
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractJpaRepository_CUD_Test.class );

    private static EntityManagerFactory entityManagerFactory = null;

    private static EntityManager entityManager = null;

    private static IDatabaseConnection connection = null;

    private static IDataSet dataset = null;

    private static UserRepositoryImpl userRepositoryImpl = null;

    private static PersistService persistService = null;

    private static UnitOfWork unitOfWork;

    // @BeforeClass
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

    @BeforeClass
    public static void initDbAndManagers()
        throws Exception
    {
        Properties properties = new Properties();
        properties.put( JPASettings.JDBC_URL, "jdbc:derby:memory:unit-testing-jpa;create=true" );
        properties.put( BJPASettings.DDL, "CREATE" );

        JpaPersistModule jpaPersistModule = new JpaPersistModule( "fimJpaUnit" );
        jpaPersistModule.properties( properties );

        final Injector injector = Guice.createInjector( jpaPersistModule );
        persistService = injector.getInstance( PersistService.class );
        persistService.start();

        Connection conn = DriverManager.getConnection( "jdbc:derby:memory:unit-testing-jpa" );
        connection = new DatabaseConnection( conn );

        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing( true );
        dataset =
            flatXmlDataSetBuilder.build( Thread.currentThread().getContextClassLoader().getResourceAsStream( "dbunit/user/users.xml" ) );

        // DatabaseOperation.CLEAN_INSERT.execute( connection, dataset );

        userRepositoryImpl = new UserRepositoryImpl();
        injector.injectMembers( userRepositoryImpl );
        unitOfWork = injector.getInstance( UnitOfWork.class );
    }

    @Before
    public void dbSetup()
        throws Exception
    {
        DatabaseOperation.CLEAN_INSERT.execute( connection, dataset );
        // unitOfWork.begin();
    }

    // @Test
    // public void test_deleteId_noUser()
    // {
    // userRepositoryImpl.delete( 1000 );
    // }

    @Test
    @Ignore
    public void test_deleteId_validUser()
    {
        User user = userRepositoryImpl.findByUsername( "user1" );
        userRepositoryImpl.delete( user );
        // entityManager.flush();
        Assert.assertFalse( "User should not be found", userRepositoryImpl.exists( user.getId() ) );
    }

    @Test
    public void test_save_newObject(){
        User user = new User();
        user.setEmail( "csaba@csaba.ro" );
        user.setLogin( "csaba@csaba.ro" );
        user.setPassword( "abc" );
        user.setStatus( UserStatus.NEW );

        userRepositoryImpl.save( user );
    }

    @After
    public void finishTest()
    {
        // unitOfWork.end();
    }

    @AfterClass
    public static void closeEntityManager()
    {
        if ( persistService != null )
        {
            persistService.stop();
            persistService = null;
            entityManager = null;
        }
        if ( entityManager != null )
        {
            entityManager.close();
            entityManager = null;
        }
        if ( entityManagerFactory != null )
        {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }


}
