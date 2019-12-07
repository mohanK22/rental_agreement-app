<?php 
	include 'mysqlconfig.php';

	if($_SERVER['REQUEST_METHOD']=='POST') {

		$username=$_POST['username'];
        $passwd=$_POST['pwd'];
        
		$pwd=md5($passwd);

		//Creating sql query
		$sql="SELECT * FROM admin_mast WHERE admin_usrname='". $username ."' AND admin_pwd='". $pwd ."'";

		//executing query
		$result=mysqli_query($conn, $sql);

		//fetching result
		$check=mysqli_fetch_array($result);

		if(isset($check)) {
            echo "success";
		}else {
			echo "error";
		}

	}

?>