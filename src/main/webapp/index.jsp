<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<%@page import="java.util.jar.Attributes"%>
<%@page import="pfa.alliance.fim.util.ManifestUtils"%>
<html>
<style>
	tr.line0{ background-color: lightgray; }
	tr.line1{}
</style>
<body>
<h1>FIM</h1>
<a href="/fim/user/register">Register</a><br />
<a href="/fim/user/login">Login</a><br />
<a href="/fim/user/dashboard?lang=en">Dashboard in EN</a><br />
<a href="/fim/user/dashboard?lang=ro">Dashboard in RO</a><br />
<a href="/fim/project/edit/users">Edit project users</a><br/>
<a href="/fim/project/show/P1">P1 Project dashboard</a><br/>

<h1>Solr</h1>
<a href="/solr/" target="solr">Home</a><br/>
<a href="/solr/active_users/dataimport?command=full-import" target="aufi">FULL index Active users</a><br />
<a href="/solr/active_users/dataimport?command=delta-import" target="audi">DELTA index Active users</a><br />
<a href="/solr/users/dataimport?command=full-import" target="ufi">FULL index Users</a><br />
<a href="/solr/users/dataimport?command=delta-import" target="udi">DELTA index Users</a><br />
<a href="/solr/projects/dataimport?command=full-import" target="pfi">FULL index Projects</a><br />
<a href="/solr/projects/dataimport?command=delta-import" target="pdi">DELTA index Projects</a><br />
<a href="/solr/issues/dataimport?command=full-import" target="ifi">FULL index Issues</a><br />
<a href="/solr/issues/dataimport?command=delta-import" target="idi">DELTA index Issues</a><br />

<h2>FIM Manifest</h2>
<table style="border: 1px solid black">
<%
	Attributes attributes = ManifestUtils.getFimMainAttributes(  );
	if(attributes != null){
	    Iterator iterator = attributes.keySet(  ).iterator(  );
	    int index = 0;
	    while(iterator.hasNext(  )){
			String key = iterator.next(  ).toString();
			Object value = attributes.getValue( key );
%>
	<tr class="line<%= index++ % 2 %>"><td><%= key %></td><td> <%= value %></td></tr>
<%	    
	    }
	}
%>
</table>
</body>
</html>