package pfa.alliance.fim.web.servlets;

import pfa.alliance.fim.jobs.ActiveUserDeltaImportJob;
import pfa.alliance.fim.jobs.CheckRegisterRequestJob;
import pfa.alliance.fim.jobs.UserDeltaImportJob;
import pfa.alliance.fim.jobs.UserFullImportJob;
import pfa.alliance.fim.jobs.guartz.QuartzModule;

public class FimJobsInitializerModule
    extends QuartzModule
{

    @Override
    protected void schedule()
    {
        scheduleJob( CheckRegisterRequestJob.class );
        scheduleJob( UserFullImportJob.class );
        scheduleJob( ActiveUserDeltaImportJob.class );
        scheduleJob( UserDeltaImportJob.class );
    }

}
