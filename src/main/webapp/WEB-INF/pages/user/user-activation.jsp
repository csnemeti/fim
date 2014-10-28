<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: User Activation">
	<stripes:layout-component name="header">
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu-nauth.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
	    <p id="success" style="display: ${actionBean.successResult}">Account activation was successful. Now you may
	    	<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean">Login</stripes:link>.
	    </p>   
	    <p id="error" class="error" style="display: ${actionBean.errorResult}">Account activation failed. You may try again and if fails, you should 
		    <stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.RegisterUserActionBean">register</stripes:link> one more time. 
		    In case user account activation fails or it's not made in time, the account information will be deleted. 
		    Deletion will occur in 48 hours since registration.
	    </p>   
    </stripes:layout-component>    
</stripes:layout-render>