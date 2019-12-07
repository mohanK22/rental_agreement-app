<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrID = intval($_POST['user_id']);
        $agmID = intval($_POST['agm_id']);

        $sql = "SELECT agm_prop_id FROM agree_mast WHERE agree_mast.agm_user_id = " . $usrID . " AND agree_mast.agm_id = " . $agmID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($propID);

        while($stmt->fetch()){
            echo $propID;
        }
        
    }

?>