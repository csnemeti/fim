/**
 * 
 */
package pfa.alliance.fim.web.stripes.action;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.mock.MockServletContext;

import org.junit.BeforeClass;

import com.silvermindsoftware.sg.context.GuiceContextListener;

/**
 * This is the base class for {@link ActionBean} testing.
 * 
 * @author Csaba
 */
public abstract class AbstractActionBeanTest
{
    private static MockServletContext context;

    @BeforeClass
    public static void initClass()
        throws Exception
    {
        context = new MockServletContext( "test" );

        // init Mock objects
        ServiceModuleMock.initMocks();

        // add context listeners
        context.addInitParameter( "GuiceInjectorFactory.Class",
                                  "pfa.alliance.fim.web.stripes.action.FimGuiceInjectorFactoryMock" );
        context.addListener( new GuiceContextListener() );

        // Add the Stripes Filter
        Map<String, String> filterParams = new HashMap<String, String>();
        filterParams.put( "Configuration.Class", "com.silvermindsoftware.sg.config.GuiceRuntimeConfiguration" );
        filterParams.put( "ActionBeanContextFactory.Class",
                          "com.silvermindsoftware.sg.controller.GuiceActionBeanContextFactory" );
        filterParams.put( "ActionResolver.Class", "com.silvermindsoftware.sg.controller.GuiceActionResolver" );
        filterParams.put( "Extension.Packages",
                          "com.silvermindsoftware.sg.extension,pfa.alliance.fim.web.stripes.extensions" );
        filterParams.put( "ActionResolver.Packages",
                          "pfa.alliance.fim.web.stripes.action.user,pfa.alliance.fim.web.stripes.action.project" );
        filterParams.put( "Interceptor.Classes", "pfa.alliance.fim.web.stripes.interceptor.SecurityInterceptor" );
        filterParams.put( "LocalePicker.Class", "pfa.alliance.fim.web.stripes.StripesLocalePicker" );
        filterParams.put( "LocalePicker.Locales", "en:UTF-8,ro:UTF-8" );

        context.addFilter( StripesFilter.class, "StripesFilter", filterParams );

        // Add the Stripes Dispatcher
        context.setServlet( DispatcherServlet.class, "StripesDispatcher", null );
    }

    protected static MockServletContext getContext()
    {
        return context;
    }

}
