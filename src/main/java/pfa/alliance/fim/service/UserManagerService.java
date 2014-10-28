/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.Locale;

import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * @author Csaba
 *
 */
public interface UserManagerService
{
    /**
     * Registers a new {@link User}. The {@link UserStatus} will be {@link UserStatus#NEW}.
     * 
     * @param email the user e-mail
     * @param cleanPassword password typed in clear form
     * @param firstName the user first name
     * @param lastName the user last name
     * @param locale the {@link Locale} used in e-mail sending
     * @return the created User
     */
    User registerUser( String email, String cleanPassword, String firstName, String lastName, Locale locale );

    /**
     * Authenticate a {@link User}.
     * 
     * @param username the user login name
     * @param cleanPassword the user password in clear text form
     * @return the authenticated {@link User} or null if there's no such {@link User}
     */
    User login( String username, String cleanPassword );

    /**
     * Gets the {@link UserOneTimeLink} with {@link User} filled in from database.
     * 
     * @param uuid the link unique identifier (uuid).
     */
    void activateUser( String uuid );
}
