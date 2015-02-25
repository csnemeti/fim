<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<stripes:layout-definition>

	<div class="footer">
		<p align="center">
			<fmt:message key="footer.version" /> ${actionBean.version} distributed under <a>Apache License</a>.
			<fmt:message key="footer.this.site.use.cookies" />, <a>Check here</a>!
		</p>
		<p align="center">Contact: .... About: ....</p>
	</div>

</stripes:layout-definition>