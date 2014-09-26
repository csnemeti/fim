/**
 * 
 */
package pfa.alliance.fim.service;

import pfa.alliance.fim.model.user.User;
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
     * @return the created User
     */
    User registerUser( String email, String cleanPassword, String firstName, String lastName );

    /**
     * Authenticate a {@link User}.
     * 
     * @param username the user login name
     * @param cleanPassword the user password in clear text form
     * @return the authenticated {@link User} or null if there's no such {@link User}
     */
    User login( String username, String cleanPassword );

}
