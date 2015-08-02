package pfa.alliance.fim.web.stripes.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.util.ManifestUtils;

/**
 * ActionBean designed for the FIM pages.
 * 
 * @author Balaceanu Sergiu-Denis
 */
public class BasePageActionBean
    extends BaseActionBean
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( BasePageActionBean.class );

    /**
     * Gets the FIM version
     * 
     * @return the version found from configuration and JAR files
     */
    public String getVersion()
    {
        String version = ManifestUtils.getImplementationVersion();
        LOG.debug( "Found version: {}", version );
        return version;
    }

    /**
     * Gets the title of the page.
     * 
     * @return the title of the page
     */
    public String getTitle()
    {
        return "F.I.M.";
    }

    /**
     * Gets the context path for URL builder.
     * 
     * @return the path (if any) or empty string if path is root.
     */
    public String findContextPath()
    {
        String contextPath = getContext().getServletContext().getContextPath();
        if ( contextPath.equals( "/" ) )
        {
            contextPath = "";
        }
        return contextPath;
    }

    public String getCreateIssueUrl()
    {
        Integer projectId = getSelectedProjectId();
        return findContextPath() + "/issue/create" + ( ( projectId != null ) ? "/" + projectId : "" );
    }

    /**
     * Gets the currently selected Project Id. Default implementation return null.
     * 
     * @return the ID of the selected project or null if no project is selected
     */
    protected Integer getSelectedProjectId()
    {
        return null;
    }
}
