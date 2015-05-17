<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>
	<h1><fmt:message key="page.title.project.edit.basic.title" /></h1>
		<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" focus="projectName" id="compForm" class="form-vertical">
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="projectName" class="control-label"><fmt:message key="project.create.projectName" /></label>
				</div>
				<div class="col-sm-9">
					<stripes:text class="form-control clearable" name="project.name" id="projectName"></stripes:text>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-4">
					<label for="projectCode" class="control-label"><fmt:message key="project.create.projectCode" /></label>
				</div>
				<div class="col-sm-6">
					<stripes:text class="form-control clearable" name="project.code" id="projectCode" onkeypress="return isValidForCode(event);"></stripes:text>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="projectName" class="control-label"><fmt:message key="project.create.projectDescription" /></label>
				</div>
				<div class="col-sm-9">
					<stripes:textarea class="form-control clearable" rows="5" cols="40" name="project.description" id="projectDescription"></stripes:textarea>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-12">
					<stripes:checkbox name="project.hidden" id="hidden"></stripes:checkbox>  <label for="hidden"><fmt:message key="project.create.projectHidden" /></label>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-sm-1">
					<label for="projectName" class="control-label"><fmt:message key="page.title.project.edit.basic.state" /></label>
				</div>
				<div class="col-md-9">
					<stripes:select name="projectState">
						<stripes:options-collection collection="${actionBean.states}" value="id" label="description" />
					</stripes:select>
				</div>
			</div>  
			<div class="row">
				<div class="col-md-1"><stripes:submit class="btn btn-primary" name="update"><fmt:message key="action.submit" /></stripes:submit></div>
				<div class="col-md-1"><stripes:reset class="btn btn-default" name="reset"><fmt:message key="action.reset" /></stripes:reset></div>
			</div>  
		</stripes:form>
</stripes:layout-definition>