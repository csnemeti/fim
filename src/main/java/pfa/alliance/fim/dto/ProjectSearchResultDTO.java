/**
 * 
 */
package pfa.alliance.fim.dto;

import pfa.alliance.fim.model.project.ProjectState;

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

    /** The textual name of the state. */
    private String stateName;

    /** The icon corresponding to given state. */
    private String stateIcon;

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

    public String getStateName()
    {
        return stateName;
    }

    public String getStateIcon()
    {
        return stateIcon;
    }

    @Override
    public void setState( ProjectState state )
    {
        super.setState( state );
        stateName = state.name();
        stateIcon = state.getStateClass();
    }
}
