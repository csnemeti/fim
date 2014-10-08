<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean">  
	<div class="form-group">
		 <label for="inputEmail">E-mail address</label>  
        	 <stripes:text name="username" type="email" class="form-control" id="inputEmail" placeholder="Enter email"></stripes:text>  
     	</div>
	<div class="form-group">
        	<label for="inputPassword">Password</label>
        	<stripes:password name="password" class="form-control" id="inputPassword" placeholder="Password"></stripes:password>
     	</div>

        <stripes:submit name="tryLogin" class="btn btn-default" value="Login..."></stripes:submit>  
       	<input class="btn btn-default" type="button" name="reset" value="Clear" />  

</stripes:form>       
</stripes:layout-definition>
