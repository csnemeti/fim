package pfa.alliance.fim.dao;

import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;


public interface IssueStateRepository
    extends JpaRepository<IssueState, Long>
{
    /**
     * Finds the first (start) {@link IssueState} an Issue should have when is created.
     * 
     * @param project the {@link Project} we're interested in
     * @return the first / start state
     */
    IssueState findStartStateForProject( Project project );
}
