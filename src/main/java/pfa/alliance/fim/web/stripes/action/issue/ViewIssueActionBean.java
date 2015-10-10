/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.issue;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;
import net.sourceforge.stripes.validation.Validate;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.issue.IssueBaseDTO;
import pfa.alliance.fim.dto.issue.IssueDTO;
import pfa.alliance.fim.dto.issue.IssueDependencyDTO;
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

    private List<IssueBaseDTO> ancestors;

    private List<IssueDTO> children;

    private List<IssueDependencyDTO> dependencies;

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
        if ( issue != null )
        {
            ancestors = issueManagerService.getAncestorsFor( id, true );
            Locale locale = getLocale();
            children = issueManagerService.getChildernFor( id, locale );
            dependencies = issueManagerService.getDependenciesFor( id, locale );
        }
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
            name = getMessage( "unassigned" );
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
            name = getMessage( "unknown" );
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
            Period period = DateUtils.buildTimePeriod( created, new Date() );
            PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
            if ( period.getYears() > 0 )
            {
                builder.appendYears().appendSuffix( " year", " years" ).appendSeparator( " and " ).printZeroRarelyLast();
            }
            if ( period.getMonths() > 0 )
            {
                builder.appendMonths().appendSuffix( " month", " months" ).appendSeparator( " and " ).printZeroRarelyLast();
            }
            if ( period.getDays() > 0 )
            {
                builder.appendDays().appendSuffix( " day", " days" ).appendSeparator( " and " ).printZeroRarelyLast();
            }
            builder.appendHours().appendSuffix( " hour", " hours" ).appendSeparator( " and " ).printZeroRarelyLast();
            builder.appendMinutes().appendSuffix( " minute", " minutes" ).printZeroRarelyLast();

            PeriodFormatter dateFormat = builder.toFormatter();
            name = period.toString( dateFormat );
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

    public String getLevel1Link()
    {
        return getLevelUrl( 0 );
    }

    public String getLevel1Title()
    {
        return getLevelTitle( 0 );
    }

    public String getLevel2Link()
    {
        return getLevelUrl( 1 );
    }

    public String getLevel2Title()
    {
        return getLevelTitle( 1 );
    }

    public String getLevel2Show()
    {
        return getLevelShowString( 1 );
    }

    public String getLevel3Link()
    {
        return getLevelUrl( 2 );
    }

    public String getLevel3Title()
    {
        return getLevelTitle( 2 );
    }

    public String getLevel3Show()
    {
        return getLevelShowString( 2 );
    }

    public String getLevel4Link()
    {
        return getLevelUrl( 3 );
    }

    public String getLevel4Title()
    {
        return getLevelTitle( 3 );
    }

    public String getLevel4Show()
    {
        return getLevelShowString( 3 );
    }

    public String getLevel5Link()
    {
        return getLevelUrl( 4 );
    }

    public String getLevel5Title()
    {
        return getLevelTitle( 4 );
    }

    public String getLevel5Show()
    {
        return getLevelShowString( 4 );
    }

    public String getLevel6Link()
    {
        return getLevelUrl( 5 );
    }

    public String getLevel6Title()
    {
        return getLevelTitle( 5 );
    }

    public String getLevel6Show()
    {
        return getLevelShowString( 5 );
    }

    public String getLevel7Link()
    {
        return getLevelUrl( 6 );
    }

    public String getLevel7Title()
    {
        return getLevelTitle( 6 );
    }

    public String getLevel7Show()
    {
        return getLevelShowString( 6 );
    }

    private String getLevelShowString( final int level )
    {
        String style = "display: none";
        if ( level < ancestors.size() )
        {
            style = "display: block";
        }
        return style;
    }

    private String getLevelTitle( final int level )
    {
        String title = "";
        if ( level < ancestors.size() )
        {
            IssueBaseDTO ancestor = ancestors.get( level );
            title = ancestor.getCode() + ": " + ancestor.getTitle();
        }
        return title;
    }

    private String getLevelUrl( final int level )
    {
        String url = "";
        if ( level < ancestors.size() )
        {
            IssueBaseDTO ancestor = ancestors.get( level );
            url = ancestor.getUrl();
        }
        return url;
    }

    public List<IssueDTO> getChildren()
    {
        return children;
    }

    public List<IssueDependencyDTO> getDependencies()
    {
        return dependencies;
    }

}
