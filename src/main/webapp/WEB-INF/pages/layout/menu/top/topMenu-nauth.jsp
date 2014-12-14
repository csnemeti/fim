<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<stripes:layout-definition>

	<ul class="nav navbar-top-links navbar-right">

				<!-- LOG IN --> 
		<li class="dropdown">
			<a href="/fim/user/login"> 
				<i class="fa fa-sign-in fa-fw"></i>
			</a>
		</li>
		
		<!-- REGISTER --> 
		<li class="dropdown">
			<a href="/fim/user/register"> 
				<i class="fa fa-plus-square fa-fw"></i>
			</a>
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