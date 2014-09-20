/**
 * 
 */
package pfa.alliance.fim;

import org.junit.Assert;

/**
 * This class is used for testing equals, hashcode and to String on 3 objects. Objects must have same instance.
 * 
 * @author Nemeti
 */
public class EqualsHashCodeAndToStringTester
{

    /**
     * Test the objects. o1 must be equals with o2 and not with o3. Also, The toString of o1 should be different from
     * the one oof o3.
     * 
     * @param o1 first object
     * @param o2 second object equals with first
     * @param o3 third object different from first
     */
    public static void testInstances( Object o1, Object o2, Object o3 )
    {
        test_o1_EqualsWithNull( o1 );
        test_o1_equals_aString( o1 );
        test_o1_EqualsWithHimself( o1 );
        test_o1_EqualsWithO2( o1, o2 );
        test_o1_NotEqualsWithO3( o1, o3 );
    }

    private static void test_o1_EqualsWithNull( Object o1 )
    {
        Assert.assertFalse( "o1.equals(null) failed", o1.equals( null ) );
    }

    private static void test_o1_equals_aString( Object o1 )
    {
        Assert.assertFalse( "o1.equals(\"abc\") failed", o1.equals( "abc" ) );
    }

    private static void test_o1_EqualsWithHimself( Object o1 )
    {
        Assert.assertTrue( "o1.equals(o1) failed", o1.equals( o1 ) );
    }

    private static void test_o1_EqualsWithO2( Object o1, Object o2 )
    {
        Assert.assertTrue( "o1.equals(o2) failed", o1.equals( o2 ) );
        Assert.assertEquals( "o1.hashCode() == o2.hashCode() failed", o1.hashCode(), o2.hashCode() );
    }

    private static void test_o1_NotEqualsWithO3( Object o1, Object o3 )
    {
        Assert.assertFalse( "!o1.equals(o3) failed", o1.equals( o3 ) );
        Assert.assertNotEquals( "o1.hashCode() != o3.hashCode() failed", o1.hashCode(), o3.hashCode() );
        Assert.assertNotEquals( "o1.toString() != o3.toString() failed", o1.toString(), o3.toString() );
    }

}
