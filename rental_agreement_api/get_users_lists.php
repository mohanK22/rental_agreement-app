<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {
    
        $sql = "SELECT u.user_id, u.user_name, u.user_usrname FROM user_mast u;";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($id, $name, $username);

        $userLists = array();

        while($stmt->fetch()){
            $tmpArr = array();
        
            $tmpArr['user_id'] = $id;
            $tmpArr['user_name'] = $name;
            $tmpArr['user_username'] = $username;

            array_push($userLists, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($userLists);
        
    }

?>