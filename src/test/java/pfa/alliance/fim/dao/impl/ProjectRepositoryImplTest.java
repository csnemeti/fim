/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @AfterClass
    public static void finish()
    {
        projectRepositoryImpl = null;
    }
}
