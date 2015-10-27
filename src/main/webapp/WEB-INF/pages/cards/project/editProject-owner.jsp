<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="/WEB-INF/tlds/security.tld"%>


<stripes:layout-definition>
<c:set var="changeOwner" scope="request" value="false"/>
<security:security checkIfAll="PROJECT_EDIT_PROJECT_CHANGE_OWNER">
	<c:set var="changeOwner" scope="request" value="true"/>
</security:security>
	<c:if test="${ changeOwner == false}">
	<h1><fmt:message key="page.title.project.edit.owner.titleInfo" /></h1>
	</c:if>
	<c:if test="${ changeOwner == true}">
	<h1><fmt:message key="page.title.project.edit.owner.titleChange" /></h1>
	</c:if>
	<div>
	<label>Current owner: </label>
	<span style="font-weight: bold">${actionBean.projectOwner.name}</span>&nbsp;${actionBean.projectOwner.email}</div>
	<c:if test="${ changeOwner == true}">
	<stripes:form  beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" id="changeOwner" class="form-vertical">
	<stripes:hidden name="code" value="${actionBean.code}" />
	<stripes:hidden name="focus" value="${actionBean.focus}" />
	<table>
		<tr><td>
				<label>Change owner: </label>
				<stripes:select name="newOwner">
					<c:forEach items="${actionBean.assignedUsers}" var="element">
						<option value="${element.id}">${element.name}, ${element.email}</option>
					</c:forEach>
				</stripes:select>
			</td>
		</tr>
		<tr><td><stripes:submit name="changeOwner" class="btn btn-primary"><fmt:message key="action.change"/></stripes:submit></td></tr>
	</table>
	</stripes:form>
	</c:if>
</stripes:layout-definition>