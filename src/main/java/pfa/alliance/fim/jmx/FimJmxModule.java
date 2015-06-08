/**
 * 
 */
package pfa.alliance.fim.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import com.google.inject.AbstractModule;

/**
 * Module used for loading JMX related
 * 
 * @author Csaba
 */
public class FimJmxModule
    extends AbstractModule
{

    @Override
    protected void configure()
    {
        // binding MBean Server if necessary in future
        //bind( MBeanServer.class ).toInstance( MBeanServerFactory.createMBeanServer() );

        bind( MBeanServer.class ).toInstance( getMbeanServer() );
        bind( UserManagerMBean.class ).to( UserManager.class ).asEagerSingleton();
    }

    private static MBeanServer getMbeanServer()
    {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        return server;
    }

}
