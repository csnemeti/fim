/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Properties;

import javax.sql.DataSource;

import org.batoo.jpa.JPASettings;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is used for testing {@link DatabaseMigrationServiceImpl}.
 * 
 * @author Csaba
 */
public class DatabaseMigrationServiceImplTest
{
    private FlywayMock flywayMock;

    @Before
    public void init()
    {
        flywayMock = null;
    }

    @Test
    public void test_upgradeDb()
    {
        // prepare
        Properties props = new Properties();
        props.setProperty( JPASettings.JDBC_URL, "url" );
        props.setProperty( JPASettings.JDBC_USER, "user" );
        props.setProperty( JPASettings.JDBC_PASSWORD, "password" );
        DatabaseMigrationServiceImpl service = new DatabaseMigrationServiceImplMock( props );
        // call
        service.upgradeDb();
        // test
        Assert.assertNotNull( "FlywayMock should not be null!", flywayMock );
        Assert.assertTrue( "Upgrade not run", flywayMock.migrateCalls > 0 );
        Assert.assertTrue( "Location not called", flywayMock.locationsCalls > 0 );
        Assert.assertTrue( "Datasources not called", flywayMock.datasourceCalls > 0 );
    }

    private static class FlywayMock extends Flyway{
        private int migrateCalls = 0;

        private int datasourceCalls = 0;

        private int locationsCalls = 0;
        @Override
        public int migrate()
            throws FlywayException
        {
            return ++migrateCalls;
        }

        @Override
        public void setDataSource( String url, String user, String password, String... initSqls )
        {
            ++datasourceCalls;
        }

        @Override
        public void setDataSource( DataSource dataSource )
        {
            ++datasourceCalls;
        }

        @Override
        public void setLocations( String... locations )
        {
            ++locationsCalls;
        }
    }
    
    private class DatabaseMigrationServiceImplMock
        extends DatabaseMigrationServiceImpl
    {

        DatabaseMigrationServiceImplMock( Properties jpaConfiguration )
        {
            super( jpaConfiguration );
        }

        @Override
        Flyway createFlyway()
        {
            flywayMock = new FlywayMock();
            return flywayMock;
        }
    }
}
