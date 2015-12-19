/**
 * 
 */
package pfa.alliance.fim.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.SolrConnectException;
import pfa.alliance.fim.service.SolrCore;
import pfa.alliance.fim.service.SolrManager;
import pfa.alliance.fim.service.SolrOperationFailedException;

/**
 * This is an base class that is used for indexing data (delta or full) on a core specified in constructor.
 * 
 * @author Csaba
 */
abstract class AbstractIndexingJob
    implements Job
{
    private static final Logger LOG = LoggerFactory.getLogger( AbstractIndexingJob.class );

    private final SolrManager solrManager;

    private final SolrCore core;

    private final boolean full;

    /**
     * Called when index of this class is created.
     * 
     * @param solrManager the {@link SolrManager} instance
     * @param core the core to index
     * @param full if <code>true</code> full index will be made, otherwise is a delta index
     */
    protected AbstractIndexingJob( final SolrManager solrManager, final SolrCore core, final boolean full )
    {
        this.solrManager = solrManager;
        this.core = core;
        this.full = full;
    }

    @Override
    public void execute( JobExecutionContext context )
        throws JobExecutionException
    {
        LOG.debug( "Starting an index (type is full {} ) on Sorl core", full, core );
        try
        {
            solrManager.runIndex( core, full );
        }
        catch ( SolrOperationFailedException | SolrConnectException e )
        {
            LOG.error( "Could not send index command for {}. Index is full = {}", core, full, e );
        }
    }
}
