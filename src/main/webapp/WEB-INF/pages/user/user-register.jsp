<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_${actionBean.localeLanguage}.min.js" />"></script>
		<script type="text/javascript">
			function clearFormContent(theForm){
				$("#" + theForm.id + " :text").val("");
				$("#" + theForm.id + " :password").val("");
			}
			
			$().ready(function() {
				// validate the comment form when it is submitted
				$("#regForm").validate({
					rules: {
						firstName: "required",
						lastName: "required",
						password: {
							required: true,
							minlength: 6
						},
						password2: {
							required: true,
							minlength: 6,
							equalTo: "#password"
						},
						email: {
							required: true,
							email: true
						}
						//agree: "required"
					}
				});
			});
			
		</script>
	</stripes:layout-component>

	<stripes:layout-component name="menu">
		<stripes:layout-render name="/WEB-INF/pages/layout/menu/menu-nauth.jsp" />
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.RegisterUserActionBean" focus="firstName" id="regForm">  
			<div class="col-md-6" style="padding-top: 30px">
				<div class="row form-group">
					<h1><fmt:message key="register.user.title" /></h1>
				</div>
				<div class="row form-group">
					<div class="col-md-3" style="text-align: right"><stripes:label for="userRegistration.firstName"/></div>
	     			<div class="col-md-9"><stripes:text class="form-control" name="firstName" id="firstName"></stripes:text></div>
	     		</div>
				<div class="row form-group">
					<div class="col-md-3" style="text-align: right"><stripes:label for="userRegistration.lastName"/></div> 
					<div class="col-md-9"><stripes:text class="form-control" name="lastName" id="lastName"></stripes:text></div>
				</div>
				<div class="row form-group">
					<div class="col-md-3" style="text-align: right"><stripes:label for="userRegistration.email"/></div> 
					<div class="col-md-9"><stripes:text class="form-control" name="email" id="email"></stripes:text></div>
				</div>
				<div class="row form-group">
					<div class="col-md-3" style="text-align: right"><stripes:label for="userRegistration.password"/></div>  
					<div class="col-md-9"><stripes:password class="form-control" name="password" id="password"></stripes:password></div>
				</div>
				<div class="row form-group">
					<div class="col-md-3" style="text-align: right"><stripes:label for="userRegistration.confirmPassword"/></div>  
					<div class="col-md-9"><stripes:password class="form-control" name="password2" id="password2"></stripes:password></div>
				</div>
				<div class="row form-group">
	 				<div class="col-md-12" style="text-align: right"><stripes:errors></stripes:errors></div>
				</div>
	     		<div class="row form-group" style="text-align: center">
	     			${actionBean.dbOperationResult}
	     		</div>
				<div class="row form-group" style="text-align: center">
					<stripes:submit class="btn btn-primary" name="tryRegister"><fmt:message key="action.send" /></stripes:submit>
					<stripes:button class="btn btn-default" name="reset" onclick="clearFormContent(this.form)"></stripes:button>
				</div>  
			</div>
		</stripes:form>       
	</stripes:layout-component>
</stripes:layout-render>