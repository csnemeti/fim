/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This class is the base exception for all Solr related problems. Class is declared abstract in order to enforce
 * creating customized exceptions.
 * 
 * @author Csaba
 */
public abstract class SolrException
    extends Exception
{
    private static final long serialVersionUID = -8252291244403467873L;

    /**
     * Called when instance of this class is created.
     * 
     * @param message a message of exception
     * @param cause the original exception cause
     */
    protected SolrException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Called when instance of this class is created.
     * 
     * @param message a message of exception
     */
    protected SolrException( String message )
    {
        super( message );
    }

}
