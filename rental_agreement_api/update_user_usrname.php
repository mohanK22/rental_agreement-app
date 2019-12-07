<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $id = intval($_POST['id']);
        $username = $_POST['username'];

        $sql = "UPDATE user_mast SET user_usrname = '" . $username . "' WHERE user_id = " . $id . ";";
        
        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }
        
    }

?>