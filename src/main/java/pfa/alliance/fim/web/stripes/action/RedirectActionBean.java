package pfa.alliance.fim.web.stripes.action;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import pfa.alliance.fim.web.common.FimPageURLs;

/**
 * Action bean designed to redirect requests to specific pages.
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
@UrlBinding(value="/redirect")
public class RedirectActionBean extends BaseActionBean {
  
  /**
   * Event for redirecting a request to the Dashboard page.
   * @return
   */
  public Resolution goToDashboard(){
    return new ForwardResolution(FimPageURLs.USER_DASBOARD_JSP.getURL());
  }


}
