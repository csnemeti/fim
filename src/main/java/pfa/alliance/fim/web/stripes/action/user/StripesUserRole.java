/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.io.Serializable;

import pfa.alliance.fim.model.user.UserRole;

/**
 * The {@link UserRole} in Stripes.
 * 
 * @author Csaba
 */
public class StripesUserRole
    implements Serializable
{
    private static final long serialVersionUID = -8955465921568929223L;

    /** The object ID, the enumeration name. This will be set on Option value. */
    private String id;

    /** The name / description of the object. */
    private String description;

    /**
     * Called when instance of this class is created.
     * 
     * @param id the record ID / option value
     * @param description the record description
     */
    public StripesUserRole( String id, String description )
    {
        this.id = id;
        this.description = description;
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param role the {@link UserRole} which name will be record ID / option value
     * @param description the record description
     */
    public StripesUserRole( UserRole role, String description )
    {
        this.id = role.name();
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
        return "StripesUserRole [id=" + id + "]";
    }

}
