<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
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
</stripes:layout-definition>