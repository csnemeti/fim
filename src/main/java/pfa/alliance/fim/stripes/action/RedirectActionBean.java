package pfa.alliance.fim.stripes.action;

import pfa.alliance.fim.common.FimPageURLs;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * Action bean designed to redirect requests to specific pages.
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class RedirectActionBean implements ActionBean {
  private ActionBeanContext context;

  @Override
  public void setContext(ActionBeanContext context) {
    this.context = context;
  }

  @Override
  public ActionBeanContext getContext() {
    return context;
  }

  /**
   * Event for redirecting a request to the Dashboard page.
   * @return
   */
  @DefaultHandler
  public Resolution goToDashboard(){
    return new ForwardResolution(FimPageURLs.DASBOARD_PAGE.getURL());
  }


}
