<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>

<stripes:layout-definition>

<nav class="navbar navbar-default navbar-static-top" role="navigation"style="margin-bottom: 0">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="index.jsp">FIM LOGO</a>
	</div>

	<!-- TOP MENU SIDE -->
	
	<!-- Left side of the menu -->
	<stripes:layout-render name="/layout/menu/leftTopMenu.jsp"/>
	
	<!-- Right side of the menu -->
	<stripes:layout-render name="/layout/menu/rightTopMenu.jsp"/>
</nav>

</stripes:layout-definition>