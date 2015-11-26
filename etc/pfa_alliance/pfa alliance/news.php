<!DOCTYPE html>
<html lang="en">

<head>
<?php include './includes/meta.php';?>

    <meta name="description" content="Website where you can find information and builds regarding PFA Alliance free software products">
    <meta name="keywords" content="News, PFA Alliance, PFA Alliance Software, FIM, FIM Software Contact, Playing cards, Cards, Pocket watch, MoGeo">
    <title>PFA Alliance: News</title>

	<style type="text/css">
		.logoCell {
			width: 50px;
			height: 50px;
		}
		.logo {
			width: 100%;
		}
		.postInfo {
			padding-left: 10px;
		}
		.newsTitle{
			font-size:26px;
			font-weight: bold;
			padding: 0px;
			margin: 0px;
		}
		.postDate {
			font-size:20px;
			padding: 0px;
			margin: 0px;
		}
		.appTitle {
			font-weight: bold;
		}
		.newsImage {
			width: 100%;
			margin-top: 5px;
		}
		.textCell {
			margin-top: 5px;
		}
	</style>
<?php include './includes/css-js.php';?>
</head>

<body>
<?php 
	include './includes/header.php';
	include './includes/db-connect.php';
	
	function clean($string) {
	   $string = str_replace(' ', '-', $string); // Replaces all spaces with hyphens.	
	   return preg_replace('/[^A-Za-z0-9\-]/', '', $string); // Removes special chars.
	}	
?>
    <div class="container">
		<div class="row">
			<div class="col-lg-9">
				<h1 style="margin-top: 0px">News About PFA Alliance and it's Products</h1>
				<p style="text-align: justify;">This page contains news about PFA Alliance and it's products. You can see all the news or filter them based on your interest.
				The news are ordered in <i>newest first</i> order, meaning the news that is closest in time to our curent timeline is the first in the list.
				This page contains the summary of the of each news, you may see details by clickent on <i>Read more</i> link. If you like the content, 
				you may like & share it on Facebook. Also, we are givving the possibility to provide feedback based on what you read, so if you have 
				comments or questions they are welkomed. Do not forget in this case to add reference to news you read.
				</p>
			</div>
			<div class="col-lg-3" style="border: 1px solid black">
				<form id="newsForm" action="news.php">
					<table style="padding: 5px; border-collapse: separate">
<?php 
						$transmitter = array();
						if (isset($_GET['select'])) {
							$name = $_GET['select'];
							foreach ($name as $selectValue) {
								array_push($transmitter, clean($selectValue));
							}
						}
						$selectAll = count($transmitter) == 0;

						$result = pg_query($conn, "SELECT code, name, count(news.id) as news_no FROM project LEFT JOIN news on project.code = news.project GROUP BY code, name ORDER BY ordinal");						
						while ($row = pg_fetch_row($result)) {
							echo "\t\t\t\t\t\t\t<tr><td><label><input type=\"checkbox\" name=\"select[]\" value=\"" . $row[0] . "\" "; 
							if ($selectAll || in_array($row[0], $transmitter)) {
								echo "checked=\"checked\"";
							} 
 							echo "/> " . $row[1] . " - (" . $row[2] . ")</label></td></tr>\n";
						}						
?>
						<tr><td style="text-align: center"><input type="submit" class="btn btn-primary" value="Update" /></td></tr>
					</table>
				</form>
			</div>
		</div>
		<div class="row">
<?php 
		$sql = "Select id, project, project.name as project_name, project.logo as project_logo, type, title, short_description, image_url, to_char(post_timestamp, 'YYYY-MM-DD HH24:MI:SS.MS'), logo_alt_text From news Inner Join project on news.project = project.code";
		if ($selectAll == false) {
			$sql = $sql . " Where project IN ('" . join("', '", $transmitter) . "')"; 
		}
		$orderBy = " Order by post_timestamp desc";
		$result = pg_query($conn, $sql . $orderBy);
		while ($row = pg_fetch_row($result)) {
?>
		<div class="col-lg-9" style="padding: 10px;">
			<div class="col-lg-12" style="border: 1px solid black">
				<table style="width: 100%">				
					<tr>
						<td class="logoCell">
<?php 						echo "\t\t\t\t\t\t\t<img src=\"./images/" . $row[3] . "\" alt=\"" . $row[9] . "\" class=\"logo\" />\n"; ?>
						</td>
						<td class="postInfo">
							<h2 class="newsTitle">
<?php 						echo "$row[5]"; ?>
							</h2>
							<p class="postDate">
<?php 						
							$postTs = new DateTime($row[8]);
							echo "\t\t\t\t\t\t\t<span class=\"appTitle\">" . $row[2] . "</span> on " . $postTs->format("l, F j, Y"); 
?>
							</p>
						</td>
					</tr>
<?php
					if ($row[7] != null) {
					echo "<tr><td colspan=\"2\" class=\"imageCell\"><img src=\"./images/news/" . $row[7] . "\" class=\"newsImage\" /></td></tr>\n";
					} 						 
?>					
					<tr>
						<td colspan="2" class="textCell">
<?php 						echo $row[6] . "\n"; ?>
						</td>
					</tr>
				</table>
				<div>
					<a href="#">Like</a>
					<a href="#">Read more</a>
					<a href="#">Provide feedback</a>
				</div>
			</div>
		</div>
<?php 
		
		}						
?>
		</div>
<?php 
	include './includes/footer.php';
	closeConnection();
?>
    </div>
</body>
</html>
