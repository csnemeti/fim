package pfa.alliance.fim.web.stripes.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * Base entity for action beans that implements trivial methods.
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class BaseActionBean implements ActionBean {
	private ActionBeanContext context;

	@Override
	public void setContext(ActionBeanContext context) {
		this.context = context;
	}

	@Override
	public ActionBeanContext getContext() {
		return context;
	}
}
