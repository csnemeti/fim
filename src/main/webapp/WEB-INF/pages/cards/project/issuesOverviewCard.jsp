<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
       <div class="col-md-3" style="margin-top: 20px; min-width:300px">
       		<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div style="margin-left: 3px ; margin-right: 3px;" class="row">
                    	<div style="float:left;width: 100%">
                        	<div class="medium" title="[localize] Issues Overview"><stripes:label for="projectcard-IssuesOverview"/></div>
                        </div>
                 	</div>
              	</div>
              	<div class="panel-body">
              	Details here...
              	</div>
                <a href="#">
                	<div class="panel-footer">
                    	<span class="pull-left"><stripes:label for="projectcard-ViewDetails"/></span>
                        	<span class="pull-right">
                        	<i class="fa fa-arrow-circle-right"></i>
                        </span>
                        <div class="clearfix"></div>
                     </div>
                </a>
      	</div>
	</div>
</stripes:layout-definition>