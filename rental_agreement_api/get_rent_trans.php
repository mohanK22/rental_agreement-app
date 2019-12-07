<?php
    include 'mysqlconfig.php';
    
    /*
        $usrID;
        $propID;
        $rentTrasID;

        rent_trans_id, rent_trans_prop_id, rent_trans_mode, rent_trans_cash_amt, rent_trans_chk_bank_name, rent_trans_chk_branch_name, rent_trans_chk_date, rent_trans_chk_no, rent_trans_chk_amt, rent_trans_chk_acc_no, rent_trans_mode_type, rent_trans_bnk_name, rent_trans_branch_name, rent_trans_acc_no, rent_trans_dt, rent_trans_refid, rent_trans_pay_amt, rent_trans_pay_opt, rent_trans_charges, rent_trans_balance, rent_trans_stat
    */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['user_id']);
        $propID = intval($_POST['property_id']);
        $rentTrasID = intval($_POST['rent_trans_id']);

        $sql = "SELECT t.rent_trans_id, t.rent_trans_prop_id, t.rent_trans_mode, t.rent_trans_cash_amt, t.rent_trans_chk_bank_name, t.rent_trans_chk_branch_name, t.rent_trans_chk_date, t.rent_trans_chk_no, t.rent_trans_chk_amt, t.rent_trans_chk_acc_no, t.rent_trans_mode_type, t.rent_trans_bnk_name, t.rent_trans_branch_name, t.rent_trans_acc_no, t.rent_trans_dt, t.rent_trans_refid, t.rent_trans_pay_amt, t.rent_trans_pay_opt, t.rent_trans_charges, t.rent_trans_balance, t.rent_trans_stat FROM rent_serv_trans t, prop_mast p WHERE p.prop_id=" . $propID . " AND p.prop_user_id=" . $usrID . " AND t.rent_trans_id=" . $rentTrasID . " AND t.rent_trans_prop_id=" . $propID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($rent_trans_id, $rent_trans_prop_id, $rent_trans_mode, $rent_trans_cash_amt, $rent_trans_chk_bank_name, $rent_trans_chk_branch_name, $rent_trans_chk_date, $rent_trans_chk_no, $rent_trans_chk_amt, $rent_trans_chk_acc_no, $rent_trans_mode_type, $rent_trans_bnk_name, $rent_trans_branch_name, $rent_trans_acc_no, $rent_trans_dt, $rent_trans_refid, $rent_trans_pay_amt, $rent_trans_pay_opt, $rent_trans_charges, $rent_trans_balance, $rent_trans_stat);

        $rentTrans = array();

        while($stmt->fetch()){
            $tmpArr = array();

            $tmpArr['id'] = $rent_trans_id; 
            $tmpArr['prop_id'] = $rent_trans_prop_id; 
            $tmpArr['mode'] = $rent_trans_mode; 
            $tmpArr['cash_amt'] = $rent_trans_cash_amt; 
            $tmpArr['chk_bank_name'] = $rent_trans_chk_bank_name; 
            $tmpArr['chk_branch_name'] = $rent_trans_chk_branch_name; 
            $tmpArr['chk_date'] = $rent_trans_chk_date; 
            $tmpArr['chk_no'] = $rent_trans_chk_no; 
            $tmpArr['chk_amt'] = $rent_trans_chk_amt; 
            $tmpArr['chk_acc_no'] = $rent_trans_chk_acc_no; 
            $tmpArr['mode_type'] = $rent_trans_mode_type; 
            $tmpArr['bnk_name'] = $rent_trans_bnk_name; 
            $tmpArr['branch_name'] = $rent_trans_branch_name; 
            $tmpArr['acc_no'] = $rent_trans_acc_no; 
            $tmpArr['trans_dt'] = $rent_trans_dt; 
            $tmpArr['ref_id'] = $rent_trans_refid; 
            $tmpArr['pay_amt'] = $rent_trans_pay_amt; 
            $tmpArr['pay_opt'] = $rent_trans_pay_opt; 
            $tmpArr['charges'] = $rent_trans_charges; 
            $tmpArr['balance'] = $rent_trans_balance; 
            $tmpArr['stat'] = $rent_trans_stat;

            array_push($rentTrans, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($rentTrans);
    }
?>