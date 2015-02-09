package pfa.alliance.fim.servlets;

import pfa.alliance.fim.jobs.CheckRegisterRequestJob;
import pfa.alliance.fim.jobs.guartz.QuartzModule;

public class FimJobsInitializerModule extends QuartzModule{

	@Override
	protected void schedule() {
		
		scheduleJob(CheckRegisterRequestJob.class);
		
	}

}
