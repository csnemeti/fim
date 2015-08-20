<%@ taglib prefix="stripes" 	uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt"  %>	

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/project-code.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_${actionBean.localeLanguage}.min.js" />"></script>
		<script type="text/javascript">
	        
			function clearFormContent(theForm){
				theForm.reset();
				$(".clearable").val("");
			}
			
			function changeSelectedProject(theSelect){
				$( "#cissuePriority option:selected").removeAttr("selected");
				$( "#changeProject" ).trigger( "click" );
			}
			
			$().ready(function() {
			});
			
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">             	
	<c:if test="${actionBean.createIssueAllowed eq false}">
		<h1 class="error">Issue create: DENIED!</h1>
		<p>You are not allowed to create issues because at least one of the following problems occurred:</p>
		<ul>
			<li>You are not assigned to any project! Given this, you are not allowed to create issue on a project where you're not assigned to.</li>
			<li>You do not have privilege to create issue on projects you are assigned to! You might be assigned at least to one project. For each project assignment, you are played a specific role on that project. It might be happen that your role on every project where you are assigned prevents you creating issues.</li>
		</ul>
	</c:if>  
	<c:if test="${actionBean.createIssueAllowed eq true}">
		<h1><fmt:message key="issue.create.title" /></h1>
		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.issue.CreateIssueActionBean" focus="projectName" id="prjForm" class="form-horizontal">
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="projectId" class="control-label"><fmt:message key="issue.create.selectProject" /></label>
				</div>
				<div class="col-sm-9">
			         <stripes:select name="projectId" onchange='changeSelectedProject(this)'>
			             <stripes:options-collection collection="${actionBean.projects}" value="id" label="description" />
			         </stripes:select> 
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="issueType" class="control-label"><fmt:message key="issue.create.issueType" /></label>
				</div>
				<div class="col-sm-9">
			         <stripes:select name="issueType">
			             <stripes:options-collection collection="${actionBean.issueTypes}" value="id" label="description" />
			         </stripes:select>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="issueTitle" class="control-label"><fmt:message key="issue.create.issueTitle" /></label>
				</div>
				<div class="col-sm-9">
			        <stripes:text name="issueTitle" class="clearable"></stripes:text>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="issueDescription" class="control-label"><fmt:message key="issue.create.issueDescription" /></label>
				</div>
				<div class="col-sm-9">
			        <stripes:textarea name="issueDescription" class="clearable"></stripes:textarea>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="issueEnvironment" class="control-label"><fmt:message key="issue.create.issueEnvironment" /></label>
				</div>
				<div class="col-sm-9">
			        <stripes:textarea name="issueEnvironment" class="clearable"></stripes:textarea>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="issueType" class="control-label"><fmt:message key="issue.create.issuePriority" /></label>
				</div>
				<div class="col-sm-9">
			         <stripes:select name="issuePriority" id="issuePriority">
			             <stripes:options-collection collection="${actionBean.issuePriorities}" value="id" label="description" />
			         </stripes:select>
				</div>
			</div>

			<div class="row form-group error">
 				<stripes:errors></stripes:errors>
			</div>
			<div class="row form-group">
     			
     		</div>
			<stripes:submit class="btn btn-primary" name="create"><fmt:message key="action.submit" /></stripes:submit>
			<stripes:button class="btn btn-default" name="resetBtn" onclick="clearFormContent(this.form)"><fmt:message key="action.clear" /></stripes:button>  
	        <stripes:submit id="changeProject" name="changeProject" style="display: none">Change</stripes:submit>
         </stripes:form> 
	</c:if>  
	</stripes:layout-component>
</stripes:layout-render>