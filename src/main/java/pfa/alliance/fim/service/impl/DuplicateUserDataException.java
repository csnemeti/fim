/**
 * 
 */
package pfa.alliance.fim.service.impl;

/**
 * This exception is thrown when provided user information (when user registration is made) is duplicate / in conflict
 * with already existing user information that belongs to same or another user from database.
 * 
 * @author Csaba
 */
public class DuplicateUserDataException
    extends RuntimeException
{
    private static final long serialVersionUID = -655784225301356740L;

    /**
     * Called when instance of this case is created.
     * 
     * @param message the exception message
     * @param cause the caouse of this exception
     */
    DuplicateUserDataException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
