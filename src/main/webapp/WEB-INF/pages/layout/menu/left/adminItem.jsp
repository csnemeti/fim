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
				<a href="#">
					<i class="fa fa-plus fa-fw"></i>
					<stripes:label for="createUserItem" />
				</a>
			</li>
			<li>
				<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.InviteUserActionBean">
					<i class="fa fa-envelope-o fa-fw"></i>
					<stripes:label for="inviteUserItem" />
				</stripes:link>				
			</li>
			<li>
				<a href="#">
					<i class="fa fa-search fa-fw"></i>
					<stripes:label for="searchUsersItem" />
				</a>
			</li>
		</ul>
	</li>
</stripes:layout-definition>