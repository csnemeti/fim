<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>

    <meta name="description" content="Website where you can find information and builds regarding Pocket Watch by PFA Alliance">
    <meta name="keywords" content="Pocket Watch, watch, JavaScript watch, free watch, PFA Alliance">
    <title>Pocket Watch by PFA Alliance</title>

<?php include './includes/css-js.php';?>
	<style type="text/css">
		#digitalWatch {
			border: 5px ridge black;
			padding: 1px 5px 1px 5px;
			background-color: gray;
			color: yellow;
			font-family: "Courier New", Courier, monospace;
			font-size: 25px;
			font-weight: bold;
			display: inline;
		}
		#analogWatchWrapper {
			border: 1px solid black;
			width: 252px;
		}
		.strong {
			font-weight: bold;
		}
		.italic {
			font-style: oblique;
		}
	</style>
    <script src="js/jssor.slider.mini.js"></script>
    
    <script type="text/javascript">
		jssor_slider1_starter = function (containerId) {
			var options = { $AutoPlay: true };
			var jssor_slider1 = new $JssorSlider$(containerId, options);
		};
		
		var digitalTime = null;
		var ctx = null;
		var radius = 125;
		
		function initDigitalWatch(){
			digitalTime = document.getElementById("digitalWatch");
		}
		function twoDigits(number){
			return ((number < 10)? "0" : "") + number;
		}
		
		function initAnalogWatch(){
			var canvas = document.getElementById("analogWatch");
			ctx = canvas.getContext("2d");
			ctx.translate(radius, radius);			
			radius = radius * 0.90;
			drawClock();
		}
		
		function drawClock(){
			// draw circle for watch face
			ctx.beginPath();
			ctx.arc(0, 0, radius, 0 , 2*Math.PI);
			ctx.fillStyle = "black";
			ctx.fill();
			
			// draw clock border
			var grad = ctx.createRadialGradient(0,0,radius*0.95, 0,0,radius*1.05);
			grad.addColorStop(0, '#333');
			grad.addColorStop(0.5, 'white');
			grad.addColorStop(1, '#333');
			ctx.strokeStyle = grad;
			ctx.lineWidth = radius*0.1;
			ctx.stroke();
			
			// draw inner dot (10px)
			ctx.beginPath();
			ctx.arc(0, 0, 10, 0, 2*Math.PI);
			ctx.fillStyle = 'white';
			ctx.fill();
			
			// draw the numbers on the watch
			ctx.beginPath();
			ctx.font = radius*0.15 + "px arial";
			ctx.textBaseline="middle";
			ctx.textAlign="center";
			ctx.fillStyle = 'white';
			for(num= 1; num < 13; num++){
				ang = num * Math.PI / 6;
				ctx.rotate(ang);
				ctx.translate(0, -radius*0.85);
				ctx.rotate(-ang);
				ctx.fillText(num.toString(), 0, 0);
				ctx.rotate(ang);
				ctx.translate(0, radius*0.85);
				ctx.rotate(-ang);
			}
			drawTime(ctx, radius);

			// add inner dot (4px)
			ctx.beginPath();
			ctx.arc(0, 0, 4, 0, 2*Math.PI);
			ctx.fillStyle = 'black';
			ctx.fill();
		}
		function drawTime(ctx, radius){
			var now = new Date();
			var hour = now.getHours();
			var minute = now.getMinutes();
			var second = now.getSeconds();
			//hour
			hour=hour%12;
			hour=(hour*Math.PI/6)+(minute*Math.PI/(6*60))+(second*Math.PI/(360*60));
			drawHand(ctx, hour, radius*0.5, radius*0.07);
			//minute
			minute=(minute*Math.PI/30)+(second*Math.PI/(30*60));
			drawHand(ctx, minute, radius*0.8, radius*0.07);
			// second
			second=(second*Math.PI/30);
			drawHand(ctx, second, radius*0.9, radius*0.02);
		}

		function drawHand(ctx, pos, length, width) {
			ctx.beginPath();
			ctx.strokeStyle = 'white';
			ctx.lineWidth = width;
			ctx.lineCap = "round";
			ctx.moveTo(0,0);
			ctx.rotate(pos);
			ctx.lineTo(0, -length);
			ctx.stroke();
			ctx.rotate(-pos);
		}
		
		function secondElapsed(){
			var nowIs = new Date();
			digitalTime.innerHTML = twoDigits(nowIs.getHours()) + ":" + twoDigits(nowIs.getMinutes()) + ":" + twoDigits(nowIs.getSeconds());
			drawClock();
		}
		
		function initWatches(){
			initDigitalWatch();
			initAnalogWatch();
			setInterval(secondElapsed, 1000);
		}
    </script>
</head>
<body onload="initWatches()">

<?php include './includes/header.php';?>

    <div class="container">
        <div class="row">
			<div class="col-lg-8">
				<h1 style="margin-left: 20px;">
					<img style="height:50px;weight:50px" src="images/pocket-watch.jpg" alt="Pocket Watch logo" />
					Pocket Watch
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
								Pocket Watch<br />
								Loading... Please Wait!
							</div>
							<!-- Slides Container -->
							<div u="slides" style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 600px; height: 300px;">
								<div><img u="image" src="./images/products/pocket-watch-full-version.jpg" /></div>
								<div><img u="image" src="./images/products/pocket-watch-simple-version.jpg" /></div>
								<div><img u="image" src="./images/products/pocket-watch-localized-version.jpg" /></div>
							</div>
					</div>	
				</div>
			</div>
			
            <div class="col-md-4" style="margin-top: 0px">
				<h2>What Pocket Watch is?</h2>
                <p>Pocket Watch is a free JavaScript plug-in that generates a digital or analog looking watch.</p>
                <h2>Features</h2>
                <ul>
                    <li>Themes support (allows changing the look).</li>
					<li>Allows adding date, day of week.</li>
					<li>Allows localization.</li>
					<li>Plays a song.</li>
                </ul>
			</div>
			<div class="col-md-12" style="margin-top: 0px">
				<h2>Why (to use) Pocket Watch?</h2>
				<p>Since dawn of Dynamic HTML (DHTML) JavaScript was one of the main actors. Today, you have several ways of making a web page dynamic but the main and most liked solution is by using JavaScript and CSS.</p>
				<p>From the beginning, JavaScript was used for:
					<ul>
						<li>Validate forms.</li>
						<li>Make pages dynamic by associating actions to various events.</li>
						<li>Build (partial or complete) user interfaces.</li>
					</ul>
				</p>
			</div>
			<div class="col-md-9" style="margin-top: 0px; height: 50px; padding-top: 10px;">
				<p>Regarding animation, one of the solutions I always like it was the watch. It was a simple digital watch that looked like this:</p>
			</div>
			<div class="col-md-3" style="margin-top: 0px; padding: 0px;">
				<div style="margin-top: 0px; padding: 0px;">
					<span id="digitalWatch">20:20:20</span>
				</div>
			</div>
			<div class="col-md-9" style="margin-top: 0px">
				<p>With HTML 5 features, this watch idea seems to reborn, but this time into analog watches. This is probably amplified by the <i>Smartwatch</i> concept also that is surrounding us during these days. 
				Now we can do something nicer and fancier like this:</p>
			</div>
			<div class="col-md-3" style="margin-top: 0px; padding: 0px;">
				<div id="analogWatchWrapper" style="margin-top: 0px; padding: 0px;">
					<canvas id="analogWatch" width="250" height="250"></canvas>
					<p style="text-align: center">Code for the watch was inspired<br/>by <a href="http://www.w3schools.com/canvas/canvas_clock.asp" target="w3school">W3Schools.Com</a></p>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 0px">
				<p><b>Pocket Watch</b> represents such an effort but taken to what we consider <i>the next level</i>. 
				<ul>
					<li>Who said a watch can only show the time?</li> 
					<li>Why cannot show also the date? Why cannot play a song?</li> 
					<li>Why if it looks like a regular watch it cannot be exactly like a regular watch?</li> 
				</ul>
				The answer to all of this is: <span class="italic">it can... <span class="strong">use Pocket Watch</span>!</span></p>
				<h2>How to use Pocket Watch?</h2>
				<p>Embedding Pocket Watch into a web page is very easy... and free. All you have to do is follow the next staps.</p>
				<h3>1. Download...</h3>
				<p>You may download the latest version and all it's dependencies from <a href="/pw/pocket-watch-0.2.zip">this</a> link.
				The ZIP file contains the JavaScript library (<strong>lib</strong> directory) and all it's dependencies plus some examples (<strong>examples</strong> directory) regarding how to use it.
				</p>
				<h3>2. Copy content into your project</h3>
				<p>You should copy all the files from <i>lib</i> directory into your project. This contains all the necessary elements to make the watch running in your web page. 
				Do not modify the structure of the files! In case you do that, the watch might not work.</p>
				<h3>3. Modify Web page</h3>				
				<h4>3.1. Create DIV</h4>				
				<h4>3.2. Define JavaScript function</h4>				
				<h4>3.3. Call the JavaScript function</h4>	
				<h2>What do you think?</h2>				
            </div>
        </div>
		
		<div class="row">		
        <hr>

<?php include './includes/footer.php';?>
    </div>
</body>
	<script>jssor_slider1_starter('productsCarusel');</script>
</html>