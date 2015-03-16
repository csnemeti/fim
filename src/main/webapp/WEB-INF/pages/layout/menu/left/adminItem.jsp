<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
	<li>
		<a href="">
			<i class="fa fa-key fa-fw"></i>
			<stripes:label for="adminItem" />
			<span class="fa arrow"></span>
		</a>
		<ul class="nav nav-second-level collapse">
			<li>
				<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.EditOwnProfileActionBean">
					<i class="fa fa-user fa-fw"></i>
					<stripes:label for="userProfileItem"/>
				</stripes:link>
			</li>
			<li>
				<a href="#">
					<i class="fa fa-gear fa-fw"></i>
					<stripes:label for="userAccountSettings"/>
				</a>
			</li>
		</ul>
	</li>
</stripes:layout-definition>