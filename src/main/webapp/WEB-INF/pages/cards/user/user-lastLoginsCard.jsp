<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"  %>


<stripes:layout-definition>
       <div class="col-md-3" style="margin-top: 20px">
       		<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div style="margin-left: 3px ; margin-right: 3px;" class="row">
                    	<div style="float:left;width: 100%">
                        	<div class="medium" title="<fmt:message key="user.cards.lastlogins.title.title" />"><stripes:label for="user.cards.lastlogins.title"/></div>
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
              	</div>
              	<div class="panel-footer">
              		<a href="#" title="<fmt:message key="user.cards.lastlogins.showMoreLess" />">
	                   	<span class="pull-right">
	                       	<i class="fa fa-arrow-circle-down"></i>
	   	                </span>
	   	            </a>
                    <div class="clearfix"></div>
            	</div>
      	</div>
	</div>
</stripes:layout-definition>