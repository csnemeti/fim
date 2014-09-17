<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
	<div role="navigation" class="navbar-default sidebar">
		<div class="sidebar-nav navbar-collapse">
			<ul id="side-menu" class="nav">
				<li>
					<div class="navbar-header" style="height: 84px;">
						<stripes:link class="navbar-brand" beanclass="pfa.alliance.fim.stripes.action.RedirectActionBean" event="goToDashboard">
							<img class="fim-logo" src="images/fim-logo.png">
						</stripes:link>
					</div>
				</li>

				<!-- SEARCH -->
				<stripes:layout-render name="/WEB-INF/pages/layout/menu/left/searchItem.jsp"/>
				
				<!-- ISSUES -->
				<stripes:layout-render name="/WEB-INF/pages/layout/menu/left/issuesItem.jsp"/>
				
				<!-- AGILE -->
				<stripes:layout-render name="/WEB-INF/pages/layout/menu/left/agileItem.jsp"/>
				
				<!-- PROJECT -->
				<stripes:layout-render name="/WEB-INF/pages/layout/menu/left/projectItem.jsp"/>
				
				<!-- ADMIN -->
				<stripes:layout-render name="/WEB-INF/pages/layout/menu/left/adminItem.jsp"/>
			</ul>
			
		</div>
		<!-- /.sidebar-collapse -->
	</div>

</stripes:layout-definition>
