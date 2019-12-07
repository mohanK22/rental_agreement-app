<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $id = intval($_POST['id']);
        $passwd = $_POST['pwd'];

        $pwd = md5($passwd);

        $sql = "UPDATE user_mast SET user_pwd = '" . $pwd . "' WHERE user_id = " . $id . ";";
        
        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }
        
    }

?>