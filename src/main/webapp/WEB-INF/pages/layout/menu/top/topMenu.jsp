<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>

	<ul class="nav navbar-top-links navbar-right">
		
		<!-- PROJECT -->
		<li class="dropdown">
			<div align="right" id="selectedProjectef">
				<div>
					<label class="label label-primary">Issue tracker</label>
					<a id="removeFromSessioned" href="#"><i class="fa fa-times fa-fw"></i></a>
				</div>
			</div>
		</li>
		<!-- USER -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
				<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.EditOwnProfileActionBean"><i class="fa fa-user fa-fw"></i><stripes:label for="userProfileItem"/></stripes:link></li>
				<li><a href="#"><i class="fa fa-gear fa-fw"></i><stripes:label for="userAccountSettings"/></a></li>
				<li class="divider"></li>
				<li><stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.LogoutUserActionBean"><i class="fa fa-sign-out fa-fw"></i><stripes:label for="userLogout"/></stripes:link></li>
			</ul>
		</li>
		
		<!-- LANGUAGES -->
		<li class="dropdown"><a class="dropdown-toggle navbar-flag"
			data-toggle="dropdown" href="#"> <i class="fa fa-flag fa-fw"></i>
				<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a>English</a></li>
				<li><a>Romana</a></li>
			</ul>
		</li>
	</ul>

</stripes:layout-definition>