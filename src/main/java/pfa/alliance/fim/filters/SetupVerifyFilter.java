package pfa.alliance.fim.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.Configuration;
import pfa.alliance.fim.service.ConfigurationImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

@WebFilter("/SetupVerifyFilter")
public class SetupVerifyFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(SetupVerifyFilter.class);
	private static final String WIZZARD_PATH = "/setup";
	
	private Configuration configuration;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.debug("SetupVerifyFilter initialized ...");
		// init configuration
		configuration = new ConfigurationImpl();
		
		Injector injector = Guice.createInjector(new AbstractModule() {
			
			@Override
			protected void configure() {
				//binding configuration
				bind(Configuration.class).to(ConfigurationImpl.class);
			}
		});
		injector.injectMembers(configuration);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("doFilter SetupVerifyFilter");
		
		// if the application configuration is not completed , we redirect it to
		// the wizzard setup
		if (!configuration.isConfigurationCompleted()) {
			LOG.debug("Application is not configured !");
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(WIZZARD_PATH);
			return;
		}
		LOG.debug("Application is properly configured !");
		
	}

	@Override
	public void destroy() {
		LOG.debug("SetupVerifyFilter destroyed ...");
		// add code to release any resource
	}

}
