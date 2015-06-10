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
        bind( MBeanServer.class ).toInstance( ManagementFactory.getPlatformMBeanServer() );
        bind( UserManagerMBean.class ).to( UserManager.class ).asEagerSingleton();
    }
}
