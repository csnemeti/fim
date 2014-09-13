<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>

<stripes:layout-definition>

<nav class="navbar navbar-default navbar-static-top" role="navigation"style="margin-bottom: 0">
	<div class="navbar-header">
		<a class="navbar-brand" href="index.jsp"><img style="width: 50px; height: 37px"src="images/fim-logo.png"></a>
	</div>

	<!-- TOP MENU SIDE -->
	
	<!-- Left side of the menu -->
	<stripes:layout-render name="/WEB-INF/pages/layout/menu/leftTopMenu.jsp"/>
	
	<form class="navbar-form navbar-left" style="margin-top: 2px" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search issues">
          <a> <i class="fa fa-search fa-fw"></i></a>
        </div>
      </form>
	
	<!-- Right side of the menu -->
	<stripes:layout-render name="/WEB-INF/pages/layout/menu/rightTopMenu.jsp"/>
</nav>

</stripes:layout-definition>