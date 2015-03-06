/**
 * 
 */
package pfa.alliance.fim.dto;


/**
 * This class defines a search result for project.
 * 
 * @author Csaba
 */
public class ProjectSearchResultDTO
    extends ProjectDTO
{
    /** This represents the index value of this item in the total results list. */
    private int indexInTotalResults;

    /** This represents the index value of this item in the currently displayed results list. */
    private int indexInCurrentResults;


    /** HTML encoded text for actions to fill in. */
    private String actions = "";

    public int getIndexInTotalResults()
    {
        return indexInTotalResults;
    }

    public void setIndexInTotalResults( int indexInTotalResults )
    {
        this.indexInTotalResults = indexInTotalResults;
    }

    public int getIndexInCurrentResults()
    {
        return indexInCurrentResults;
    }

    public void setIndexInCurrentResults( int indexInCurrentResults )
    {
        this.indexInCurrentResults = indexInCurrentResults;
    }

    public String getActions()
    {
        return actions;
    }

    public void setActions( String actions )
    {
        this.actions = actions;
    }

}
