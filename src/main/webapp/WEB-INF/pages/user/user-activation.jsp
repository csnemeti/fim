<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu-nauth.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
	    <p id="success" style="display: ${actionBean.successResult}"><fmt:message key="userActivation.success1" />
	    	<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean"><fmt:message key="action.login" /></stripes:link><fmt:message key="userActivation.success2" />
	    </p>   
	    <p id="error" class="error" style="display: ${actionBean.errorResult}"><fmt:message key="userActivation.fail1" />
		    <stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.RegisterUserActionBean"><fmt:message key="action.register.small" /></stripes:link><fmt:message key="userActivation.fail2" /> 
	    </p>   
    </stripes:layout-component>    
</stripes:layout-render>