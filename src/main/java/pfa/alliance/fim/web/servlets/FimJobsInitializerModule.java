package pfa.alliance.fim.web.servlets;

import pfa.alliance.fim.jobs.UserStateUpdateJob;
import pfa.alliance.fim.jobs.guartz.QuartzModule;

public class FimJobsInitializerModule extends QuartzModule{

	@Override
	protected void schedule() {
		
		scheduleJob(UserStateUpdateJob.class);
		
	}

}
