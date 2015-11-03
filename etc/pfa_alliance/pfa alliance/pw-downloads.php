<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>

    <meta name="description" content="Website where you can find information and builds regarding Pocket Watch by PFA Alliance">
    <meta name="keywords" content="Pocket watch, watch, PFA Alliance, downalod">
    <title>Pocket Watch by PFA Alliance: Downloads</title>

<?php include './includes/css-js.php';?>
</head>
<?php
function startsWith($haystack, $needle) {
    // search backwards starting from haystack length characters from the end
    return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== FALSE;
}
function writeOneVersion($buildNo, $path) {
	// echo "Build: " . $build . " located at: " . $path;
}
function writeVersions($parentDirectoryName, $directoryPattern) {
	// get the list of valid directories
	$validFolders = array();
	$filesAndFolders = scandir($parentDirectoryName);
	foreach ($filesAndFolders as $file) {
		if (startsWith($file, $directoryPattern)) {
			array_push($validFolders, $file);
		}
	}
	
	// iterate the valid directories that get all build numbers
	$buildNumbers = array();
	foreach ($validFolders as $folder) {
		$versionCandidates = scandir($parentDirectoryName . $folder);
		foreach ($versionCandidates as $candidate) {
			if (is_numeric($candidate)) {
				$buildNumbers[intval($candidate)] = $parentDirectoryName . $folder . "/" . $candidate;
			}
		}
	}
	// verify found build numbers
	if (count($buildNumbers) > 0) {
		// order the array, descending after keys
		krsort($buildNumbers);
		foreach ($buildNumbers as $build => $path) {
			writeOneVersion($build, $path);
		}
	} else{
    	echo "<p>None.</p>";
    }
}
?>
<body>
<?php include './includes/header.php';?>
    <div class="container">
		<h1>Pocket Watch Versions</h1>
		<p class="paragraph">
		Here you can find several version of the PocketWatch project. Please note that application fund under 
		<i>Test</i> and <i>Development</i> may contain severe bugs and may not be able to run correctly. 
		<i>Other version</i> contain new or experimental features that also can have severe bugs. 
		<b>We recommend to use only versions from <i>Production</i></b> but we do not intend to limit 
		the curiosity and interest for this product.
		</p>
		<p class="paragraph">
		In order to keep the occupied space limited, from time to time we delete the older versions.
		</p>
		<h2>Production</h2>
		<div>
		<?= writeVersions("pw-downloads/", "master") ?>
		</div>
		<h2>Test</h2>
		<div>
		<?= writeVersions("pw-downloads/", "test") ?>
		</div>
		<h2>Development (nightly builds)</h2>
		<div>
		<?= writeVersions("pw-downloads/", "dev") ?>
		</div>
		<h2>Other versions - feature branches</h2>
		<div>
		<?= writeVersions("pw-downloads/", "fb-") ?>
		</div>		
		
<?php include './includes/footer.php';?>
    </div>
</body>
</html>
