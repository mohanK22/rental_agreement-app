<?php
    include 'mysqlconfig.php';

	if($_SERVER['REQUEST_METHOD']=='POST') {

        $global_cnt_users;
        $global_cnt_agms;

        $values = array();

        $sql = "SELECT COUNT(u.user_id) FROM user_mast u;";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($cntUsers);

        while($stmt->fetch()){
            $tmpArr = array();
            
            $global_cnt_users = $cntUsers;

            $tmpArr['no_users'] = $cntUsers;
            $tmpArr['no_agms'] = null;

            array_push($values, $tmpArr);
        }

        $sql = "SELECT COUNT(a.agm_id) FROM agree_mast a;";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($cntAgms);


        while($stmt->fetch()){
            $tmpArr = array();
            
            $tmpArr['no_users'] = $global_cnt_users;
            $tmpArr['no_agms'] = $cntAgms;

            array_push($values, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($values);

    }
?>