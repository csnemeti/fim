package pfa.alliance.fim.stripes.action;

import pfa.alliance.fim.util.ManifestUtils;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * ActionBean which is responsible for the footer data and links .
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class FooterActionBean implements ActionBean {
  private ActionBeanContext context;

  @Override
  public void setContext(ActionBeanContext context) {
    this.context = context;
  }

  @Override
  public ActionBeanContext getContext() {
    return context;
  }
  
  public String getVersion(){
    return ManifestUtils.getImplementationVersion();
  }
  
  @DefaultHandler
  public Resolution currentVersion(){
    return new ForwardResolution("/index.jsp");
  }

}
