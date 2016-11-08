<?php 
	function send_notification ($tokens, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $tokens,
			 'data' => $message
			);
		$headers = array(
			'Authorization:key = AIzaSyDFQx16OEfoS90zyBjdS_ifUvIQG-hscUM ',
			'Content-Type: application/json'
			);
	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}
	
 /*   //secure way to build connection
	$file = parse_ini_file("exxonconn.ini");

	//store in php var info from ini var
	$dbhost = trim($file["dbhost"]);
	$dbuser = trim($file["dbuser"]);
	$dbpass = trim($file["dbpass"]);
	$dbname = trim($file["dbname"]);
*/

    require ("access.php");
	require ("exxonconn.php");

	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();

	$regIDs = $_POST['sendmsg'];
	$promotion = $_POST['message'];
	$image = $_POST['image'];
	
	$access->insertPromotion($promotion, $image);
/*	
    $result = $access->selectAllUsers();
	$regIDs = array();
	if(mysqli_num_rows($result) > 0 ){
		while ($row = mysqli_fetch_assoc($result)) {
			$regIDs[] = $row["regID"];
		}
	}
*/    
	$message = array("promotion" => $promotion);
	$message_status = send_notification($regIDs, $message);
	
    $access->disconnect();

	$resp = "<tr id='header'><td>Server Response [".date("h:i:sa")."]</td></tr>";
	$resp = $resp."<tr><td>".$message_status."</td></tr>";
	echo "<table>".$resp."</table>";
    
 ?>