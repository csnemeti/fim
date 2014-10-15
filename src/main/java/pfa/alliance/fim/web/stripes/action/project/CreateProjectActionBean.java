package pfa.alliance.fim.web.stripes.action.project;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

@UrlBinding(value = "/project/create")
public class CreateProjectActionBean extends BasePageActionBean {
  private static final Logger LOG = LoggerFactory.getLogger(CreateProjectActionBean.class);
  
  @DefaultHandler
  public Resolution goToHomePage() {
    LOG.debug("Show page...");
    return new ForwardResolution(FimPageURLs.CREATE_PROJECT_JSP.getURL());
  }
}
