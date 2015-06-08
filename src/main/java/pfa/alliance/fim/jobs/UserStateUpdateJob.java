package pfa.alliance.fim.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import pfa.alliance.fim.jobs.guartz.Scheduled;



@Scheduled( cronExpression = "0 15 4 * * ?" )
public class UserStateUpdateJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Working job");
	}

}
