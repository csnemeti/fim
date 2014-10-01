<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>

<!-- TRY TO REDIRECT TO DASHBOARD PAGE -->
<!--c:redirect url="http://localhost:8080/fim/user/dashboard"/-->

<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Hello world">
    <stripes:layout-component name="content">
       
       <stripes:layout-render name="/WEB-INF/pages/cards/userCard.jsp"/>
        	
    </stripes:layout-component>
</stripes:layout-render>
