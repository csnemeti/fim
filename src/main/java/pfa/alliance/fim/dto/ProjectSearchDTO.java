/**
 * 
 */
package pfa.alliance.fim.dto;

import java.util.Arrays;

/**
 * This class defines serach criterias for a project search.
 * 
 * @author Csaba
 */
public class ProjectSearchDTO
    extends AbstractSearchDTO
{
    private static final long serialVersionUID = -6938261188214243459L;

    private String code;

    private String name;

    private String[] states;

    private boolean hidden;

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String[] getStates()
    {
        return states;
    }

    public void setStates( String[] states )
    {
        this.states = states;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public void setHidden( boolean hidden )
    {
        this.hidden = hidden;
    }

    @Override
    public String toString()
    {
        return "ProjectSearchDTO [code=" + code + ", name=" + name + ", hidden=" + hidden + ", startIndex="
            + getStartIndex() + ", itemsPerPage=" + getItemsPerPage() + ", orderBy=" + getOrderBy() + ", ascending="
            + isAscending() + ", states=" + Arrays.toString( states ) + "]";
    }

}
