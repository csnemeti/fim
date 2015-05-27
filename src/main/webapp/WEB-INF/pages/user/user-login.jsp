<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
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
				$("#loginForm").validate({
					rules: {
						password: "required",
						username: "required",
					}
				});
			});			
			</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu-nauth.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.LoginUserActionBean" focus="username" id="loginForm"> 
			<div class="col-md-6" style="padding-top: 50px">
				<div class="row form-group">
					<div class="col-md-3" style="text-align: right"><stripes:label for="userLogin.username"/></div>
		        	<div class="col-md-9"><stripes:text class="form-control" name="username"></stripes:text></div>
		    	</div>
		    	<div class="row form-group">
		    		<div class="col-md-3" style="text-align: right"><stripes:label for="userLogin.password"/></div>
		    	   	<div class="col-md-9"><stripes:password class="form-control" name="password"></stripes:password></div>
		    	</div>
		    	<div class="row" style="text-align: center">
		    		${actionBean.dbOperationResult}
		    	</div>
		    	<div class="row form-group" style="text-align: center">
			    	<stripes:submit class="btn btn-primary" name="tryLogin"></stripes:submit> 
			    	<stripes:button class="btn btn-default" name="reset" onclick="clearFormContent(this.form)"></stripes:button> 
		    	</div>
		    	<div class="row" style="text-align: center">
					<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.ForgotPasswordActionBean">
						<stripes:label for="forgotPassword" />
					</stripes:link>	
		    	</div>
			</div>
		</stripes:form>
    </stripes:layout-component>    
</stripes:layout-render>