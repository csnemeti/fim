/**
 * 
 */
package pfa.alliance.fim.web.stripes.action;

import java.io.Serializable;

/**
 * This class keeps the option values in a select box.
 * 
 * @author Csaba
 */
public class StripesDropDownOption
    implements Serializable
{
    private static final long serialVersionUID = -8955465921568929223L;

    /** The Option value. */
    private String id;

    /** The name / description of the Option. */
    private String description;

    /**
     * Called when instance of this class is created.
     * 
     * @param id option value
     * @param description the Option description
     */
    public StripesDropDownOption( String id, String description )
    {
        this.id = id;
        this.description = description;
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param enumValue the enumeration which name will be used as id
     * @param description the option description
     */
    public StripesDropDownOption( Enum<?> enumValue, String description )
    {
        this.id = enumValue.name();
        this.description = description;
    }

    public String getId()
    {
        return id;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return "StripesDropDownOption [value=" + id + ", description=" + description + "]";
    }

}
