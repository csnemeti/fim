<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>
	<h1><fmt:message key="page.title.project.edit.lables.users" /></h1>
		
	<div class="col-md-9">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="pull-left">
					<h4><fmt:message key="page.title.project.edit.lables.users.assigned" /></h4>
				</div>
				<div class="pull-right">
					<button class="btn btn-default" onclick="openAddUser()"><fmt:message key="page.title.project.edit.lables.users.add" /></button>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
			</div>
           	<div class="panel-footer">
           		<span style="font-weight: bold"><fmt:message key="page.title.project.edit.lables.users.totalUsers" />: 0</span>
           	</div>
       	</div>
	</div>
	
	<div id="addUser" class="modal fade">
	  <div class="modal-dialog">
	    <div class="modal-content">
			<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" id="addUserForm" class="form-vertical">  
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="addUserTitle"><fmt:message key="page.title.project.edit.lables.users.addModalTitle" /></h4>
		      </div>
		      <div class="modal-body">
		      	<div class="row form-group">
		      		<div class="col-sm-3">
		      		</div>
		      		<div class="col-sm-9">
						<stripes:text class="form-control typeahead clearable" name="userSuggestion" id="userSuggestion" value=""  />
					</div>
				</div>
				<div class="row form-group">
		      		<div class="col-sm-3">
		      		</div>
		      		<div class="col-sm-9">
						<stripes:select name="newUserRole" id="newUserRole" class="form-control">
							 <stripes:options-collection collection="${actionBean.userRoles}" value="id" label="description"/>
						</stripes:select>
					</div> 
				</div>
					<stripes:hidden name="code" value="${actionBean.code}" />
					<stripes:hidden name="focus" value="${actionBean.focus}" />
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="action.close"/></button>
				<stripes:submit class="btn btn-primary" name="editLabel"><fmt:message key="action.add"/></stripes:submit>
		      </div>
			</stripes:form>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</stripes:layout-definition>