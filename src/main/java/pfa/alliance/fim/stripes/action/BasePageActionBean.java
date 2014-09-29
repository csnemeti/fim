package pfa.alliance.fim.stripes.action;

import pfa.alliance.fim.util.ManifestUtils;

/**
 * ActionBean designed for the FIM pages.
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class BasePageActionBean extends BaseActionBean {
	
  public String getVersion(){
    return ManifestUtils.getImplementationVersion();
  }

}
