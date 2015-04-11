/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This exception is thrown in order to inform you that an operation with Solr server failed.
 * 
 * @author Csaba
 */
public class SolrOperationFailedException
    extends SolrException
{
    private static final long serialVersionUID = -8252291244403467878L;

    /**
     * Called when instance of this class is created.
     * 
     * @param message a message of exception
     * @param cause the original exception cause
     */
    public SolrOperationFailedException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param message a message of exception
     */
    public SolrOperationFailedException( String message )
    {
        super( message );
    }

}
