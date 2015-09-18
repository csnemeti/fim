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
				 border:none;
			}
		</style>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
    	<ul style="list-style-type:none; padding-left: 0px;">
    		<li><a href="">Project: ${actionBean.issue.project.name}</a></li>
    		<li>
    			<a href="">Issue: ${actionBean.title}</a>
    		</li>
    	</ul>
    	<div class="row">
	    	<div class="col-sm-9">
				<h1 style="margin: 0px">${actionBean.title}</h1>
			</div>
	    	<div class="col-sm-3">
				<button>EDIT</button>
			</div>
		</div>
		<div>
			Actions
		</div>    	
		<div class="row form-group">
			<div class="col-sm-4">
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Issue Type:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">2015-12-12 22:22:22</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Issue State:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">2015-12-12 22:22:22</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Priority:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">2015-12-12 22:22:22</label>						
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
					<label for="issueType" class="control-label">2015-12-12 22:22:22</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Created By:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">2015-12-12 22:22:22</label>						
				</div>
				<div class="col-sm-6 right">
					<label for="issueType" class="control-label">Created At:</label>					
				</div>
				<div class="col-sm-6">
					<label for="issueType" class="control-label">2015-12-12 22:22:22</label>						
				</div>
			</div>
		</div>		
		<div>    	
    		<fieldset>
				<legend>Description</legend>
				<textarea>${actionBean.issue.description}</textarea>
			</fieldset>
    	</div>
		<div>    	
    		<fieldset>
				<legend>Environment</legend>
				<textarea>${actionBean.issue.environment}</textarea>
			</fieldset>
    	</div>
    	<div style="margin-top: 10px">
	    	<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#home">Comments</a></li>
    			<li><a data-toggle="tab" href="#menu1">Work</a></li>
			</ul>
			<div class="tab-content">
				<div id="home" class="tab-pane fade in active">
			      <h3>HOME</h3>
			      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
			    </div>
				<div id="menu1" class="tab-pane fade">
			      <h3>Menu 1</h3>
			      <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			    </div>
			</div>
    	</div>
	</stripes:layout-component>
</stripes:layout-render>	