<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: User search">
	<stripes:layout-component name="header">
		<link rel="stylesheet" href="<stripes:url value="/js/bootstrap-multiselect/css/bootstrap-multiselect.css" />" type="text/css"/>
		<link rel="stylesheet" href="<stripes:url value="/js/datatables.net-1.10.4/jquery.dataTables.min.css" />" type="text/css"/>
		<link rel="stylesheet" href="<stripes:url value="/js/datatables.net-1.10.4/jquery-ui.css" />" type="text/css"/>
		<link rel="stylesheet" href="<stripes:url value="/js/datatables.net-1.10.4/jquery.dataTables_themeroller.css" />" type="text/css"/>
		
		<script type="text/javascript" src="<stripes:url value="/js/bootstrap-multiselect/js/bootstrap-multiselect.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/datatables.net-1.10.4/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/datatables.net-1.10.4/dataTables.jqueryui.js" />"></script>
		
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_en.min.js" />"></script>
		
		<script type="text/javascript">
			function clearFormContent(theForm){
				$("#" + theForm.id + " :text").val("");
			}
			
			$().ready(function() {
				$('#roles').multiselect({
					numberDisplayed: 1
				});
			<c:if test="${actionBean.showResults}">				
				var searchResultTable = $('#users').dataTable( {
					<%-- UI change. --%>
				    "jQueryUI" :  true,
			        <%-- Search in table is off. --%>
					"searching" : false,
			        "processing": true,
			        <%-- Default ordering, last name --%>
			        "order": [[ 3, "asc" ]],
			        <%-- Set as items / page options 25, 50, 100 with 25 as default. 
			        First set is the value sent with request, second set represents the values to display. --%>
			        "iDisplayLength": 25,
			        "aLengthMenu": [[25, 50, 100], [25, 50, 100]],
			        <%-- Server side processing and URL for download results. --%>
			        "serverSide": true,
			        "ajax": {
			        	"url" : "<c:url value="${actionBean.resultsUrl}" />",
			        	"type": "POST"
			        },
			        "oLanguage": 
				            {"oPaginate": 
				                  {
				                  "sNext": '&gt;',
				                  "sLast": '&gt;&gt;',
				                  "sFirst": '&lt;&lt;',
				                  "sPrevious": '&lt;'
				                  }
				            //}
	            			},
	            "aoColumns": [
	                          { "mData" : null, "sWidth":"25px", "bSortable": false, "mRender": function (data) {return '';}},
	                          { "mData" : null, "sWidth":"25px", "bSortable": false, "mRender": function (data) {return '222';}},
	                          { "mData" : "firstName"}, 
	                          { "mData" : "lastName"}, 
	                          { "mData" : "email"},
	                          { "mData" : "defaultRole", "bSortable": false}, 
	                          { "mData" : null, "bSortable": false, "mRender": function (data) {return '333';}} 
	                      ]
			    } );
/*				
				searchResultTable.on( 'order.dt search.dt', function () {
					searchResultTable.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
			            cell.innerHTML = i+1;
			        } );
			    } ).draw();
*/			    
			</c:if>
				// validate the comment form when it is submitted
/* 				$("#loginForm").validate({
					rules: {
						password: "required",
						username: "required",
					}
				});
 */			});			
			</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp" />
    </stripes:layout-component>
    <stripes:layout-component name="content">
    	<div id="searchCriteria">    	
        <stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.SearchUsersActionBean" focus="email" id="searchForm">
        	<div class="row">
        		<div class="col-sm-6">
					<div class="form-group form-group-sm">
						<stripes:label class="col-sm-4 control-label" for="userRegistration.firstName"/>
						<div class="col-sm-8">
		     			<stripes:text class="form-control" name="userSearch.firstName" id="firstName"></stripes:text>
		     			</div>
		     		</div>		     	
					<div class="form-group form-group-sm">
						<stripes:label class="col-sm-4 control-label" for="userRegistration.lastName"/> 
						<div class="col-sm-8">
						<stripes:text class="form-control" name="userSearch.lastName" id="lastName"></stripes:text>
						</div>
					</div>
				</div>
        		<div class="col-sm-6">
					<div class="form-group">
						<stripes:label class="col-sm-4 control-label" for="userRegistration.email"/> 
						<div class="col-sm-8">
						<stripes:text class="form-control" name="userSearch.email" id="email"></stripes:text>
						</div>
					</div>
					<div class="form-group">
						<stripes:label class="col-sm-4 control-label" for="userInvite.defaultRole"/> 
						<div class="col-sm-8">
						<stripes:select multiple="multiple" name="userSearch.roles" id="roles">
							<stripes:options-collection collection="${actionBean.defaultRoles}" value="id" label="description"/>
						</stripes:select>		
						</div>				
					</div>
        		</div>
			</div>
        	<div align="center" style="padding-top: 10px">
				<stripes:submit class="btn btn-default" name="search" value="Send..."></stripes:submit>
				<input class="btn btn-default" type="button" name="reset" value="Clear" onclick="clearFormContent(this.form)"/>  
        	</div>
        </stripes:form>	
        </div>
		<c:if test="${actionBean.showResults}">				
        <div id="results" style="width:100%; padding-top:50px">
        	<table id="users" class="table table-striped table-bordered" cellspacing="0" width="100%">
       	        <thead>
		            <tr>
		                <th></th>
		                <th></th>
		                <th>First Name</th>
		                <th>Last Name</th>
		                <th>E-mail</th>
		                <th>Default Role</th>
		                <th></th>
		            </tr>
		        </thead>
       	        <tfoot>
		            <tr>
		                <th></th>
		                <th></th>
		                <th>First Name</th>
		                <th>Last Name</th>
		                <th>E-mail</th>
		                <th>Default Role</th>
		                <th></th>
		            </tr>
		        </tfoot>
        	</table>
        </div>
        </c:if>
    </stripes:layout-component>    
</stripes:layout-render>