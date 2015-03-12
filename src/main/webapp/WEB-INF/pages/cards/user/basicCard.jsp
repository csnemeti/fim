<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"  %>


<stripes:layout-definition>
       <div class="col-md-6" style="margin-top: 20px">
       		<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div style="margin-left: 3px ; margin-right: 3px;" class="row">
                    	<div style="float:left;width: 100%">
                    		<img class="userState" title="${actionBean.stateName}" src='<c:url value="${actionBean.stateImage}" />' />&nbsp;
                        	<span class="medium" title="<fmt:message key="user.cards.basic.name.title" />">${actionBean.user.name}</span>
                        </div>
                 	</div>
              	</div>
              	<div class="panel-body">
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="user.cards.basic.name" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.user.name}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="user.cards.basic.login" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.user.login}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="user.cards.basic.email" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.user.email}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="user.cards.basic.status" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px"><img class="userState" title="${actionBean.stateName}" src='<c:url value="${actionBean.stateImage}" />' />&nbsp;${actionBean.stateName}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="user.cards.basic.defaultRole" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.defaultRoleName}</div>
                        </div>
                 	</div>
                 	<c:if test="${actionBean.showLastLoginAndSessionAge}">	
                	<div style="margin-left: 5px ; margin-right: 5px; margin-top: 20px" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="user.cards.basic.lastLogin" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.lastLoginTime}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;"><fmt:message key="user.cards.basic.sessionAge" /></div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.sessionAge}</div>
                        </div>
                 	</div>
                 	</c:if>
              	</div>
              	<div class="panel-footer">
                <stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.EditOwnProfileActionBean" title="${actionBean.editUserProfileTitle}">
                    	<span class="pull-left"><stripes:label for="usercard-EditDetails"/></span>
                        	<span class="pull-right">
                        	<i class="fa fa-arrow-circle-right"></i>
                        </span>
                        <div class="clearfix"></div>
                </stripes:link>
            	</div>
      	</div>
	</div>
</stripes:layout-definition>