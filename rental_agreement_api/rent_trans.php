<?php

    include 'mysqlconfig.php';

    /*
        $prop_id
        
        $trans_mode 
        $trans_cash_amt 
        $trans_chk_bank_name 
        $trans_chk_branch_name 
        $trans_chk_date 
        $trans_chk_no 
        $trans_chk_amt 
        $trans_chk_acc_no 
        $trans_mode_type 
        $trans_bnk_name 
        $trans_branch_name 
        $trans_acc_no 
        $trans_dt 
        $trans_refid 
        $trans_pay_amt 
        $trans_pay_opt

        $stat
    */

    if($_SERVER['REQUEST_METHOD']=='POST') {
    
        $prop_id = intval($_POST['prop_id']);

        $trans_mode = $_POST['trans_mode'];
        $trans_cash_amt = $_POST['cash_amt'];
        $trans_chk_bank_name = $_POST['chk_bnk_name'];
        $trans_chk_branch_name = $_POST['chk_branch_name'];
        $trans_chk_date = $_POST['chk_dt'];
        $trans_chk_no = $_POST['chk_no'];
        $trans_chk_amt = $_POST['chk_amt'];
        $trans_chk_acc_no = $_POST['chk_acc_no'];
        $trans_mode_type = $_POST['mode_type'];
        $trans_bnk_name = $_POST['bnk_name'];
        $trans_branch_name = $_POST['branch_name'];
        $trans_acc_no = $_POST['acc_no'];
        $trans_dt = $_POST['trans_dt'];
        $trans_refid = $_POST['ref_id'];
        $trans_pay_amt = $_POST['pay_amt'];
        $trans_pay_opt = $_POST['pay_opt'];
        
        $stat = intval($_POST['stat']);

        $sql="INSERT INTO rent_serv_trans(rent_trans_prop_id, rent_trans_mode, rent_trans_cash_amt, rent_trans_chk_bank_name, rent_trans_chk_branch_name, rent_trans_chk_date, rent_trans_chk_no, rent_trans_chk_amt, rent_trans_chk_acc_no, rent_trans_mode_type, rent_trans_bnk_name, rent_trans_branch_name, rent_trans_acc_no, rent_trans_dt, rent_trans_refid, rent_trans_pay_amt, rent_trans_pay_opt, rent_trans_stat) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        $stmt=$conn->prepare($sql);

        //binding params
        $stmt->bind_param("issssssssssssssssi", $prop_id,
        $trans_mode,
        $trans_cash_amt,
        $trans_chk_bank_name,
        $trans_chk_branch_name,
        $trans_chk_date, 
        $trans_chk_no,
        $trans_chk_amt,
        $trans_chk_acc_no, 
        $trans_mode_type, 
        $trans_bnk_name,
        $trans_branch_name,
        $trans_acc_no,
        $trans_dt,
        $trans_refid,
        $trans_pay_amt,
        $trans_pay_opt,
        $stat);

        if($stmt->execute()) {
            $status="success";

            if($status=="success") {

                $sql="SELECT rent_trans_id FROM rent_serv_trans WHERE rent_trans_prop_id=". $prop_id . ";";
                $stmt=$conn->prepare($sql);
                $stmt->execute();
                $stmt->bind_result($rent_trans_id);

                while($stmt->fetch()) {
                    echo $rent_trans_id;
                }
            }
        }else {
            echo "error";
        }

    }

?>