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
@Scheduled( cronExpression = "0 35 3 * * ?" )
public class ProjectFullImportJob
    extends AbstractIndexingJob
{
    @Inject
    ProjectFullImportJob( SolrManager solrManager )
    {
        super( solrManager, SolrCore.PROJECTS, false );
    }
}
