/**
 * 
 */
package pfa.alliance.fim.model.issue;

/**
 * Defines possible issue resolutions when an issue implementation ends
 * 
 * @author Csaba
 */
public enum IssueResolution
{
    /** This means issue was completed / fixed (in case of bug or enhancement). */
    FIXED,
    /** Usually appears in case of BUG, it means issue was identified but it will not be fixed. */
    WONT_FIX,
    /** Usually appears in case of BUG, it means issue could not be reproduced. */
    CANNOT_REPRODUCE,
    /** This means same thing is described in another issue (and this one will not be implemented / fixed). */
    DUPLICATE
}
