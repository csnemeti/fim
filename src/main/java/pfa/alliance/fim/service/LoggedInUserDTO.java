/**
 * 
 */
package pfa.alliance.fim.service;

import java.io.Serializable;

import pfa.alliance.fim.model.user.User;

/**
 * This class defines a logged in {@link User} with all permissions the user has.
 * 
 * @author Csaba
 */
public class LoggedInUserDTO
    implements Serializable
{
    /** The logged in user. */
    private final User user;

    /**
     * Called when instance of this class is created.
     * 
     * @param user the logged in user
     */
    public LoggedInUserDTO( User user )
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

}
