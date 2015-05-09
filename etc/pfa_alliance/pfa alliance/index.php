<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>

    <meta name="description" content="Website where you can find information and builds regarding PFA Alliance free products: FIM, Cards, MoGeo, Pocket Watch">
    <meta name="keywords" content="PFA Alliance, FIM, Playing cards, Cards, Pocket watch, watch, JavaScript watch">
    <title>PFA Alliance</title>

<?php include './includes/css-js.php';?>
    <script src="js/pfa-pocket-watch-0.1.js"></script>
    <script src="js/jssor.slider.mini.js"></script>
    
    <script type="text/javascript">
		jssor_slider1_starter = function (containerId) {
			var options = { $AutoPlay: true };
			var jssor_slider1 = new $JssorSlider$(containerId, options);
		};
		
		function pageLoaded() {
		  // Handler for .ready() called.
		  (new pfaAllianceClock('watch', null)).start();
		  jssor_slider1_starter('productsCarusel');
		}
    </script>
</head>

<body onload="pageLoaded()">
<?php include './includes/header.php';?>

    <div class="container">
        <div class="row">
			<h1 style="margin-left: 20px;">PFA Alliance</h1>
            <div class="col-lg-8">
                <hr>
				<div class="col-lg-12">
					<div class="col-lg-4">
						<img style="width: 200px; height: 300px;" src="./images/watch/nowis.png" alt="Now is: Monday, February 9, 2015, 19:41:37 GMT." />
					</div>
					<div id="watch" class="col-lg-4" style="height: 360px;">
					</div>
				</div>
				<div class="col-lg-12">
					<hr>
					<p class="bg-info">This site contains builds for the software products developed under PFA Alliance name.
						PFA Alliance and / or any of its developers <b>cannot be hold responsible</b> in case you loose data for / by any of the developed applications and under any circumstances.<br />
						We recommend you to back-up data frequently because this is the only way to keep data safe.
					</p>
				</div>
				<div class="col-lg-12">
					<hr>
					<div id="productsCarusel" class="col-lg-12" style="height: 300px; padding: 0px; text-align: center">
							<!-- Loading Screen -->
							<div u="loading" style="width: 600px; height: 300px;">
								PFA Alliance Products <br />
								Loading... Please Wait!
							</div>
							<!-- Slides Container -->
							<div u="slides" style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 600px; height: 300px;">
								<div><img u="image" src="./images/products/fim-user-dashboard.jpg" /></div>
								<div><img u="image" src="./images/products/fim-project-search.jpg" /></div>
								<div><img u="image" src="./images/products/fim-user-search.jpg" /></div>
								<div><img u="image" src="./images/products/pocket-watch-full-version.jpg" /></div>
								<div><img u="image" src="./images/products/pocket-watch-simple-version.jpg" /></div>
								<div><img u="image" src="./images/products/pocket-watch-localized-version.jpg" /></div>
							</div>
					</div>
				</div>
			</div>
            <div class="col-md-4" style="margin-top: 20px">
<!--
                <div class="well">
                    <h4>Search</h4>
                    <div class="input-group">
                        <input type="text" class="form-control">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                <span class="glyphicon glyphicon-search"></span>
                        </button>
                        </span>
                    </div>
                </div>
-->
                <div class="well">
                    <h4>Daily jokes</h4>
                    <p>
						These two strings walk into a bar and sit down. The bartender says, "So what'll it be?"
						The first string says, "I think I'll have a beer quag fulk boorg jdk^CjfdLk jk3s d#f67howe%^U r89nvy owmc63^Dz x.xvcu"
						"Please excuse my friend," the second string says, "He isn't null-terminated."
					</p>
                </div>
				
				<div class="well">
                    <h4>Daily photos</h4>
						 <img class="img-responsive" src="images/daily/1-wtf.png" alt="" />
                </div>

                <div class="well">
                    <h4>Join the Team</h4>
                </div>

            </div>

        </div>
		
        <hr>

			<a name="products"></a>
            <div class="row">
                <div class="col-lg-12">
                   <section id="services" class="services" style="background-color:#f5f5f5">
				        <div class="container">
				            <div class="row text-center">
				                <div class="col-lg-10 col-lg-offset-1">
				                    <h2>Our Products</h2>
				                    <hr style="border-top:1px solid black" class="small">
				                    <div class="row">
				                        <div class="col-md-3 col-sm-6">
				                            <div class="service-item">
				                                <span class="fa-stack fa-4x">
				                                <i class="fa fa-circle fa-stack-2x"></i>
				                                <i class="fa fa-cloud fa-stack-1x text-primary"></i>
				                            </span>
				                                <h4>
				                                    <strong><span class="asimovF">F</span>IM</strong>
				                                </h4>
												<img style="height:50px;weight:50px" src="images/fim-logo.png" alt="Free Issue Manager logo" />
				                                <p>Free Issue Manager</p>
				                                <a href="fim.php" class="btn btn-light">Learn More</a>
				                            </div>
				                        </div>
				                        <div class="col-md-3 col-sm-6">
				                            <div class="service-item">
				                                <span class="fa-stack fa-4x">
				                                <i class="fa fa-circle fa-stack-2x"></i>
				                                <i class="fa fa-cloud fa-stack-1x text-primary"></i>
				                            </span>
				                                <h4>
				                                    <strong>Cards</strong>
				                                </h4>
												<img style="height:50px;weight:50px" src="images/cards-logo.png" alt="Playing cards logo" />
				                                <p>Playing cards</p>
				                                <a href="#" class="btn btn-light">Learn More</a>
				                            </div>
				                        </div>
				                        <div class="col-md-3 col-sm-6">
				                            <div class="service-item">
				                                <span class="fa-stack fa-4x">
				                                <i class="fa fa-circle fa-stack-2x"></i>
				                                <i class="fa fa-cloud fa-stack-1x text-primary"></i>
				                            </span>
				                                <h4>
				                                    <strong>MoGeo</strong>
				                                </h4>
												<img style="height:50px;weight:50px" src="images/mogeo-logo.png" alt="MoGeo logo" />
				                                <p>2D Geometry Application</p>
				                                <a href="#" class="btn btn-light">Learn More</a>
				                            </div>
				                        </div>
				                        <div class="col-md-3 col-sm-6">
				                            <div class="service-item">
				                                <span class="fa-stack fa-4x">
				                                <i class="fa fa-circle fa-stack-2x"></i>
				                                <i class="fa fa-cloud fa-stack-1x text-primary"></i>
				                            </span>
				                                <h4>
				                                    <strong>PocketWatch</strong>
				                                </h4>
												<img style="height:50px;weight:50px" src="images/pocket-watch.jpg" alt="Pocket Watch logo" />
				                                <p>Pocket Size Watch</p>
				                                <a href="pw.php" class="btn btn-light">Learn More</a>
				                            </div>
				                        </div>
				                    </div>
				                    <hr style="border-top:1px solid black" class="small">
									<div class="row">
										<p>Didn't find what you are looking for? You may <a href="contact.php?s=suggest">suggest</a> what would you like.
									</div>
				                </div>
				            </div>
				        </div>
				    </section>
                </div>
            </div>
			
<?php include './includes/footer.php';?>
    </div>
</body>
</html>
