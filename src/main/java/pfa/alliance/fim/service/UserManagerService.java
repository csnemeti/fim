/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import pfa.alliance.fim.dto.UserSearchDTO;
import pfa.alliance.fim.dto.UserSearchResultDTO;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserOneTimeLink;
import pfa.alliance.fim.model.user.UserRole;
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
     * Invite a new {@link User}. The {@link UserStatus} will be {@link UserStatus#NEW}.
     * 
     * @param email the user e-mail
     * @param firstName the user first name
     * @param lastName the user last name
     * @param locale the {@link Locale} used in e-mail sending
     * @param defaultRole the default role this user should have
     * @return the created User
     */
    User inviteUser( String email, String firstName, String lastName, UserRole defaultRole, Locale locale );

    /**
     * Authenticate a {@link User}.
     * 
     * @param username the user login name
     * @param cleanPassword the user password in clear text form
     * @return a DTO containing the the authenticated {@link User} or null if there's no such {@link User} and a map
     *         with permissions based on each project
     */
    LoggedInUserDTO login( String username, String cleanPassword );

    /**
     * Generates a One time link that allows user to set a new password.
     * 
     * @param username the username of user that forgot his / her password
     * @param locale the locale to be used in e-mail sending
     * @return The {@link User} that was found to reset it's password
     * @throws MessagingException if e-mail sending fails
     */
    User forgotPassword( String username, Locale locale )
        throws MessagingException;

    /**
     * Gets the User with given ID.
     * 
     * @param id the ID of the user
     * @return the found user or null if not found
     */
    User findById( int id );

    /**
     * Gets the {@link UserOneTimeLink} with {@link User} filled in from database.
     * 
     * @param uuid the link unique identifier (uuid).
     */
    void activateUser( String uuid );
    
    /**
     * Counts the number of results based on the given search criteria.
     * 
     * @param criteriaCriteria the search criteria
     * @return the number of results
     */
    long count( UserSearchDTO criteriaCriteria );
    
    /**
     * Search for users matching the given search criteria
     * 
     * @param criteria the search criteria
     * @return the results
     */
    List<UserSearchResultDTO> search( UserSearchDTO criteria );

    /**
     * Disable the user at own request. In order to prove it's not a trick, the current password must be provided.
     * 
     * @param userId the ID of the user that made the request
     * @param clearTextPassword the current password of the user in clear text (as typed in browser) form
     */
    void disableUserAtOwnRequest( int userId, String clearTextPassword );

    /**
     * Change the the user password IF current password if correct.
     * 
     * @param userId the database ID of the user
     * @param curentPassword the user current password
     * @param newPassword the user new password
     */
    void changePassword( int userId, String curentPassword, String newPassword );

    /**
     * Update some of the user data.
     * 
     * @param userId the user ID
     * @param firstName the user new first name
     * @param lastName the user new last name
     */
    void changeUserData( int userId, final String firstName, final String lastName );
}
