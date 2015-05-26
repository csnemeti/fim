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
    PROJECT_CREATE_PROJECT, PROJECT_SHOW_PROJECT, PROJECT_EDIT_PROJECT,
    /** Search for projects. */
    PROJECT_LIST_PROJECTS,
    /** Show hidden projects in search result. */
    PROJECT_SHOW_HIDDEN_PROJECTS, ADMIN_INVITE_USER, ADMIN_SEARCH_USERS,
    /** Show assigned projects for given user. */
    ADMIN_SHOW_ASSIGNED_PROJECTS,
}
