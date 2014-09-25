/**
 * 
 */
package pfa.alliance.fim.dao;

import pfa.alliance.fim.model.user.User;

/**
 * This interface defines the {@link User} repository methods.
 * 
 * @author Csaba
 */
public interface UserRepository
    extends JpaRepository<User, Integer>
{
    /**
     * Search for a user that has the given username and the given password.
     * 
     * @param username the user login name
     * @param password the user password (encrypted)
     * @return the corresponding {@link User} or null if username and / or password did not match
     */
    User findBy( String username, String password );

    /**
     * This method is used for search for users with a given exact username. Since username is unique, only one result
     * will be returned.
     * 
     * @param username the login name of the user, no wild chars are allowed
     * @return the {@link User} with the given login name or null if no such user is found
     */
    User findByUsername( String username );

    /**
     * This method is used for search for users with a given exact e-mail address. Since e-mail address is unique, only
     * one result will be returned.
     * 
     * @param email the e-mail address of the user, no wild chars are allowed
     * @return the {@link User} with the given e-mail address or null if no such user is found
     */
    User findByEmail( String email );
}
