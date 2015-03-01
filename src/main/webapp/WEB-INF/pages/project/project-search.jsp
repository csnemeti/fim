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
				$('#states').multiselect({
					numberDisplayed: 1,
					nonSelectedText: "<fmt:message key='multiselect.noneSelected' />"
				});
				// set the placeholder
				var placeholderSupported = ( 'placeholder' in document.createElement('input') );
				if(placeholderSupported){
					document.getElementById("code").placeholder = "<fmt:message key='projectSearch.code.placeholder' />";
					document.getElementById("name").placeholder = "<fmt:message key='projectSearch.name.placeholder' />";
				}
				
				<c:if test="${actionBean.showResults}">
				</c:if>
			});
		</script>		
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp" />
    </stripes:layout-component>
    <stripes:layout-component name="content">
    	<div id="searchCriteria">    	
        <stripes:form beanclass="pfa.alliance.fim.web.stripes.action.project.SearchProjectsActionBean" focus="projectSearch.code" id="searchForm">
        	<div class="row">
        		<div class="col-sm-6">
					<div class="form-group form-group-sm">
						<stripes:label class="col-sm-4 control-label" for="projectSearch.code"/>
						<div class="col-sm-8">
		     			<stripes:text class="form-control input-sm" name="projectSearch.code" id="code" maxlength="100"></stripes:text>
		     			</div>
		     		</div>		     	
		     	</div>
        		<div class="col-sm-6">
					<div class="form-group form-group-sm">
						<stripes:label class="col-sm-4 control-label" for="projectSearch.name"/> 
						<div class="col-sm-8">
						<stripes:text class="form-control input-sm" name="projectSearch.name" id="name" maxlength="100"></stripes:text>
						</div>
					</div>
				</div>
			</div>
        	<div class="row">
        		<div class="col-sm-6">
					<div class="form-group">
						<stripes:label class="col-sm-4 control-label" for="projectSearch.states"/> 
						<div class="col-sm-8">
						<stripes:select multiple="multiple" name="projectSearch.states" id="states">
							<stripes:options-collection collection="${actionBean.defaultStates}" value="id" label="description"/>
						</stripes:select>		
						</div>				
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<stripes:checkbox class="" name="hidden" id="hidden"></stripes:checkbox> <fmt:message key="projectSearch.showHidden" />			
					</div>
        		</div>        		
			</div>
        	<div align="center" style="padding-top: 10px">
				<stripes:submit class="btn btn-default" name="search"></stripes:submit>
				<stripes:button class="btn btn-default" name="reset" onclick="clearFormContent(this.form)"></stripes:button>  
        	</div>
        </stripes:form>	
		</div>
	</stripes:layout-component>
</stripes:layout-render>