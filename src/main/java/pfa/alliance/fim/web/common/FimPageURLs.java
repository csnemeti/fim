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
    USER_DASBOARD_JSP( "/WEB-INF/pages/user/dashboard.jsp" ),
    WIZZARD_PAGE( "/setup" ),
    USER_REGISTER_PAGE( "/user/register" ),
    USER_REGISTER_JSP( "/WEB-INF/pages/user/user-register.jsp" ),
    USER_INVITE_PAGE( "/user/invite" ),
    USER_INVITE_JSP( "/WEB-INF/pages/user/user-invite.jsp" ),
    USER_LOGIN_PAGE( "/user/login" ),
    USER_LOGIN_JSP( "/WEB-INF/pages/user/user-login.jsp" ),
    USER_ACTIVATION_JSP( "/WEB-INF/pages/user/user-activation.jsp" ),
    CREATE_PROJECT_PAGE("/project/create"),
    CREATE_PROJECT_JSP("/WEB-INF/pages/project/createProject.jsp"),
    EDIT_PROJECT_USERS_PAGE("/project/edit/users"),
    EDIT_PROJECT_USERS_JSP("/WEB-INF/pages/project/editProjectUsers.jsp");

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
