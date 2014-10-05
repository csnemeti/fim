<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>

<stripes:layout-definition>

<nav class="navbar navbar-default navbar-static-top" role="navigation"style="margin-bottom: 0">
	<!-- MOBILE MENU COLLAPSE -->
	<div class="navbar-header">
    	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        	<span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
           	<span class="icon-bar"></span>
            <span class="icon-bar"></span>
       	</button>
    </div>

	<!-- TOP MENU SIDE -->	
	<stripes:layout-render name="/WEB-INF/pages/layout/menu/top/topMenu-nauth.jsp"/>
	
	<!-- LEFT MENU -->
	<stripes:layout-render name="/WEB-INF/pages/layout/menu/left/leftMenu-minimal.jsp"/>
</nav>

</stripes:layout-definition>