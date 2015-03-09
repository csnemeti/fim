/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;

/**
 * This class is used for testing {@link ProjectRepositoryImpl}.
 * 
 * @author Nemeti
 */
public class ProjectRepositoryImplTest
    extends BaseDbUnitTest
{
    private static ProjectRepositoryImpl projectRepositoryImpl;

    @BeforeClass
    public static void init()
    {
        projectRepositoryImpl = new ProjectRepositoryImpl();
        getInjector().injectMembers( projectRepositoryImpl );
    }

    @Test
    public void testInstances()
    {
        Class<?> entityClass = projectRepositoryImpl.getEntityClass();
        Assert.assertNotNull( "Entity class should not be null", entityClass );
        Assert.assertEquals( "Different entiry class, check if this is correct", Project.class,  entityClass );
        
        Class<?> idClass = projectRepositoryImpl.getIdClass();
        Assert.assertNotNull( "ID class should not be null", idClass );
        Assert.assertEquals( "Different id class, check if this is correct", Integer.class,  idClass );
    }

    @Test
    public void test_findByCode_invalidCode()
    {
        Project project = projectRepositoryImpl.findByCode( "invalid" );
        Assert.assertNull( "Project should be null", project );
    }

    @Test
    public void test_findByCode_validCode()
    {
        Project project = projectRepositoryImpl.findByCode( "P1" );
        Assert.assertNotNull( "Project should NOT be null", project );
        Assert.assertEquals( "Project code issue", "P1", project.getCode() );
        Assert.assertEquals( "Project name issue", "Project One", project.getName() );
    }

    @Test
    public void test_findOwnerForProject_invalidId()
    {
        User user = projectRepositoryImpl.findOwnerForProject( -1 );
        Assert.assertNull( "User should be null", user );
    }

    @Test
    public void test_findOwnerForProject_validIdNoOwner()
    {
        User user = projectRepositoryImpl.findOwnerForProject( 3 );
        Assert.assertNull( "User should be null", user );
    }

    @Test
    public void test_findOwnerForProject_validIdWithOwner()
    {
        User user = projectRepositoryImpl.findOwnerForProject( 1 );
        Assert.assertNotNull( "User should NOT be null", user );
        Assert.assertEquals( "User ID issue", Integer.valueOf( 7 ), user.getId() );
    }

    @Test
    public void test_count_noSpecialQuery()
    {
        ProjectSearchDTO searchDTO = new ProjectSearchDTO();

        long result = projectRepositoryImpl.count( searchDTO );

        Assert.assertEquals( "Count issues", 3L, result );
    }

    @Test
    public void test_count_showHiddenQuery()
    {
        ProjectSearchDTO searchDTO = new ProjectSearchDTO();
        searchDTO.setHidden( true );

        long result = projectRepositoryImpl.count( searchDTO );

        Assert.assertEquals( "Count issues", 4L, result );
    }

    @Test
    public void test_search_withCriteriaAndResults()
    {
        ProjectSearchDTO searchDTO = new ProjectSearchDTO();
        searchDTO.setStates( new String[] { "IN_PREPARATION", "ACTIVE" } );
        searchDTO.setName( "Project" );
        searchDTO.setCode( "2" );
        searchDTO.setOrderBy( "id" );
        searchDTO.setAscending( true );
        searchDTO.setStartIndex( 0 );
        searchDTO.setItemsPerPage( 100 );

        List<ProjectSearchResultDTO> result = projectRepositoryImpl.search( searchDTO );

        Assert.assertNotNull( "Result should not be null", result );
        Assert.assertEquals( "Result size issue", 1, result.size() );
        Assert.assertEquals( "Result element issue", 2, result.get( 0 ).getId() );
    }

    @Test
    public void test_search_withCriteriaNoResults()
    {
        ProjectSearchDTO searchDTO = new ProjectSearchDTO();
        searchDTO.setStates( new String[] { "IN_PREPARATION", "ACTIVE" } );
        searchDTO.setName( "Project" );
        searchDTO.setCode( "2" );
        searchDTO.setOrderBy( "id" );
        searchDTO.setAscending( false );
        searchDTO.setStartIndex( 10 );
        searchDTO.setItemsPerPage( 100 );

        List<ProjectSearchResultDTO> result = projectRepositoryImpl.search( searchDTO );

        Assert.assertNotNull( "Result should not be null", result );
        Assert.assertEquals( "Result size issue", 0, result.size() );
    }

    @AfterClass
    public static void finish()
    {
        projectRepositoryImpl = null;
    }
}
