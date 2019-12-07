<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $id = intval($_POST['id']);
        $phone = $_POST['phone'];

        $sql = "UPDATE user_mast SET user_mobno = '" . $phone . "' WHERE user_id = " . $id . ";";
        
        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }
        
    }

?>