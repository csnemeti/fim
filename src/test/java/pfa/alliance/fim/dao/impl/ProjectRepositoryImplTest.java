/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import org.junit.Assert;
import org.junit.Test;

import pfa.alliance.fim.model.project.Project;

/**
 * This class is used for testing {@link ProjectRepositoryImpl}.
 * 
 * @author Nemeti
 */
public class ProjectRepositoryImplTest
{
    @Test
    public void testInstances()
    {
        ProjectRepositoryImpl projectRepositoryImpl = new ProjectRepositoryImpl();
        
        Class<?> entityClass = projectRepositoryImpl.getEntityClass();
        Assert.assertNotNull( "Entity class should not be null", entityClass );
        Assert.assertEquals( "Different entiry class, check if this is correct", Project.class,  entityClass );
        
        Class<?> idClass = projectRepositoryImpl.getIdClass();
        Assert.assertNotNull( "ID class should not be null", idClass );
        Assert.assertEquals( "Different id class, check if this is correct", Integer.class,  idClass );
    }
}
