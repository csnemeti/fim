<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"  %>


<stripes:layout-definition>
       <div class="col-md-8" style="margin-top: 20px; min-width:600px;">
       		<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div style="margin-left: 3px ; margin-right: 3px;" class="row">
                    	<div style="float:left;width: 100%">
                        	<div class="medium" title="<fmt:message key="user.cards.projects.assigned.title" />"><stripes:label for="user.cards.projects.assigned.title"/></div>
                        </div>
                 	</div>
              	</div>
              	<div class="panel-body">
              		<c:if test="${actionBean.assignedProjectsNumber == 0}">
              			<p class="medium"><fmt:message key="user.cards.projects.assigned.none" /></p>
              		</c:if>
              		<c:if test="${actionBean.assignedProjectsNumber > 0}">
              		<table style="width: 100%" class="assignedProjects">
              			<tr style="margin-left: 5px ; margin-right: 5px;" class="row">
              				<th class="medium" style="padding-left: 5px; padding-right: 5px"><fmt:message key="user.cards.projects.assigned.code" /></th>
              				<th class="medium" style="padding-left: 5px; padding-right: 5px"><fmt:message key="user.cards.projects.assigned.name" /></th>
              				<th class="medium" style="padding-left: 5px; padding-right: 5px"><fmt:message key="user.cards.projects.assigned.role" /></th>
              				<th style="padding-left: 5px; padding-right: 5px"></th>
              			</tr>
              		<c:forEach items="${actionBean.assignedProjects}" var="element" varStatus="loop">
              			<c:if test="${ loop.index == 4 }" >
              			</table>
              			<div id="remainingProjects" style="display: none; width: 100%" class="assignedProjects">
              			<table style="width: 100%">
              			</c:if> 
		                	<tr style="margin-left: 5px ; margin-right: 5px;" class="row">
		                    	<td class="medium" style="width: 200px; padding-left: 5px; padding-right: 5px"><i class="fa ${element.stateIcon}" title="${element.stateName}"></i> ${element.code}</td>
		                    	<td class="medium" style="padding-left: 5px; padding-right: 5px">${element.name}</td>
		                    	<td class="medium" style="width: 170px; padding-left: 5px; padding-right: 5px">${element.roleName}</td>
		                    	<td class="medium" style="width: 25px; padding-left: 5px; padding-right: 5px">${element.actions}</td>
		                 	</tr>
              			<c:if test="${ loop.isLast()}">
                 	</table>
              			<c:if test="${ loop.index >= 4 }">
              			</div>
              			</c:if> 
              			</c:if>
                 	</c:forEach>
                 	</c:if>
              	</div>
              	<div class="panel-footer">
              		<span style="font-weight: bold"><fmt:message key="user.cards.projects.assigned.total" />: ${actionBean.assignedProjectsNumber}</span>
              		<a id="toogleRemainingProjects" href="#" title="<fmt:message key="user.cards.projects.assigned.showMoreLess" />" style="display: none">
	                   	<span class="pull-right">
	                       	<i id="remainingProjectsOnOff" class="fa fa-arrow-circle-down"></i>
	   	                </span>
	   	            </a>
                    <div class="clearfix"></div>
            	</div>
      	</div>
</stripes:layout-definition>