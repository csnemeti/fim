package pfa.alliance.fim.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for the manifest attributes .
 * 
 * @author Balaceanu Sergiu-Denis
 */
public class ManifestUtils
{
    private static final Logger LOG = LoggerFactory.getLogger( ManifestUtils.class );

    private static String MANIFEST_URL = "META-INF/MANIFEST.MF";

    public static String FIM_CI = "FIM_CI";

    public static String FIM_BUILD_NUMBER = "FIM_BUILD_NUMBER";

    public static String FIM_GIT_COMMIT = "FIM_GIT_COMMIT";

    public static String FIM_GIT_BRACH = "FIM_GIT_BRACH";

    public static String IMPLEMENTATION_TITLE = "Implementation-Title";

    public static String IMPLEMENTATION_VERSION = "Implementation-Version";

    public static String CREATED_DATE = "Created-At";

    /** Cached value for implementation version. */
    private static String implementationVersion = null;

    /** The FIM manifest. */
    private static Manifest fimManifest = null;

    /**
     * Gets the current version of FIM Application
     * 
     * @return the version read from MANIFEST.MF
     */
    public static String getImplementationVersion()
    {
        Object result = null;
        getFimManifest();
        if ( fimManifest != null )
        {
            LOG.debug( "Found Manifest file" );
            Attributes attributes = fimManifest.getMainAttributes();
            result = attributes.getValue( IMPLEMENTATION_VERSION );
        }
        else
        {
            LOG.info( "Manifest file with version information was not found" );
        }
        return ( result != null ) ? result.toString() : null;
    }

    public static Attributes getFimMainAttributes()
    {
        Attributes attributes = null;
        getFimManifest();
        if ( fimManifest != null )
        {
            attributes = fimManifest.getMainAttributes();
        }
        return attributes;
    }

    /**
     * Gets the FIM's manifest file.
     * 
     * @return the manifest file
     */
    private static Manifest getFimManifest()
    {
        if ( fimManifest == null )
        {
            try
            {
                Enumeration<URL> resources = ManifestUtils.class.getClassLoader().getResources( MANIFEST_URL );
                while ( resources.hasMoreElements() && fimManifest == null )
                {
                    URL url = resources.nextElement();
                    LOG.debug( "Checking: {}", url );
                    try
                    {
                        Manifest manifest = new Manifest( url.openStream() );
                        // check that this is your manifest and do what you need or get the next one
                        Attributes attributes = manifest.getMainAttributes();
                        if ( attributes != null && attributes.getValue( FIM_CI ) != null )
                        {
                            fimManifest = manifest;
                        }
                    }
                    catch ( IOException e )
                    {
                        LOG.error( "Could not read manifest from: {}", url, e );
                    }
                }
            }
            catch ( IOException e )
            {
                LOG.error( "Could not get the manifests", e );
            }
        }
        return fimManifest;
    }
}
