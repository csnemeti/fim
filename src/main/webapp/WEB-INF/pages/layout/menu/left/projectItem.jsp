<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>
	<li>
		<a href="">
			<i class="fa fa-sitemap fa-fw"></i>
			<stripes:label for="projectItem" />
			<span class="fa arrow"></span>
		</a>
		<ul class="nav nav-second-level collapse">
			<li>
				<a href="#">
					<i class="fa fa-plus fa-fw"></i>
					<stripes:label for="createProjectItem" />
				</a>
			</li>
			<li>
				<a href="#">
					<i class="fa fa-list-ol fa-fw"></i>
					<stripes:label for="listProjectsItem" />
				</a>
			</li>
		</ul>
	</li>
</stripes:layout-definition>