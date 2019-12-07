<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
     
        $sql = "SELECT a.agm_id, a.agm_user_id, u.user_name, u.user_usrname FROM agree_mast a, user_mast u WHERE a.agm_user_id=u.user_id;";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($agmId, $userId, $userName, $username);

        $agmLists = array();

        while($stmt->fetch()){
            $tmpArr = array();
        
            $tmpArr['agm_id'] = $agmId;
            $tmpArr['user_id'] = $userId;
            $tmpArr['user_name'] = $userName;
            $tmpArr['user_username'] = $username;

            array_push($agmLists, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($agmLists);

    }

?>