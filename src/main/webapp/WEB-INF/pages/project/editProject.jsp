<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
	    <div class="row">
	        <div class="span12">
	            <ul class="nav nav-pills" id="projectTabs">
	              <li class="active"><a id="tab-basic" href="#basic" data-toggle="tab">Basic</a></li>
	              <li><a href="#states" id="tab-states" data-toggle="tab">Issue states</a></li>
	              <li><a href="#labels" id="tab-labels" data-toggle="tab">Components & Labels</a></li>
	              <li><a href="#users" id="tab-users" data-toggle="tab">Users</a></li>
	            </ul>
	            
	            <div class="tab-content">
	              	<div class="tab-pane active" id="basic">
	                	<p>You're in basic data</p>
                	</div>
	              	<div class="tab-pane" id="states">
	                  	<p>You're on issue states</p> 
	                </div>
	              	<div class="tab-pane" id="labels">
	                  	<p>Now on components & labels.</p>
	                </div>
	              	<div class="tab-pane" id="users">
	                  	<p>Now users</p>
	                </div>
	            </div>
	        </div>
	    </div>
	</stripes:layout-component>
</stripes:layout-render>