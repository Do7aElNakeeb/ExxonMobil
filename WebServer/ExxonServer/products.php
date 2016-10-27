<?php

    require ("secure/access.php");
	require ("secure/exxonconn.php");
	
	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();
    $stations = $access->selectAllUsers("products");
    $a = array();
    $b = array();
    if ($stations != false){
        while ($row = mysqli_fetch_array($stations)) {      
            $b["name"] = $row["name"];
            $b["message"] = $row["message"];
			$b["image"] = $row["image"];
            $b["created_at"] = $row["created_at"];
            array_push($a,$b);
        }
        echo json_encode($a);
    }
?>