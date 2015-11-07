<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>

    <meta name="description" content="Website where you can find information and builds regarding Pocket Watch by PFA Alliance">
    <meta name="keywords" content="Pocket watch, watch, PFA Alliance, downalod">
    <title>Pocket Watch by PFA Alliance: Downloads</title>

<?php include './includes/css-js.php';?>

    <link href="css/pfa-downloads.css" rel="stylesheet" />
	<style type="text/css">
		.versionIcon {
			background: url('images/pocket-watch.jpg');
		}
	</style>
</head>
<?php include './includes/pfa-downloads.php';?>
<body>
<?php include './includes/header.php';?>
    <div class="container">
		<h1>Pocket Watch Versions</h1>
		<p class="paragraph">
		Here you can find several versions of the Pocket Watch project. Please note that versions of the application fund under 
		<i>Test</i> and <i>Development</i> may contain bugs and may not be able to run correctly. 
		<i>Other version</i> contain new or experimental features that also can have severe bugs. 
		<b>We recommend to use only versions from <i>Production</i></b> but we do not intend to limit 
		your curiosity and interest for this product.
		</p>
		<p class="paragraph">
		In order to keep the occupied space by these versions limited, from time to time, we delete the older versions.
		</p>
		<h2>Production</h2>
		<div class="row">
		<?= writeVersions("pw-downloads/", "master") ?>
		</div>
		<h2>Test</h2>
		<div class="row">
		<?= writeVersions("pw-downloads/", "test") ?>
		</div>
		<h2>Development (nightly builds)</h2>
		<div class="row">
		<?= writeVersions("pw-downloads/", "dev") ?>
		</div>
		<h2>Other versions - feature branches</h2>
		<div class="row">
		<?= writeVersions("pw-downloads/", "fb-") ?>
		</div>		
		
<?php include './includes/footer.php';?>
    </div>
</body>
</html>
