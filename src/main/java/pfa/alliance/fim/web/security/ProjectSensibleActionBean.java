/**
 * 
 */
package pfa.alliance.fim.web.security;


/**
 * This interface defines the methods necessary to be implemented by an
 * {@link net.sourceforge.stripes.action.ActionBean} so that it's permission should be validated against a specific
 * Project. For example: If you would like to see or edit a Project, we should be check IF you are allowed to see or
 * edit that specific Project. Even if you have right to Edit Projects, that doesn't mean you are allowed to edit ALL
 * the projects.
 * 
 * @author Csaba
 */
public interface ProjectSensibleActionBean
{
    /**
     * Gets the ID of the project against witch user permission should be validated.
     * 
     * @return The ID of the project OR null if project is not defined yet or doesn't have ID (when create a Project it
     *         has no ID until you save it)
     */
    Integer getProjectId();
}
