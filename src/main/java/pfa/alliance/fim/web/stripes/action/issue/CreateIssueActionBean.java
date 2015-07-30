/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.issue;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for Creating an {@link Issue}.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/issue/create/{projectId}" )
@FimSecurity( )
public class CreateIssueActionBean
    extends BasePageActionBean
{
    private static final Logger LOG = LoggerFactory.getLogger( CreateIssueActionBean.class );

    /** The ID of the project where the issue must be created. */
    private Integer projectId;

    private final ProjectManagementService projectManagementService;

    /**
     * Called when instance of this class is created.
     * 
     * @param projectManagementService the {@link ProjectManagementService} instance to be used in this class
     */
    @Inject
    public CreateIssueActionBean( ProjectManagementService projectManagementService )
    {
        this.projectManagementService = projectManagementService;
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        // redirect to JSP
        return new ForwardResolution( FimPageURLs.CREATE_ISSUE_JSP.getURL() );
    }

    @Override
    public String getTitle()
    {
        return getMessage( "issue.createTitle" );
    }

    public Integer getProjectId()
    {
        return projectId;
    }

    public void setProjectId( Integer projectId )
    {
        this.projectId = projectId;
    }

}
