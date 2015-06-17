/**
 * 
 */
package pfa.alliance.fim.model.issue.states;

import javax.persistence.Entity;

import pfa.alliance.fim.model.GenericLocalizedModel;
import pfa.alliance.fim.model.issue.IssuePriorityLocalized;

/**
 * Localization for {@link IssueState}.
 * 
 * @author Csaba
 */
@Entity( name = "issue_state_localized" )
public class IssueStateLocalized
    extends GenericLocalizedModel<Long, IssueState>
{
    private static final long serialVersionUID = 253859477759207415L;

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof IssuePriorityLocalized ) )
        {
            return false;
        }
        IssueStateLocalized localized = (IssueStateLocalized) obj;
        return super.equalsHelper( localized );
    }
}
