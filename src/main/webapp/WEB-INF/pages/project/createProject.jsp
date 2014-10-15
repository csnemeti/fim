<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Hello world">
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">             	
	<form class="form-horizontal" role="form" >
		<div class="">
			<div class="form-group">
				<label for="projectName" class="col-sm-2 control-label">Name:</label>
				<input id="projectName" type="text" class="form-control" value="" name="projectName" placeholder="Enter a name for the project">
			</div>
			
			<div class="form-group">
				<label for="projectFriendlyId" class="col-sm-2 control-label">Friendly id:</label>
				<input id="projectFriendlyId" type="text" class="form-control" value="" name="projectName" placeholder="Enter a ID for the project">
			</div>
				
			<div class="form-group">
				<label for="projectName" class="col-sm-2 control-label">Description:</label>
				<textarea class="form-control" rows="5" cols="40" name="projectDescription" placeholder="Enter a description for the project"></textarea>
			</div>
			
			<div class="form-group">
				<label for="projectOwner" class="col-sm-2 control-label">Project owner:</label>
				<input id="projectOwner" class="form-control" rows="5" cols="40" name="projectOwner" placeholder="Enter the project owner">
			</div>
		</div>
	</form>
	</stripes:layout-component>
</stripes:layout-render>