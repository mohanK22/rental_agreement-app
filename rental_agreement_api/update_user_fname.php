<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $id = intval($_POST['id']);
        $name = $_POST['name'];

        $sql = "UPDATE user_mast SET user_name = '" . $name . "' WHERE user_id = " . $id . ";";
        
        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }
        
    }

?>