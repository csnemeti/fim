<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Hello world">
    <stripes:layout-component name="content">
       <div class="col-md-6">
        		<h1>Hello F.I.M.</h1>
        		<p>
            		If you can read this it means <span style="font-weight: bold">it works</span>!
        		</p>
        </div>
    </stripes:layout-component>
</stripes:layout-render>