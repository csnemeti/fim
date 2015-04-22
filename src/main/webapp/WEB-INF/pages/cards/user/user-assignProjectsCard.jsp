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
              		<c:forEach items="${actionBean.lastLogins}" var="element" varStatus="loop">
              			<c:if test="${ loop.index == 4 }" >
              			<div id="remainingProjects" style="display: none">
              			</c:if> 
		                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
		                    	<div style="float:left;">
		                        	<div class="medium" style="float:left;"><c:out value = "${element}" /></div>
		                        </div>
		                 	</div>
              			<c:if test="${ loop.isLast() && loop.index >= 4 }">
              			</div>
              			</c:if> 
                 	</c:forEach>
              	</div>
              	<div class="panel-footer">
              		<a id="toogleRemainingProjects" href="#" title="<fmt:message key="user.cards.projects.assigned.showMoreLess" />" style="display: none">
	                   	<span class="pull-right">
	                       	<i id="remainingProjectsOnOff" class="fa fa-arrow-circle-down"></i>
	   	                </span>
	   	            </a>
                    <div class="clearfix"></div>
            	</div>
      	</div>
</stripes:layout-definition>