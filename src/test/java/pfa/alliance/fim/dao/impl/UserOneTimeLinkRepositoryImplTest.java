/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import org.junit.Assert;
import org.junit.Test;

import pfa.alliance.fim.model.user.UserOneTimeLink;

/**
 * This class is used for testing {@link UserOneTimeLinkRepositoryImpl}.
 * 
 * @author Nemeti
 */
public class UserOneTimeLinkRepositoryImplTest
{
    @Test
    public void testInstances()
    {
        UserOneTimeLinkRepositoryImpl userOneTimeLinkRepositoryImpl = new UserOneTimeLinkRepositoryImpl();
        
        Class<?> entityClass = userOneTimeLinkRepositoryImpl.getEntityClass();
        Assert.assertNotNull( "Entity class should not be null", entityClass );
        Assert.assertEquals( "Different entiry class, check if this is correct", UserOneTimeLink.class,  entityClass );
        
        Class<?> idClass = userOneTimeLinkRepositoryImpl.getIdClass();
        Assert.assertNotNull( "ID class should not be null", idClass );
        Assert.assertEquals( "Different id class, check if this is correct", Long.class,  idClass );
    }
}
