/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.dao.ProjectComponentRepository;
import pfa.alliance.fim.dao.ProjectLabelRepository;
import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.FimUrlGeneratorService;

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

    private EmailService emailServiceMock;

    private EmailGeneratorService emailGeneratorServiceMock;

    private FimUrlGeneratorService fimUrlGeneratorServiceMock;
    private ProjectComponentRepository componentRepositoryMock;

    private ProjectLabelRepository labelRepositoryMock;

    private IssueStateRepository issueStateRepositoryMock;

    @Before
    public void init()
    {
        projectRepositoryMock = Mockito.mock( ProjectRepository.class );
        userRepositoryMock = Mockito.mock( UserRepository.class );
        emailServiceMock = Mockito.mock( EmailService.class );
        emailGeneratorServiceMock = Mockito.mock( EmailGeneratorService.class );
        fimUrlGeneratorServiceMock = Mockito.mock( FimUrlGeneratorService.class );
        componentRepositoryMock = Mockito.mock( ProjectComponentRepository.class );
        labelRepositoryMock = Mockito.mock( ProjectLabelRepository.class );
        issueStateRepositoryMock = Mockito.mock( IssueStateRepository.class );

        projectManagementServiceImpl =
            new ProjectManagementServiceImpl( projectRepositoryMock, userRepositoryMock, componentRepositoryMock,
                                              labelRepositoryMock, emailServiceMock, issueStateRepositoryMock,
                                              emailGeneratorServiceMock, fimUrlGeneratorServiceMock );
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

        projectManagementServiceImpl.create( "name", "code", "description", false, null, 1, null, Locale.US );

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

        projectManagementServiceImpl.create( "name", "code", "description", false, null, 1, null, Locale.US );

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

        Project returned =
            projectManagementServiceImpl.create( "name", "code", "description", false, null, 1, null, Locale.US );

        Assert.assertNotNull( "Returned project should not be null", returned );
        Assert.assertSame( "Returned project is different", project, returned );

        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( Project.class ) );
    }

    @Test
    public void test_create_successHiddenAndActive()
    {
        User user = new User();
        user.setId( 1 );
        Mockito.when( userRepositoryMock.findOne( 1 ) ).thenReturn( user );
        Project project = new Project();
        project.setId( 1 );
        Mockito.when( projectRepositoryMock.save( Mockito.any( Project.class ) ) ).thenReturn( project );

        Project returned =
            projectManagementServiceImpl.create( "name", "code", "description", true, ProjectState.ACTIVE, 1, null,
                                                 Locale.US );

        Assert.assertNotNull( "Returned project should not be null", returned );
        Assert.assertSame( "Returned project is different", project, returned );

        Mockito.verify( userRepositoryMock, Mockito.atLeastOnce() ).findOne( 1 );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).save( Mockito.any( Project.class ) );
    }

    @Test
    public void test_getProjectDetails_invalidCode()
    {
        Mockito.when( projectRepositoryMock.findByCode( "invalid" ) ).thenReturn( null );

        ProjectDTO returned = projectManagementServiceImpl.getProjectDetails( "invalid" );

        Assert.assertNull( "Returned project should be null", returned );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).findByCode( Mockito.anyString() );
    }

    @Test
    public void test_getProjectDetails_validCodeNoOwner()
    {
        Project project = new Project();
        project.setId( 111 );
        project.setCode( "P1" );
        project.setName( "Name: 111" );
        project.setDescription( "Description: 111" );
        User user = null;

        Mockito.when( projectRepositoryMock.findByCode( "P1" ) ).thenReturn( project );
        Mockito.when( projectRepositoryMock.findOwnerForProject( 111 ) ).thenReturn( user );

        ProjectDTO returned = projectManagementServiceImpl.getProjectDetails( "P1" );

        Assert.assertNotNull( "Returned project should be null", returned );
        Assert.assertEquals( "Id issue", project.getId(), Integer.valueOf( returned.getId() ) );
        Assert.assertEquals( "Code issue", project.getCode(), returned.getCode() );
        Assert.assertEquals( "Name issue", project.getName(), returned.getName() );
        Assert.assertEquals( "Description issue", project.getDescription(), returned.getDescription() );
        Assert.assertEquals( "User issue", "<< unknown >>", returned.getOwnerInfo() );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).findByCode( Mockito.anyString() );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).findOwnerForProject( Mockito.anyInt() );
    }

    @Test
    public void test_getProjectDetails_validCodeWithOwner()
    {
        Project project = new Project();
        project.setId( 111 );
        project.setCode( "P1" );
        project.setName( "Name: 111" );
        project.setDescription( "Description: 111" );
        User user = new User();
        user.setId( 112 );
        user.setFirstName( "First" );
        user.setLastName( "Name" );
        user.setEmail( "e@ma.il" );

        Mockito.when( projectRepositoryMock.findByCode( "P1" ) ).thenReturn( project );
        Mockito.when( projectRepositoryMock.findOwnerForProject( 111 ) ).thenReturn( user );

        ProjectDTO returned = projectManagementServiceImpl.getProjectDetails( "P1" );

        Assert.assertNotNull( "Returned project should be null", returned );
        Assert.assertEquals( "Id issue", project.getId(), Integer.valueOf( returned.getId() ) );
        Assert.assertEquals( "Code issue", project.getCode(), returned.getCode() );
        Assert.assertEquals( "Name issue", project.getName(), returned.getName() );
        Assert.assertEquals( "Description issue", project.getDescription(), returned.getDescription() );
        Assert.assertEquals( "User issue", "First Name ( e@ma.il )", returned.getOwnerInfo() );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).findByCode( Mockito.anyString() );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).findOwnerForProject( Mockito.anyInt() );
    }

    @Test
    public void test_count()
    {
        Mockito.when( projectRepositoryMock.count( Mockito.any( ProjectSearchDTO.class ) ) ).thenReturn( 4L );
        ProjectSearchDTO searchDTO = new ProjectSearchDTO();
        
        long result = projectManagementServiceImpl.count( searchDTO );

        Assert.assertEquals( "Result issue", 4L, result );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).count( Mockito.any( ProjectSearchDTO.class ) );
    }

    @Test
    public void test_search()
    {
        List<ProjectSearchResultDTO> expected = new ArrayList<>();
        Mockito.when( projectRepositoryMock.search( Mockito.any( ProjectSearchDTO.class ) ) ).thenReturn( expected );
        ProjectSearchDTO searchDTO = new ProjectSearchDTO();

        List<ProjectSearchResultDTO> result = projectManagementServiceImpl.search( searchDTO );

        Assert.assertSame( "Result issue", expected, result );
        Mockito.verify( projectRepositoryMock, Mockito.atLeastOnce() ).search( Mockito.any( ProjectSearchDTO.class ) );
    }
}
