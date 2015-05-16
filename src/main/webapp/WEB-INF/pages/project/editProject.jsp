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
		
   		<script type="text/javascript" src="<c:url value="/plugins/bootstrap/js/bootstrap-dialog.min.js" />"></script>
		<script src="<c:url value="/js/jquery.simplecolorpicker/jquery.simplecolorpicker.js" />"></script>
		
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
		                	$("#labelId").val(labelId);
		                	$("#labelType").val(labelType.toLowerCase());
		                	$( "#deleteLabel" ).trigger( "click" );
		                }
		            }
		        });
				
			}
			function editComponent(labelId, labelType, labelColor){
				alert(labelId);
			}
			$().ready(function() {
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
					document.getElementById("componentName").placeholder = "<fmt:message key='page.title.project.edit.lables.newComponent.placeholder' />";
					document.getElementById("labelName").placeholder = "<fmt:message key='page.title.project.edit.lables.newLabel.placeholder' />";
				}
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