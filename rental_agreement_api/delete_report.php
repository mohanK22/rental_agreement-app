<?php

    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $agmID = intval($_POST['agmId']);
        
        $dir = "report/";
        $file_name = $agmID . ".pdf";

        if(!unlink($dir . "" . $file_name)){
            echo "error";
        }else{
            echo "success";
        }

    }

?>