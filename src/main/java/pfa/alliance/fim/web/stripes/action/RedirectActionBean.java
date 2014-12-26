package pfa.alliance.fim.web.stripes.action;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import pfa.alliance.fim.web.common.FimPageURLs;

/**
 * Action bean designed to redirect requests to specific pages.
 * 
 * @author Balaceanu Sergiu-Denis
 */
@UrlBinding( value = "/redirect" )
public class RedirectActionBean
    extends BaseActionBean
{

    /**
     * Event for redirecting a request to the Dashboard page.
     * 
     * @return resolution for forward to User Dashboard JSP
     */
    public Resolution goToDashboard()
    {
        return new RedirectResolution( FimPageURLs.USER_DASBOARD_PAGE.getURL() );
    }

}
