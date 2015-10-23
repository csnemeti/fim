/**
 * 
 */
package pfa.alliance.fim.web.security;

/**
 * This enumeration contains all permissions a user may have.
 * 
 * @author Csaba
 */
public enum Permission
{
    ISSUE_CRAETE_ISSUE, ISSUE_SHOW_ISSUE, ISSUE_EDIT_ISSUE,

    PROJECT_CREATE_PROJECT, PROJECT_SHOW_PROJECT, PROJECT_EDIT_PROJECT, 

    /** Can assign Project Admin. */
    PROJECT_EDIT_PROJECT_ASSIGN_PA,
    /** Can assign Product Owner. */
    PROJECT_EDIT_PROJECT_ASSIGN_PO,
    /** Can assign Scrum master. */
    PROJECT_EDIT_PROJECT_ASSIGN_SM,
    /** Can change the owner of a project. */
    PROJECT_EDIT_PROJECT_CHANGE_OWNER,

    /** Search for projects. */
    PROJECT_LIST_PROJECTS,

    /** Show hidden projects in search result. */
    PROJECT_SHOW_HIDDEN_PROJECTS,

    ADMIN_INVITE_USER, ADMIN_SEARCH_USERS,
    /** Show assigned projects for given user. */
    ADMIN_SHOW_ASSIGNED_PROJECTS,
}
