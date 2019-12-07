<?php
    
    include 'mysqlconfig.php';

    //$usrID;

    /**
     *      prop_id from prop_mast              prop_user_id
     *      
     *      own_id from own_mast                own_prop_id
     *      tent_id from tent_mast              tent_prop_id
     *      wit_id from wit_mast                wit_prop_id
     *      prop_pay_id from prop_pay_trans     prop_pay_prop_id
     *      rent_trans_id from rent_serv_trans  rent_trans_prop_id
     *      agm_token_id from agree_mast        agm_user_id AND agm_prop_id
     */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['user_id']);

        $sql = "SELECT p.prop_id, p.prop_serv_type FROM prop_mast p WHERE prop_user_id=" . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($propID, $serviceType);

        $ids = array();
        // $tmpArr = array();

        $tmpID;

        while($stmt->fetch()){
            $tmpArr = array();
            
            $tmpArr['prop_id'] = $propID;
            $tmpArr['service_type'] = $serviceType;
            $tmpArr['own_id'] = 0;
            $tmpArr['tent_id'] = 0;
            $tmpArr['wit_id'] = 0;
            $tmpArr['rent_id'] = 0;
            $tmpArr['rent_trans_id'] = 0;
            $tmpArr['agm_id'] = 0;
            $tmpArr['token_id'] = "";
            
            $tmpID = $propID;
            $tmpServiceType = $serviceType;

            array_push($ids, $tmpArr);
        }

        // echo "Property ID : " . empty($propID);

        if(empty($propID)){
            echo "error";
            return;
        }else{
            $sql = "SELECT o.own_id, t.tent_id, w.wit_id, pay.prop_pay_id, r.rent_trans_id, a.agm_id, a.agm_token_id FROM own_mast o, tent_mast t, wit_mast w, prop_pay_trans pay, rent_serv_trans r, agree_mast a WHERE o.own_prop_id=" . $tmpID . "  OR t.tent_prop_id=" . $tmpID . " OR w.wit_prop_id=" . $tmpID . " OR pay.prop_pay_prop_id=" . $tmpID . " OR r.rent_trans_prop_id=" . $tmpID . " OR (a.agm_prop_id=" . $tmpID . " AND a.agm_user_id=" . $usrID .")";
            $stmt = $conn->prepare($sql);
            $stmt->execute();
            $stmt->bind_result($ownID, $tentID, $witID, $rentID, $rentTransID, $agmID, $tokenID);
    
    
            while($stmt->fetch()){
                $tmpArr = array();
    
                $tmpArr['prop_id'] = $tmpID;
                $tmpArr['service_type'] = $tmpServiceType;
                $tmpArr['own_id'] = $ownID;
                $tmpArr['tent_id'] = $tentID;
                $tmpArr['wit_id'] = $witID;
                $tmpArr['rent_id'] = $rentID;
                $tmpArr['rent_trans_id'] = $rentTransID;
                $tmpArr['agm_id'] = $agmID;
                $tmpArr['token_id'] = $tokenID;
    
                array_push($ids, $tmpArr);
            }

            header('Content-Type: application/json');
            echo json_encode($ids);
        }
        
    }
?>