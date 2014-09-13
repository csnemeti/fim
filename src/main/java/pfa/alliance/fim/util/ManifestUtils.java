package pfa.alliance.fim.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Balaceanu Sergiu-Denis
 *
 */
public class ManifestUtils {
  private static final Logger LOG = LoggerFactory.getLogger(ManifestUtils.class);
  private static String MANIFEST_URL = "META-INF/MANIFEST.MF";
  public static String FIM_CI = "FIM_CI";
  public static String FIM_BUILD_NUMBER = "FIM_BUILD_NUMBER";
  public static String FIM_GIT_COMMIT = "FIM_GIT_COMMIT";
  public static String FIM_GIT_BRACH = "FIM_GIT_BRACH";
  public static String IMPLEMENTATION_TITLE = "Implementation-Title";
  public static String IMPLEMENTATION_VERSION = "Implementation-Version";
  public static String CREATED_DATE = "Created-At";


  /**
   * 
   * @return the current version of FIM Application
   */
  public static String getImplementationVersion() {
    return ManifestUtils.class.getPackage().getImplementationVersion();
  }



}
