/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.UrlBuilder;
import net.sourceforge.stripes.validation.Validate;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectComponent;
import pfa.alliance.fim.model.project.ProjectLabel;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.security.Permission;
import pfa.alliance.fim.web.stripes.action.BasePageActionBean;
import pfa.alliance.fim.web.stripes.action.StripesDropDownOption;

/**
 * This is the base class for editing a specific project.
 * 
 * @author Csaba
 */
@UrlBinding( value = "/project/edit/{code}/{focus}" )
@FimSecurity( checkIfAny = Permission.PROJECT_EDIT_PROJECT )
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

    /** The name of a new Label. */
    @Validate( required = true, trim = true, on = "{createLabel, createComponent}", maxlength = 40 )
    private String labelName;

    /** The chosen color for a new Label. */
    @Validate( required = true, trim = true, on = "{createLabel, createComponent}", maxlength = 40 )
    private String labelColor;

    private final ProjectManagementService projectManagementService;

    private Project project;
    private List<ProjectLabel> labelList;
    private List<ProjectComponent> componentList;

    private static final List<StripesDropDownOption> COLORS;

    static
    {
        String[] colorCodes =
            new String[] { "#000000", "#1f497d", "#4f81bd", "#c0504d", "#9bbb59", "#8064a2", "#4bacc6", "#f79646",
                "#ffff00", "#7f7f7f", "#ddd9c3", "#595959", "#c4bd97", "#8db3e2", "#b8cce4", "#e5b9b7", "#ffe694",
                "#bfbfbf", "#3f3f3f", "#938953", "#548dd4", "#95b3d7", "#d99694", "#c3d69b", "#b2a2c7", "#a5d0e0",
                "#fac08f", "#f2c314", "#a5a5a5", "#262626", "#494429", "#17365d", "#366092", "#953734", "#76923c",
                "#5f497a", "#92cddc", "#e36c09", "#c09100", "#7f7f7f", "#0c0c0c", "#1d1b10", "#0f243e", "#244061",
                "#632423", "#4f6128", "#3f3151", "#31859b", "#974806", "#7f6000", "#ff0000" };
        List<StripesDropDownOption> colors = new ArrayList<StripesDropDownOption>();
        for ( String colorCode : colorCodes )
        {
            colors.add( new StripesDropDownOption( colorCode, colorCode ) );
        }
        COLORS = Collections.unmodifiableList( colors );
        colorCodes =
            new String[] { "#ffffff", "#000000", "#eeece1", "#1f497d", "#4f81bd", "#c0504d", "#9bbb59", "#8064a2",
                "#4bacc6", "#f79646", "#ffff00", "#f2f2f2", "#7f7f7f", "#ddd9c3", "#c6d9f0", "#dbe5f1", "#f2dcdb",
                "#ebf1dd", "#e5e0ec", "#dbeef3", "#fdeada", "#fff2ca", "#d8d8d8", "#595959", "#c4bd97", "#8db3e2",
                "#b8cce4", "#e5b9b7", "#d7e3bc", "#ccc1d9", "#b7dde8", "#fbd5b5", "#ffe694", "#bfbfbf", "#3f3f3f",
                "#938953", "#548dd4", "#95b3d7", "#d99694", "#c3d69b", "#b2a2c7", "#a5d0e0", "#fac08f", "#f2c314",
                "#a5a5a5", "#262626", "#494429", "#17365d", "#366092", "#953734", "#76923c", "#5f497a", "#92cddc",
                "#e36c09", "#c09100", "#7f7f7f", "#0c0c0c", "#1d1b10", "#0f243e", "#244061", "#632423", "#4f6128",
                "#3f3151", "#31859b", "#974806", "#7f6000", "#ff0000" };
    }

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
        project = null;
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

    public Resolution createLabel()
    {
        // process
        projectManagementService.createLabel( code, labelName, "#FFFFFF", labelColor );
        // redirect to this page again
        return redirectBackHere();
    }

    public Resolution createComponent()
    {
        // process
        projectManagementService.createComponent( code, labelName, labelColor, "#FFFFFF" );
        // redirect to this page again
        return redirectBackHere();
    }

    private Resolution redirectBackHere()
    {
        RedirectResolution resolution = new RedirectResolution( getClass() );
        resolution.addParameter( "code", code );
        resolution.addParameter( "focus", focus );
        return resolution;
    }

    public int getLabelsNumber()
    {
        return getLabels().size();
    }

    public synchronized List<ProjectLabel> getLabels()
    {
        if ( labelList == null )
        {
            labelList = projectManagementService.findLabelsByProjectId( project.getId() );
        }
        return labelList;
    }

    public int getComponentsNumber()
    {
        return getComponents().size();
    }

    public synchronized List<ProjectComponent> getComponents()
    {
        if ( componentList == null )
        {
            componentList = projectManagementService.findComponentsByProjectCode( project.getId() );
        }
        return componentList;
    }

    public List<StripesDropDownOption> getColors()
    {
        return COLORS;
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

    public String getLabelName()
    {
        return labelName;
    }

    public void setLabelName( String labelName )
    {
        this.labelName = labelName;
    }

    public String getLabelColor()
    {
        return labelColor;
    }

    public void setLabelColor( String labelColor )
    {
        this.labelColor = labelColor;
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.project.edit", code );
    }
}
