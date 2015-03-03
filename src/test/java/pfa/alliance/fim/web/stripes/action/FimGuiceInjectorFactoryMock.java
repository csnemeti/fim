/**
 * 
 */
package pfa.alliance.fim.web.stripes.action;

import javax.servlet.ServletContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.silvermindsoftware.sg.guice.GuiceInjectorFactory;

/**
 * This class is used for crating customized Guice {@link Injector} to be used during unit testing.
 * 
 * @author Csaba
 */
public class FimGuiceInjectorFactoryMock
    implements GuiceInjectorFactory
{

    @Override
    public Injector getInjector( ServletContext aServletContext )
    {
        return Guice.createInjector( new ServiceModuleMock() );
    }

}
