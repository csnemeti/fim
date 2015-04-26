/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pfa.alliance.fim.dao.Sort;
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
        List<IssuePriority> priorities = issuePriorityRepositoryImpl.findAll( 1 );
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
    public void test_findAll_withSorting()
    {
        Sort sort = new Sort( "id", true );
        List<IssuePriority> priorities = issuePriorityRepositoryImpl.findAll( 1, sort );
        Assert.assertNotNull( "Priorities should not be null", priorities );
        Assert.assertEquals( "Priorities size issue", 5, priorities.size() );

        // order of the priorities should be ascending
        long current = Long.MIN_VALUE;
        for ( IssuePriority priority : priorities )
        {
            if ( current >= priority.getId() )
            {
                Assert.fail( "Wrong order for: " + priority + " in list: " + priorities );
            }
            else
            {
                current = priority.getId();
            }
        }
    }

    @AfterClass
    public static void finish()
    {
        issuePriorityRepositoryImpl = null;
    }
}
