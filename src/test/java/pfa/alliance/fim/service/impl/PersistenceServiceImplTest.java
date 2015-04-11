/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.inject.Provider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.service.DatabaseMigrationService;
import pfa.alliance.fim.service.SolrManager;

import com.google.inject.persist.PersistService;

/**
 * This class is used for testing {@link PersistenceServiceImpl}.
 * 
 * @author Csaba
 */
public class PersistenceServiceImplTest
{
    private PersistService persistServiceMock;

    private DatabaseMigrationService databaseMigrationServiceMock;

    private Provider<DatabaseMigrationService> databaseMigrationServiceProviderMock;

    private SolrManager solrManagerMock;

    private PersistenceServiceImpl persistenceServiceImpl;

    @Before
    public void init()
    {
        persistServiceMock = Mockito.mock( PersistService.class );
        solrManagerMock = Mockito.mock( SolrManager.class );
        databaseMigrationServiceMock = Mockito.mock( DatabaseMigrationService.class );
        databaseMigrationServiceProviderMock = Mockito.mock( Provider.class );
        Mockito.when( databaseMigrationServiceProviderMock.get() ).thenReturn( databaseMigrationServiceMock );

        persistenceServiceImpl =
            new PersistenceServiceImpl( persistServiceMock, databaseMigrationServiceProviderMock, solrManagerMock );
    }

    @Test
    public void test_startPersistence_notRunning()
    {
        // precondition
        Assert.assertFalse( "It should not run", persistenceServiceImpl.isRunning() );
        // call
        persistenceServiceImpl.startPersistence();
        // test
        Assert.assertTrue( "NOW it should run", persistenceServiceImpl.isRunning() );
        Mockito.verify( databaseMigrationServiceMock, Mockito.atLeastOnce() ).upgradeDb();
        Mockito.verify( persistServiceMock, Mockito.atLeastOnce() ).start();
    }

    @Test
    public void test_startPersistence_alreadyRunning()
    {
        // precondition
        persistenceServiceImpl.startPersistence();
        Assert.assertTrue( "It should run", persistenceServiceImpl.isRunning() );
        Mockito.reset( databaseMigrationServiceMock, databaseMigrationServiceProviderMock, persistServiceMock );
        // call
        persistenceServiceImpl.startPersistence();
        // test
        Assert.assertTrue( "NOW it should still run", persistenceServiceImpl.isRunning() );
        Mockito.verifyZeroInteractions( databaseMigrationServiceMock, databaseMigrationServiceProviderMock,
                                        persistServiceMock );
    }

    @Test
    public void test_stopPersistence_notRunning()
    {
        // precondition
        Assert.assertFalse( "It should not run", persistenceServiceImpl.isRunning() );
        Mockito.reset( databaseMigrationServiceMock, databaseMigrationServiceProviderMock, persistServiceMock );
        // call
        persistenceServiceImpl.stopService();
        // test
        Assert.assertFalse( "NOW it still not run", persistenceServiceImpl.isRunning() );
        Mockito.verifyZeroInteractions( databaseMigrationServiceMock, databaseMigrationServiceProviderMock,
                                        persistServiceMock );
    }

    @Test
    public void test_stopPersistence_wheRunning()
    {
        // precondition
        persistenceServiceImpl.startPersistence();
        Assert.assertTrue( "It should run", persistenceServiceImpl.isRunning() );
        Mockito.reset( databaseMigrationServiceMock, databaseMigrationServiceProviderMock, persistServiceMock );
        // call
        persistenceServiceImpl.stopService();
        // test
        Assert.assertFalse( "NOW it should not run", persistenceServiceImpl.isRunning() );
        Mockito.verifyZeroInteractions( databaseMigrationServiceMock, databaseMigrationServiceProviderMock );
        Mockito.verify( persistServiceMock, Mockito.atLeastOnce() ).stop();
    }
}
