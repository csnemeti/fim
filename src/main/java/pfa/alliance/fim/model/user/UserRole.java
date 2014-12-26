/**
 * 
 */
package pfa.alliance.fim.model.user;

/**
 * This is the list of all roles of a {@link User}.
 * 
 * @author Csaba
 */
public enum UserRole
{
    SCRUM_MASTER, PRODUCT_OWNER,
    /** Entire team, whatever "team" means: QA + developer or only one of it. */
    TEAM,
    /** Possibility to only see thing on the project, mainly for access statistical part of the project.. */
    STATISTICAL,
    /** Right to manage a Project. */
    PROJECT_ADMIN,
    /** Right to manage entire FIM. */
    ADMIN;
}
