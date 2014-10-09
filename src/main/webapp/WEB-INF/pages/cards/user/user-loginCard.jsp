<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean" focus="username" id="loginForm">    
	<table>  
		<tr>  
        	<td><stripes:label for="userLogin.username"/></td>  
        	<td><stripes:text name="username"></stripes:text></td>  
     	</tr>
		<tr>  
        	<td><stripes:label for="userLogin.password"/></td>  
        	<td><stripes:password name="password"></stripes:password></td>  
     	</tr>
     	<tr>
     		<td colspan="2" class="error">
     			<stripes:errors></stripes:errors>
     		</td>
     	</tr>
     	<tr>
     		<td colspan="2">
     			${actionBean.dbOperationResult}
     		</td>
     	</tr>
		<tr>  
        	<td><stripes:submit name="tryLogin" value="Login..."></stripes:submit></td>  
        	<td><input type="button" name="reset" value="Clear" onclick="clearFormContent(this.form)"/></td>  
     	</tr>
	</table>
</stripes:form>       
</stripes:layout-definition>