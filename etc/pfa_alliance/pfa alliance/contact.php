<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>

    <title>PFA Alliance contact us</title>

<?php include './includes/css-js.php';?>
    <script src="js/jssor.slider.mini.js"></script>
	<script src="js/jquery.validate.min.js"></script>
    
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
	
	<script>
	$.validator.setDefaults({
		submitHandler: function() {
			alert("submitted!");
		}
	});

	$().ready(function() {
		// validate signup form on keyup and submit
		$("#contactUsForm").validate({
			rules: {
				email: {
					required: true,
					email: true
				},
				name: {
					required: true
				}
				message: {
					required: true
				}
			},
			messages: {
				email: "Please enter a valid email address",
				name: "Please provide your name",
				message: "Please provide a message",
			}
		});
	});
	</script>
</head>

<body onload="pageLoaded()">
<?php include './includes/header.php';?>


    <div class="container">
        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h1 class="contact-us intro-text text-center">Let's Get In Touch!
                    </h1>
                    <hr>
                    <p>Ready to start your next project with us? That's great! Give us a call or send us an email and we will get back to you as soon as possible!</p>
                    <form role="form" method="POST" id="contactUsForm">
                        <div class="row">
                            <div class="form-group col-lg-4">
                                <label>Name (*)</label>
                                <input type="text" name="name" class="form-control" required>
                            </div>
                            <div class="form-group col-lg-4">
                                <label>Email Address (*)</label>
                                <input type="email" name="email" class="form-control" required>
                            </div>
                            <div class="form-group col-lg-4">
                                <label>Phone Number</label>
                                <input type="tel" name="tel" class="form-control" >
                            </div>
                            <div class="clearfix"></div>
                            <div class="form-group col-lg-12">
                                <label>Message (*)</label>
                                <textarea class="form-control" rows="12" name="message" required></textarea>
                            </div>
                            <div class="form-group col-lg-12">
                                <input type="hidden" name="save" value="contact">
                                <button type="submit" class="btn btn-default">Submit</button>
                            </div>
                            <div class="form-group col-lg-12">
								<p>Fields having a star (*) are mandatory.
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-lg-12 text-center">
                    <i class="fa fa-envelope-o fa-3x wow bounceIn" data-wow-delay=".1s"></i>
					<!--
                    <p><img src="images/contactus.jpg" alt="Contact us..."</p>
					-->
                </div>
            </div>
        </div>
		
<?php include './includes/footer.php';?>
    </div>
</body>
<script>jssor_slider1_starter('productsCarusel');</script>
</html>
