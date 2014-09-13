<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>

<stripes:layout-definition>
    <html>
        <head>
            <!-- PLUGINS -->
    		<link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
    		<link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">
    		<link rel="stylesheet" href="plugins/metisMenu/css/metisMenu.min.css">
    		<link rel="stylesheet" href="css/menu.css">
    		<link rel="stylesheet" href="css/fim-styles.css">
    	
    		<!-- PLUGINS -->
    		<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
    		<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
    		<script type="text/javascript" src="plugins/metisMenu/js/metisMenu.min.js"></script>
        	<script type="text/javascript" src="js/menu/menu.js"></script>
        
        	<meta charset="utf-8">
        	<meta http-equiv="X-UA-Compatible" content="IE=edge">
        	<meta name="viewport" content="width=device-width, initial-scale=1">
        	
        	<title>${pageTitle}</title>
        </head>
        <body>
        	<!-- MENU -->
        	<stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>
			
			<div id="page-wrapper">
        		<stripes:layout-component name="content"/>
        	</div>
        	
        	<!-- FOOTER -->
        	<stripes:layout-render name="/WEB-INF/pages/layout/footer.jsp"/>
		
        </body>
    </html>
</stripes:layout-definition>