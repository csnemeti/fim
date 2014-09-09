<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>

	<ul class="nav navbar-top-links navbar-left">
		<!-- ISSUES -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"><stripes:label for="issuesItem" /><i
				class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i> </a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#"><i class="fa fa-plus fa-fw"></i>
					<stripes:label for="createIssueItem" /></a></li>
				<li><a href="#"><i class="fa fa-search fa-fw"></i>
					<stripes:label for="searchIssuesItem" /></a></li>
			</ul></li>
		<!-- Agile -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"> <stripes:label for="agileItem" />
				<i class="fa fa-bolt fa-fw"></i>
		</a></li>
		<!-- Project -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"> <stripes:label for="projectItem" />
				<i class="fa fa-sitemap fa-fw"></i> <i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#"><i class="fa fa-plus fa-fw"></i> <stripes:label
							for="createProjectItem" /></a></li>
				<li><a href="#"><i class="fa fa-list-ol fa-fw"></i> <stripes:label
							for="listProjectsItem" /></a></li>
			</ul></li>
		<!-- Admin -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"><stripes:label for="adminItem" /><i
				class="fa fa-key fa-fw"></i> <i class="fa fa-caret-down"></i> </a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#"><i class="fa fa-plus fa-fw"></i>
					<stripes:label for="createUserItem" /></a></li>
				<li><a href="#"><i class="fa fa-envelope-o fa-fw"></i>
					<stripes:label for="inviteUserItem" /></a></li>
				<li><a href="#"><i class="fa fa-search fa-fw"></i>
					<stripes:label for="searchUsersItem" /></a></li>
			</ul></li>
	</ul>

</stripes:layout-definition>