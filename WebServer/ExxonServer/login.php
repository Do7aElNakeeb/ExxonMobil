<?php
	
	$email = htmlentities($_REQUEST["email"]);
	$password = htmlentities($_REQUEST["password"]);
	$regID = htmlentities($_REQUEST["regID"]);

	if (empty($email) || empty($password) || empty($regID)){

		$returnArray["status"] = "400";
		$returnArray["message"] = "Missing Fields!";
		echo json_encode($returnArray);
		return;
	}

	require ("secure/access.php");
	require ("secure/exxonconn.php");

	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();

	$user = $access->selectUser($email);

	if ($user){
		// verifying user password
        $salt = $user['salt'];
        $secured_password = $user['password'];
        $hash = $access->checkhashSSHA($salt, $password);
    
	    // check for password equality       
		if ($hash == $secured_password){
			$returnArray["error"] = FALSE;
			$returnArray["message"] = "Login is Successfull";
			$returnArray["id"] = $user["id"];
			$returnArray["name"] = $user["name"];
			$returnArray["email"] = $user["email"];
			$returnArray["mobile"] = $user["mobile"];
			$returnArray["carBrand"] = $user["carBrand"];
			$returnArray["carModel"] = $user["carModel"];
			$returnArray["carYear"] = $user["carYear"];
			$returnArray["regID"] = $user["regID"];
			$returnArray["created_at"] = $user["created_at"];
			$access->updateUser($user["name"], $user["email"], $user["mobile"], $secured_password, $salt, $user["carBrand"], $user["carModel"], $user["carYear"], $regID);
		}
		else{
			$returnArray["error"] = TRUE;
			$returnArray["message"] = "Password is Incorrect";
		}
	}
	else{
		$returnArray["error"] = TRUE;
		$returnArray["message"] = "User isn't existed!";
	}

	$access->disconnect();

	echo json_encode($returnArray);
?>