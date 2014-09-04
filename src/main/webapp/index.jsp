<!DOCTYPE html>
<html lang="en">
    <head>
    	<link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">
    	<link rel="stylesheet" href="css/menu.css">
    	
    	<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
    	<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
    	<script type="text/javascript" src="js/menu/menu.js"></script>
    	<script type="text/javascript" src="js/menu/metisMenu/jquery.metisMenu.js"></script>
        
        <meta charset="utf-8">
        <title>FIM: Hello world</title>
    </head>
    <body>
    <div id="wrapper">
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
			<!-- HEADER MENU -->
			<div class="navbar-header">
                <a class="navbar-brand" href="./">FIM LOGO</a>
            </div>
		
			<!-- TOP SIDE MENU -->
			<ul class="nav navbar-top-links navbar-right">	
				<li class="dropdown open">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li>
                        	<a><i class="fa fa-user fa-fw"></i><span>Some username</span></a>
                        </li>
                        <li class="divider"></li>
                        <li>
                        	<a href=""><i class="fa fa-sign-out fa-fw"></i><span>Sign out</span></a>
                        </li>
                    </ul>
        		</li>
			</ul>
			
			<!-- LEFT SIDE MENU -->
			<div class="navbar-default navbar-static-side" role="navigation">
           	     <div class="sidebar-collapse">
               		 <ul class="nav" id="side-menu">
               		 	<li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" value="" placeholder="Search issues">
                                <span class="input-group-addon">
                                    <i class="fa fa-search"></i>
                            	</span>
                            </div>
                        </li>
						<li class="">
							<a href="javascript:;" title="Manage projects">
								<i class="fa fa-sitemap fa-fw"></i>
								<span>Projects</span>
								<span class="fa arrow"></span>
							</a> 
							<ul class="nav nav-second-level collapse" style="height: 0px;">
								<li>
									<a href="" title="Create new project">
										<i class="fa fa-floppy-o fa-fw"></i>
										<span>Create new project</span>
									</a> 
								</li>
							</ul>
						</li>
						<li>
							<a href="javascript:;" title="Issues">
								<i class="fa fa-tasks fa-fw"></i>
								<span>Issues</span>
								<span class="fa arrow"></span>
							</a> 
							<ul class="nav nav-second-level collapse" style="height: 0px;">
								<li>
									<a href="" title="Create issue">
										<i class="fa fa-bug fa-fw"></i>
										<span>Create issue</span>		
									</a> 
								</li>
							</ul>
						</li>
						<li class="active">
							<a href="javascript:;" title="Statistics">
								<i class="fa fa-bar-chart-o fa-fw"></i>
								<span>Statistics</span>
								<span class="fa arrow"></span>
							</a> 
							<ul class="nav nav-second-level collapse in" style="height: auto;">
								<li>
									<a href="" title="Issue statistics">
										<i class="fa fa-edit fa-fw"></i>
										<span>Issue statistics</span>
									</a> 
								</li>
							</ul>
						</li>
					 </ul>
				</div>
			</div>
		</nav>
		<div id="page-wrapper">
    		<div class="col-md-6">
        		<h1>Hello F.I.M.</h1>
        		<p>
            		If you can read this it means <span style="font-weight: bold">it works</span>!
        		</p>
        	</div>
        </div>
   	</div>
    </body>
</html>