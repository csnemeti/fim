/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
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

    static Connection createConnection()
        throws SQLException
    {
        return DriverManager.getConnection( "jdbc:derby:memory:unit-testing-jpa" );
    }

    static void reloadDataFromScratch()
        throws Exception
    {
        Connection conn = createConnection();
        patchDerbyAutoIncrements( conn );
        reloadDataFromScratch( conn );
    }

    private static void patchDerbyAutoIncrements( final Connection conn )
        throws SQLException
    {
        DatabaseMetaData metaData = conn.getMetaData();
        List<String> tableNames = getTableNamedFromDB( metaData );
        for ( String table : tableNames )
        {
            checkAndPatchPkForTable( conn, metaData, table );
        }
    }

    /**
     * Gets the DB table names.
     * 
     * @param metaData the {@link DatabaseMetaData} for where the table names can be found
     * @return the list of tables
     * @throws SQLException in case of error
     */
    private static List<String> getTableNamedFromDB( final DatabaseMetaData metaData )
        throws SQLException
    {
        List<String> tables = new ArrayList<String>();
        try (ResultSet rs = metaData.getTables( null, null, "%", new String[] { "TABLE" } ))
        {
            while ( rs.next() )
            {
                tables.add( rs.getString( "TABLE_NAME" ) );
            }
        }
        return tables;
    }

    /**
     * Patch primary keys...
     * 
     * @param conn
     * @param metaData
     * @param tableNames
     * @throws SQLException
     */
    private static void checkAndPatchPkForTable( Connection conn, DatabaseMetaData metaData, final String tableName )
        throws SQLException
    {
        List<String> pks = new ArrayList<String>();
        try (ResultSet rs = metaData.getPrimaryKeys( null, null, tableName ))
        {
            while ( rs.next() )
            {
                pks.add( rs.getString( "COLUMN_NAME" ) );
            }
        }
        if ( pks.size() == 1 )
        {
            String type = null;
            String columnName = pks.get( 0 );
            try (ResultSet rs = metaData.getColumns( null, null, tableName, columnName ))
            {
                if ( rs.next() )
                {
                    if ( isIntegerAndAutoincrementType( rs ) )
                    {
                        type = rs.getString( "TYPE_NAME" );
                        String columnDef = rs.getString( "COLUMN_DEF" );
                        if ( columnDef.toLowerCase().contains( " start 1 " ) )
                        {
                            // this needs fix
                            patchPk( conn, tableName, columnName, type );
                        }
                    }
                }
            }
        }
    }

    private static void patchPk( Connection conn, final String tableName, final String columnName,
                                 final String columnType ) throws SQLException
    {
        String sql =
            "ALTER TABLE " + tableName + " ALTER COLUMN " + columnName //+ " SET DEFAULT "// + columnType
                + " RESTART WITH 1";
        try (Statement stmt = conn.createStatement())
        {
            stmt.execute( sql );
        }catch (SQLException e) {
            LOG.error( "Cound not alter column {}.{}", tableName, columnName, e );
            throw e;
        };
    }

    private static boolean isIntegerAndAutoincrementType( ResultSet rs )
        throws SQLException
    {
        final int typeCode = rs.getInt( "DATA_TYPE" );
        boolean autoincrement = "YES".equalsIgnoreCase( rs.getString( "IS_AUTOINCREMENT" ) );
        return autoincrement
            && ( Types.TINYINT == typeCode || Types.SMALLINT == typeCode || Types.INTEGER == typeCode || Types.BIGINT == typeCode );
    }

    static void reloadDataFromScratch( final Connection conn )
        throws Exception
    {
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
