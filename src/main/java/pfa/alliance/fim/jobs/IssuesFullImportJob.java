/**
 * 
 */
package pfa.alliance.fim.jobs;

import javax.inject.Inject;

import pfa.alliance.fim.jobs.guartz.Scheduled;
import pfa.alliance.fim.service.SolrCore;
import pfa.alliance.fim.service.SolrManager;

/**
 * This class is used for performing full import on Project records.
 * 
 * @author Csaba
 */
@Scheduled( cronExpression = "0 1 4 * * ?" )
public class IssuesFullImportJob
    extends AbstractIndexingJob
{
    @Inject
    IssuesFullImportJob( SolrManager solrManager )
    {
        super( solrManager, SolrCore.PROJECTS, false );
    }
}
