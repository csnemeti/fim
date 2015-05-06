/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.project;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;

/**
 * This is the base class for editing a specific project.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/project/edit/{code}/{focus}" )
@FimSecurity
public class EditProjectActionBean
    extends BasePageActionBean

{
    private static final Logger LOG = LoggerFactory.getLogger( EditProjectActionBean.class );

    /** Error code returned when project is not found. */
    private static final int PROJECT_NOT_FOUND = 410;

    /** The code of the project. */
    private String code;

    /** Tells us what tab to focus on. */
    private String focus = "basic";

    private final ProjectManagementService projectManagementService;

    /**
     * Called when instance of this class is created.
     * 
     * @param projectManagementService the {@link ProjectManagementService} instance to be used in this class
     */
    @Inject
    public EditProjectActionBean( ProjectManagementService projectManagementService )
    {
        this.projectManagementService = projectManagementService;
    }

    @DefaultHandler
    public Resolution goToPage()
    {
        Project project = null;
        LOG.debug( "Show project with code: {}", code );
        if ( StringUtils.isNotBlank( code ) )
        {
            project = projectManagementService.findByCode( code );
        }
        if ( project == null )
        {
            // redirect to error page
            return new ErrorResolution( PROJECT_NOT_FOUND, "Project with code (" + code + ") was not found" );
        }
        else
        {
            // redirect to JSP
            return new ForwardResolution( FimPageURLs.EDIT_PROJECT_JSP.getURL() );
        }
    }

    public String getBasicLink()
    {
        return buildTabLink( "basic" );
    }

    public String getBasicClass()
    {
        return buildTabCssClass( "basic" );
    }

    public String getStatesLink()
    {
        return buildTabLink( "states" );
    }

    public String getStatesClass()
    {
        return buildTabCssClass( "states" );
    }

    public String getLabelsLink()
    {
        return buildTabLink( "labels" );
    }

    public String getLabelsClass()
    {
        return buildTabCssClass( "labels" );
    }

    public String getUsersLink()
    {
        return buildTabLink( "users" );
    }

    public String getUsersClass()
    {
        return buildTabCssClass( "users" );
    }

    private String buildTabLink( final String expectedTab )
    {
        UrlBuilder builder = new UrlBuilder( getLocale(), getClass(), true );
        builder.addParameter( "code", code );
        builder.addParameter( "focus", expectedTab );
        String url = builder.toString();
        String contextPath = getContext().getServletContext().getContextPath();
        if ( contextPath != null )
        {
            url = contextPath + url;
        }
        return url;
    }
    /**
     * Verifies if the tab name given as parameter is the selected one and returns corresponding CSS class
     * 
     * @param expectedTab the expected tab name
     * @return the CSS class or empty string if class should not be applied
     */
    private String buildTabCssClass( final String expectedTab )
    {
        return expectedTab.equalsIgnoreCase( focus ) ? " class=\"active\"" : "";
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getFocus()
    {
        return focus;
    }

    public void setFocus( String focus )
    {
        this.focus = focus;
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.project.edit", code );
    }
}
