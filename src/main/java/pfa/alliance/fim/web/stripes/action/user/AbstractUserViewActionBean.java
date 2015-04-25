/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.util.UrlBuilder;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dto.UserAssignedProjectDTO;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserLogin;
import pfa.alliance.fim.service.UserManagerService;
import pfa.alliance.fim.util.DateUtils;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.project.ProjectDashboardActionBean;

/**
 * This class is used for base class in user profile view.
 * 
 * @author Csaba
 */
public abstract class AbstractUserViewActionBean
    extends BasePageActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( AbstractUserViewActionBean.class );

    /** The instance of {@link UserManagerService} to be used. */
    private final UserManagerService userManagerService;

    private User user;

    protected AbstractUserViewActionBean( UserManagerService userManagerService )
    {
        this.userManagerService = userManagerService;
    }

    @DefaultHandler
    public Resolution goToHomePage()
    {
        int userId = getUserId();
        LOG.debug( "Getting data for User ID = {}", userId );
        user = userManagerService.findById( userId, isShowLastLoginCard(), isShowProjectAssignments() );
        // TODO handle the case that ID is not valid
        return new ForwardResolution( FimPageURLs.USER_DASBOARD_JSP.getURL() );
    }

    /**
     * Gets the ID of the user we should display.
     * 
     * @return the ID of user to display
     */
    protected abstract int getUserId();

    /**
     * Gets the flag value that decides if last login and session age should be displayed.
     * 
     * @return true if the information should be displayed
     */
    public abstract boolean isShowLastLoginAndSessionAge();

    /**
     * Gets the flag value that decides if project assignments should be displayed or not.
     * 
     * @return true if the information should be displayed
     */
    public abstract boolean isShowProjectAssignments();

    /**
     * Gets the flag value that decides if last logins card should be displayed.
     * 
     * @return true if the information should be displayed. Default implementation returns false
     */
    public boolean isShowLastLoginCard()
    {
        return false;
    }

    public User getUser()
    {
        return user;
    }

    public String getStateName()
    {
        return getEnumMessage( user.getStatus() );
    }

    public String getStateImage()
    {
        return "/images/user-state/" + user.getStatus().name().toLowerCase() + ".png";
    }

    public String getDefaultRoleName()
    {
        return getEnumMessage( user.getDefaultRole() );
    }

    public Collection<String> getLastLogins()
    {
        // we order the logins in descending order
        SortedSet<Timestamp> logins = new TreeSet<>( new ReverseComparator() );
        for ( UserLogin login : user.getLogins() )
        {
            logins.add( login.getCreatedAt() );
        }
        // we create the formated response
        List<String> formatedLogins = new ArrayList<>();
        for ( Timestamp login : logins )
        {
            formatedLogins.add( DateUtils.formatDate( login, DateUtils.DATETIME_FORMAT_DAY_FIRST ) );
        }
        return formatedLogins;
    }

    public int getAssignedProjectsNumber()
    {
        return user.getUserProjectRelation().size();
    }

    public Collection<UserAssignedProjectDTO> getAssignedProjects()
    {
        String contextPath = getContext().getServletContext().getContextPath();
        if ( contextPath.equals( "/" ) )
        {
            contextPath = null;
        }

        List<UserAssignedProjectDTO> assignments = new ArrayList<>();
        for ( UserProjectRelation relation : user.getUserProjectRelation() )
        {
            UserAssignedProjectDTO dto = new UserAssignedProjectDTO();
            dto.setRole( relation.getUserRole() );
            dto.setRoleName( getEnumMessage( relation.getUserRole() ) );

            Project project = relation.getProject();
            dto.setProjectId( project.getId() );
            dto.setCode( project.getCode() );
            dto.setName( project.getName() );
            dto.setState( project.getState() );
            dto.setStateName( getEnumMessage( project.getState() ) );

            UrlBuilder builder = new UrlBuilder( getContext().getLocale(), ProjectDashboardActionBean.class, true );
            builder.addParameter( "code", dto.getCode() );
            String url = builder.toString();
            if ( contextPath != null )
            {
                url = contextPath + url;
            }
            StringBuilder sb = new StringBuilder();
            sb.append( "<a href='" ).append( url ).append( "' title='" ).append( getMessage( "action.view" ) ).append( "'><i class='fa fa-eye'></i></a>" );
            dto.setActions( sb.toString() );

            assignments.add( dto );
        }
        return assignments;
    }
}
