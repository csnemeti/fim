/**
 * 
 */
package pfa.alliance.fim.model.user;

/**
 * Define the statuses a user may have
 * 
 * @author Nemeti
 *
 */
public enum UserStatus
{
    /** Status reserved for new users that didn't confirmed their e-mail address yet. These users cannot log in into application until they do not confirm the e-mail address. */
    NEW, 
    /** Status for active users. */
    ACTIVE,
    /** Status reserved for users that were disabled or close their account. */
    DISABLED,
    /**
     * This status means user account should be deleted OR modified beyound repairs (so that user should not be
     * identified anymore.
     */
    SCHEDULED_FOR_DELETE
}
