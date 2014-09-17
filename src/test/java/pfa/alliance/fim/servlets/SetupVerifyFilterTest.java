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
import org.mockito.Mockito;

import pfa.alliance.fim.service.ConfigurationService;
import pfa.alliance.fim.service.PersistenceService;
import pfa.alliance.fim.servlets.SetupVerifyFilter;

/**
 * Test if the {@link SetupVerifyFilter} is redirecting the request properly .
 * 
 * @author Balaceanu Sergiu-Denis
 */
public class SetupVerifyFilterTest
{
  
    @Test
    public void testProperlyConfigured()
        throws IOException
    {
        ServletRequest servletRequest = mock( ServletRequest.class );
        HttpServletResponse httpServletResponse = mock( HttpServletResponse.class );
        FilterChain filterChain = mock( FilterChain.class );
        FilterConfig filterConfig = mock( FilterConfig.class );

        // mock configuration as the application is properly configured
        ConfigurationService configurationService = mock( ConfigurationService.class );
        when( configurationService.isConfigurationCompleted() ).thenReturn( true );

        PersistenceService persistenceService = mock( PersistenceService.class );

        SetupVerifyFilter setupVerifyFilter = new SetupVerifyFilter( configurationService, persistenceService );
        try
        {
            setupVerifyFilter.init( filterConfig );
        }
        catch ( ServletException e )
        {
            assertTrue( "ServletException while init filter !", false );
        }

        try
        {
            setupVerifyFilter.doFilter( servletRequest, httpServletResponse, filterChain );
        }
        catch ( IOException e )
        {
            assertTrue( "IOException while doFilter !", false );
        }
        catch ( ServletException e )
        {
            assertTrue( "ServletException while doFilter !", false );
        }

        setupVerifyFilter.destroy();

        // verify if the response was not redirected to the wizard
        verify( httpServletResponse, never() ).sendRedirect( pfa.alliance.fim.common.FimPageURLs.WIZZARD_PAGE.getURL() );
        verify( configurationService ).isConfigurationCompleted();
        verify( persistenceService, Mockito.times( 1 ) ).startPersistence();
        Mockito.verifyNoMoreInteractions( configurationService, persistenceService );
    }

    @Test
    public void testApplicationIsNotConfigured()
        throws IOException
    {
        ServletRequest servletRequest = mock( ServletRequest.class );
        HttpServletResponse httpServletResponse = mock( HttpServletResponse.class );
        FilterChain filterChain = mock( FilterChain.class );
        FilterConfig filterConfig = mock( FilterConfig.class );

        // mock configuration as the application is not properly configured
        ConfigurationService configurationService = mock( ConfigurationService.class );
        when( configurationService.isConfigurationCompleted() ).thenReturn( false );

        PersistenceService persistenceService = mock( PersistenceService.class );

        SetupVerifyFilter setupVerifyFilter = new SetupVerifyFilter( configurationService, persistenceService );
        try
        {
            setupVerifyFilter.init( filterConfig );
        }
        catch ( ServletException e )
        {
            assertTrue( "ServletException while init filter !", false );
        }

        try
        {
            setupVerifyFilter.doFilter( servletRequest, httpServletResponse, filterChain );
        }
        catch ( IOException e )
        {
            assertTrue( "IOException while doFilter !", false );
        }
        catch ( ServletException e )
        {
            assertTrue( "ServletException while doFilter !", false );
        }

        setupVerifyFilter.destroy();

        // verify if the response was redirected to the wizard
        verify( httpServletResponse ).sendRedirect( pfa.alliance.fim.common.FimPageURLs.WIZZARD_PAGE.getURL() );
        verify( configurationService ).isConfigurationCompleted();
        Mockito.verifyNoMoreInteractions( configurationService, persistenceService );
    }
}
