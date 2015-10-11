<?php
	header('Content-Type: text/xsl');	
?>
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 	xmlns:sm="http://www.sitemaps.org/schemas/sitemap/0.9">
	<xsl:template match="/">
		<html lang="en">
		<head>
			<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" /> 
			<title>PFA Alliance: Sitemap</title>

			<?php include './includes/css-js.php';?>
		</head>
		<body>
			<?php include './includes/header.php';?>
			<div class="container">
				<h1>Sitemap</h1>
				<p>You can use our sitemap to easily navigate to any section of our website. If you still cannot find the information you need don't hesitate to <a href="contact.php">contact</a> us.</p>
				<ul>
					<xsl:for-each select="sm:urlset/sm:url">
					<li><a href="{sm:loc}"><b><xsl:value-of select="sm:filename"/></b></a> - <xsl:value-of select="sm:description"/></li>
					</xsl:for-each>
				</ul>
				<?php include './includes/footer.php';?>
			</div>
		</body>
		</html>
	</xsl:template>

</xsl:stylesheet>  