/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This class represents an exception thrown if user password is not valid.
 * 
 * @author Csaba
 */
public class InvalidUserPasswordException
    extends RuntimeException
{
    private static final long serialVersionUID = -2943097061384399306L;

    /**
     * Called when instance of this class created.
     * 
     * @param message the reason of the message
     */
    public InvalidUserPasswordException( String message )
    {
        super( message );
    }

}
