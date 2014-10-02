package pfa.alliance.fim.web.common;

/**
 * Enumeration that defines the URL to all the FIM Application pages .
 * 
 * @author Balaceanu Sergiu-Denis
 */
public enum FimPageURLs
{
    MAIN_PAGE( "/index.jsp" ),
    DASBOARD_PAGE( "/WEB-INF/pages/dashboard.jsp" ),
    WIZZARD_PAGE( "/setup" ),
    USER_REGISTER_PAGE( "/WEB-INF/pages/user/user-register.jsp" );

    private FimPageURLs( String url )
    {
        this.url = url;
    }

    public String getURL()
    {
        return url;
    }

    private String url;
}
