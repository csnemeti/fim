/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Locale;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;

/**
 * This class is used for testing {@link ProjectManagementServiceImpl}
 * 
 * @author Csaba
 */
public class ProjectManagementServiceImplTest
{
    private ProjectManagementServiceImpl projectManagementServiceImpl;

    private ProjectRepository projectRepositoryMock;

    private UserRepository userRepositoryMock;

    @Before
    public void init()
    {
        projectRepositoryMock = Mockito.mock( ProjectRepository.class );
        userRepositoryMock = Mockito.mock( UserRepository.class );

        projectManagementServiceImpl = new ProjectManagementServiceImpl( projectRepositoryMock, userRepositoryMock );
    }

    @Test( expected = DuplicateDataException.class )
    public void test_create_duplicateData()
    {
        User user = new User();
        user.setId( 1 );
        Mockito.when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );
        Exception e =
            new PersistenceException( new IllegalStateException( "Duplicate data violates unique constraint" ) );
        Mockito.when( projectRepositoryMock.save( Mockito.any( Project.class ) ) ).thenThrow( e );

        projectManagementServiceImpl.create( "name", "code", "description", 1, null, Locale.US );

        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( Project.class ) );
    }

    @Test( expected = PersistenceException.class )
    public void test_create_errorSaving()
    {
        User user = new User();
        user.setId( 1 );
        Mockito.when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );
        Exception e = new PersistenceException( "4 testing" );
        Mockito.when( projectRepositoryMock.save( Mockito.any( Project.class ) ) ).thenThrow( e );

        projectManagementServiceImpl.create( "name", "code", "description", 1, null, Locale.US );

        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( Project.class ) );
    }

    @Test
    public void test_create_success()
    {
        User user = new User();
        user.setId( 1 );
        Mockito.when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );
        Project project = new Project();
        project.setId( 1 );
        Mockito.when( projectRepositoryMock.save( Mockito.any( Project.class ) ) ).thenReturn( project );

        Project returned = projectManagementServiceImpl.create( "name", "code", "description", 1, null, Locale.US );

        Assert.assertNotNull( "Returned project should not be null", returned );
        Assert.assertSame( "Returned project is different", project, returned );

        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( Project.class ) );
    }
}
