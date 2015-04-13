<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.RegisterUserActionBean" focus="firstName" id="regForm" acceptcharset="UTF-8">  
	<div align="center">
		<div style="padding-top:15px">
			<div class="form-group">
				<stripes:label for="userRegistration.firstName"/>
     			<stripes:text class="form-control" name="firstName" id="firstName"></stripes:text>
     		</div>
			<div class="form-group">
				<stripes:label for="userRegistration.lastName"/> 
				<stripes:text class="form-control" name="lastName" id="lastName"></stripes:text>
			</div>
			<div class="form-group">
				<stripes:label for="userRegistration.email"/> 
				<stripes:text class="form-control" name="email" id="email"></stripes:text>
			</div>
			<div class="form-group">
				<stripes:label for="userRegistration.password"/>  
				<stripes:password class="form-control" name="password" id="password"></stripes:password>
			</div>
			<div class="form-group">
				<stripes:label for="userRegistration.confirmPassword"/>  
				<stripes:password class="form-control" name="password2" id="password2"></stripes:password>
			</div>
			<div class="form-group">
 				<stripes:errors></stripes:errors>
			</div>
     		<div class="form-group">
     			${actionBean.dbOperationResult}
     		</div>

			<stripes:submit class="btn btn-default" name="tryRegister" value="Send..."></stripes:submit>
			<stripes:button class="btn btn-default" name="reset" onclick="clearFormContent(this.form)"></stripes:button>  
		</div>
	</div>
</stripes:form>       
</stripes:layout-definition>