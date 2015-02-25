<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Create Project">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_en.min.js" />"></script>
		<script type="text/javascript">
			var specialKeys = new Array();
	        specialKeys.push(8); //Backspace
	        specialKeys.push(9); //Tab
	        specialKeys.push(46); //Delete
	        specialKeys.push(36); //Home
	        specialKeys.push(35); //End
	        specialKeys.push(37); //Left
	        specialKeys.push(39); //Right
	        
	        //specialKeys.push(43); //Plus
	        //specialKeys.push(45); //Minus
	        specialKeys.push(95); //Underscore
	        
			function clearFormContent(theForm){
				$(".clearable").val("");
			}
			function isValidForCode(e){
				var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
	            var ret = ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || 
	            		(keyCode >= 97 && keyCode <= 122) || (specialKeys.indexOf(keyCode) != -1 && e.charCode != e.keyCode));
	            return ret;
			}
			
			$().ready(function() {
				// block some operations on project code
				var prjCode = document.getElementById("projectCode");
				prjCode.onpaste = function(){
					return false;
				}
				prjCode.ondrop = function(){
					return false;
				}
				prjCode.onblur = function(){
					var theValue = prjCode.value;
					if(theValue != null){
						prjCode.value = theValue.toUpperCase();
					}
				}
				
				// set the placeholder
				var placeholderSupported = ( 'placeholder' in document.createElement('input') );
				if(placeholderSupported){
					document.getElementById("projectName").placeholder = "Enter a name for the project";
					document.getElementById("projectCode").placeholder = "Enter a code for the project";
					document.getElementById("projectDescription").placeholder = "Enter a description for the project";
					document.getElementById("ownerName").placeholder = "Enter the project owner";
				}
				$("[data-toggle='tooltip']").tooltip();
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
				<label for="projectCode" class="col-sm-6 control-label">Project Code (only letters, numbers and underscore (_)):</label>
				<stripes:text class="form-control clearable" name="projectCode" id="projectCode" onkeypress="return isValidForCode(event);"></stripes:text>
			</div>
			<div class="form-group">
				<label for="projectName" class="col-sm-2 control-label">Description:</label>
				<stripes:textarea class="form-control clearable" rows="5" cols="40" name="projectDescription" id="projectDescription"></stripes:textarea>
			</div>
			<div class="form-group">
				<label for="ownerName" class="col-sm-2 control-label">Project owner:</label>
				<stripes:text class="form-control" name="ownerName" id="ownerName" readonly="readonly"></stripes:text>
			</div>
			<div class="form-group">
				<stripes:checkbox class="" name="hidden" id="hidden"></stripes:checkbox> Make the project <a href="javascript: void(0)" data-toggle="tooltip" title="Hidden project means is not displayed on search.">hidden</a>
			</div>
			<div class="form-group">
				<stripes:checkbox class="" name="activate" id="activate"></stripes:checkbox> <a href="javascript: void(0)" data-toggle="tooltip" title="By default projects are created with state IN PREPARATION. If you check this, projects state will be ACTIVE.">Activate</a> project directly
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