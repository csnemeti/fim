<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_${actionBean.localeLanguage}.min.js" />"></script>
		<script type="text/javascript">
			function clearFormContent(theForm){
				$("#" + theForm.id + " :text").val("");
			}
			
			$().ready(function() {
				// validate the comment form when it is submitted
				$("#loginForm").validate({
					rules: {
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
		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.ForgotPasswordActionBean" focus="username" id="loginForm"> 
			<div align="center">
			<div style="padding-top:15px">
				<div class="form-group">
		    		<stripes:label for="userLogin.username"/>
		        	<stripes:text class="form-control" name="username"></stripes:text>
		    	</div>
		    	<div class="form-group">
		    		${actionBean.dbOperationResult}
		    	</div>
		    	<stripes:submit class="btn btn-primary" name="tryForgotPassword"></stripes:submit> 
		    	<stripes:button class="btn btn-default" name="reset" onclick="clearFormContent(this.form)"></stripes:button> 
			</div>
			</div>
		</stripes:form>
    </stripes:layout-component>    
</stripes:layout-render>