<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>	

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<link rel="stylesheet" href="<stripes:url value="/js/bootstrap-multiselect/css/bootstrap-multiselect.css" />" type="text/css"/>
		<link rel="stylesheet" href="<stripes:url value="/js/datatables.net-1.10.4/jquery.dataTables.min.css" />" type="text/css"/>
		<link rel="stylesheet" href="<stripes:url value="/js/datatables.net-1.10.4/jquery-ui.css" />" type="text/css"/>
		<link rel="stylesheet" href="<stripes:url value="/js/datatables.net-1.10.4/jquery.dataTables_themeroller.css" />" type="text/css"/>
		<style type="text/css">
			img.userState {
				width:  25px;
				height: 25px;
			}
			.rowIndex {
				text-align: right;
			}
			.noSpacing {
				padding: 3px 5px 3px 5px !important;
			}
		</style>
		
		<script type="text/javascript" src="<stripes:url value="/js/bootstrap-multiselect/js/bootstrap-multiselect.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/datatables.net-1.10.4/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/datatables.net-1.10.4/dataTables.jqueryui.js" />"></script>
		
		<script type="text/javascript">
			var localeMap = ${actionBean.localizationString};
			function clearFormContent(theForm){
				$("#" + theForm.id + " :text").val("");
				$("option:selected").removeAttr("selected");
				$('#roles').multiselect('refresh');
				$('#statuses').multiselect('refresh');
			}
			function localizeText(text){
				return localeMap[text];
			}
			function buildUserStatusImage(userStatus){
				return "<img class='userState' src='<c:url value="/images/user-state/" />" + userStatus.toLowerCase() + ".png' title='" + localizeText(userStatus) + "' />";
			}
			
			$().ready(function() {
				$('#roles').multiselect({
					numberDisplayed: 1,
					nonSelectedText: "<fmt:message key='multiselect.noneSelected' />",
					nSelectedText:   "<fmt:message key='multiselect.nSelectedText' />",
					allSelectedText: "<fmt:message key='multiselect.allSelectedText' />"
				});
				$('#statuses').multiselect({
					numberDisplayed: 1,
					nonSelectedText: "<fmt:message key='multiselect.noneSelected' />",
					nSelectedText:   "<fmt:message key='multiselect.nSelectedText' />",
					allSelectedText: "<fmt:message key='multiselect.allSelectedText' />"
				});
				// set the placeholder
				var placeholderSupported = ( 'placeholder' in document.createElement('input') );
				if(placeholderSupported){
					document.getElementById("firstName").placeholder = "<fmt:message key='userSearch.firstName.placeholder' />";
					document.getElementById("lastName").placeholder = "<fmt:message key='userSearch.lastName.placeholder' />";
					document.getElementById("email").placeholder = "<fmt:message key='userSearch.email.placeholder' />";
				}
				
			<c:if test="${actionBean.showResults}">				
				var searchResultTable = $('#users').dataTable( {
					<%-- UI change. --%>
				    "jQueryUI" :  true,
			        <%-- Search in table is off. --%>
					"searching" : false,
			        <%-- Default ordering, last name --%>
			        "order": [[ 3, "asc" ]],
			        <%-- Set as items / page options 25, 50, 100 with 25 as default. 
			        First set is the value sent with request, second set represents the values to display. --%>
					"bPaginate": true,
			        "iDisplayLength": 25,
			        "aLengthMenu": [[25, 50, 100], [25, 50, 100]],
			        <%-- Server side processing and URL for download results. --%>
			        "serverSide": true,
			        "processing": true,
			        "ajax": {
			        	"url" : "<c:url value="${actionBean.resultsUrl}" />",
			        	"type": "POST"
			        },
			        <%-- Pagination, and localization. http://legacy.datatables.net/usage/i18n --%>
			        "oLanguage": {
			        		"oPaginate": <%-- No localization required. --%>
				                  {
				                  "sNext": '&gt;',
				                  "sLast": '&gt;&gt;',
				                  "sFirst": '&lt;&lt;',
				                  "sPrevious": '&lt;'
				                  },
				            "sInfoThousands": " ", <%-- thousand separator --%>
				    		"sLengthMenu": "<fmt:message key='datatables.sLengthMenu' />",
				    		"sEmptyTable": "<fmt:message key='datatables.search.sEmptyTable' />",
				    		"sInfoEmpty": "<fmt:message key='datatables.search.sInfoEmpty' />",
				    		"sLoadingRecords": "<fmt:message key='datatables.sLoadingRecords' />",
				    		"sInfo": "<fmt:message key='datatables.sInfo' />"
	            	},
	            	<%-- Column definition. --%>
	            	"aoColumns": [
	                          { "mData" : null, "sWidth":"25px", "bSortable": false, "sClass": "rowIndex", "mRender": function (data) { return data.indexInTotalResults + 1;}},
	                          { "mData" : null, "sWidth":"25px", "bSortable": false, "mRender": function (data) {return buildUserStatusImage(data.userStatusAsText);}},
	                          { "mData" : "firstName"}, 
	                          { "mData" : "lastName"}, 
	                          { "mData" : "email"},
	                          { "mData" : null, "bSortable": false, "mRender": function (data) {return localizeText(data.defaultRoleAsText);}}, 
	                          { "mData" : "actions", "sWidth":"110px", "bSortable": false, "sClass": "noSpacing"} 
	                      ]
			    	});
			</c:if>
	    		});
			</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp" />
    </stripes:layout-component>
    <stripes:layout-component name="content">
    	<div id="searchCriteria">    	
        <stripes:form beanclass="pfa.alliance.fim.web.stripes.action.user.SearchUsersActionBean" focus="userSearch.email" id="searchForm">
        	<div class="row">
        		<div class="col-sm-6">
					<div class="form-group form-group-sm">
						<stripes:label class="col-sm-4 control-label" for="userSearch.firstName"/>
						<div class="col-sm-8">
		     			<stripes:text class="form-control input-sm" name="userSearch.firstName" id="firstName" maxlength="100"></stripes:text>
		     			</div>
		     		</div>		     	
		     	</div>
        		<div class="col-sm-6">
					<div class="form-group form-group-sm">
						<stripes:label class="col-sm-4 control-label" for="userSearch.lastName"/> 
						<div class="col-sm-8">
						<stripes:text class="form-control input-sm" name="userSearch.lastName" id="lastName" maxlength="100"></stripes:text>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
        		<div class="col-sm-6">
					<div class="form-group">
						<stripes:label class="col-sm-4 control-label" for="userSearch.email"/> 
						<div class="col-sm-8">
						<stripes:text class="form-control input-sm" name="userSearch.email" id="email" maxlength="200"></stripes:text>
						</div>
					</div>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-sm-6">
					<div class="form-group">
						<stripes:label class="col-sm-4 control-label" for="userSearch.userStatus"/> 
						<div class="col-sm-8">
						<stripes:select multiple="multiple" name="userSearch.statuses" id="statuses">
							<stripes:options-collection collection="${actionBean.defaultStatuses}" value="id" label="description"/>
						</stripes:select>		
						</div>				
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<stripes:label class="col-sm-4 control-label" for="userSearch.defaultRole"/> 
						<div class="col-sm-8">
						<stripes:select multiple="multiple" name="userSearch.roles" id="roles">
							<stripes:options-collection collection="${actionBean.defaultRoles}" value="id" label="description"/>
						</stripes:select>		
						</div>				
					</div>
        		</div>        		
			</div>
        	<div align="center" style="padding-top: 10px">
				<stripes:submit class="btn btn-primary" name="search"></stripes:submit>
				<stripes:button class="btn btn-default" name="reset" onclick="clearFormContent(this.form)"></stripes:button>  
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
		                <th><fmt:message key="userSearch.firstName" /></th>
		                <th><fmt:message key="userSearch.lastName" /></th>
		                <th><fmt:message key="userSearch.email" /></th>
		                <th><fmt:message key="userSearch.defaultRole" /></th>
		                <th><fmt:message key="userSearch.actions" /></th>
		            </tr>
		        </thead>
       	        <tfoot>
		            <tr>
		                <th></th>
		                <th></th>
		                <th><fmt:message key="userSearch.firstName" /></th>
		                <th><fmt:message key="userSearch.lastName" /></th>
		                <th><fmt:message key="userSearch.email" /></th>
		                <th><fmt:message key="userSearch.defaultRole" /></th>
		                <th><fmt:message key="userSearch.actions" /></th>
		            </tr>
		        </tfoot>
        	</table>
        </div>
        </c:if>
    </stripes:layout-component>    
</stripes:layout-render>