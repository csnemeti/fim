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
				$(".clearable").val("");
			}
			
			$().ready(function() {
			});
			
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">             	
	<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.CreateProjectActionBean" focus="projectName" id="prjForm" class="form-horizontal">
	<c:if test="${actionBean.createIssueAllowed eq false}">
		<h1 class="error">Issue create: DENIED!</h1>
		<p>You are not allowed to create issues because at least one of the following problems occurred:</p>
		<ul>
			<li>You are not assigned to any project! Given this, you are not allowed to create issue on a project where you're not assigned to.</li>
			<li>You do not have privilege to create issue on projects you are assigned to! You might be assigned at least to one project. For each project assignment, you are played a specific role on that project. It might be happen that your role on every project where you are assigned prevents you creating issues.</li>
		</ul>
	</c:if>  
	<c:if test="${actionBean.createIssueAllowed eq true}">
		<h1>Issue create: GRANTED!</h1>
	</c:if>  
	</stripes:form>
	</stripes:layout-component>
</stripes:layout-render>