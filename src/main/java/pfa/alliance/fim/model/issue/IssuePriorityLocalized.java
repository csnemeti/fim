/**
 * 
 */
package pfa.alliance.fim.model.issue;

import javax.persistence.Entity;

import pfa.alliance.fim.model.GenericLocalizedModel;

/**
 * Localizations for {@link IssuePriority}
 * 
 * @author Csaba
 */
@Entity( name = "issue_priority_localized" )
public class IssuePriorityLocalized
    extends GenericLocalizedModel<Long, IssuePriority>
{
    private static final long serialVersionUID = 253859477759207412L;

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof IssuePriorityLocalized ) )
        {
            return false;
        }
        IssuePriorityLocalized localized = (IssuePriorityLocalized) obj;
        return super.equalsHelper( localized );
    }
}
