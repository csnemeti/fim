package pfa.alliance.fim.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import pfa.alliance.fim.jobs.guartz.Scheduled;



@Scheduled(cronExpression = "0/2 * * * * ?")
public class CheckRegisterRequestJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Working job");
	}

}
