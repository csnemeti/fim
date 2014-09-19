package pfa.alliance.fim.stripes.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import pfa.alliance.fim.common.FimPageURLs;
import pfa.alliance.fim.util.ManifestUtils;

/**
 * ActionBean which is responsible for the footer data and links .
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class FooterActionBean extends BaseActionBean {
	
  public String getVersion(){
    return ManifestUtils.getImplementationVersion();
  }
  
  @DefaultHandler
  public Resolution currentVersion(){
    return new ForwardResolution(FimPageURLs.MAIN_PAGE.getURL());
  }

}
