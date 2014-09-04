/**
 * 
 */
package pfa.alliance.fim.servlets;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import pfa.alliance.fim.service.Configuration;
import pfa.alliance.fim.service.ConfigurationImpl;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;

/**
 * This class is used for define the mappings for Fim servlets and filters.
 * 
 * @author Csaba
 *
 */
class FimServletModule extends ServletModule{
	@Override
	protected void configureServlets() {
		super.configureServlets();
		
		// binding MBean Server if necessary in future
		bind(MBeanServer.class).toInstance(MBeanServerFactory.createMBeanServer());
		//bind configuration
		bind(Configuration.class).to(ConfigurationImpl.class);
		//bind configuration checker filter as singleton
		bind(SetupVerifyFilter.class).in(Scopes.SINGLETON);
		filter("/*").through(SetupVerifyFilter.class);
	}
}
