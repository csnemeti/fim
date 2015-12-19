/**
 * 
 */
package pfa.alliance.fim.jobs;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.jobs.guartz.Scheduled;
import pfa.alliance.fim.service.SolrConnectException;
import pfa.alliance.fim.service.SolrCore;
import pfa.alliance.fim.service.SolrManager;
import pfa.alliance.fim.service.SolrOperationFailedException;

/**
 * This class represents a Quartz job that is used for perform FULL indexing.
 * 
 * @author Csaba
 */
@Scheduled( cronExpression = "0 1 3 * * ?" )
public class UserFullImportJob
    implements Job
{
    private static final Logger LOG = LoggerFactory.getLogger( UserFullImportJob.class );
    private SolrManager solrManager;

    @Inject
    UserFullImportJob( SolrManager solrManager )
    {
        this.solrManager = solrManager;
    }

    @Override
    public void execute( JobExecutionContext context )
        throws JobExecutionException
    {
        LOG.info( "Full index Job started" );
        try
        {
            LOG.debug( "Starting Full index on users..." );
            solrManager.runIndex( SolrCore.USERS, true );
            LOG.info( "Users Full index command sent" );
        }
        catch ( SolrOperationFailedException e )
        {
            LOG.error( "Error running full import on users core", e );
        }
        catch ( SolrConnectException e )
        {
            LOG.error( "Error conneting to Solr for full import on users", e );
        }
        try
        {
            LOG.debug( "Starting Full index on active_users..." );
            solrManager.runIndex( SolrCore.ACTIVE_USERS, true );
            LOG.info( "ActiveUsers Full index command sent" );
        }
        catch ( SolrOperationFailedException e )
        {
            LOG.error( "Error running full import on active_users core", e );
        }
        catch ( SolrConnectException e )
        {
            LOG.error( "Error conneting to Solr for full import on active_users", e );
        }
    }

}
