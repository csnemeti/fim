/**
 * 
 */
package pfa.alliance.fim.web.stripes.action;

import org.mockito.Mockito;

import pfa.alliance.fim.service.UserManagerService;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author Csaba
 *
 */
public class ServiceModuleMock
    extends AbstractModule
{

    private static UserManagerService userManagerServiceMock;

    /**
     * Initialize all mock objects.
     */
    public static void initMocks()
    {
        userManagerServiceMock = Mockito.mock( UserManagerService.class );
    }

    @Override
    protected void configure()
    {
        // TODO Auto-generated method stub
        // bind( ConfigurationService.class ).to( ConfigurationServiceImpl.class );
        // bind( DatabaseMigrationService.class ).to( DatabaseMigrationServiceImpl.class );
        // bind( EmailService.class ).to( EmailServiceImpl.class );
        // bind( EmailGeneratorService.class ).to( EmailGeneratorServiceImpl.class );
        // bind( PersistenceService.class ).to( PersistenceServiceImpl.class );
        // bind( FimUrlGeneratorService.class ).to( FimUrlGeneratorServiceImpl.class );
        //
        // bind( UserManagerService.class ).to( UserManagerServiceImpl.class );
        // bind( ProjectManagementService.class ).to( ProjectManagementServiceImpl.class );
    }

    /**
     * This is used by Guice for dependency injection
     * 
     * @return the mock instance
     */
    @Provides
    public UserManagerService getUserManagerService()
    {
        return userManagerServiceMock;
    }

    /**
     * This is used for program the mock object in unit test.
     * 
     * @return the mock object
     */
    public static UserManagerService getUserManagerServiceMock()
    {
        return userManagerServiceMock;
    }
}
