<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.RegisterUserActionBean" focus="firstName" id="regForm">  
	<table>  
		<tr>  
        	<td><stripes:label for="userRegistration.firstName"/></td>  
        	<td>
        		<stripes:text name="firstName" id="firstName"></stripes:text>
        	</td>  
     	</tr>
		<tr>  
        	<td><stripes:label for="userRegistration.lastName"/></td>  
        	<td>
        		<stripes:text name="lastName" id="lastName"></stripes:text>
        	</td>  
     	</tr>
		<tr>  
        	<td><stripes:label for="userRegistration.email"/></td>  
        	<td>
        		<stripes:text name="email" id="email"></stripes:text>
        	</td>  
     	</tr>
		<tr>  
        	<td><stripes:label for="userRegistration.password"/></td>  
        	<td>
        		<stripes:password name="password" id="password"></stripes:password>
        	</td>  
     	</tr>
		<tr>  
        	<td><stripes:label for="userRegistration.confirmPassword"/></td>  
        	<td>
        		<stripes:password name="password2" id="password2"></stripes:password>
        	</td>  
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
        	<td><stripes:submit name="tryRegister" value="Send..."></stripes:submit></td>  
        	<td><input type="button" name="reset" value="Clear" onclick="clearFormContent(this.form)"/></td>  
     	</tr>
	</table>
</stripes:form>       
</stripes:layout-definition>