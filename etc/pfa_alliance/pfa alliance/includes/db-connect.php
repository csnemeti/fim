<?php
	$con = null;
	
		$conn = pg_pconnect("host=localhost port=5432 dbname=pfa user=fim password=Fim4Pfa");
		if (!$conn) {
				echo "An error occurred when connecting to DB.\n";
			  	exit;
		}
	
	function closeConnection() {
		if ($conn != null) {
			pg_close($conn);
			$conn = null;
		}
	}
?>