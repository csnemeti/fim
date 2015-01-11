<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.ForgotPasswordActionBean" focus="username" id="loginForm"> 
	<div align="center">
	<div style="padding-top:15px">
		<div class="form-group">
    		<stripes:label for="userLogin.username"/>
        	<stripes:text class="form-control" name="username"></stripes:text>
    	</div>
    	<div class="form-group">
    		${actionBean.dbOperationResult}
    	</div>
    	<stripes:submit class="btn btn-default" name="tryForgotPassword" value="Submit..." ></stripes:submit> 
    	<input type="button" class="btn btn-default" name="reset" value="Clear" onclick="clearFormContent(this.form)"/> 
	</div>
	</div>
</stripes:form>
</stripes:layout-definition>