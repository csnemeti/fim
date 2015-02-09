<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Invite User">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_en.min.js" />"></script>
		<script type="text/javascript">
			function clearFormContent(theForm){
				$("#" + theForm.id + " :text").val("");
				$("#" + theForm.id + " :password").val("");
			}
			
			$().ready(function() {
				// validate the comment form when it is submitted
				$("#regForm").validate({
					rules: {
						email: {
							required: true,
							email: true
						}
					}
				});
			});
			
		</script>
	</stripes:layout-component>

	<stripes:layout-component name="menu">
		<stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp" />
	</stripes:layout-component>
	<stripes:layout-component name="content">	
		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.InviteUserActionBean" focus="email" id="regForm">  
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
						<stripes:label for="userInvite.defaultRole"/> 
						<stripes:select class="form-control" name="defaultRole" id="defaultRole">
							<stripes:options-collection collection="${actionBean.defaultRoles}" value="id" label="description"/>
						</stripes:select>						
					</div>
					<div class="form-group">
		 				<stripes:errors></stripes:errors>
					</div>
		     		<div class="form-group">
		     			${actionBean.dbOperationResult}
		     		</div>
		
					<stripes:submit  class="btn btn-default" name="tryRegister" value="Send..."></stripes:submit>
					<input  class="btn btn-default" type="button" name="reset" value="Clear" onclick="clearFormContent(this.form)"/>  
				</div>
			</div>
		</stripes:form>       		
	</stripes:layout-component>
</stripes:layout-render>