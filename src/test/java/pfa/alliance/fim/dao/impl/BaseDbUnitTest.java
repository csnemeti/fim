/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.batoo.jpa.BJPASettings;
import org.batoo.jpa.JPASettings;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * This is the base class of a DB unit test
 * 
 * @author Csaba
 */
public abstract class BaseDbUnitTest
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( BaseDbUnitTest.class );

    private static EntityManager entityManager = null;

    private static PersistService persistService = null;

    private static Injector injector;

    /** Flag that tells us data was loaded into database. */
    private static boolean dataLoaded = false;

    @BeforeClass
    public static synchronized void initDbAndManagers()
        throws Exception
    {
        if ( dataLoaded )
        {
            LOG.debug( "Data already loaded, skip loading..." );
            return;
        }
        Properties properties = new Properties();
        properties.put( JPASettings.JDBC_URL, "jdbc:derby:memory:unit-testing-jpa;create=true" );
        properties.put( BJPASettings.DDL, "CREATE" );

        LOG.info( "Initialize the database: {}", properties );

        JpaPersistModule jpaPersistModule = new JpaPersistModule( "fimJpaUnit" );
        jpaPersistModule.properties( properties );

        injector = Guice.createInjector( jpaPersistModule );
        persistService = injector.getInstance( PersistService.class );
        LOG.info( "Starting persistence..." );
        persistService.start();
        entityManager = injector.getInstance( EntityManager.class );
        reloadDataFromScratch();
    }

    static EntityManager getEntityManager()
    {
        return entityManager;
    }

    static Injector getInjector()
    {
        return injector;
    }

    static void reloadDataFromScratch()
        throws Exception
    {

        Connection conn = DriverManager.getConnection( "jdbc:derby:memory:unit-testing-jpa" );
        IDatabaseConnection connection = new DatabaseConnection( conn );

        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing( true );
        IDataSet dataset =
            flatXmlDataSetBuilder.build( Thread.currentThread().getContextClassLoader().getResourceAsStream( "dbunit/user/users.xml" ) );

        LOG.info( "Loading / Reloading database with data..." );
        DatabaseOperation.CLEAN_INSERT.execute( connection, dataset );
        dataLoaded = true;
    }

    // @AfterClass
    // public static void closeEntityManager()
    // {
    // entityManager.close();
    // try
    // {
    // DriverManager.getConnection( "jdbc:derby:;shutdown=true" );
    // }
    // catch ( SQLException e )
    // {
    // }
    // }

}
