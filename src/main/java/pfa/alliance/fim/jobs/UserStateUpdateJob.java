package pfa.alliance.fim.jobs;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.jobs.guartz.Scheduled;
import pfa.alliance.fim.service.UserManagerService;



@Scheduled( cronExpression = "0 15 4 * * ?" )
public class UserStateUpdateJob implements Job {
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserStateUpdateJob.class );

    /** The instance used in this class. */
    private final UserManagerService userManagerService;

    /**
     * Called when instance of this class is created.
     * 
     * @param userManagerService the {@link UserManagerService} instance to be used in this class
     */
    @Inject
    UserStateUpdateJob( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
    }

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.debug( "Starting job execution...." );
        userManagerService.deleteExpiredOneTimeLinks();
        userManagerService.markDeleteNotActivatedUserAccounts();
        LOG.info( "Job execution completed." );
	}

}
