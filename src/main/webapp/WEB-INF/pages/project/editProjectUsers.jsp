<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-render name="/WEB-INF/pages/layout/default.jsp" pageTitle="FIM: Hello world">
	<stripes:layout-component name="menu">
       <stripes:layout-render name="/WEB-INF/pages/layout/menu/menu.jsp"/>       
    </stripes:layout-component>
    <stripes:layout-component name="content">
   
   <form style="padding-top: 20px" class="form-inline" role="form" >
		<div class="btn-group">
			<button type="button" class="btn btn-default" id="addUsersLinkfe">
				<i class="fa fa-plus fa-fw"></i>
				Add users
			</button>
		</div>

		<div class="form-group" id="addUserContainer145">
			<label for="selectUser">User:</label>
    		<input class="form-control" id="selectUser" value="" autocomplete="off" placeholder="Search users to add">
		</div>
			
		<button type="submit" class="btn btn-default">
			<i class="fa fa-plus fa-fw"></i>
		</button>
	</form>
      	
	<form class="form-horizontal" role="form" >
		<div style="margin-top: 10px" class="panel panel-default">
        	<div style="padding:5px 15px" class="panel-heading">
             	Users
            </div>
            <div class="panel-body">
				<table class="table table-hover table-condensed">
					<thead>
						<tr class="headers">
							<th><span>Email</span></th>
							<th><span>Username</span></th>
							<th><span>Role</span></th>
							<th><span></span></th>
						</tr>
					</thead>
					<tbody>
					<tr class="even">
						<td><div>fpierce@viva.edu</div></td>
						<td><div>aberry</div></td>
						<td><div>
								<div>
			 						<select>
										<option selected="selected" value="">Developer</option>
										<option value="0">Manager</option>
										<option value="1">User</option>
										<option value="2">Customer</option>
										<option value="3">Scrum master</option>
										<option value="4">Product owner</option>
									</select>
								</div>
							</div>
						</td>
						<td><div><div><a href="#" class="fa fa-times fa-fw"><i></i><label></label></a></div></div></td>
					</tr>
					<tr class="odd">
						<td><div>erodriguez@aibox.edu</div></td>
						<td><div>bhowell</div></td>
						<td><div>
								<div>
			 						<select>
										<option selected="selected" value="">Scrum master</option>
										<option value="0">Manager</option>
										<option value="1">User</option>
										<option value="2">Customer</option>
										<option value="3">Scrum master</option>
										<option value="4">Product owner</option>
									</select>
								</div>
							</div>
						</td>
						<td><div><div><a href="#" class="fa fa-times fa-fw"><i></i><label></label></a></div></div></td>
					</tr>
					</tbody>
			</table>
		</div>
	</div>
</form>
</stripes:layout-component>
</stripes:layout-render>