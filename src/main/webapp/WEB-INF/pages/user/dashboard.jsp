<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>	

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<style type="text/css">
			img.userState {
				width:  25px;
				height: 25px;
			}
		</style>
		<script type="text/javascript">
			<%-- Function called when last login toogle=ing ended. --%>
			function lastLoginToogleListener(){
				var newClass = ($( "#remainingLogins" ).is(":visible"))? "fa fa-arrow-circle-up" : "fa fa-arrow-circle-down";
				$( "#remainingLoginsOnOff" ).attr("class", newClass);
			}
			<%-- Function called when assigned projects toogle=ing ended. --%>
			function assignedProjectsToogleListener(){
				var newClass = ($( "#remainingProjects" ).is(":visible"))? "fa fa-arrow-circle-up" : "fa fa-arrow-circle-down";
				$( "#remainingProjectsOnOff" ).attr("class", newClass);
			}
			$(document).ready(function(){
				<%-- If we have the DIV with additional logins (remaining logins from 4 - 10) 
				we should the toogle link and set-up the toogle. --%>
			    if ( $( "#remainingLogins" ).length ) {
			    	$( "#toogleRemainingLogins" ).show();
				    $("#toogleRemainingLogins").click(function(){
				        $("#remainingLogins").slideToggle("slow", lastLoginToogleListener);
				    });
			    }
				<%-- If we have the DIV with additional projects (remaining logins from 4+) 
				we should the toogle link and set-up the toogle. --%>
			    if ( $( "#remainingProjects" ).length ) {
			    	$( "#toogleRemainingProjects" ).show();
				    $("#toogleRemainingProjects").click(function(){
				        $("#remainingProjects").slideToggle("slow", assignedProjectsToogleListener);
				    });
			    }
			});
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">       
       <stripes:layout-render name="/WEB-INF/pages/cards/user/basicCard.jsp"/>    
       <c:if test="${actionBean.showLastLoginCard}">	    	
       		<stripes:layout-render name="/WEB-INF/pages/cards/user/user-lastLoginsCard.jsp"/>
       </c:if>        	
       <c:if test="${actionBean.showLastLoginCard}">	    	
       		<stripes:layout-render name="/WEB-INF/pages/cards/user/user-assignProjectsCard.jsp"/>
       </c:if>        	
    </stripes:layout-component>
</stripes:layout-render>