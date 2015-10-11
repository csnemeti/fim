<?php
	$siteUrl = "http://www.pfa-alliance.com/";
	
	function getChangeFrequency($file) {
		switch ($file) {
			case "index.php" : return "daily";
			case "sitemap.php" : return "weekly";
			default: return "monthly";
		}
	}
	function getLastModified($file) {
		return date("Y-m-d", filemtime($file));
	}
	function getPriority($file) {
		switch ($file) {
			case "index.php" : return 0.8;
			case "sitemap.php" : return 0.6;
			default: return 0.5;
		}
	}
	function getDescription($file) {
		switch ($file) {
			case "contact.php" : 	return "Page used for contact us and provide feedback.";
			case "fim.php" : 		return "Page dedicated to Free Issue Manager (FIM) project.";
			case "index.php" : 		return "Site main page.";
			case "pw.php" : 		return "Page dedicated to ProcketWatch project.";
			case "sitemap.php" : 	return "This sitemap page.";
			default: return $file . " page";
		}
	}

	header('Content-Type: text/xml');	
	// Verify the output format 
	$format = $_GET["format"];
	$isHtml = ($format != null) && (strcasecmp("HTML", $format) == 0);
	echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	// Add XSLT processing. 
	if ($isHtml) {
		echo "<?xml-stylesheet type=\"text/xsl\" href=\"sitemap-xsl.php\"?>\n";		
	}
	echo "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
	
	// Get all files from current directory, ordered alphabetically.
	$files = array();
	if ($handle = opendir('.')) {
	    while (false !== ($file = readdir($handle))) {
	        if ($file != "." && $file != ".." && strtolower(substr($file, strrpos($file, '.') + 1)) == 'php') {
	        	$files[] = $file;
	        }
	    }
	    closedir($handle);
	    
	    // order the files based on name
	    natsort($files);
	    
	    foreach($files as $file) {
	    	$lowerFile = strtolower($file);
            echo "<url>\n<loc>" . $siteUrl . $file. "</loc>\n";
            echo "<changefreq>" . getChangeFrequency($lowerFile) . "</changefreq>\n"; 
            echo "<lastmod>" . getLastModified($file) . "</lastmod>\n"; 
            echo "<priority>" . getPriority($lowerFile) . "</priority>\n"; 
            if ($isHtml) {
            	echo "<filename>" . $file . "</filename>\n";
            	echo "<description>" . getDescription($lowerFile) . "</description>\n";
            } 
            echo "</url>\n";
		}
	}	
	
	
	
	echo "</urlset>";
?>