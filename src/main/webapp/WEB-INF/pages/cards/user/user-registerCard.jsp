<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.RegisterUserActionBean" focus="firstName">  
	<table>  
		<tr>  
        	<td>First name</td>  
        	<td><stripes:text name="firstName"></stripes:text></td>  
     	</tr>
		<tr>  
        	<td>Last name</td>  
        	<td><stripes:text name="lastName"></stripes:text></td>  
     	</tr>
		<tr>  
        	<td>E-mail address</td>  
        	<td><stripes:text name="email"></stripes:text></td>  
     	</tr>
		<tr>  
        	<td>Password</td>  
        	<td><stripes:password name="password"></stripes:password></td>  
     	</tr>
		<tr>  
        	<td>Confirm password</td>  
        	<td><stripes:password name="password2"></stripes:password></td>  
     	</tr>
		<tr>  
        	<td><stripes:submit name="tryRegister" value="Send..."></stripes:submit></td>  
        	<td><input type="button" name="reset" value="Clear" /></td>  
     	</tr>
	</table>
</stripes:form>       
</stripes:layout-definition>