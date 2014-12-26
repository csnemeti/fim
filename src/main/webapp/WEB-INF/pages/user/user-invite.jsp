<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Invite User">
	<stripes:layout-component name="header">
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<stripes:url value="/js/jquery-validation-1.13.0/localization/messages_en.min.js" />"></script>
		<script type="text/javascript">
			function clearFormContent(theForm){
				$("#" + theForm.id + " :text").val("");
				$("#" + theForm.id + " :password").val("");
			}
			
			$().ready(function() {
				// validate the comment form when it is submitted
				$("#regForm").validate({
					rules: {
						email: {
							required: true,
							email: true
						}
					}
				});
			});
			
		</script>
	</stripes:layout-component>

	<stripes:layout-component name="menu">
		<stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp" />
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<stripes:layout-render name="/WEB-INF/pages/cards/user/user-inviteCard.jsp" />
	</stripes:layout-component>
</stripes:layout-render>