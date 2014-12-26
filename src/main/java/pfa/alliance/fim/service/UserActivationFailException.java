/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This exception is thrown for signal that user activation fails.
 * 
 * @author Csaba
 */
public class UserActivationFailException
    extends RuntimeException
{
    private static final long serialVersionUID = -1984439295201348464L;

    /** The reason why user activation failed. */
    private ActivationFailReason reason;

    /**
     * Called when instance of this class is created.
     * 
     * @param reason the fail reaosn
     */
    public UserActivationFailException( ActivationFailReason reason )
    {
        this.reason = reason;
    }

    /**
     * The reason why activation failed.
     * 
     * @return the reason
     */
    public ActivationFailReason getReason()
    {
        return reason;
    }

}
