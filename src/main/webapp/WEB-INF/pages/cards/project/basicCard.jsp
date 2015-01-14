<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
       <div class="col-md-9" style="margin-top: 20px; min-width:600px">
       		<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div style="margin-left: 3px ; margin-right: 3px;" class="row">
                    	<div style="float:left;width: 100%">
                        	<div class="medium" title="[localize] project name">${actionBean.project.name}</div>
                        </div>
                 	</div>
              	</div>
              	<div class="panel-body">
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;">Code</div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.project.code}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;">Owner</div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.project.ownerInfo}</div>
                        </div>
                 	</div>
                	<div style="margin-left: 5px ; margin-right: 5px;" class="row">
                    	<div style="float:left;">
                        	<div class="medium" style="float:left;">Description</div>
                        	<div class="medium" style="flow:left;margin-left: 150px">${actionBean.project.description}</div>
                        </div>
                 	</div>
              	</div>
                <a href="#">
                	<div class="panel-footer">
                    	<span class="pull-left"><stripes:label for="projectcard-EditDetails"/></span>
                        	<span class="pull-right">
                        	<i class="fa fa-arrow-circle-right"></i>
                        </span>
                        <div class="clearfix"></div>
                     </div>
                </a>
      	</div>
	</div>
</stripes:layout-definition>