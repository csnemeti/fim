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
@UrlBinding( value = "/project/edit/{code}" )
@FimSecurity
public class EditProjectActionBean
    extends BasePageActionBean

{
    private static final Logger LOG = LoggerFactory.getLogger( EditProjectActionBean.class );

    /** Error code returned when project is not found. */
    private static final int PROJECT_NOT_FOUND = 410;

    /** The code of the project. */
    private String code;

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

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    @Override
    public String getTitle()
    {
        return getMessage( "page.title.project.edit", code );
    }
}
