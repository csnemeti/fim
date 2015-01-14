<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Project dashboard">
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">       
       <stripes:layout-render name="/WEB-INF/pages/cards/project/basicCard.jsp"/>         	
       <stripes:layout-render name="/WEB-INF/pages/cards/project/issuesOverviewCard.jsp"/>         	
       <stripes:layout-render name="/WEB-INF/pages/cards/project/assignedUsersCard.jsp"/>         	
    </stripes:layout-component>
</stripes:layout-render>