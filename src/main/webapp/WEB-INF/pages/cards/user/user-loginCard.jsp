<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean" focus="username" id="loginForm"> 
	<div align="center">
	<div style="padding-top:15px">
		<div class="form-group">
    		<stripes:label for="userLogin.username"/>
        	<stripes:text class="form-control" name="username"></stripes:text>
    	</div>
    	<div class="form-group">
    		<stripes:label for="userLogin.password"/>
    	   	<stripes:password class="form-control" name="password"></stripes:password>  
    	</div>
    	<div class="form-group">
    		${actionBean.dbOperationResult}
    	</div>
    	<stripes:submit class="btn btn-default" name="tryLogin"></stripes:submit> 
    	<stripes:button class="btn btn-default" name="reset" onclick="clearFormContent(this.form)"></stripes:button> 
	</div>
	<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.ForgotPasswordActionBean">
		<stripes:label for="forgotPassword" />
	</stripes:link>	
	</div>
</stripes:form>

</stripes:layout-definition>
