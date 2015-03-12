<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<style type="text/css">
			img.userState {
				width:  25px;
				height: 25px;
			}
		</style>
	</stripes:layout-component>
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">       
       <stripes:layout-render name="/WEB-INF/pages/cards/user/basicCard.jsp"/>        	
       <stripes:layout-render name="/WEB-INF/pages/cards/user/user-lastLoginsCard.jsp"/>        	
    </stripes:layout-component>
</stripes:layout-render>