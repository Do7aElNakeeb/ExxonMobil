<?php

class access{
	//connection global variables
	var $host = null;
	var $username = null;
	var $dpass = null;
	var $dname = null;
	var $conn = null;
	var $result = null;
	
	public function __construct($dbhost, $dbuser, $dbpass, $dbname){
		
		$this->host = $dbhost;
		$this->username = $dbuser;
		$this->dpass = $dbpass;
		$this->dname = $dbname;
	}

	public function connect(){
		$this->conn = new mysqli($this->host, $this->username, $this->dpass, $this->dname);
		if (mysqli_connect_errno($this->conn))
		{
			echo "Failed to connect to MySQL: " . mysqli_connect_error();  
		}

		$this->conn->set_charset("utf8");

	}
		
	public function disconnect(){
		if($this->conn != null){
			$this->conn->close();

		}
	}

	// insert user into database
	public function registerUser($name, $email, $password, $salt, $mobile, $carBrand, $carModel, $carYear, $regID){

		$result = $this->selectUser($email);
		if ($result){
			return;
		}
		else{
			$sql = "INSERT INTO users SET name=?, email=?, password=?, salt=?, mobile=?, carBrand=?, carModel=?, carYear=?, regID=?";
			$statement = $this->conn->prepare($sql);

			if(!$statement){
				throw new Exception($statement->error);
			}		
			// bind 9 parameters of type string to be placed in $sql command
			$statement->bind_param("sssssssss", $name, $email, $password, $salt, $mobile, $carBrand, $carModel, $carYear, $regID);
			$returnValue = $statement->execute();
			return $returnValue;
			
		}
	}
	
	// insert user into database
	public function updateUser($name, $email, $password, $salt, $mobile, $carBrand, $carModel, $carYear, $regID){

		$sql = "UPDATE users SET name=?, email=?, password=?, salt=?, mobile=?, carBrand=?, carModel=?, carYear=? WHERE regID=?";
		$statement = $this->conn->prepare($sql);

		if(!$statement){
			throw new Exception($statement->error);
		}		
		// bind 9 parameters of type string to be placed in $sql command
		$statement->bind_param("sssssssss", $name, $email, $password, $salt, $mobile, $carBrand, $carModel, $carYear, $regID);
		$returnValue = $statement->execute();
		return $returnValue;
			
	}
 
	//select user form database
	public function selectUser($email){
		$sql = "SELECT * FROM users WHERE email = '".$email."' ";
		$result = $this->conn->query($sql);

		if($result !=null && (mysqli_num_rows($result) >=1)){
			$row = $result->fetch_array(MYSQLI_ASSOC);

			if(!empty($row)){
				$returnArray = $row;
				return $returnArray;
			}
		}
	}
	
	// insert promotions into database
	public function insertPromotion($message, $image){

		$sql = "INSERT INTO promotions SET message=?, image=?";
		$statement = $this->conn->prepare($sql);
		if(!$statement){
			throw new Exception($statement->error);
		}		
		// bind 9 parameters of type string to be placed in $sql command
		$statement->bind_param("ss", $message, $image);
		$returnValue = $statement->execute();
		return $returnValue;
			
	}
	
	// insert product into database
	public function insertProduct($name, $description, $image){

		$sql = "INSERT INTO products SET name=?, description=?, image=?";
		$statement = $this->conn->prepare($sql);
		if(!$statement){
			throw new Exception($statement->error);
		}		
		// bind 9 parameters of type string to be placed in $sql command
		$statement->bind_param("sss", $name, $description, $image);
		$returnValue = $statement->execute();
		return $returnValue;
			
	}

/*	public function selectRegIDsOfAllUsers(){
		$sql = "SELECT regID FROM users";
		$result = $this->conn->query($sql);
		$regIDs = array();
		if(mysqli_num_rows($result) > 0 ){
			while ($row = mysqli_fetch_assoc($result)) {
				$regIDs[] = $row["regID"];
			}
		}
		return $regIDs;
	}
*/
	public function selectAllUsers($tableName){
		$sql = "SELECT * FROM $tableName ORDER by id DESC";
		$result = $this->conn->query($sql);
		return $result;
	}
	
	   /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
 
}

?>