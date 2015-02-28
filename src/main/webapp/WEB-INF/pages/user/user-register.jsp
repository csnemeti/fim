<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="${actionBean.title}">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_${actionBean.localeLanguage}.min.js" />"></script>
		<script type="text/javascript">
			function clearFormContent(theForm){
				$("#" + theForm.id + " :text").val("");
				$("#" + theForm.id + " :password").val("");
			}
			
			$().ready(function() {
				// validate the comment form when it is submitted
				$("#regForm").validate({
					rules: {
						firstName: "required",
						lastName: "required",
						password: {
							required: true,
							minlength: 6
						},
						password2: {
							required: true,
							minlength: 6,
							equalTo: "#password"
						},
						email: {
							required: true,
							email: true
						}
						//agree: "required"
					}
				});
			});
			
		</script>
	</stripes:layout-component>

	<stripes:layout-component name="menu">
		<stripes:layout-render name="/WEB-INF/pages/layout/menu/menu-nauth.jsp" />
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<stripes:layout-render name="/WEB-INF/pages/cards/user/user-registerCard.jsp" />
	</stripes:layout-component>
</stripes:layout-render>