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
	              <li${actionBean.basicClass}>
	              	<a href="${actionBean.basicLink}" id="tab-basic"><fmt:message key="page.title.project.edit.tab.basic" /></a>
	              </li>
	              <li${actionBean.statesClass}>
	              	<a href="${actionBean.statesLink}" id="tab-states"><fmt:message key="page.title.project.edit.tab.states" /></a>
	              </li>
	              <li${actionBean.labelsClass}>
	              	<a href="${actionBean.labelsLink}" id="tab-labels"><fmt:message key="page.title.project.edit.tab.labels" /></a>
	              </li>
	              <li${actionBean.usersClass}>
	              	<a href="${actionBean.usersLink}" id="tab-users"><fmt:message key="page.title.project.edit.tab.users" /></a>
	              </li>
	            </ul>
	        </div>
	    </div>
	    <stripes:layout-render name="/WEB-INF/pages/cards/project/editProject-${actionBean.focus}.jsp"/>
	</stripes:layout-component>
</stripes:layout-render>