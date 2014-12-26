<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Create Project">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_en.min.js" />"></script>
		<script type="text/javascript">
			function clearFormContent(theForm){
				$(".clearable").val("");
			}
			
			$().ready(function() {
				// set the placeholder
				var placeholderSupported = ( 'placeholder' in document.createElement('input') );
				if(placeholderSupported){
					document.getElementById("projectName").placeholder = "Enter a name for the project";
					document.getElementById("projectCode").placeholder = "Enter a code for the project";
					document.getElementById("projectDescription").placeholder = "Enter a description for the project";
					document.getElementById("ownerName").placeholder = "Enter the project owner";
				}
				// validate the comment form when it is submitted
				$("#prjForm").validate({
					rules: {
						projectName: {
							required: true,
							maxlength: 50
						},
						projectCode: {
							required: true,
							minlength: 2,
							maxlength: 20
						},
						projectDescription: {
							maxlength: 2000
						},
						ownerName: "required"
					}
				});
			});
			
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">             	
	<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.CreateProjectActionBean" focus="projectName" id="prjForm" class="form-horizontal">  
		<div class="">
			<div class="form-group">
				<label for="projectName" class="col-sm-2 control-label">Name:</label>
				<stripes:text class="form-control clearable" name="projectName" id="projectName"></stripes:text>
			</div>
			
			<div class="form-group">
				<label for="projectCode" class="col-sm-2 control-label">Project Code:</label>
				<stripes:text class="form-control clearable" name="projectCode" id="projectCode"></stripes:text>
			</div>
				
			<div class="form-group">
				<label for="projectName" class="col-sm-2 control-label">Description:</label>
				<stripes:textarea class="form-control clearable" rows="5" cols="40" name="projectDescription" id="projectDescription"></stripes:textarea>
			</div>
			
			<div class="form-group">
				<label for="ownerName" class="col-sm-2 control-label">Project owner:</label>
				<stripes:text class="form-control" name="ownerName" id="ownerName" readonly="readonly"></stripes:text>
			</div>
			<div class="form-group error">
 				<stripes:errors></stripes:errors>
			</div>
     		<div class="form-group">
     			${actionBean.dbOperationResult}
     		</div>
			<stripes:submit  class="btn btn-default" name="create" value="Submit"></stripes:submit>
			<input  class="btn btn-default" type="button" name="reset" value="Clear" onclick="clearFormContent(this.form)"/>  
		</div>
	</stripes:form>
	</stripes:layout-component>
</stripes:layout-render>