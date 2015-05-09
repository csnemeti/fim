<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>

    <meta name="description" content="Website where you can find information and builds regarding PFA Alliance free products">
    <meta name="keywords" content="Contact, Feedback, Suggestion, PFA Alliance, FIM, Playing cards, Cards, Pocket watch">
    <title>PFA Alliance: Contact us</title>

<?php include './includes/css-js.php';?>
	<script src="js/jquery.validate.min.js"></script>
	
	<script>
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
				},
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

<body>
<?php include './includes/header.php';?>
<?php							
	$subject = $_GET["s"];
	if (strlen(trim($subject)) == 0){
		$subject = "contact";
	}
	$subject_text = "";
	$title = "";
	switch ($subject){
		case "suggest":
			$subject_text = "Suggest new product for PFA Alliance";
			$title = "Suggest new product for PFA Alliance!";
			break;
		case "pwfeedback":
			$subject_text = "Feedback for Pocket Watch!";
			$title = "Feedback for Pocket Watch!";
			break;
		default :
			$subject_text = "Feedback from PFA Alliance";
			$title = "Let's Get In Touch!";
			break;
	}
	
	$user_name = 	$_POST["name"];
	$user_email = 	$_POST["email"];
	$user_tel = 	$_POST["tel"];
	$user_message = $_POST["message"];
	$res = false;
	
	if (strlen(trim($user_name)) > 0 && strlen(trim($user_email)) > 0 && strlen(trim($user_message)) > 0){
		$email_content = "FROM: " . $user_name . " <" . $user_email . ">\r\nPHONE: " . $user_tel . "\r\n\r\n" . $user_message;
		$headers = 'From: <feedback@pfa-alliance.com>' . "\r\n";
		$res = mail ( "fim.pfaalliance@gmail.com", $subject_text , $email_content, $headers );
	}
	
?>								
    <div class="container">
        <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h1 class="contact-us intro-text text-center"><?php	echo $title; ?></h1>
<?php
					if ($res){
						echo "<h4 class=\"contact-us text-center\">E-mail has been sent!</h4>";
					}
?>					
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
<?php							
								echo "<input type=\"hidden\" name=\"subject\" value=\"$subject\">";
?>								
                                <button type="submit" class="btn btn-default">Submit</button>
                            </div>
                            <div class="form-group col-lg-12">
								<p><i>Fields having a star (*) are mandatory.</i></p>
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
</html>
