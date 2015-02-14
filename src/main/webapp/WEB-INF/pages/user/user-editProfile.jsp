<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: User search">
	<stripes:layout-component name="header">
		<style type="text/css">
			h1 {
				margin: 0px !important;
				font-size: 26px !important;
			}
		</style>
		<link rel="stylesheet" href="<stripes:url value="/js/bootstrap-multiselect/css/bootstrap-multiselect.css" />" type="text/css"/>
		
		<script type="text/javascript" src="<stripes:url value="/js/bootstrap-multiselect/js/bootstrap-multiselect.js" />"></script>		
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_en.min.js" />"></script>
		
		<script type="text/javascript">
			var localeMap = ${actionBean.localizationString};
			function clearFormContent(theForm){
				$("#" + theForm.id + " :password").val("");
			}
			function localizeText(text){
				return localeMap[text];
			}
			function buildUserStatusImage(userStatus){
				return "<img class='userState' src='<c:url value="/images/user-state/" />" + userStatus.toLowerCase() + ".png' title='" + localizeText(userStatus) + "' />";
			}
			
			$().ready(function() {
				$('#status').multiselect();
				$('#role').multiselect();
				// set the placeholder
				var placeholderSupported = ( 'placeholder' in document.createElement('input') );
				if(placeholderSupported){
					//document.getElementById("firstName").placeholder = "Starts with...";
				}
				$('#dataForm').on('reset', function(){
				    setTimeout(function(){
				    	$('#status').multiselect('refresh');
				    	$('#role').multiselect('refresh');
				    });
				});
				// validate the data from forms
 				$("#dataForm").validate({
					rules: {
						firstName: "required",
						lastName: "required"
					}
				});
 				$("#passwordForm").validate({
					rules: {
						password0: "required",
						password1: {
							required: true,
							minlength: 6
						},
						password2: {
							required: true,
							minlength: 6,
							equalTo: "#password1"
						},
					}
				});
 				$("#emailForm").validate({
					rules: {
						email: {
							required: true,
							email: true
						}
					}
				});
 				$("#disableAccountForm").validate({
					rules: {
						password: "required"
					}
				});
		});			
			</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp" />
    </stripes:layout-component>
    <stripes:layout-component name="content">
       	<div class="row">
	    	<div class="col-md-2"></div>
	       	<div class="col-md-8" style="margin-top: 20px; min-width:400px">
					<div class="form-group error">
		 				<stripes:errors></stripes:errors>
					</div>
			</div>
		</div>							
    	<div class="row">
	    	<div class="col-md-2"></div>
	       	<div class="col-md-8" style="margin-top: 20px; min-width:400px">
	       		<div class="panel panel-primary">
	            	<div class="panel-heading">
	            		<h1>Change User Data</h1>
	              	</div>
	              	<div class="panel-body">
				        <stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.EditOwnProfileActionBean" focus="firstName" id="dataForm">
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userRegistration.firstName"/>
										<div class="col-sm-6">
						     			<stripes:text class="form-control input-sm" name="firstName" id="firstName" maxlength="100"></stripes:text>
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userRegistration.lastName"/>
										<div class="col-sm-6">
						     			<stripes:text class="form-control input-sm" name="lastName" id="lastName" maxlength="100"></stripes:text>
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userProfile.userStatus"/>
										<div class="col-sm-6">
											<stripes:select name="status" id="status" disabled="${actionBean.statusDisabled}">
												<stripes:options-collection collection="${actionBean.defaultStatuses}" value="id" label="description"/>
											</stripes:select>		
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userInvite.defaultRole"/>
										<div class="col-sm-6">
											<stripes:select name="role" id="role" disabled="${actionBean.roleDisabled}">
												<stripes:options-collection collection="${actionBean.defaultRoles}" value="id" label="description"/>
											</stripes:select>		
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
						     		<div class="form-group" style="text-align:center">
						     			${actionBean.updateDataDbOperationText}
						     		</div>
						     	</div>
							</div>
				        	<div align="center" style="padding-top: 10px">
								<stripes:submit class="btn btn-default" name="changeData" value="Change"></stripes:submit>
								<stripes:reset class="btn btn-default" name="resetEmail" value="Reset"></stripes:reset>
				        	</div>	              		
				        </stripes:form>
					</div>
				</div>	
			</div>
		</div>
		<div class="row">
	    	<div class="col-md-2"></div>
	       	<div class="col-md-8" style="margin-top: 20px; min-width:400px">
	       		<div class="panel panel-primary">
	            	<div class="panel-heading">
	            		<h1>Change Password</h1>
	              	</div>
	              	<div class="panel-body">
				        <stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.EditOwnProfileActionBean" id="passwordForm">
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userProfile.oldPassword"/>
										<div class="col-sm-6">
						     			<stripes:password class="form-control input-sm" name="password0" id="password0" maxlength="100"></stripes:password>
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userProfile.newPassword"/>
										<div class="col-sm-6">
						     			<stripes:password class="form-control input-sm" name="password1" id="password1" maxlength="100"></stripes:password>
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userProfile.newPassword2"/>
										<div class="col-sm-6">
						     			<stripes:password class="form-control input-sm" name="password2" id="password2" maxlength="100"></stripes:password>
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
						     		<div class="form-group" style="text-align:center">
						     			${actionBean.changePasswordDbOperationText}
						     		</div>
						     	</div>
							</div>
				        	<div align="center" style="padding-top: 10px">
								<stripes:submit class="btn btn-default" name="changePassword" value="Change"></stripes:submit>
								<input class="btn btn-default" type="button" name="reset" value="Clear" onclick="clearFormContent(this.form)"/>  
				        	</div>
				        </stripes:form>	
					</div>
				</div>	
			</div>
		</div>
<!-- 		
		<div class="row">
			<div class="col-md-2"></div>
	       	<div class="col-md-8" style="margin-top: 20px; min-width:400px">
	       		<div class="panel panel-primary">
	            	<div class="panel-heading">
	            		<h1>Change e-mail</h1>
	              	</div>
	              	<div class="panel-body">
	              		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.EditOwnProfileActionBean" id="emailForm">
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userRegistration.email"/>
										<div class="col-sm-6">
						     			<stripes:text class="form-control input-sm" name="email" id="email" maxlength="200"></stripes:text>
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
						     		<div class="form-group" style="text-align:center">
						     			${actionBean.changeEmailDbOperationText}
						     		</div>
						     	</div>
							</div>
				        	<div align="center" style="padding-top: 10px">
								<stripes:submit class="btn btn-default" name="changeEmail" value="Change"></stripes:submit>
								<stripes:reset class="btn btn-default" name="resetEmail" value="Reset"></stripes:reset>
				        	</div>	              		
	              		</stripes:form>
					</div>
				</div>	
			</div>
		</div>
 -->		
		<div class="row">
			<div class="col-md-2"></div>
	       	<div class="col-md-8" style="margin-top: 20px; min-width:400px">
	       		<div class="panel panel-primary">
	            	<div class="panel-heading">
	            		<h1>Disable account</h1>
	              	</div>
	              	<div class="panel-body">
	              		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.EditOwnProfileActionBean" id="disableAccountForm">
				        	<div class="row">
				        		<div class="col-sm-12">
									<div class="form-group form-group-sm">
										<stripes:label class="col-sm-4 control-label" style="text-align: right" for="userRegistration.password"/>
										<div class="col-sm-6">
						     			<stripes:password class="form-control input-sm" name="password" id="password" maxlength="100"></stripes:password>
						     			</div>
						     		</div>		     	
						     	</div>
							</div>
				        	<div class="row">
				        		<div class="col-sm-12">
						     		<div class="form-group" style="text-align:center">
						     			${actionBean.diableAccountDbOperationText}
						     		</div>
						     	</div>
							</div>
				        	<div align="center" style="padding-top: 10px">
								<stripes:submit class="btn btn-default" name="disableAccout" value="Disable..."></stripes:submit>
				        	</div>	              		
	              		</stripes:form>
					</div>
				</div>	
			</div>
		</div>
    </stripes:layout-component>    
</stripes:layout-render>