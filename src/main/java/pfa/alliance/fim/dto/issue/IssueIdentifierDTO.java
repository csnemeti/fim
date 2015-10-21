/**
 * 
 */
package pfa.alliance.fim.dto.issue;

import java.io.Serializable;

/**
 * @author Csaba
 */
public class IssueIdentifierDTO
    implements Serializable, IssueIdentifier
{
    /** The ID of this issue. */
    private long id;

    /** This is the issue code Project + Issue code. */
    private String code;

    /**
     * Called when instance of this class is created.
     * 
     * @param id the ID of the issue
     * @param code the Code of the issue
     */
    public IssueIdentifierDTO( long id, String code )
    {
        this.id = id;
        this.code = code;
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getCode()
    {
        return code;
    }

}
