/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.project;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * Project search action bean.
 * 
 * @author Csaba
 */
@UrlBinding( "/project/search" )
@FimSecurity( )
public class SearchProjectsActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( SearchProjectsActionBean.class );

    /** The instance of {@link ProjectManagementService} to be used. */
    private ProjectManagementService projectManagementService;

    @Inject
    public SearchProjectsActionBean( ProjectManagementService projectManagementService )
    {
        this.projectManagementService = projectManagementService;
        LOG.debug( "New instance created..." );
    }

    @DefaultHandler
    public Resolution view()
    {
        // LOG.debug( "Show search page: {}", userSearch );
        return new ForwardResolution( FimPageURLs.PROJECT_SEARCH_JSP.getURL() );
    }

}
