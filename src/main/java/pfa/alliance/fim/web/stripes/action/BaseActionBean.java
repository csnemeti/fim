package pfa.alliance.fim.web.stripes.action;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.LocalizableMessage;

/**
 * Base entity for action beans that implements trivial methods.
 * 
 * @author Balaceanu Sergiu-Denis
 */
public abstract class BaseActionBean
    implements ActionBean
{
    private ActionBeanContext context;

    @Override
    public void setContext( ActionBeanContext context )
    {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext()
    {
        return context;
    }

    /**
     * Gets the user session. A new session will be created if there's none.
     * 
     * @return the user session
     */
    protected HttpSession getSession()
    {
        return context.getRequest().getSession( true );
    }

    /**
     * Gets a localization messages.
     * 
     * @param key the message key
     * @param parameters parameters for the message
     * @return the message to be displayed
     */
    protected String getMessage( String key, Object... parameters )
    {
        return new LocalizableMessage( key, parameters ).getMessage( context.getLocale() );
    }
}
