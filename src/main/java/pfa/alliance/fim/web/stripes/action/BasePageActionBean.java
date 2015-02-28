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

}
