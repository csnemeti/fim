/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pfa.alliance.fim.model.issue.IssuePriority;

/**
 * Class used for testing {@link IssuePriorityRepositoryImpl}.
 * 
 * @author Csaba
 */
public class IssuePriorityRepositoryImplTest
    extends BaseDbUnitTest
{
    private static IssuePriorityRepositoryImpl issuePriorityRepositoryImpl;

    @BeforeClass
    public static void init()
    {
        issuePriorityRepositoryImpl = new IssuePriorityRepositoryImpl();
        getInjector().injectMembers( issuePriorityRepositoryImpl );
    }

    @Test
    public void test_findAll()
    {
        List<IssuePriority> priorities = issuePriorityRepositoryImpl.findAll();
        Assert.assertNotNull( "Priorities should not be null", priorities );
        Assert.assertEquals( "Priorities size issue", 5, priorities.size() );

        // order of the priorities should be descending
        int current = Integer.MAX_VALUE;
        for ( IssuePriority priority : priorities )
        {
            if ( current <= priority.getOrder() )
            {
                Assert.fail( "Wrong order for: " + priority + " in list: " + priorities );
            }
            else
            {
                current = priority.getOrder();
            }
        }
    }

    @Test
    public void test_findAllIds()
    {
        List<String> priorities = issuePriorityRepositoryImpl.findAllIds();
        Assert.assertNotNull( "Priorities should not be null", priorities );
        Assert.assertEquals( "Priorities size issue", 5, priorities.size() );

        // Order of the priority IDs should be the same as for IssuePriority objects
        List<IssuePriority> priorityObjects = issuePriorityRepositoryImpl.findAll();
        Assert.assertEquals( "Priorities size vs. Priorities objects issue", priorities.size(), priorityObjects.size() );
        for ( int i = 0; i < priorities.size(); i++ )
        {
            Assert.assertEquals( "Priority ID problem", priorityObjects.get( i ).getId(), priorities.get( i ) );
        }
    }

    @AfterClass
    public static void finish()
    {
        issuePriorityRepositoryImpl = null;
    }
}
