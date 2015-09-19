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
			legend {
				 border: 0px black solid; 
				 margin-left: 30px; 
				 margin-bottom: 0px; 
				 padding: 0px; 
				 width: 150px; 
				 text-align: center;
			}
			textarea {
				 width: 100%; 
				 height: 100%;
				 padding: 0px 5px; 
				 border: none;
			}
		</style>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
    	<ul style="list-style-type:none; padding-left: 0px;">
    		<li><a href="${actionBean.projectLink}">Project: ${actionBean.issue.project.name}</a></li>
    		<li>
    			<a href="">Issue: ${actionBean.title}</a>
    		</li>
    	</ul>
    	<div class="row">
			<h1 style="margin: 0px">${actionBean.title}</h1>
		</div>
		<div class="row form-group">
			<div class="col-sm-2">
				<button type="button" class="btn btn-primary">Edit</button>
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default">Prev State(s)</button>
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default">Next State(s)</button>				
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default">Reassign</button>
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default">Option 5</button>				
			</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-default">More</button>
			</div>			
		</div>    	
		<div class="row form-group">
			<div class="col-sm-4">
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Issue Type:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.issue.type}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Issue State:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.issue.state.name}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Priority:</label>					
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
					<label for="issueType" class="control-label">Assigned To:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.assignedTo}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Created By:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">${actionBean.createdBy}</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Created At:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label" alt="${actionBean.createdAtSince}">${actionBean.createdAt}</label>						
				</div>
			</div>
		</div>		
		<div class="col-sm-12">    	
    		<fieldset>
				<legend>Description</legend>
				<textarea>${actionBean.issue.description}</textarea>
			</fieldset>
    	</div>
		<div class="col-sm-12">     	
    		<fieldset>
				<legend>Environment</legend>
				<textarea>${actionBean.issue.environment}</textarea>
			</fieldset>
    	</div>
		<div class="col-sm-12">     	
			<div class="col-sm-4" style="padding-left: 0px">     	
	    		<fieldset>
					<legend>Components</legend>
					<textarea></textarea>
				</fieldset>
	    	</div>
			<div class="col-sm-4">     	
	    		<fieldset>
					<legend>Labels</legend>
					<textarea></textarea>
				</fieldset>
	    	</div>
			<div class="col-sm-4" style="padding-right: 0px">     	
	    		<fieldset>
					<legend>Watch list</legend>
					<textarea></textarea>
				</fieldset>
	    	</div>
	    </div>
    	<div class="col-sm-12" style="margin-top: 10px"> 
	    	<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#comments">Comments</a></li>
				<!-- 
    			<li><a data-toggle="tab" href="#work">Work logged</a></li>
    			 -->
			</ul>
			<div class="tab-content">
				<div id="comments" class="tab-pane fade in active">
			      <h3>Comments</h3>
			      <p>Add your comment.</p>
			    </div>
				<div id="work" class="tab-pane fade">
			      <h3>Work logged</h3>
			      <p>Here comes the logged work.</p>
			    </div>
			</div>
    	</div>
	</stripes:layout-component>
</stripes:layout-render>	