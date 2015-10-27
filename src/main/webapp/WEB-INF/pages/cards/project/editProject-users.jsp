<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="security" uri="/WEB-INF/tlds/security.tld"%>
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
					<security:security checkIfAny="PROJECT_EDIT_PROJECT_ASSIGN_PA, PROJECT_EDIT_PROJECT_ASSIGN_PO, PROJECT_EDIT_PROJECT_ASSIGN_SM">
					<button class="btn btn-default" onclick="openAddUser()"><fmt:message key="page.title.project.edit.lables.users.add" /></button>
					</security:security>
				</div>
				<div class="clearfix"></div>
			</div>
			<c:if test="${actionBean.assignedUsersNumber > 0}">
			<div class="panel-body">
	        	<table id="users" class="table table-striped table-bordered" cellspacing="0" width="100%">
	       	        <thead>
			            <tr>
			                <th></th>
			                <th><fmt:message key="userSearch.firstName" /></th>
			                <th><fmt:message key="userSearch.lastName" /></th>
			                <th><fmt:message key="userSearch.email" /></th>
			                <th><fmt:message key="userSearch.defaultRole" /></th>
			                <th><fmt:message key="userSearch.actions" /></th>
			            </tr>
			        </thead>
			        <tbody>
			        <c:forEach items="${actionBean.assignedUsers}" var="element" varStatus="loop">
			        	<tr>
			        		<td class="rowIndex">${ element.indexInTotalResults }</td>
			        		<td>${ element.firstName }</td>
			        		<td>${ element.lastName }</td>
			        		<td>${ element.email }</td>
			        		<td>${ element.localizedDefaulRole }</td>
			        		<td class="actions">
			        		<security:security checkIfAll="PROJECT_EDIT_PROJECT_ASSIGN_USER">
			        			${ element.actions }
			        		</security:security>
			        		</td>
			        	</tr>
			        </c:forEach>
			        </tbody>
			        <!-- 
	       	        <tfoot>
			            <tr>
			                <th></th>
			                <th></th>
			                <th><fmt:message key="userSearch.firstName" /></th>
			                <th><fmt:message key="userSearch.lastName" /></th>
			                <th><fmt:message key="userSearch.email" /></th>
			                <th><fmt:message key="userSearch.defaultRole" /></th>
			                <th><fmt:message key="userSearch.actions" /></th>
			            </tr>
			        </tfoot>
			         -->
	        	</table>			
			</div>
			</c:if>
           	<div class="panel-footer">
           		<span style="font-weight: bold"><fmt:message key="page.title.project.edit.lables.users.totalUsers" />: ${actionBean.assignedUsersNumber}</span>
           	</div>
       	</div>
	</div>
	
	<div id="addUser" class="modal fade">
	  <div class="modal-dialog">
	    <div class="modal-content">
			<stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.EditProjectActionBean" id="addUserForm" class="form-vertical" focus="userSuggestion">  
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="addUserTitle"><fmt:message key="page.title.project.edit.lables.users.addModalTitle" /></h4>
		      </div>
		      <div class="modal-body">
		      	<div class="row form-group">
		      		<div class="col-sm-3" style="text-align: right">
		      			<label><fmt:message key="page.title.project.edit.lables.users.newName"/></label>
		      		</div>
		      		<div class="col-sm-9">
						<stripes:text class="form-control typeahead clearable" name="userSuggestion" id="userSuggestion" value=""  />
					</div>
				</div>
				<div class="row form-group">
		      		<div class="col-sm-3" style="text-align: right">
		      			<label><fmt:message key="page.title.project.edit.lables.users.newRole"/></label>
		      		</div>
		      		<div class="col-sm-9">
						<stripes:select name="newUserRole" id="newUserRole" class="form-control">
							 <stripes:options-collection collection="${actionBean.userRoles}" value="id" label="description"/>
						</stripes:select>
					</div> 
				</div>
					<stripes:hidden name="userId" id="addUserId" value="" />
					<stripes:hidden name="code" value="${actionBean.code}" />
					<stripes:hidden name="focus" value="${actionBean.focus}" />
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="action.close"/></button>
				<stripes:submit class="btn btn-primary" name="addUser"><fmt:message key="action.add"/></stripes:submit>
		      </div>
			</stripes:form>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</stripes:layout-definition>