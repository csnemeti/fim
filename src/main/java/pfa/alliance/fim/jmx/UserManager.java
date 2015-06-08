/**
 * 
 */
package pfa.alliance.fim.jmx;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.UserManagerService;

/**
 * This is the implementation of {@link UserManagerMBean}.
 * 
 * @author Csaba
 */
@Singleton
class UserManager
    implements UserManagerMBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( UserManager.class );

    /** The Object Name of this MBean. */
    private static final String NAME = "pfaalliance.fim:name=user,type=manager";

    /** The instance of {@link UserManagerService} to use. */
    private UserManagerService userManagerService;

    /**
     * Called when instance of this class is created.
     * 
     * @param userManagerService the instance of {@link UserManagerService} to use
     */
    @Inject
    UserManager( UserManagerService userManagerService )
    {
        super();
        this.userManagerService = userManagerService;
    }

    @Override
    public int deleteExpiredOneTimeLinks()
    {
        try
        {
            LOG.debug( "Deleting expired one-time links..." );
            return userManagerService.deleteExpiredOneTimeLinks();
        }
        catch ( RuntimeException e )
        {
            LOG.error( "Error while calling service", e );
            throw e;
        }
    }

    /**
     * This method is used to inject an MBeanServer instance that will be used to register this MBean
     */
    @Inject
    public void register( MBeanServer server )
    {
        try
        {
            ObjectInstance instance = server.registerMBean( this, new ObjectName( NAME ) );
            LOG.info( "MBean {} registered", instance );
        }
        catch ( JMException e )
        {
            LOG.error( "Could not registed MBean: {}", NAME, e );
        }
    }

}
