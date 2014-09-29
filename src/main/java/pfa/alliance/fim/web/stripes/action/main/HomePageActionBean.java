package pfa.alliance.fim.web.stripes.action.main;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

@UrlBinding(value = "/user/dashboard")
public class HomePageActionBean extends BasePageActionBean {

  @DefaultHandler
  public Resolution goToHomePage() {
    return new ForwardResolution(FimPageURLs.DASBOARD_PAGE.getURL());
  }

}
