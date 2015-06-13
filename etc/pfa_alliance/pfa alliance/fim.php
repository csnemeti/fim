<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>
    <meta name="description" content="Website where you can find information and builds regarding FIM (Free Issue Manager) Software by PFA Alliance">
    <meta name="keywords" content="FIM, FIM Software, free issue manager, issue manager, bugtracker, issue trackier, PFA Alliance, PFA Alliance Software">

    <title>FIM by PFA Alliance</title>

<?php include './includes/css-js.php';?>

    <script src="js/jssor.slider.mini.js"></script>
    
    <script type="text/javascript">
    jssor_slider1_starter = function (containerId) {
        var options = { $AutoPlay: true };
        var jssor_slider1 = new $JssorSlider$(containerId, options);
    };
    </script>
</head>

<body>

<?php include './includes/header.php';?>

    <div class="container">
        <div class="row">
			<div class="col-lg-8">
				<h1 style="margin-left: 20px;">
					<img style="height:50px;weight:50px" src="images/fim-logo.png" alt="FIM logo" />
					FIM (Free Issue Manager)
					<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-expanded="false" onclick="alert('Comming soon')">Try it for free</button>
				</h1>
			</div>
			<div class="col-md-12" style="margin-top: 0px">
				<hr>
			</div>
            <div class="col-lg-8">
				<div class="col-lg-12">
				<div id="productsCarusel" class="col-lg-12" style="height: 300px; padding: 0px; text-align: center">
							<!-- Loading Screen -->
							<div u="loading" style="width: 600px; height: 300px;">
								FIM<br />
								Loading... Please Wait!
							</div>
							<!-- Slides Container -->
							<div u="slides" style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 600px; height: 300px;">
								<div><img u="image" src="./images/products/fim-user-dashboard.jpg" /></div>
								<div><img u="image" src="./images/products/fim-project-search.jpg" /></div>
								<div><img u="image" src="./images/products/fim-user-search.jpg" /></div>
							</div>
					</div>	
				</div>
			</div>
			
            <div class="col-md-4" style="margin-top: 0px">
				<h3>What does <span class="asimovF">F</span>IM?</h3>
                <p><span class="asimovF">F</span>IM represents an Issue Manager we develop and give it to use for free.</p>
                <h3>What can you do with <span class="asimovF">F</span>IM ?</h3>
                <ul>
                    <li>Issues management</li>
					<ul>
						<li>Create, edit, delete, search issues</li>
						<li>Assign, re-assign users to issues</li>
						<li>Change issue state and priority</li>
					</ul>
                    <li>Project management</li>
					<ul>
						<li>Project component management</li>
						<li>Project label management</li>
						<li>Assigned users management</li>
						<li>Issue state and flow customization</li>
					</ul>
					<li>Agile (in preparation)</li>
					<ul>
						<li>Scrum</li>
						<li>Kanban</li>
					</ul>
                </ul>
			</div>
			<div class="col-md-12" style="margin-top: 0px">
				<h3>Why (to use) <span class="asimovF">F</span>IM?</h3>
				<p>Well… it is a difficult question. First: I would like to say we do not intend to put any pressure on you to use any of our products, <span class="asimovF">F</span>IM included. 
				Of course, it would make us very happy and proud if we find out that somebody is using our product but that is entirely up to you. 
				Second: there are many products like this on the market but none of them has the simplicity and power of FIM in a cost free "flavor". 
				Third: these applications intend to start from something simple and easy, and become so complex and so big… Our goal was from beginning to provide a compact but very robust application…</p>
				<h3>Why letter F is reversed?</h3>
				<p>I have to admit the original idea is not mine... I don't know if anybody else came with idea of reversing letter F but I read about this in a book written by <i><b>Isaac Asimov</b></i> (I think it was <i>Foundation and Earth</i>). 
				There was an experimental ship there called <i><b>Far Star</b></i>. There was nothing special about the name of this ship, until those who were using it didn't meet some people, presumed to be descendants of the last humans 
				who left Earth before becoming totally uninhabitable. They were surprised to see that letter <b>F</b> is not written correct... it's reversed</p>
            </div>

        </div>
		
		<div class="row">		
        <hr>

<?php include './includes/footer.php';?>
    </div>
</body>
	<script>jssor_slider1_starter('productsCarusel');</script>
</html>
