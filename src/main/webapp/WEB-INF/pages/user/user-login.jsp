<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: User Login">
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu-nauth.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
       <stripes:layout-render name="/WEB-INF/pages/cards/user/user-loginCard.jsp"/> 	
    </stripes:layout-component>    
</stripes:layout-render>