/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.UserDTO;
import pfa.alliance.fim.service.SearchService;

/**
 * {@link SearchService} implementation using Solr.
 * 
 * @author Csaba
 */
@Singleton
class SolrSearchServiceImpl
    implements SearchService
{
    private static final Logger LOG = LoggerFactory.getLogger( SolrSearchServiceImpl.class );

    private SolrServer activeUsersServer;

    @Inject
    SolrSearchServiceImpl( @ServiceConfiguration( ServiceConfigurationType.SOLR ) Properties solrConfig )
    {
        initSolrServer( solrConfig );
    }

    @Override
    public List<UserDTO> findActiveUsersNotAssignedToProject( int projectId, String contains )
    {
        LOG.debug( "Find active users not assigned to project (ID = {}) and having in name: {}", projectId, contains );
        SolrQuery query = new SolrQuery();
        query.setQuery( "nameAndEmail:*" + contains + "*" );
        List<UserDTO> results = new ArrayList<UserDTO>();
        try
        {
            QueryResponse response = activeUsersServer.query( query );
            SolrDocumentList searchResults = response.getResults();
            LOG.debug( "Acvive users: {} -> {}", query, searchResults.size() );
            for ( SolrDocument searcResult : searchResults )
            {
                results.add( new UserDTO( getIntegerValue( searcResult, "id" ), getStringValue( searcResult,
                                                                                                "firstName" ),
                                          getStringValue( searcResult, "lastName" ), getStringValue( searcResult,
                                                                                                     "email" ) ) );
            }
        }
        catch ( SolrServerException e )
        {
            LOG.error( "Error finding active users not assigned to project (ID = {}) and having in name: {} ",
                       projectId, contains, e );
        }
        return results;
    }

    private static Integer getIntegerValue( SolrDocument document, String fieldName )
    {
        Integer result = null;
        Object object = document.getFieldValue( fieldName );
        if ( object != null )
        {
            if ( object instanceof Number )
            {
                result = ( (Number) object ).intValue();
            }
            else
            {
                result = Integer.valueOf( object.toString() );
            }
        }
        return result;
    }

    private static String getStringValue( SolrDocument document, String fieldName )
    {
        Integer result = null;
        Object object = document.getFieldValue( fieldName );
        return ( object == null ) ? null : object.toString();
    }

    protected void initSolrServer( Properties solrConfig )
    {
        String url = solrConfig.getProperty( "solr.url" );
        String separator = "/";
        if ( url.endsWith( "/" ) )
        {
            separator = "";
        }
        activeUsersServer = new HttpSolrServer( url + separator + "active_users" );
    }
}
