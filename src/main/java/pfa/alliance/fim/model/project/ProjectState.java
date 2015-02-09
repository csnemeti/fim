/**
 * 
 */
package pfa.alliance.fim.model.project;

/**
 * This enumeration defines the {@link Project} possible states.
 * 
 * @author Csaba
 */
public enum ProjectState
{
    /**
     * This is the first state and represents the project preparation phase. While in this state, <b>you can</b>:
     * <ul>
     * <li>Assign / un-assign users from project
     * <li>Create new issues and "walk" them through the issue state
     * </ul>
     * While in this state, <b>you cannot</b>:
     * <ul>
     * <li>Create reports.
     * <li>Define sprints, their content and start / stop sprint.
     * </ul>
     */
    IN_PREPARATION( "fa-spinner fa-spin" ),
    /** This is the state where project "runs in full power". Everything is allowed based on user permission. */
    ACTIVE( "fa-cog fa-spin" ),
    /**
     * When a project is closed it means activity on it is suspended. While in this state, <b>you can</b>:
     * <ul>
     * <li>Assign / un-assign users from project
     * <li>See issues, see result for previously generated reports.
     * </ul>
     * While in this state, <b>you cannot</b>:
     * <ul>
     * <li>Create new issues and "walk" them through the issue state
     * <li>Create new reports.
     * <li>Define sprints, their content and start / stop sprint.
     * </ul>
     */
    CLOSED( "fa-cog" ),
    /**
     * This state means ready for physical delete. In this state, you cannot perform anything, a back-end job will
     * un-assign all users from the project and physically delete its content (including issues, reports...) from
     * database. Once this is set, there's not comming back.
     */
    SCHEDULED_FOR_DELETE( "fa-close" );
    
    /** The CSS class to use for render the state. */
    private final String stateClass;

    /**
     * Called when instance of this class is created.
     * 
     * @param stateClass the state class to use
     */
    private ProjectState(String stateClass){
        this.stateClass = stateClass;
    }

    /**
     * Gets the state class used for rendering this
     * 
     * @return the font awesome class to be used
     */
    public String getStateClass()
    {
        return stateClass;
    }

}
