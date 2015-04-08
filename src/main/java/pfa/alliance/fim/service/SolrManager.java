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
     * Method called to perform initialization.
     * 
     * @throws SolrOperationFailedException in case the call failed
     */
    void initDb()
        throws SolrOperationFailedException;

    /**
     * Method called to start Solr server.
     * 
     * @throws SolrOperationFailedException in case the call failed
     */
    void start()
        throws SolrOperationFailedException;

    /**
     * Method called to run full index on Users.
     * 
     * @throws SolrOperationFailedException in case the call failed
     */
    void runUserFullIndex()
        throws SolrOperationFailedException;

    /**
     * Method called to run delta index on Users.
     * 
     * @throws SolrOperationFailedException in case the call failed
     */
    void runUserDeltaIndex()
        throws SolrOperationFailedException;
}
