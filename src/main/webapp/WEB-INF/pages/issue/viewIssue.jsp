<%@ taglib prefix="stripes" 	uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt"  %>	

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<style type="text/css">
			.left {
				text-align: left;
			}
			.right {
				text-align: right;
			}
			fieldset {
				border: 1px black solid;
			}
			.legendTitle {
				 border: 0px black solid; 
				 margin-left: 30px; 
				 margin-bottom: 0px; 
				 padding: 0px; 
				 width: 150px; 
				 text-align: center;
			}
			.legendAdd {
				 border: 0px black solid; 
				 margin-right: 30px; 
				 margin-bottom: 0px; 
				 padding: 0px; 
				 width: 150px; 
				 text-align: center;
			}
			pre, textarea {
				 width: 100%; 
				 min-height: 20px;
				 padding: 0px 5px; 
				 border: none;
				 background: none;
			}
			.dependIssueName, .childIssueName {
				padding: 0px 5px;
			}
			.dependencyType {
				width: 100px; 
				padding: 0px 5px:
			}
			.dependIssueStatus, .dependIssuePriority, .childIssueStatus, .childIssuePriority {
				width: 100px; 
				padding: 0px 5px:
			}
			.dependIssueAssignedTo, .childIssueAssignedTo {
				width: 200px; 
				padding: 0px 5px;
			}
			.row0 {
				background-color: #F0F0F0;
			}
			.row1 {
			}
			.addLink {
				margin-left: 20px;
				margin-bottom: 10px;
			}
		</style>
		<script type="test/javascript">
			
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
    	<div class="row">
			<h1 style="margin: 0px">${actionBean.title}</h1>
		</div>
    	<ul style="list-style-type:none; padding-left: 0px;">
    		<li><a href="${actionBean.projectLink}">${actionBean.issue.project.name}</a></li>
    		<li>
    			<a href="${actionBean.level1Link}">${actionBean.level1Title}</a>
    			<ul style="list-style-type:none;">
    				<li style="${actionBean.level2Show}">
		    			<a href="${actionBean.level2Link}">${actionBean.level2Title}</a>
		    			<ul style="list-style-type:none;">
		    				<li style="${actionBean.level3Show}">
				    			<a href="${actionBean.level3Link}">${actionBean.level3Title}</a>
				    			<ul style="list-style-type:none;">
				    				<li style="${actionBean.level4Show}">
						    			<a href="${actionBean.level4Link}">${actionBean.level4Title}</a>
						    			<ul style="list-style-type:none;">
						    				<li style="${actionBean.level5Show}">
								    			<a href="${actionBean.level5Link}">${actionBean.level5Title}</a>
								    			<ul style="list-style-type:none;">
								    				<li style="${actionBean.level6Show}">
										    			<a href="${actionBean.level6Link}">${actionBean.level6Title}</a>
										    			<ul style="list-style-type:none;">
										    				<li style="${actionBean.level7Show}">
												    			<a href="${actionBean.level7Link}">${actionBean.level7Title}</a>
										    				</li>
										    			</ul>
								    				</li>
								    			</ul>
						    				</li>
						    			</ul>
				    				</li>
				    			</ul>
		    				</li>
		    			</ul>
    				</li>
    			</ul>
    		</li>
    	</ul>
		<div class="row form-group">
			<div class="col-sm-2">
				<button type="button" class="btn btn-primary"><fmt:message key="action.edit" /></button>
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default">Prev State(s)</button>
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default">Next State(s)</button>				
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default"><fmt:message key="action.assign" /></button>
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default"><fmt:message key="action.print-pdf" /></button>				
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default"><fmt:message key="action.more" /></button>
			</div>			
		</div>    	
		<div class="row form-group">
			<div class="col-sm-4">
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label"><fmt:message key="issue.view.issueType" />:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.issue.type}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label"><fmt:message key="issue.view.issueState" />:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.issue.state.name}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label"><fmt:message key="issue.view.issuePriority" />:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.issue.priority.name}</label>						
				</div>
			</div>    	
			<div class="col-sm-4">
				Column 2: properties
			</div>    	
			<div class="col-sm-4">
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label"><fmt:message key="issue.view.assignedTo" />:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.assignedTo}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label"><fmt:message key="issue.view.createdBy" />:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.createdBy}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label"><fmt:message key="issue.view.createdAt" />:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label" title="${actionBean.createdAtSince}">${actionBean.createdAt}</label>						
				</div>
			</div>
		</div>		
		<div class="col-sm-12">    	
    		<fieldset>
				<legend class="legendTitle"><fmt:message key="issue.view.issueDescription" /></legend>
				<pre>${actionBean.issue.description}</pre>
			</fieldset>
    	</div>
    	<c:if test="${ not empty actionBean.issue.environment}">
		<div class="col-sm-12">     	
    		<fieldset>
				<legend class="legendTitle"><fmt:message key="issue.view.issueEnvironment" /></legend>
				<pre >${actionBean.issue.environment}</pre>
			</fieldset>
    	</div>
    	</c:if>
		<div class="col-sm-12">     	
			<div class="col-sm-4" style="padding-left: 0px">     	
	    		<fieldset>
					<legend class="legendTitle"><fmt:message key="issue.view.components" /></legend>
					<table>
						<tr>
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="btn-group  pull-right" style="padding: 0px 20px 10px 0px">
						<a href="#" class="btn btn-xs btn-default"><fmt:message key="action.add" /></a>
					</div>
				</fieldset>
	    	</div>
			<div class="col-sm-4">     	
	    		<fieldset>
					<legend class="legendTitle"><fmt:message key="issue.view.labels" /></legend>
					<table>
						<tr>
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="btn-group  pull-right" style="padding: 0px 20px 10px 0px">
						<a href="#" class="btn btn-xs btn-default"><fmt:message key="action.add" /></a>
					</div>
				</fieldset>
	    	</div>
			<div class="col-sm-4" style="padding-right: 0px">     	
	    		<fieldset>
					<legend class="legendTitle"><fmt:message key="issue.view.watchList" /></legend>
					<table>
						<tr>
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="btn-group  pull-right" style="padding: 0px 20px 10px 0px">
						<a href="#" class="btn btn-xs btn-default"><fmt:message key="action.add" /></a>
					</div>
				</fieldset>
	    	</div>
	    </div>
	    <c:if test="${ not empty actionBean.dependencies}">
		<div class="col-sm-12">    	
    		<fieldset>
				<legend class="legendTitle"><fmt:message key="issue.view.dependencies" /></legend>
				<table style="width: 100%; margin:5px 0px">
					<tr>
						<th class="dependIssueName"><fmt:message key="issue.view.dependencies.name" /></th>
						<th class="dependencyType"><fmt:message key="issue.view.dependencies.dependency" /></th>
						<th class="dependIssueStatus"><fmt:message key="issue.view.dependencies.state" /></th>
						<th class="dependIssuePriority"><fmt:message key="issue.view.dependencies.priority" /></th>
						<th class="dependIssueAssignedTo"><fmt:message key="issue.view.dependencies.assignedTo" /></th>
					</tr>
					<c:forEach items="${actionBean.dependencies}" var="dependency" varStatus="loop">
					<tr class="row${loop.index % 2}">
						<td class="childIssueName"><a href="${dependency.url}">${dependency.code}: ${dependency.title}</a></td>
						<td class="dependencyType">${dependency.dependencyName}</td>
						<td class="childIssueStatus">${dependency.stateName}</td>
						<td class="childIssuePriority">${dependency.priorityName}</td>
						<td class="childIssueAssignedTo">${dependency.assignedTo}</td>
					</tr>
					</c:forEach>
				</table>
			</fieldset>
    	</div>
    	</c:if>
	    <c:if test="${ not empty actionBean.children}">
		<div class="col-sm-12">    	
    		<fieldset>
				<legend class="legendTitle"><fmt:message key="issue.view.childern" /></legend>
				<table style="width: 100%; margin:5px 0px">
					<tr>
						<th class="childIssueName"><fmt:message key="issue.view.childern.name" /></th>
						<th class="childIssueStatus"><fmt:message key="issue.view.childern.state" /></th>
						<th class="childIssuePriority"><fmt:message key="issue.view.childern.priority" /></th>
						<th class="childIssueAssignedTo"><fmt:message key="issue.view.childern.assignedTo" /></th>
					</tr>
					<c:forEach items="${actionBean.children}" var="child" varStatus="loop">
					<tr class="row${loop.index % 2}">
						<td class="childIssueName"><a href="${child.url}">${child.code}: ${child.title}</a></td>
						<td class="childIssueStatus">${child.stateName}</td>
						<td class="childIssuePriority">${child.priorityName}</td>
						<td class="childIssueAssignedTo">${child.assignedTo}</td>
					</tr>
					</c:forEach>
				</table>
			</fieldset>
    	</div>
    	</c:if>
    	<div class="col-sm-12" style="margin-top: 10px"> 
	    	<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#comments"><fmt:message key="issue.view.comments.title" /></a></li>				 
    			<li><a data-toggle="tab" href="#work"><fmt:message key="issue.view.worklog.title" /></a></li>    			
			</ul>
			<div class="tab-content">
				<div id="comments" class="tab-pane fade in active">
			      <h3><fmt:message key="issue.view.comments.title" /></h3>
			      <p>Add your comment.</p>
			    </div>
				<div id="work" class="tab-pane fade">
			      <h3><fmt:message key="issue.view.worklog.title" /></h3>
			      <p>Here comes the logged work.</p>
			    </div>
			</div>
    	</div>
	</stripes:layout-component>
</stripes:layout-render>	