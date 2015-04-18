/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This exception is thrown in order to inform you that connecting to Solr server failed.
 * 
 * @author Csaba
 */
public class SolrConnectException
    extends SolrException
{
    private static final long serialVersionUID = -8252291244403467879L;

    /**
     * Called when instance of this class is created.
     * 
     * @param message a message of exception
     * @param cause the original exception cause
     */
    public SolrConnectException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
