<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>
	<h1><fmt:message key="page.title.project.edit.lables.projectComponents" /></h1>
	<div class="col-md-9">
		<c:if test="${actionBean.componentsNumber == 0}">
			<p><fmt:message key="page.title.project.edit.lables.noComponents" /></p>
		</c:if>
		<c:if test="${actionBean.componentsNumber > 0}">
			<c:forEach items="${actionBean.components}" var="element" varStatus="loop">
				<div class="labelWrapperEditor">
					<div class="actions">
						<a href="#" title="<fmt:message key="page.title.project.edit.lables.editComponent" />"   onclick="editComponent(${element.id}, 'Component', '${element.textColor}')"><i class="fa fa-pencil fa-2x"></i></a>
						<a href="#" title="<fmt:message key="page.title.project.edit.lables.deleteComponent" />" onclick="deleteComponent(${element.id}, 'Component')"><i class="fa fa-remove fa-2x"></i></a>
					</div>
					<div style="width: 10px; height: 5px;"></div>
					<div class="fimComponent" style="background-color: ${element.backgroundColor}; color: ${element.textColor};">
						<span id="fimComponent${element.id}">${element.componentName}</span>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<div class="col-md-3">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4><fmt:message key="page.title.project.edit.lables.newComponent" /></h4>
			</div>
			<div class="panel-body">
				<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" focus="labelName" id="compForm" class="form-vertical">  
					<stripes:text class="form-control clearable" name="labelName" id="componentName" value="" maxlength="40" />
					<div style="width: 10px; height: 5px;"></div>
					<div style="width: 20px; height: 20px; float: left; padding-top: 5px;">
					<stripes:select name="labelColor">
						 <stripes:options-collection collection="${actionBean.colors}" value="id" label="description" />
					</stripes:select> 
					</div>
					<div style="width: 10px; height: 10px; float: left;"></div>
					<stripes:hidden name="code" value="${actionBean.code}" />
					<stripes:hidden name="focus" value="${actionBean.focus}" />
					<stripes:submit class="btn btn-default" name="createComponent" value="Submit"></stripes:submit>
				</stripes:form>
			</div>
		</div>
	</div>
	<hr class="col-md-12">
	<h1><fmt:message key="page.title.project.edit.lables.projectLabels" /></h1>
	<div class="col-md-9">
		<c:if test="${actionBean.labelsNumber == 0}">
			<p><fmt:message key="page.title.project.edit.lables.noLabels" /></p>
		</c:if>
		<c:if test="${actionBean.labelsNumber > 0}">
			<c:forEach items="${actionBean.labels}" var="element" varStatus="loop">
				<div class="labelWrapperEditor">
					<div class="actions">
						<a href="#" title="<fmt:message key="page.title.project.edit.lables.editLabel" />"   onclick="editComponent(${element.id}, 'Label', '${element.backgroundColor}')"><i class="fa fa-pencil fa-2x"></i></a>
						<a href="#" title="<fmt:message key="page.title.project.edit.lables.deleteLabel" />" onclick="deleteComponent(${element.id}, 'Label')"><i class="fa fa-remove fa-2x"></i></a>
					</div>
					<div style="width: 10px; height: 5px;"></div>
					<div class="fimLabel" style="background-color: ${element.backgroundColor}; color: ${element.textColor}; border-color: ${element.backgroundColor};">
						<span id="fimLabel${element.id}">${element.label}</span>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<div class="col-md-3">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4><fmt:message key="page.title.project.edit.lables.newLabel" /></h4>
			</div>
			<div class="panel-body">
				<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" id="labelForm" class="form-vertical">  
					<stripes:text class="form-control clearable" name="labelName" id="labelName" value="" maxlength="40" />
					<div style="width: 10px; height: 5px;"></div>
					<div style="width: 20px; height: 20px; float: left; padding-top: 5px;">
					<stripes:select name="labelColor">
						 <stripes:options-collection collection="${actionBean.colors}" value="id" label="description" />
					</stripes:select> 
					</div>
					<div style="width: 10px; height: 10px; float: left;"></div>
					<stripes:hidden name="code" value="${actionBean.code}" />
					<stripes:hidden name="focus" value="${actionBean.focus}" />
					<stripes:submit class="btn btn-default" name="createLabel" value="Submit"></stripes:submit>
				</stripes:form>
			</div>
		</div>
	</div>
	<div>
		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" id="deleteForm" class="form-vertical">  
			<stripes:hidden name="labelType" id="labelType" value="" />
			<stripes:hidden name="labelId" id="labelId" value="" />
			<stripes:hidden name="code" value="${actionBean.code}" />
			<stripes:hidden name="focus" value="${actionBean.focus}" />
			<stripes:submit class="btn btn-default" name="deleteLabel" id="deleteLabel" value="Submit"></stripes:submit>
		</stripes:form>
	</div>
</stripes:layout-definition>