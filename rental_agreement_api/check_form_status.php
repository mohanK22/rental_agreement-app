<?php
    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrID = intval($_POST['user_id']);
        $propID = intval($_POST['property_id']);

        $status = array();

        $global_own_stat = null;
        $global_tent_stat = null;
        $global_wit_stat = null;
        $global_prop_pay_stat = null;
        $global_rent_trans_stat = null;

        $statusBool = false;

        //own_stat
        $sql = "SELECT o.own_id FROM own_mast o, prop_mast prop WHERE o.own_prop_id = " . $propID . " AND prop.prop_id = " . $propID . " AND prop.prop_user_id = " . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($ownStat);

        while($stmt->fetch()){
            $tmpArr = array();

            $global_own_stat = $ownStat;

            $tmpArr['own_stat'] = $global_own_stat;
            $tmpArr['tent_stat'] = $global_tent_stat;
            $tmpArr['wit_stat'] = $global_wit_stat;
            $tmpArr['prop_pay_stat'] = $global_prop_pay_stat;
            $tmpArr['rent_trans_stat'] = $global_rent_trans_stat;

            array_push($status, $tmpArr);

            $statusBool = true;
        }


        //tent_stat
        $sql = "SELECT t.tent_id FROM tent_mast t, prop_mast prop WHERE t.tent_prop_id = " . $propID . " AND prop.prop_id = " . $propID . " AND prop.prop_user_id = " . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($tentStat);

        while($stmt->fetch()){
            $tmpArr = array();

            $global_tent_stat = $tentStat;

            $tmpArr['own_stat'] = $global_own_stat;
            $tmpArr['tent_stat'] = $global_tent_stat;
            $tmpArr['wit_stat'] = $global_wit_stat;
            $tmpArr['prop_pay_stat'] = $global_prop_pay_stat;
            $tmpArr['rent_trans_stat'] = $global_rent_trans_stat;

            array_push($status, $tmpArr);

            $statusBool = true;
        }

        //wit_stat
        $sql = "SELECT w.wit_id FROM wit_mast w, prop_mast prop WHERE w.wit_prop_id = " . $propID . " AND prop.prop_id = " . $propID . " AND prop.prop_user_id = " . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($witStat);

        while($stmt->fetch()){
            $tmpArr = array();

            $global_wit_stat = $witStat;

            $tmpArr['own_stat'] = $global_own_stat;
            $tmpArr['tent_stat'] = $global_tent_stat;
            $tmpArr['wit_stat'] = $global_wit_stat;
            $tmpArr['prop_pay_stat'] = $global_prop_pay_stat;
            $tmpArr['rent_trans_stat'] = $global_rent_trans_stat;

            array_push($status, $tmpArr);

            $statusBool = true;
        }



        //prop_pay_stat
        $sql = "SELECT p.prop_pay_id FROM prop_pay_trans p, prop_mast prop WHERE p.prop_pay_prop_id = " . $propID . " AND prop.prop_id = " . $propID . " AND prop.prop_user_id = " . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($propPayStat);


        while($stmt->fetch()){
            $tmpArr = array();

            $global_prop_pay_stat = $propPayStat;

            $tmpArr['own_stat'] = $global_own_stat;
            $tmpArr['tent_stat'] = $global_tent_stat;
            $tmpArr['wit_stat'] = $global_wit_stat;
            $tmpArr['prop_pay_stat'] = $global_prop_pay_stat;
            $tmpArr['rent_trans_stat'] = $global_rent_trans_stat;

            array_push($status, $tmpArr);

            $statusBool = true;
        }

        //rent_trans_stat
        $sql = "SELECT r.rent_trans_id FROM rent_serv_trans r, prop_mast prop WHERE r.rent_trans_prop_id = " . $propID . " AND prop.prop_id = " . $propID . " AND prop.prop_user_id = " . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($rentTransStat);

        while($stmt->fetch()){
            $tmpArr = array();

            $global_rent_trans_stat = $rentTransStat;

            $tmpArr['own_stat'] = $global_own_stat;
            $tmpArr['tent_stat'] = $global_tent_stat;
            $tmpArr['wit_stat'] = $global_wit_stat;
            $tmpArr['prop_pay_stat'] = $global_prop_pay_stat;
            $tmpArr['rent_trans_stat'] = $global_rent_trans_stat;

            array_push($status, $tmpArr);

            $statusBool = true;
        }


        // $sql = "SELECT o.own_stat, t.tent_stat, w.wit_stat, p.prop_pay_stat, r.rent_trans_stat FROM own_mast o, tent_mast t, wit_mast w, prop_pay_trans p, rent_serv_trans r, prop_mast prop WHERE o.own_prop_id=" . $propID . " AND t.tent_prop_id=" . $propID . " AND w.wit_prop_id=" . $propID . " AND p.prop_pay_prop_id=" . $propID . " AND r.rent_trans_prop_id=" . $propID . " AND prop.prop_id=" . $propID . " AND prop.prop_user_id=" . $usrID . ";";
        // $stmt = $conn->prepare($sql);
        // $stmt->execute();
        // $stmt->bind_result($ownStat, $tentStat, $witStat, $propPayStat, $rentTransStat);

        // $status = array();

        // $statusBool = false;

        // while($stmt->fetch()){
        //     $tmpArr = array();

        //     $tmpArr['own_stat'] = $ownStat;
        //     $tmpArr['tent_stat'] = $tentStat;
        //     $tmpArr['wit_stat'] = $witStat;
        //     $tmpArr['prop_pay_stat'] = $propPayStat;
        //     $tmpArr['rent_trans_stat'] = $rentTransStat;

        //     array_push($status, $tmpArr);

        //     $statusBool = true;
        // }

        //echo "status value : > " . $statusBool . "<";

        if(!$statusBool){
            $tmpArr = array();

            $tmpArr['own_stat'] = null;
            $tmpArr['tent_stat'] = null;
            $tmpArr['wit_stat'] = null;
            $tmpArr['prop_pay_stat'] = null;
            $tmpArr['rent_trans_stat'] = null;

            array_push($status, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($status);
    }
?>