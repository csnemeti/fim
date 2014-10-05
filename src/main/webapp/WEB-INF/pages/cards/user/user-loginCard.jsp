<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean" focus="email">  
	<table>  
		<tr>  
        	<td>E-mail address</td>  
        	<td><stripes:text name="username"></stripes:text></td>  
     	</tr>
		<tr>  
        	<td>Password</td>  
        	<td><stripes:password name="password"></stripes:password></td>  
     	</tr>
		<tr>  
        	<td><stripes:submit name="tryLogin" value="Login..."></stripes:submit></td>  
        	<td><input type="button" name="reset" value="Clear" /></td>  
     	</tr>
	</table>
</stripes:form>       
</stripes:layout-definition>