<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>
	<h1><fmt:message key="page.title.project.edit.lables.projectComponents" /></h1>
	<div class="col-md-9">
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title="Edit component"><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title="Delete component"><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 1</span>
			</div>
		</div>
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title=""><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title=""><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 2</span>
			</div>
		</div>
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title=""><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title=""><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 3</span>
			</div>
		</div>
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title=""><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title=""><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 4</span>
			</div>
		</div>
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title=""><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title=""><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 5</span>
			</div>
		</div>
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title=""><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title=""><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 6</span>
			</div>
		</div>
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title=""><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title=""><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 7</span>
			</div>
		</div>
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title=""><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title=""><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimComponent">
				<span>The Project Component 8</span>
			</div>
		</div>
	</div>
	<div class="col-md-3">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4><fmt:message key="page.title.project.edit.lables.newComponent" /></h4>
			</div>
			<div class="panel-body">
				<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" focus="name" id="compForm" class="form-vertical">  
					<stripes:text class="form-control clearable" name="name" id="componentName" value=""></stripes:text>
					<div style="width: 10px; height: 5px;"></div>
					<div style="width: 20px; height: 20px; float: left; padding-top: 5px;">
					<stripes:select name="colorpicker">
						 <stripes:options-collection collection="${actionBean.colors}" value="id" label="description" />
					</stripes:select> 
					</div>
					<div style="width: 10px; height: 10px; float: left;"></div>
					<stripes:submit class="btn btn-default" name="createComponent" value="Submit"></stripes:submit>
				</stripes:form>
			</div>
		</div>
	</div>
	<hr class="col-md-12">
	<h1><fmt:message key="page.title.project.edit.lables.projectLabels" /></h1>
	<div class="col-md-9">
		<div class="labelWrapperEditor">
			<div class="actions">
				<a href="#" title="Edit label"><i class="fa fa-pencil fa-2x"></i></a>
				<a href="#" title="Delete label"><i class="fa fa-remove fa-2x"></i></a>
			</div>
			<div style="width: 10px; height: 5px;"></div>
			<div class="fimLabel">
				<span>The Project Label 1</span>
			</div>
		</div>
	</div>
	<div class="col-md-3">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4><fmt:message key="page.title.project.edit.lables.newLabel" /></h4>
			</div>
			<div class="panel-body">
				<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" focus="componentName" id="labelForm" class="form-vertical">  
					<stripes:text class="form-control clearable" name="name" id="labelName" value=""></stripes:text>
					<div style="width: 10px; height: 5px;"></div>
					<div style="width: 20px; height: 20px; float: left; padding-top: 5px;">
					<stripes:select name="colorpicker">
						 <stripes:options-collection collection="${actionBean.colors}" value="id" label="description" />
					</stripes:select> 
					</div>
					<div style="width: 10px; height: 10px; float: left;"></div>
					<stripes:submit class="btn btn-default" name="createLabel" value="Submit"></stripes:submit>
				</stripes:form>
			</div>
		</div>
	</div>
</stripes:layout-definition>