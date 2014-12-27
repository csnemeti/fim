package pfa.alliance.fim.servlets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import pfa.alliance.fim.service.Configuration;
import pfa.alliance.fim.servlets.SetupVerifyFilter;

/**
 * Test if the {@link SetupVerifyFilter} is redirecting the request properly .
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class SetupVerifyFilterTest {

  @Test
  public void testProperlyConfigured() throws IOException {
    ServletRequest servletRequest = mock(ServletRequest.class);
    HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    FilterConfig filterConfig = mock(FilterConfig.class);

    // mock configuration as the application is properly configured
    Configuration configuration = mock(Configuration.class);
    when(configuration.isConfigurationCompleted()).thenReturn(true);

    SetupVerifyFilter setupVerifyFilter = new SetupVerifyFilter(configuration);
    try {
      setupVerifyFilter.init(filterConfig);
    } catch (ServletException e) {
      assertTrue("ServletException while init filter !", false);
    }

    try {
      setupVerifyFilter.doFilter(servletRequest, httpServletResponse, filterChain);
    } catch (IOException e) {
      assertTrue("IOException while doFilter !", false);
    } catch (ServletException e) {
      assertTrue("ServletException while doFilter !", false);
    }

    setupVerifyFilter.destroy();

    // verify if the response was not redirected to the wizard
    verify(httpServletResponse, never()).sendRedirect(SetupVerifyFilter.WIZZARD_PATH);
    verify(configuration).isConfigurationCompleted();
  }

  @Test
  public void testApplicationIsNotConfigured() throws IOException {
    ServletRequest servletRequest = mock(ServletRequest.class);
    HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    FilterConfig filterConfig = mock(FilterConfig.class);

    // mock configuration as the application is not properly configured
    Configuration configuration = mock(Configuration.class);
    when(configuration.isConfigurationCompleted()).thenReturn(false);

    SetupVerifyFilter setupVerifyFilter = new SetupVerifyFilter(configuration);
    try {
      setupVerifyFilter.init(filterConfig);
    } catch (ServletException e) {
      assertTrue("ServletException while init filter !", false);
    }

    try {
      setupVerifyFilter.doFilter(servletRequest, httpServletResponse, filterChain);
    } catch (IOException e) {
      assertTrue("IOException while doFilter !", false);
    } catch (ServletException e) {
      assertTrue("ServletException while doFilter !", false);
    }

    setupVerifyFilter.destroy();

    // verify if the response was redirected to the wizard
    verify(httpServletResponse).sendRedirect(SetupVerifyFilter.WIZZARD_PATH);
    verify(configuration).isConfigurationCompleted();
  }
}
