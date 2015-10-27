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
<?php include './includes/header.php';?>
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
				<form>
					<table>
						<tr><td><label><input type="checkbox" name="select" value="pfa" checked="checked" /> PFA Alliance</label></td></tr>
						<tr><td><label><input type="checkbox" name="select" value="fim" checked="checked" /> <span class="asimovF">F</span>IM (Free Issue Manager)</label></td></tr>
						<tr><td><label><input type="checkbox" name="select" value="cards" checked="checked" /> Playing Cards</label></td></tr>
						<tr><td><label><input type="checkbox" name="select" value="mogeo" checked="checked" /> MoGeo</label></td></tr>
						<tr><td><label><input type="checkbox" name="select" value="pw" checked="checked" /> Pocket Watch</label></td></tr>
					</table>
				</for>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-9" style="padding: 10px;">
			<div class="col-lg-12" style="border: 1px solid black">
				<table style="width: 100%">
					<tr>
						<td class="logoCell"><img src="./images/pfa.png" alt="PFA Alliance" class="logo" /></td>
						<td class="postInfo">
							<h2 class="newsTitle">Version 0.5 of PFA Alliance's website was launched</h2>
							<p class="postDate"><span class="appTitle">PFA Alliance</span> on Thursday, October 1, 2015</p>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="textCell">
							Version 0.5 of PFA Alliance's website was started. New features include:
							<ul>
								<li>Updates on main page (implement joke of the day, more pictures for image of the day, update on Pocket Watch, etc.).</li>
								<li>Dynamic sitemap in XML and HTML format.</li>
								<li>This news section.</li>
								<li>Updates on FIM and PocketWatch pages.</li>
							</ul>
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
			<div class="col-lg-9" style="padding: 10px;">
			<div class="col-lg-12" style="border: 1px solid black">
				<table style="width: 100%">
					<tr>
						<td class="logoCell"><img src="./images/fim-logo.png" alt="Free Issue Manager" class="logo" /></td>
						<td class="postInfo">
							<h2 class="newsTitle"><span class="asimovF">F</span>IM version 0.5 development started</h2>
							<p class="postDate"><span class="appTitle"><span class="asimovF">F</span>IM (Free Issue Manager)</span> on Saturday, October 10, 2015</p>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="imageCell"><img src="./images/news/00001.png" class="newsImage" /></td>
					</tr>
					<tr>
						<td colspan="2" class="textCell">
							Development started on FIM version 0.5. The milestone name is <i>What issues do we have?</i> and is focusing mainly on issue management.
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
		</div>
<?php include './includes/footer.php';?>
    </div>
</body>
</html>
