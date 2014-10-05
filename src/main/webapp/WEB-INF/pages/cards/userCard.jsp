<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
       <div class="col-md-6" style="margin-top: 20px">
       		<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div style="margin-left: 3px ; margin-right: 3px;"class="row">
                    	<div style="float:left;width: 60%">
                        	<div class="row"><b><stripes:label for="usercard-Username"/></b>${actionBean.username} </div>
                        	<div class="row"><b><stripes:label for="usercard-Email"/></b>${actionBean.userEmail}</div>
                        </div>
                        <div style="float: right;width: 40%"class="text-right">
                        	<div class="medium">${actionBean.lastLoginTime}</div>
                           	<div>${actionBean.sessionAge}</div>
                        </div>
                 	</div>
              	</div>
                <a href="#">
                	<div class="panel-footer">
                    	<span class="pull-left"><stripes:label for="usercard-ViewDetails"/></span>
                        	<span class="pull-right">
                        	<i class="fa fa-arrow-circle-right"></i>
                        </span>
                        <div class="clearfix"></div>
                     </div>
                </a>
      	</div>
	</div>
</stripes:layout-definition>