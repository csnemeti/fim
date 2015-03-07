package pfa.alliance.fim.web.stripes.action.project;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

@UrlBinding( value = "/project/edit/users" )
public class EditProjectUsersActionBean
    extends BasePageActionBean
{
    private static final Logger LOG = LoggerFactory.getLogger( EditProjectUsersActionBean.class );

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        return new ForwardResolution( FimPageURLs.EDIT_PROJECT_USERS_JSP.getURL() );
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.project.edit" );
    }
}
