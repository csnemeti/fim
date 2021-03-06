package pfa.alliance.fim.web.common;

/**
 * Enumeration that defines the URL to all the FIM Application pages .
 * 
 * @author Balaceanu Sergiu-Denis
 */
public enum FimPageURLs
{
    MAIN_PAGE( "/index.jsp" ),
    USER_DASBOARD_PAGE( "/user/dashboard" ),
    USER_DASBOARD_JSP( "/WEB-INF/pages/dashboard.jsp" ),
    WIZZARD_PAGE( "/setup" ),
    USER_REGISTER_PAGE( "/user/register" ),
    USER_REGISTER_JSP( "/WEB-INF/pages/user/user-register.jsp" ),
    USER_LOGIN_PAGE( "/user/login" ),
    USER_LOGIN_JSP( "/WEB-INF/pages/user/user-login.jsp" );

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
