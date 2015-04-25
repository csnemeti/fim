<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>
       <div class="col-md-9" style="margin-top: 20px; min-width:600px">
       		<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div style="margin-left: 3px ; margin-right: 3px;" class="row">
                    	<div style="float:left;width: 100%">
                    		<i class="fa ${actionBean.project.state.stateClass} fa-2x" title="${actionBean.stateTitle}"></i> &nbsp;
                        	<span class="medium" title="<fmt:message key="project.cards.basic.projectName.title" />">${actionBean.project.name}</span>
                        </div>
                 	</div>
              	</div>
              	<div class="panel-body">
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="project.cards.basic.code" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.project.code}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="project.cards.basic.owner" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.project.ownerInfo}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="project.cards.basic.state" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.stateTitle} <span style="font-style:italic">since</span> ${actionBean.formatedLastStateChange} (${actionBean.formatedLastStateChangePeriod})</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="project.cards.basic.description" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.project.description}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="project.cards.basic.created" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.formatedCreateAt} (${actionBean.formatedCreatePeriod})</div>
                        </div>
                 	</div>
                 	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="project.cards.basic.created" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.project.issueFlow.diagram}</div>
                        </div>
                 	</div>
                 	<c:if test="${actionBean.project.hidden}">
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="project.cards.basic.hidden" /></div>
                        </div>
                 	</div>
                 	</c:if>
              	</div>
               	<div class="panel-footer">
                	<a href="#">
                    	<span class="pull-left"><stripes:label for="projectcard-EditDetails"/></span>
                        	<span class="pull-right">
                        	<i class="fa fa-arrow-circle-right"></i>
                        </span>
                        <div class="clearfix"></div>
                	</a>
                </div>
      	</div>
	</div>
</stripes:layout-definition>