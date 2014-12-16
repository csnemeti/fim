/**
 * 
 */
package pfa.alliance.fim.service.impl;

/**
 * This exception is thrown when provided information (when user registration is made) is duplicate / in conflict with
 * already existing information that belongs to same or another record from database. The exception is thrown if:
 * <ul>
 * <li>User registration is made and you provide duplicate user information (like same e-mail address with another
 * user's e-mail address).</li>
 * <li>Project is created and provided project code is already defined for another project.</li>
 * </ul>
 * 
 * @author Csaba
 */
public class DuplicateDataException
    extends RuntimeException
{
    private static final long serialVersionUID = -655784225301356740L;

    /**
     * Called when instance of this case is created.
     * 
     * @param message the exception message
     * @param cause the cause of this exception
     */
    DuplicateDataException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
