<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<stripes:layout-definition>
	<!DOCTYPE html>
    <html>
        <head>
        	<link rel="shortcut icon" type="image/png" href="<c:url value="/images/favicon.ico" />"/>
        
            <!-- PLUGINS -->
    		<link rel="stylesheet" href="<c:url value="/plugins/bootstrap/css/bootstrap.min.css" />"/>
    		<link rel="stylesheet" href="<c:url value="/plugins/font-awesome/css/font-awesome.min.css" />"/>
    		<link rel="stylesheet" href="<c:url value="/plugins/metisMenu/css/metisMenu.min.css" />"/>
    		<link rel="stylesheet" href="<c:url value="/plugins/flags/flags.css" />"/>
    		<link rel="stylesheet" href="<c:url value="/css/menu.css" />"/>
    		<link rel="stylesheet" href="<c:url value="/css/fim-styles.css" />"/>
    	
    		<!-- PLUGINS -->
    		<script type="text/javascript" src="<c:url value="/js/jquery-2.1.1.min.js" />"></script>
    		<script type="text/javascript" src="<c:url value="/plugins/bootstrap/js/bootstrap.min.js" />"></script>
    		<script type="text/javascript" src="<c:url value="/plugins/metisMenu/js/metisMenu.min.js" />"></script>
        	<script type="text/javascript" src="<c:url value="/js/menu/menu.js" />"></script>
        
        	<meta charset="utf-8">
        	<meta http-equiv="X-UA-Compatible" content="IE=edge">
        	<meta name="viewport" content="width=device-width, initial-scale=1">
        	
        	<title>${pageTitle}</title>
        	
        	<stripes:layout-component name="header"/>
        </head>
        <body>
        	<!-- MENU -->
        	<stripes:layout-component name="menu"/>
			
			<div id="page-wrapper">
        		<stripes:layout-component name="content"/>
        	</div>
        	
        	<!-- FOOTER -->
        	<stripes:layout-render name="/WEB-INF/pages/layout/footer.jsp"/>
		
        </body>
    </html>
</stripes:layout-definition>