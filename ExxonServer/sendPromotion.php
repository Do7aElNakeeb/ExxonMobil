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
 
if($_SERVER["REQUEST_METHOD"] == "POST")
{
  	require ("secure/access.php");
	require ("secure/exxonconn.php");
	
	$promotion = $_POST['message'];
	
	$folder = "promotions/";
	$upload_image=$_FILES["imageToUpload"][ "name" ];
	move_uploaded_file($_FILES["imageToUpload"]["tmp_name"], "$folder".$_FILES["imageToUpload"]["name"]);
	
	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();
	$access->insertPromotion($promotion, $upload_image);
	$users = $access->selectAllUsers("users");
	
	if ($users !=false){
        $no_of_users = mysqli_num_rows($users);
    }
    else{
        $no_of_users = 0;    
    }

    if ($no_of_users > 0) {
		$regIDs = array();
		if(mysqli_num_rows($users) > 0 ){
			while ($row = mysqli_fetch_array($users)) {
				$regIDs[] = $row["regID"];
			}
		}
	}
	
	$message = array("promotion" => $promotion);
	$message_status = send_notification($regIDs, $message);
	
    $access->disconnect();
	
	echo "Promotion Sent Successfully";

 
}

?>



<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Mobil Promotions">
    <meta name="author" content="">

    <title>Send Alerts</title>

        <!-- Icon -->
    <link rel="shortcut icon" href="img/Logo.png" >
    
    <!--  Custom CSS -->
	<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"  media="screen,projection"/>	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
   
    <!-- Custom Fonts -->
    <link href="font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='http://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

    

</head>

<body id="page-top">

	<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>

    <!-- Header -->
    <header>
        
    </header>
    
	</br>
		</br>
			</br>
    
    <div class="logo" align="center"> <img src="img/mobil_tm.png"  /> </div>
           
					<h1 class="text-center">Send Promotions</h1>
					</br>

	<!-- Promotion Form -->
	   <div class="row">
	<form role="form" name="form1" id="form1" method="post" enctype="multipart/form-data" action="" class="form-horizontal">
  
       
      <div class="form-group">
      <i class="mdi-editor-mode-edit prefix"></i>
      <label for="message" class="col-lg-2 control-label">Message</label>
      <div class="col-md-4">
      <textarea class="form-control" rows="5" id="message" name="message" cols="45" placeholder="Promotion to send to Mobil users"></textarea> <br/>
      <input type="file" name="imageToUpload" id="imageToUpload">
	  </div>
      </div>
      <div class="form-group">
      <label class="col-md-1"></label><label class="col-md-1"></label><label class="col-md-1"></label>
		<button type="submit" class="btn btn-success" name="submit" value="Submit">Send Message<i class="mdi-content-send right"></i></button>
        <button class="btn btn-danger" name="Reset" type="reset" value="Reset">Reset!</button>
	  </div>

</form>
</div>	   
	    
  </div>

      
      
      

</body>

</html>