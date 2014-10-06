<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.RegisterUserActionBean" focus="firstName" id="regForm">  
	<table>  
		<tr>  
        	<td>First name</td>  
        	<td>
        		<stripes:text name="firstName" id="firstName"></stripes:text>
        	</td>  
     	</tr>
		<tr>  
        	<td>Last name</td>  
        	<td>
        		<stripes:text name="lastName" id="lastName"></stripes:text>
        	</td>  
     	</tr>
		<tr>  
        	<td>E-mail address</td>  
        	<td>
        		<stripes:text name="email" id="email"></stripes:text>
        	</td>  
     	</tr>
		<tr>  
        	<td>Password</td>  
        	<td>
        		<stripes:password name="password" id="password"></stripes:password>
        	</td>  
     	</tr>
		<tr>  
        	<td>Confirm password</td>  
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
        	<td><stripes:submit name="tryRegister" value="Send..."></stripes:submit></td>  
        	<td><input type="button" name="reset" value="Clear" onclick="clearFormContent(this.form)"/></td>  
     	</tr>
	</table>
</stripes:form>       
</stripes:layout-definition>