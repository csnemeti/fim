/**
 * 
 */
package pfa.alliance.fim.dao;

import org.junit.Assert;
import org.junit.Test;

import pfa.alliance.fim.dao.Sort;

/**
 * This class is used for testing {@link Sort}.
 * 
 * @author Nemeti
 */
public class SortTest
{

    @Test
    public void test_Sort_NoSetting()
    {
        Sort sort = new Sort();
        Assert.assertTrue( "There should be no conditions", sort.getSorting().isEmpty() );
        Assert.assertFalse( "There should be no next", sort.iterator().hasNext() );
        Assert.assertEquals( "toString() issue", "ORDER BY <<none>>", sort.toString() );
    }

    @Test
    public void test_Sort_WithSetting()
    {
        Sort sort = new Sort();
        sort.add( "a", true ).add( "c", false ).add( "B", true );
        Assert.assertEquals( "Conditions size problem", 3, sort.getSorting().size() );
        Assert.assertTrue( "There should be next", sort.iterator().hasNext() );
        Assert.assertEquals( "toString() issue", "ORDER BY a ASC, c DESC, B ASC", sort.toString() );
    }
}
