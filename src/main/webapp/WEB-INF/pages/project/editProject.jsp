<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
	<stripes:layout-component name="header">
		<link rel="stylesheet" href="<c:url value="/js/jquery.simplecolorpicker/jquery.simplecolorpicker.css" />">
		<link rel="stylesheet" href="<c:url value="/plugins/bootstrap/css/bootstrap-dialog.min.css" />"/>
		
		<script type="text/javascript" src="<stripes:url value="/js/project-code.js" />"></script>
   		<script type="text/javascript" src="<c:url value="/plugins/bootstrap/js/bootstrap-dialog.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.simplecolorpicker/jquery.simplecolorpicker.js" />"></script>
		<script type="text/javascript" src="<c:url value="/js/bootstrap3-typeahead.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_${actionBean.localeLanguage}.min.js" />"></script>
		
		<script type="text/javascript">
			<%-- http://nakupanda.github.io/bootstrap3-dialog/ --%>
			function deleteComponent(labelId, labelType){
				var message = "<fmt:message key="page.title.project.edit.lables.confirmDelete"/>";
				message = message.replace("{}", document.getElementById("fim" + labelType + labelId).innerHTML);
				BootstrapDialog.confirm({
		            title: '<fmt:message key="action.delete.title"/>',
		            message: message,
		            type: BootstrapDialog.TYPE_INFO, <%-- Default value is BootstrapDialog.TYPE_PRIMARY --%>
		            closable: true, <%-- Default value is false --%>
		            draggable: false, <%-- Default value is false --%>
		            btnCancelLabel: 'No', <%-- Default value is 'Cancel', --%>
		            btnOKLabel: 'Yes', <%-- Default value is 'OK', --%>
		            callback: function(result) {
		                <%-- result will be true if button was click, while it will be false if users close the dialog directly. --%>
		                if(result) {
		                	$("#deleteLabelId").val(labelId);
		                	$("#deleteLabelType").val(labelType.toLowerCase());
		                	$("#deleteLabel" ).trigger( "click" );
		                }
		            }
		        });
				
			}
			function editComponent(labelId, labelType, labelColor){
				var message = "<fmt:message key="page.title.project.edit.lables.editTitle"/>";
				var label = document.getElementById("fim" + labelType + labelId).innerHTML;
				message = message.replace("{}", label);
            	$("#editLabelId").val(labelId);
            	$("#editLabelType").val(labelType.toLowerCase());
				$('#editLabelName').val(label);
				$('#editTitle').html(message);
				//setTimeout(function() {
					$('#editLabelColor').simplecolorpicker('selectColor', labelColor);
				//}, 1000);	
				$('#editLabel').modal('show');
			}
			function openAddUser(){
				document.getElementById("addUserForm").reset();
				$('#addUser').modal('show');
			}
			$().ready(function() {
				var prjCode = document.getElementById("projectCode");
				if (prjCode) {
 					preventCopyPasteOnProjectCode(prjCode);
				}

 				$(".component").css('margin-top',"10px");
				$(".labelWrapperEditor").hover(
					function () {
					   $(this).addClass("active");
					},
					function () {
					   $(this).removeClass("active");
					}
				);
				$('select[name="labelColor"]').simplecolorpicker({picker: true, theme: 'glyphicons'});
				// set the placeholder
				var placeholderSupported = ( 'placeholder' in document.createElement('input') );
				if(placeholderSupported){
					<%-- basic --%>
					$("#projectName").attr('placeholder', "<fmt:message key='project.create.projectName.placeholder' />");
					$("#projectCode").attr('placeholder', "<fmt:message key='project.create.projectCode.placeholder' />");
					$("#projectDescription").attr('placeholder', "<fmt:message key='project.create.projectDescription.placeholder' />");
					$("#ownerName").attr('placeholder', "<fmt:message key='project.create.ownerName.placeholder' />");
					
					<%-- Labels --%>
					$("#componentName").attr('placeholder', "<fmt:message key='page.title.project.edit.lables.newComponent.placeholder' />");
					$("#labelName").attr('placeholder', "<fmt:message key='page.title.project.edit.lables.newLabel.placeholder' />");

					<%-- Users --%>
					$("#userSuggestion").attr('placeholder', "<fmt:message key='page.title.project.edit.lables.users.nameOrEmail' />");
				}
				$('#userSuggestion').attr("autocomplete", "off");
				$("[data-toggle='tooltip']").tooltip();
				
				$("#compForm").validate({
					rules: {
						labelName: {
							required: true,
							maxlength: 40
						}
					}
				});
				$("#labelForm").validate({
					rules: {
						labelName: {
							required: true,
							maxlength: 40
						}
					}
				});								
				$("#basicForm").validate({
					rules: {
						"project.name": {
							required: true,
							maxlength: 50
						},
						"project.code": {
							required: true,
							minlength: 2,
							maxlength: 20
						},
						"project.description": {
							maxlength: 2000
						}
					}
				});
				$('#userSuggestion').typeahead({
						minLength : 2,
						source: function(query, process) {
							var url = "${actionBean.usersAutocompleteUrl}";
							url = url.replace("abc", encodeURIComponent(query));
							$.get( url, function( data, textStatus, jqXHR  ) {
								//console.log(textStatus);
								eval(data);
								process(autocompleteRes);
							}, 'script');
						}, 
						displayText: function (item) {
							return item.name;
						},
						matcher : function(item) {
							return true;
						}
				});
				$('#userSuggestion').change(function() {
					var selectedItem = $('#userSuggestion').typeahead("getActive");
					if(selectedItem){
						$('#addUserId').val(selectedItem.id);
					}
				});
			});
		</script>
	</stripes:layout-component>    
    <stripes:layout-component name="content">
	    <div class="row">
	        <div class="span12">
	            <ul class="nav nav-pills" id="projectTabs">
	              <li${actionBean.basicClass}>
	              	<a href="${actionBean.basicLink}" id="tab-basic"><fmt:message key="page.title.project.edit.tab.basic" /></a>
	              </li>
	              <li${actionBean.statesClass}>
	              	<a href="${actionBean.statesLink}" id="tab-states"><fmt:message key="page.title.project.edit.tab.states" /></a>
	              </li>
	              <li${actionBean.labelsClass}>
	              	<a href="${actionBean.labelsLink}" id="tab-labels"><fmt:message key="page.title.project.edit.tab.labels" /></a>
	              </li>
	              <li${actionBean.usersClass}>
	              	<a href="${actionBean.usersLink}" id="tab-users"><fmt:message key="page.title.project.edit.tab.users" /></a>
	              </li>
	            </ul>
	        </div>
	    </div>
	    <stripes:layout-render name="/WEB-INF/pages/cards/project/editProject-${actionBean.focus}.jsp"/>
	</stripes:layout-component>
</stripes:layout-render>