<?php
    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrID = intval($_POST['user_id']);

        $sql = "SELECT MAX(prop_id) FROM prop_mast WHERE prop_user_id = " . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($max_id);

        $max_prop_id = array();

        while($stmt->fetch()){
            $tmpArr = array();

            $tmpArr['max_prop_id'] = $max_id;

            array_push($max_prop_id, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($max_prop_id);
    }else{
        echo "error";
    }
?>