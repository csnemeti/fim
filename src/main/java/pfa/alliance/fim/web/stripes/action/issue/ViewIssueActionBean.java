/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.issue;

import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;
import net.sourceforge.stripes.validation.Validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.IssueManagerService;
import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.ProjectSensibleActionBean;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.project.ProjectDashboardActionBean;

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

    public String getAssignedTo()
    {
        String name = null;
        User user = issue.getAssigned();
        if ( user == null )
        {
            name = "Unassigned";
        }
        else
        {
            name = user.getName();
        }
        return name;
    }

    public String getCreatedBy()
    {
        String name = null;
        User user = issue.getReporter();
        if ( user == null )
        {
            name = "Unknown";
        }
        else
        {
            name = user.getName();
        }
        return name;
    }

    public String getCreatedAt()
    {
        String name = "&nbsp;";
        Timestamp created = issue.getCreatedAt();
        if ( created != null )
        {
            name = DateUtils.formatDate( created, DateUtils.DATETIME_FORMAT_DAY_FIRST );
        }
        return name;
    }

    public String getCreatedAtSince()
    {
        String name = "";
        Timestamp created = issue.getCreatedAt();
        if ( created != null )
        {
            name = DateUtils.getTimeInterval( created, new Date() );
        }
        return name;
    }

    public String getProjectLink()
    {
        UrlBuilder builder = new UrlBuilder( getContext().getLocale(), ProjectDashboardActionBean.class, true );
        builder.addParameter( "code", issue.getProject().getCode() );
        String url = builder.toString();
        String contextPath = getContext().getServletContext().getContextPath();
        if ( contextPath.equals( "/" ) )
        {
            contextPath = null;
        }
        if ( contextPath != null )
        {
            url = contextPath + url;
        }
        return url;
    }
}
