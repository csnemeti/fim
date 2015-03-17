<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"  %>



<stripes:layout-definition>

	<ul class="nav navbar-top-links navbar-right">

		<!-- LOG IN --> 
		<li class="dropdown">
			<a href="/fim/user/login" title="<fmt:message key="login.title" />"> 
				<i class="fa fa-sign-in fa-fw"></i>
			</a>
		</li>
		
		<!-- REGISTER --> 
		<li class="dropdown">
			<a href="/fim/user/register" title="<fmt:message key="register.title" />"> 
				<i class="fa fa-plus-square fa-fw"></i>
			</a>
		</li>
		
		<!-- LANGUAGES -->
		<li class="dropdown"><a class="dropdown-toggle navbar-flag"
			data-toggle="dropdown" href="#"> <i class="fa fa-flag fa-fw"></i>
				<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a><img src="<c:url value="/images/blank.gif" />" class="flag flag-us" />&nbsp;English</a></li>
				<li><a><img src="<c:url value="/images/blank.gif" />" class="flag flag-ro" />&nbsp;Română</a></li>
			</ul>
		</li>
	</ul>

</stripes:layout-definition>