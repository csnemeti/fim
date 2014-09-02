package pfa.alliance.fim.filters;

import java.io.IOException;

import javax.inject.Inject;
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

/**
 * Filter that verify if the FIM Application is properly configured.
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
@WebFilter("/SetupVerifyFilter")
public class SetupVerifyFilter implements Filter {
  private static final Logger LOG = LoggerFactory.getLogger(SetupVerifyFilter.class);
  protected static final String WIZZARD_PATH = "/setup";

  private Configuration configuration;

  @Inject
  public SetupVerifyFilter(Configuration configuration) {
    this.configuration = configuration;
  }
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    LOG.debug("SetupVerifyFilter initialized ...");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
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
