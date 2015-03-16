<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	

<stripes:layout-definition>
	<div role="navigation" class="navbar-default sidebar">
		<div class="sidebar-nav navbar-collapse">
			<ul id="side-menu" class="nav">
				<li>
					<div class="navbar-header" style="height: 84px;">
						<a class="navbar-brand" href="/fim/user/dashboard">
							<img class="fim-logo" src="<c:url value="/images/fim-logo.png" />">
						</a>
					</div>
				</li>

				<!-- SEARCH -->
				<li class="sidebar-search" style="z-index:-1">
					<div class="input-group custom-search-form">
						<input type="text" placeholder="Search issues" class="form-control">
							<span class="input-group-btn">
								<button type="button" class="btn btn-default">
									<i class="fa fa-search"></i>
								</button>
							</span>
					</div>
				</li>
				
				<!-- ISSUES -->
				<li>
					<a href="">
						<i class="fa fa-tasks fa-fw"></i>
						<stripes:label for="issuesItem" />
						<span class="fa arrow"></span>
					</a>
					<ul class="nav nav-second-level collapse">
						<li>
							<a href="#">
								<i class="fa fa-plus fa-fw"></i>
								<stripes:label for="createIssueItem" />
							</a>
						</li>
						<li>
							<a href="#">
								<i class="fa fa-search fa-fw"></i>
								<stripes:label for="searchIssuesItem" />
							</a>
						</li>
					</ul>
				</li>
				
				<!-- AGILE 
				<stripes:layout-render name="/WEB-INF/pages/layout/menu/left/agileItem.jsp"/> -->
				
				<!-- PROJECT -->
				<li>
					<a href="">
						<i class="fa fa-sitemap fa-fw"></i>
						<stripes:label for="projectItem" />
						<span class="fa arrow"></span>
					</a>
					<ul class="nav nav-second-level collapse">
						<li>
							<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.project.SearchProjectsActionBean">
								<i class="fa fa-search fa-fw"></i>
								<stripes:label for="listProjectsItem" />
							</stripes:link>
						</li>
						<li>
							<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.project.CreateProjectActionBean">
								<i class="fa fa-plus fa-fw"></i>
								<stripes:label for="createProjectItem" />
							</stripes:link>
						</li>
					</ul>
				</li>
				<!-- USER -->
				<li>
					<a href="">
						<i class="fa fa-user fa-fw"></i>
						<stripes:label for="userItem" />
						<span class="fa arrow"></span>
					</a>
					<ul class="nav nav-second-level collapse">
						<li>
							<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.SearchUsersActionBean">
								<i class="fa fa-search fa-fw"></i>
								<stripes:label for="searchUsersItem" />
							</stripes:link>				
						</li>
						<li>
							<stripes:link beanclass="pfa.alliance.fim.web.stripes.action.user.InviteUserActionBean">
								<i class="fa fa-envelope-o fa-fw"></i>
								<stripes:label for="inviteUserItem" />
							</stripes:link>				
						</li>
					</ul>
				</li>
				<!-- ADMIN -->
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
			</ul>
			
		</div>
	</div>

</stripes:layout-definition>
