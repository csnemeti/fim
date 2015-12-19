/**
 * 
 */
package pfa.alliance.fim.service;

/**
 * This enumeration defines the cores from Solr and their names.
 * 
 * @author Csaba
 */
public enum SolrCore
{
    USERS( "users" ), ACTIVE_USERS( "active_users" ), PROJECTS( "projects" ), ISSUES( "issues" );

    /** The name of the Solr core. */
    private final String coreName;

    /**
     * Called when instance of this class is created.
     * 
     * @param coreName the name of the Solr core
     */
    private SolrCore( String coreName )
    {
        this.coreName = coreName;
    }

    public String getCoreName()
    {
        return coreName;
    }

}
