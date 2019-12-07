<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $id = intval($_POST['id']);
        $emailid = $_POST['email'];

        $pwd = md5($passwd);

        $sql = "UPDATE user_mast SET user_emailid = '" . $emailid . "' WHERE user_id = " . $id . ";";
        
        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }
        
    }

?>