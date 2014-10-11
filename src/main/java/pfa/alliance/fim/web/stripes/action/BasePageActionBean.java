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

    public String getVersion()
    {
        String version = ManifestUtils.getImplementationVersion();
        LOG.debug( "Found version: {}", version );
        return version;
    }

}
