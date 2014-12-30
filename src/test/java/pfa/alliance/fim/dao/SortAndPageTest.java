/**
 * 
 */
package pfa.alliance.fim.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is used for testing {@link SortAndPage}.
 * 
 * @author Nemeti
 */
public class SortAndPageTest
{
    @Test
    public void test_SortAndPage_NoSetting()
    {
        SortAndPage page = new SortAndPage();
        Assert.assertEquals( "Initial value issue", 0, page.getStartIndex() );
        Assert.assertEquals( "Max value issue", SortAndPage.DEFAULT_MAX_ITEMS, page.getMaxItems() );
        Assert.assertTrue( "toString() issue",
                           page.toString().contains( "OFFSET 0 LIMIT " + SortAndPage.DEFAULT_MAX_ITEMS ) );
    }

    @Test
    public void test_SortAndPage_withSetting()
    {
        SortAndPage page = new SortAndPage();
        page.setStartIndex( 123 );
        page.setMaxItems( 100 );
        Assert.assertEquals( "Initial value issue", 123, page.getStartIndex() );
        Assert.assertEquals( "Max value issue", 100, page.getMaxItems() );
        Assert.assertTrue( "toString() issue", page.toString().contains( "OFFSET 123 LIMIT 100" ) );
    }

    @Test
    public void test_SortAndPage_withNextPage()
    {
        SortAndPage page = new SortAndPage();
        page.setMaxItems( 123 );
        page.nextPage();
        Assert.assertEquals( "Initial value issue (1)", 123, page.getStartIndex() );
        Assert.assertEquals( "Max value issue (1)", 123, page.getMaxItems() );

        page.nextPage();
        Assert.assertEquals( "Initial value issue (2)", 246, page.getStartIndex() );
        Assert.assertEquals( "Max value issue (2)", 123, page.getMaxItems() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_setMaxItems_withTooSmall()
    {
        SortAndPage page = new SortAndPage();
        page.setMaxItems( 0 );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_setMaxItems_withTooBig()
    {
        SortAndPage page = new SortAndPage();
        page.setMaxItems( SortAndPage.MAX_ITEMS_TO_RETRIEVE + 1 );
    }
}
