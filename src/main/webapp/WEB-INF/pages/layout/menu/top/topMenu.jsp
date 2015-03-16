<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<stripes:layout-definition>

	<ul class="nav navbar-top-links navbar-right">
		
		<li class="dropdown">
			<img style="width:24px" class="img-rounded" src="<c:url value="/images/user.jpg" />"/>
		</li>
		
		<!-- LANGUAGES -->
		<li class="dropdown"><a class="dropdown-toggle navbar-flag"
			data-toggle="dropdown" href="#"> <i class="fa fa-flag fa-fw"></i>
				<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a>English <img src="blank.gif" class="flag flag-us" /></a></li>
				<li><a>Romana <img src="blank.gif" class="flag flag-ro" /></a></li>
			</ul>
		</li>
		
		<li class="dropdown"><a class="dropdown-toggle navbar-flag"
			 href="/fim/user/logout"> <i class="fa fa-sign-out fa-fw"></i>
			</a>
		</li>
		
		
	</ul>

</stripes:layout-definition>