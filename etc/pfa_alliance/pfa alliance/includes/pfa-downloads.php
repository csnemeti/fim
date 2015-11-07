<?php 
function startsWith($haystack, $needle) {
    // search backwards starting from haystack length characters from the end
    return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== FALSE;
}
function writeOneVersion($buildNo, $path) {
	// echo "Build: " . $build . " located at: " . $path;
	echo "\n\t\t\t<div class=\"versionBuild\">\n";

	echo "\t\t\t\t<div class=\"versionIconDiv\">\n";
	echo "\t\t\t\t<div class=\"versionIcon\">\n";
	echo "\t\t\t\t\t</div>\n";	
	echo "\t\t\t\t\t</div>\n";	

	echo "\t\t\t\t<hr />\n";

	echo "\t\t\t\t<div class=\"versionDiv\">\n";
	echo "\t\t\t\t\t<p class=\"versionValue\">" . $buildNo . "</p>\n";
	echo "\t\t\t\t\t<p class=\"versionCreatedAt\">" . date ("Y-m-d H:i:s", filemtime($path)) . "</p>\n";
	echo "\t\t\t\t</div>\n";

	echo "\t\t\t\t<hr />\n";

	echo "\t\t\t\t<div class=\"versionFiles\">\n";
	echo "\t\t\t\t\t<table class=\"versionTable\">\n";
	echo "\t\t\t\t\t\t<tr>\n";
	echo "\t\t\t\t\t\t\t<th class=\"versionFileHeader\">Name</th>\n";
	echo "\t\t\t\t\t\t\t<th class=\"versionFileHeader\">Size</th>\n";
	echo "\t\t\t\t\t\t</tr>\n";
	
	$files = scandir($path);
	foreach ($files as $file) {
		if ($file != "." && $file != "..") {
			echo "\t\t\t\t\t\t<tr>\n";
			echo "\t\t\t\t\t\t\t<td class=\"versionFileName\"><a href=\"" . $path . "/" . $file . "\">" . $file . "</a></td>\n";
			$fileSize = filesize($path . "/" . $file);
			$fileUnit = "B";
			if ($fileSize > 1048576) {
				$fileSize = $fileSize / 1048576;
				$fileUnit = "MB";
			} else if ($fileSize > 1024) {
				$fileSize = $fileSize / 1024;
				$fileUnit = "KB";
			}
			echo "\t\t\t\t\t\t\t<td class=\"versionFileSize\">" . number_format($fileSize, 2) . $fileUnit . "</td>\n";
			echo "\t\t\t\t\t\t</tr>\n";
		}
	}
	
	echo "\t\t\t\t\t</table>\n";
	echo "\t\t\t\t</div>\n";

	echo "\t\t\t</div>\n";	
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