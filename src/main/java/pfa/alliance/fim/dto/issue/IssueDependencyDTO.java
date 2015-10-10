/**
 * 
 */
package pfa.alliance.fim.dto.issue;

import pfa.alliance.fim.model.issue.IssueType;

/**
 * @author Csaba
 *
 */
public class IssueDependencyDTO
    extends IssueDTO
{
    private DependencyType dependency;

    private String dependencyName;
    /**
     * Called when instance of this class is created.
     */
    public IssueDependencyDTO()
    {
        super();
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param id the ID of the issue
     * @param parentId the ID of the parent issue
     * @param projectId the ID of the proje3ct where the issue belongs
     * @param code the Code of the issue
     * @param title the issue title
     * @param type the issue type
     */
    public IssueDependencyDTO( long id, Long parentId, int projectId, String code, String title, IssueType type )
    {
        super( id, parentId, projectId, code, title, type );
    }

    public DependencyType getDependency()
    {
        return dependency;
    }

    public void setDependency( DependencyType dependency )
    {
        this.dependency = dependency;
    }

    public String getDependencyName()
    {
        return dependencyName;
    }

    public void setDependencyName( String dependencyName )
    {
        this.dependencyName = dependencyName;
    }

}
