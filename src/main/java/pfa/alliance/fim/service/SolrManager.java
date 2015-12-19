/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This interface defines the methods you can use for managing Solr server.
 * 
 * @author Csaba
 */
public interface SolrManager
{
    /**
     * Method called to perform Solr initialization.
     * 
     * @throws SolrOperationFailedException in case the call failed
     * @throws SolrConnectException in case it cannot connect to Solr server
     */
    void initDb()
        throws SolrOperationFailedException, SolrConnectException;

    /**
     * Method called to start Solr server.
     * 
     * @throws SolrOperationFailedException in case the call failed
     * @throws SolrConnectException in case it cannot connect to Solr server
     */
    void start()
        throws SolrOperationFailedException, SolrConnectException;

    /**
     * Method called to run full index on Users.
     * 
     * @throws SolrOperationFailedException in case the call failed
     * @throws SolrConnectException in case it cannot connect to Solr server
     */
    void runIndex( SolrCore core, boolean fullIndex )
        throws SolrOperationFailedException, SolrConnectException;

    /**
     * Method called to run delta index on Users in a separate thread without notifying about any problem.
     */
    void runQuiteUserDeltaIndexInSeparateThread();
}
