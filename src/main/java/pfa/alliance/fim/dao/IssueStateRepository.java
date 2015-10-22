package pfa.alliance.fim.dao;

import java.util.List;

import pfa.alliance.fim.model.issue.states.IssueFlow;
import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;

public interface IssueStateRepository
    extends JpaRepository<IssueState, Long>
{
    /**
     * Find all the {@link IssueFlow}s that can be used by a {@link pfa.alliance.fim.model.project.Project}
     * 
     * @return the List of {@link IssueFlow}s ordered by name
     */
    List<IssueFlow> findAllFlows();

    /**
     * Finds the specific flow based on id.
     * 
     * @param id the ID of the {@link IssueFlow}
     * @return the ID of the flow to find.
     */
    IssueFlow findFlowById( long id );

    /**
     * Finds the first (start) {@link IssueState} an Issue should have when is created.
     * 
     * @param project the {@link Project} we're interested in
     * @return the first / start state
     */
    IssueState findStartStateForProject( Project project );

}
