/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.issue;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.service.IssueManagerService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.ProjectSensibleActionBean;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This class is used for displaying all information about an issue.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/issue/show/{id}" )
@FimSecurity( )
public class ViewIssueActionBean
    extends BasePageActionBean
    implements ProjectSensibleActionBean
{
    private static final Logger LOG = LoggerFactory.getLogger( ViewIssueActionBean.class );

    /** Error code returned when issue is not found. */
    private static final int ISSUE_NOT_FOUND = 404;

    @Validate( required = true )
    private Long id;

    private Issue issue;

    private final IssueManagerService issueManagerService;

    /**
     * Called when instance of this class is created.
     * 
     * @param issueManagerService the {@link IssueManagerService} instance to be used
     */
    @Inject
    public ViewIssueActionBean( IssueManagerService issueManagerService )
    {
        this.issueManagerService = issueManagerService;
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        LOG.debug( "Show page..." );
        // load issue and the rest of dependencies
        loadIssue();
        // redirect to JSP
        if ( issue != null )
        {
            return new ForwardResolution( FimPageURLs.VIEW_ISSUE_JSP.getURL() );
        }
        else
        {
            // redirect to error page
            return new ErrorResolution( ISSUE_NOT_FOUND, "Issue with code (" + id + ") was not found" );
        }
    }

    @Override
    public Integer getProjectId()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTitle()
    {
        return issue.getTitle();
    }

    public Issue getIssue()
    {
        return issue;
    }

    private void loadIssue()
    {
        issue = issueManagerService.findById( id );
    }

    public void setId( Long id )
    {
        this.id = id;
    }

}
