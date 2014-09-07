/**
 * 
 */
package pfa.alliance.fim.servlets;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.silvermindsoftware.sg.guice.GuiceInjectorFactory;

/**
 * Alternative Guice {@link Injector} initialization method that uses in relation with Stripes
 * 
 * @author Csaba
 */
public class FimGuiceInjectorFactory
    implements GuiceInjectorFactory
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( FimGuiceInjectorFactory.class );

    @Override
    public Injector getInjector( ServletContext aServletContext )
    {
        LOG.debug( "Init Injector..." );
        return Guice.createInjector(new FimServletModule());
    }

}
