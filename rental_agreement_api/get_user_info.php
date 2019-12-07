<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['usrId']);

        $sql = "SELECT u.user_name, u.user_emailid, u.user_mobno, u.user_profession, u.user_dob, u.user_type, u.user_usrname FROM user_mast u WHERE u.user_id=" . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($user_name, $user_emailid, $user_mobno, $user_profession, $user_dob, $user_type, $user_usrname);

        $user_info = array();

        while($stmt->fetch()){
            $tmpArr = array();

            $tmpArr['full_name'] = $user_name;
            $tmpArr['email_id'] = $user_emailid;
            $tmpArr['mobile_no'] = $user_mobno;
            $tmpArr['profession'] = $user_profession;
            $tmpArr['dob'] = $user_dob;
            $tmpArr['user_type'] = $user_type;
            $tmpArr['user_name'] = $user_usrname;

            array_push($user_info, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($user_info);
    }

?>