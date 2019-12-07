<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {
     
        $userid = intval($_POST['id']);

        $userData = array();
        
        $sql ="SELECT u.user_id, u.user_name, u.user_emailid, u.user_mobno, u.user_profession, u.user_dob, u.user_type, u.user_usrname FROM user_mast u WHERE u.user_id = " . $userid . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($id, $name, $emailid, $phone, $profession, $dob, $type, $username);
        
        while($stmt->fetch()){
            $tmpArr = array();
        
            $tmpArr['user_id'] = $id;
            $tmpArr['user_name'] = $name;
            $tmpArr['user_email_id'] = $emailid;
            $tmpArr['user_phone'] = $phone;
            $tmpArr['user_profession'] = $profession;
            $tmpArr['user_dob'] = $dob;
            $tmpArr['user_type'] = $type;
            $tmpArr['user_username'] = $username;

            array_push($userData, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($userData);

    }

?>