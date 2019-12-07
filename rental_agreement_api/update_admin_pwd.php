<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $id = intval($_POST['id']);
        $passwd = $_POST['pwd'];
        $oldpwd = $_POST['oldpwd'];

        $pwd = md5($oldpwd);

        $sql="SELECT * FROM admin_mast WHERE admin_id ='". $id ."' AND admin_pwd='". $pwd ."'";

		//executing query
		$result=mysqli_query($conn, $sql);

		//fetching result
		$check=mysqli_fetch_array($result);

		if(isset($check)) {
            //echo "success";
            $pwd = md5($passwd);
            $sql = "UPDATE admin_mast SET admin_pwd = '" . $pwd . "' WHERE admin_id = " . $id . ";";
            
            if($conn->query($sql) === TRUE){
                echo "success";
            }else{
                echo "error";
            }

		}else {
			echo "error";
		}

        
    }

?>