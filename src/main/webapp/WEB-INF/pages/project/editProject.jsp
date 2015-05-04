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
	              <li class="active"><a id="tab-1" href="#one" data-toggle="tab">TAB #1</a></li>
	              <li><a href="#two" id="tab-2" data-toggle="tab">TAB #2</a></li>
	              <li><a href="#three" id="tab-3" data-toggle="tab">TAB #3</a></li>
	            </ul>
	            
	            <div class="tab-content">
	              <div class="tab-pane active" id="one">
	                  <p>You're in Tab #1</p>
	                </div>
	              <div class="tab-pane" id="two">
	                  <p>You're on #2</p> 
	                </div>
	              <div class="tab-pane" id="three">
	                  <p>Now #3.</p>
	                </div>
	            </div>
	        </div>
	    </div>
	</stripes:layout-component>
</stripes:layout-render>