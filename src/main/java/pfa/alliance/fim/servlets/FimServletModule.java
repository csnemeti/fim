/**
 * 
 */
package pfa.alliance.fim.servlets;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import com.google.inject.servlet.ServletModule;

/**
 * This class is used for define the mappings for Fim servlets and filters.
 * 
 * @author Csaba
 */
class FimServletModule
    extends ServletModule
{
    @Override
    protected void configureServlets()
    {
        super.configureServlets();
        
        // binding MBean Server if necessary in future
        bind( MBeanServer.class ).toInstance( MBeanServerFactory.createMBeanServer() );
        // bind configuration checker filter as singleton
        filter( "/*" ).through( SetupVerifyFilter.class );
    }
}
