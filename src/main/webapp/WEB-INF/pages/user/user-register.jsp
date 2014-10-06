<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Register User">
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
//					},
//					messages: {
//						firstname: "Please enter your firstname",
//						lastname: "Please enter your lastname",
//						username: {
//							required: "Please enter a username",
//							minlength: "Your username must consist of at least 2 characters"
//						},
//						password: {
//							required: "Please provide a password",
//							minlength: "Your password must be at least 5 characters long"
//						},
//						confirm_password: {
//							required: "Please provide a password",
//							minlength: "Your password must be at least 5 characters long",
//							equalTo: "Please enter the same password as above"
//						},
//						email: "Please enter a valid email address",
//						agree: "Please accept our policy"
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