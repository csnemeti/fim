/**
 * 
 */
package pfa.alliance.fim.web.servlets;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.servlet.ServletModule;

/**
 * This class is used for define the mappings for Fim servlets and filters.
 * 
 * @author Csaba
 */
class FimServletModule
    extends ServletModule
{
    @Override
    protected void configureServlets()
    {
        super.configureServlets();

        // bind configuration checker filter as singleton
        filter( "/*" ).through( SetupVerifyFilter.class );
        // changes necessary for DataTables
        Map<String, String> initParams = new HashMap<>();
        initParams.put( "transferColumnsParams", "false" );
        filter( "/user/search", "/user/search/*", "/project/search", "/project/search/*" ).through( DataTablesRequestFitler.class,
                                                                                                    initParams );
        // token processing for user profile
        filter( "/user/profile", "/user/profile/*" ).through( UserProfileFilter.class );
    }
}
