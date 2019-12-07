<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['usrId']);

        $sql = "SELECT agm_id FROM agree_mast WHERE agm_user_id=" . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($id);

        $agreement_id = array();

        while($stmt->fetch()){
            $tmpArr = array();
            $tmpArr['agm_id'] = $id;
            array_push($agreement_id, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($agreement_id);

        // echo "Array Lenght :  " . count($agreement_id, COUNT_NORMAL);
    }

?>