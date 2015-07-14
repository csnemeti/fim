package pfa.alliance.fim.dao;

import java.util.List;

import pfa.alliance.fim.model.issue.states.IssueFlow;
import pfa.alliance.fim.model.issue.states.IssueState;


public interface IssueStateRepository extends JpaRepository<IssueState, Integer>{
    /**
     * Find all the {@link IssueFlow}s that can be used by a {@link pfa.alliance.fim.model.project.Project}
     * 
     * @return the List of {@link IssueFlow}s ordered by name
     */
    List<IssueFlow> findAllFlows();

    /**
     * Finds the specific flow based on id.
     * 
     * @return the ID of the flow to find.
     */
    IssueFlow findFlowById( int id );
}
