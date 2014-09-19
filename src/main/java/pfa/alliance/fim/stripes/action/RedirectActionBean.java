package pfa.alliance.fim.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import pfa.alliance.fim.common.FimPageURLs;

/**
 * Action bean designed to redirect requests to specific pages.
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class RedirectActionBean extends BaseActionBean {
  /**
   * Event for redirecting a request to the Dashboard page.
   * @return
   */
  @DefaultHandler
  public Resolution goToDashboard(){
    return new ForwardResolution(FimPageURLs.DASBOARD_PAGE.getURL());
  }


}
