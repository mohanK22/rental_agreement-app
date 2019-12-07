<?php 

	include 'mysqlconfig.php';

	if($_SERVER['REQUEST_METHOD']=='POST') {

		$username=$_POST['username'];
		$passwd=$_POST['pwd'];

		$status="";

		$pwd=md5($passwd);

		//Creating sql query
		$sql="SELECT * FROM user_mast WHERE user_usrname='". $username ."' AND user_pwd='". $pwd ."'";

		//executing query
		$result=mysqli_query($conn, $sql);

		//fetching result
		$check=mysqli_fetch_array($result);

		if(isset($check)) {			
			$status="success";

			if($status=="success") {
				$sql="SELECT user_id, user_usrname FROM user_mast WHERE user_usrname='". $username ."' AND user_pwd='". $pwd ."'";
				$stmt=$conn->prepare($sql);
				$stmt->execute();
				$stmt->bind_result($usrid, $usrname);

				$usrs=array();

				while($stmt->fetch()) {
					echo $usrid;
				}
			}
		}else {
			echo "error";
		}

	}

?>