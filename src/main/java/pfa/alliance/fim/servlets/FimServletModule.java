/**
 * 
 */
package pfa.alliance.fim.servlets;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import pfa.alliance.fim.service.ConfigurationService;
import pfa.alliance.fim.service.PersistenceService;
import pfa.alliance.fim.service.impl.ConfigurationServiceImpl;
import pfa.alliance.fim.service.impl.DummPersistenceService;

import com.google.inject.Scopes;
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

        // temporary placed here
        bind( ConfigurationService.class ).to( ConfigurationServiceImpl.class );
        bind( PersistenceService.class ).to( DummPersistenceService.class );
        
        
        // binding MBean Server if necessary in future
        bind( MBeanServer.class ).toInstance( MBeanServerFactory.createMBeanServer() );
        // bind configuration checker filter as singleton
        bind( SetupVerifyFilter.class ).in( Scopes.SINGLETON );
        filter( "/*" ).through( SetupVerifyFilter.class );
    }
}
