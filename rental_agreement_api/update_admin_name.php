<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $id = intval($_POST['id']);
        $newname = $_POST['new_name'];

        $sql = "UPDATE admin_mast SET admin_usrname = '" . $newname . "' WHERE admin_id = " . $id . ";";
        
        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }
        
    }

?>