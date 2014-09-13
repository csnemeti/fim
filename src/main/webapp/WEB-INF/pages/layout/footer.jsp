<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>

	<div class="footer">
		<stripes:form beanclass="pfa.alliance.fim.stripes.action.FooterActionBean">
			<p align="center">
				FIM version ${actionBean.version} distributed under <a>Apache License</a>.The site is
				using cookies, <a>Check here</a>!
			</p>
			<p align="center">Contact: .... About: ....</p>
		</stripes:form>
	</div>

</stripes:layout-definition>