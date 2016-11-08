<?php
 
if($_SERVER["REQUEST_METHOD"] == "POST")
{
  	require ("secure/access.php");
	require ("secure/exxonconn.php");
	
	$name = $_POST['name'];
	$description = $_POST['description'];
	
	$folder = "products/";
	$upload_image=$_FILES["imageToUpload"][ "name" ];
	move_uploaded_file($_FILES["imageToUpload"]["tmp_name"], "$folder".$_FILES["imageToUpload"]["name"]);
	
	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();
	$access->insertProduct($name, $description, $upload_image);
	
  $access->disconnect();
	
	echo "Product Added Successfully";

 
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
           
					<h1 class="text-center">Add Product</h1>
					</br>

	<!-- Promotion Form -->
	   <div class="row">
	<form role="form" name="form1" id="form1" method="post" enctype="multipart/form-data" action="" class="form-horizontal">
  
       
      <div class="form-group">
      <i class="mdi-editor-mode-edit prefix"></i>
      <label for="description" class="col-lg-2 control-label">Description</label>
      <div class="col-md-4">
	  <textarea class="form-control" rows="1" id="name" name="name" cols="45" placeholder="Product Name"></textarea> <br/>
      <textarea class="form-control" rows="5" id="description" name="description" cols="45" placeholder="Product Description"></textarea> <br/>
      <input type="file" name="imageToUpload" id="imageToUpload">
	  </div>
      </div>
      <div class="form-group">
      <label class="col-md-1"></label><label class="col-md-1"></label><label class="col-md-1"></label>
		<button type="submit" class="btn btn-success" name="submit" value="Submit">Add Product<i class="mdi-content-send right"></i></button>
        <button class="btn btn-danger" name="Reset" type="reset" value="Reset">Reset!</button>
	  </div>

</form>
</div>	   
	    
  </div>

      
</body>

</html>