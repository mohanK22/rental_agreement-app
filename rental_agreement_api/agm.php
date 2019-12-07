<?php

    include 'mysqlconfig.php'; 

    /*
        $usr_id
        $serv_type
        $prop_id
        $prop_pay_id
        $rent_trans
        $token_id
     */

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usr_id = intval($_POST['user_id']);
        $serv_type = intval($_POST['service_type']);
        $prop_id = intval($_POST['prop_id']);
        $prop_pay_id = intval($_POST['prop_pay_id']);
        $rent_trans = intval($_POST['rent_trans_id']);
        $token_id = $_POST['token_id'];

        $sql="INSERT INTO agree_mast(agm_user_id, agree_serv_typ, agm_prop_id, agm_prop_pay_id, agm_rent_trans_id, agm_token_id) VALUES(?, ?, ?, ?, ?, ?);";
        $stmt=$conn->prepare($sql);

        //binding params
        $stmt->bind_param("iiiiis", $usr_id,
        $serv_type,
        $prop_id,
        $prop_pay_id,
        $rent_trans,
        $token_id);

        if($stmt->execute()) {
            $status="success";

            if($status=="success") {

                $sql="SELECT agm_id FROM agree_mast WHERE agm_prop_id=". $prop_id . ";";
                $stmt=$conn->prepare($sql);
                $stmt->execute();
                $stmt->bind_result($agmid);

                while($stmt->fetch()) {
                    echo $agmid;
                }

                // Code For PDF Generation



                //End of PDF Generation Code

            }
        }else {
            echo "error";
        }

    }

?>