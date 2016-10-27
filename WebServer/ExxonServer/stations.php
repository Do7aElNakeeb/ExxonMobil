<?php

    require ("secure/access.php");
	require ("secure/exxonconn.php");
	
	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();
    $stations = $access->selectAllUsers("stations");
    $a = array();
    $b = array();
    if ($stations != false){
        while ($row = mysqli_fetch_array($stations)) {      
            $b["name"] = $row["name"];
            $b["address"] = $row["address"];
            $b["region"] = $row["region"];
            $b["city"] = $row["city"];
            $b["latitude"] = $row["latitude"];
            $b["longitude"] = $row["longitude"];
            $b["MOG80"] = $row["MOG80"];
            $b["MOG92"] = $row["MOG92"];
            $b["MOG95"] = $row["MOG95"];
            $b["diesel"] = $row["diesel"];
            $b["MobilMart"] = $row["MobilMart"];
            $b["OnTheRun"] = $row["OnTheRun"];
            $b["TheWayToGo"] = $row["TheWayToGo"];
            $b["CarWash"] = $row["CarWash"];
            $b["lubricants"] = $row["lubricants"];
            $b["phone"] = $row["phone"];
            array_push($a,$b);
        }
        echo json_encode($a);
    }
?>