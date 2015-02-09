/**
 * 
 */
package pfa.alliance.fim.web.datatables;

import java.io.Serializable;

/**
 * This class describes a Search parameter set.
 * 
 * @author Csaba
 */
public class DatatablesSearch
    implements Serializable
{
    /** The searched value (expression). */
    private String value;
    /** Indicates if the search expression is regex based or not. */
    private Boolean regex;

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public Boolean getRegex()
    {
        return regex;
    }

    public void setRegex( Boolean regex )
    {
        this.regex = regex;
    }

}
