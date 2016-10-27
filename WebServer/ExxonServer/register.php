<?php

	$name = htmlentities($_REQUEST["name"]);
	$email = htmlentities($_REQUEST["email"]);
	$password = htmlentities($_REQUEST["password"]);
	$mobile = htmlentities($_REQUEST["mobile"]);
	$carBrand = htmlentities($_REQUEST["carBrand"]);
	$carModel = htmlentities($_REQUEST["carModel"]);
	$carYear = htmlentities($_REQUEST["carYear"]);
	$regID = htmlentities($_REQUEST["regID"]);

	if (empty($name) || empty($email) || empty($password) || empty($mobile) || empty($carBrand) || empty($carModel) || empty($carYear) || empty($regID)){

		$returnArray["error"] = TRUE;
		$returnArray["message"] = "Missing Fields!";
		echo json_encode($returnArray);
		return;
	}
	
	

/*	//secure way to build connection
	$file = parse_ini_file("secure/exxonconn.ini");

	//store in php var info from ini var
	$dbhost = trim($file["dbhost"]);
	$dbuser = trim($file["dbuser"]);
	$dbpass = trim($file["dbpass"]);
	$dbname = trim($file["dbname"]);
*/
	require ("secure/access.php");
	require ("secure/exxonconn.php");
	
	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();
	
	//secure password
//	$salt = openssl_random_pseudo_bytes(20);
//	$secured_password = sha1($password . $salt);
	$hash = $access->hashSSHA($password);
    $secured_password = $hash["encrypted"]; // encrypted password
    $salt = $hash["salt"]; // salt

	$result = $access->registerUser($name, $email, $secured_password, $salt, $mobile, $carBrand, $carModel, $carYear, $regID);

	if ($result){
		$user = $access->selectUser($email);
		$returnArray["error"] = FALSE;
		$returnArray["message"] = "Registeration is Successfull";
		$returnArray["id"] = $user["id"];
		$returnArray["name"] = $user["name"];
		$returnArray["email"] = $user["email"];
		$returnArray["mobile"] = $user["mobile"];
		$returnArray["carBrand"] = $user["carBrand"];
		$returnArray["carModel"] = $user["carModel"];
		$returnArray["carYear"] = $user["carYear"];
		$returnArray["regID"] = $user["regID"];
		$returnArray["created_at"] = $user["created_at"];
	}
	else{
		$returnArray["error"] = TRUE;
		$returnArray["message"] = "User already existed!";
	}

	$access->disconnect();

	echo json_encode($returnArray);

//http://do7aelnakeeb.cf/FOEASU/app/register.php?name=mamdouh&email=do7a@gmail.com&password=memo&mobile=01202638418&studentID=1301370&nationalID=21236589514525&GradYear=2018&gender=male

?>