package pfa.alliance.fim.web.stripes.action.main;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.AuthenticatedUserDTO;
import pfa.alliance.fim.web.security.SecurityUtil;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * ActionBean for the HomePage(UserDashBoard page ) .
 * 
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
@UrlBinding(value = "/user/dashboard")
public class HomePageActionBean extends BasePageActionBean {
  @DefaultHandler
  public Resolution goToHomePage() {
    //if no user is logged on the session we redirect it to LoginPage 
    if (getLoggedUser() != null) {
      return new ForwardResolution(FimPageURLs.DASBOARD_PAGE.getURL());
    } else {
      return new ForwardResolution(FimPageURLs.LOGIN_PAGE.getURL());
    }
  }

  /**
   * Returns the current logged user username .
   * 
   * @return
   */
  public String getUsername() {
    return getLoggedUser().getUsername();
  }

  /**
   * Returns the current logged user email .
   * 
   * @return
   */
  public String getUserEmail() {
    return getLoggedUser().getEmail();
  }

  /**
   * Returns the last login date of the current user .
   * 
   * @return
   */
  public String getLastLoginTime() {
    return DateUtils.formatDate(getLoggedUser().getLastLogin(), DateUtils.DATE_FORMAT_DAY_FIRST);
  }

  /**
   * TODO:Remove mocked value !!
   * 
   * @return
   */
  public String getSessionAge() {
    return "2 days";
  }

  private AuthenticatedUserDTO getLoggedUser() {
    return SecurityUtil.getUserFromSession(getContext().getRequest().getSession());
  }
}
