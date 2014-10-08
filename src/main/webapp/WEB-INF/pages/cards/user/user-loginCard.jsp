<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>

<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean" focus="email">  
	<div align="center">
	<div style="padding-top:15px">
		<div class="form-group">
    		<label for="exampleInputEmail1">E-mail address</label> 
        	<stripes:text class="form-control" name="username"></stripes:text>
    	</div>
    	<div class="form-group">
    		<label for="exampleInputEmail1">Password</label>  
    	   	<stripes:password class="form-control" name="password"></stripes:password>  
    	</div>
    	<stripes:submit class="btn btn-default" name="tryLogin" value="Login..." ></stripes:submit> 
    	<input type="button" class="btn btn-default" name="reset" value="Clear" />  
	</div>
	</div>
</stripes:form>

</stripes:layout-definition>
