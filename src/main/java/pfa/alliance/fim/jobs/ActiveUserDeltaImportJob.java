/**
 * 
 */
package pfa.alliance.fim.jobs;

import javax.inject.Inject;
import javax.inject.Singleton;

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
 * This class represents a JOB for automatic DELTA indexing the active users.
 * 
 * @author Csaba
 */
@Singleton
@Scheduled( cronExpression = "0/5 * * * * ?" )
public class ActiveUserDeltaImportJob
    implements Job
{
    private static final Logger LOG = LoggerFactory.getLogger( ActiveUserDeltaImportJob.class );

    private SolrManager solrManager;

    @Inject
    ActiveUserDeltaImportJob( SolrManager solrManager )
    {
        this.solrManager = solrManager;
    }

    @Override
    public void execute( JobExecutionContext context )
        throws JobExecutionException
    {
        try
        {
            solrManager.runIndex( SolrCore.ACTIVE_USERS, false );
        }
        catch ( SolrOperationFailedException | SolrConnectException e )
        {
            LOG.error( "Could not send DELTA index command for Active Users", e );
        }
    }

}
