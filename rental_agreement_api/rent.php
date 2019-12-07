<?php

    include 'mysqlconfig.php';

    /*

        $prop_id
        $period
        $deposit 
        $monthlyRent  
        $rent_type
        $fixed_amt
        $rent1
        $rent2
        $rent3
        $rent4
        $rent5
        $rent6
        $rent7
        $rent8
        $rent9
        $rent10
        $rent11
        $rent12
        $deposit_stat
        $deposit_stat_mode
        $cash_dt
        $chk_no
        $bnk_name
        $branch_name
        $chk_dt
        $acc_no
        $agr_start_dt
        $agr_end_dt
        $cs_srv_stat
        $stat

    */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $prop_id = intval($_POST['prop_id']);
        
        $period = $_POST['period'];
        $deposit = $_POST['deposit']; 
        $monthlyRent = $_POST['monthly_rent'];
        $rent_type = $_POST['rent_type'];
        $fixed_amt = $_POST['fixed_amt'];
        $rent1 = $_POST['rent1'];
        $rent2 = $_POST['rent2'];
        $rent3 = $_POST['rent3'];
        $rent4 = $_POST['rent4'];
        $rent5 = $_POST['rent5'];
        $rent6 = $_POST['rent6'];
        $rent7 = $_POST['rent7'];
        $rent8 = $_POST['rent8'];
        $rent9 = $_POST['rent9'];
        $rent10 = $_POST['rent10'];
        $rent11 = $_POST['rent11'];
        $rent12 = $_POST['rent12'];
        $deposit_stat = $_POST['deposit_stat'];
        $deposit_stat_mode = $_POST['deposit_stat_mode'];
        $cash_dt = $_POST['cash_dt'];
        $chk_no = $_POST['chk_no'];
        $bnk_name = $_POST['bnk_name'];
        $branch_name = $_POST['branch_name'];
        $chk_dt = $_POST['chk_dt'];
        $acc_no = $_POST['acc_no'];
        $agr_start_dt = $_POST['agr_start_dt'];
        $agr_end_dt = $_POST['agr_end_dt'];
        $cs_srv_stat = $_POST['cs_stat'];

        $stat = intval($_POST['stat']);

        //Save user's data in the database
        $sql = "INSERT INTO prop_pay_trans(prop_pay_prop_id, prop_pay_period, prop_pay_deposit, prop_pay_var_mnth_per, prop_pay_rent_type, prop_pay_rent_fixed_amt, prop_pay_rent1, prop_pay_rent2, prop_pay_rent3, prop_pay_rent4, prop_pay_rent5, prop_pay_rent6, prop_pay_rent7, prop_pay_rent8, prop_pay_rent9, prop_pay_rent10, prop_pay_rent11, prop_pay_rent12, prop_pay_dep_stat, prop_pay_dep_stat_mode, prop_pay_cash_dt, prop_pay_chk_no, prop_pay_bank_name, prop_pay_branch_name, prop_pay_chk_dt, prop_pay_bank_acc_no, prop_pay_agr_start_dt, prop_pay_agr_end_dt, prop_pay_cr_srv, prop_pay_stat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        $stmt = $conn->prepare($sql);   

        //binding params
        $stmt->bind_param("issssssssssssssssssssssssssssi", $prop_id,
        $period,
        $deposit,
        $monthlyRent,   
        $rent_type,
        $fixed_amt,
        $rent1,
        $rent2,
        $rent3,
        $rent4,
        $rent5,
        $rent6,
        $rent7,
        $rent8,
        $rent9,
        $rent10,
        $rent11,
        $rent12,
        $deposit_stat,
        $deposit_stat_mode,
        $cash_dt,
        $chk_no,
        $bnk_name,
        $branch_name,
        $chk_dt,
        $acc_no,
        $agr_start_dt,
        $agr_end_dt,
        $cs_srv_stat,
        $stat);

        if($stmt->execute()){        
            $status = "success";

            if($status == "success"){

                $sql = "SELECT prop_pay_id FROM prop_pay_trans WHERE prop_pay_prop_id=" . $prop_id . ";";
                $stmt= $conn->prepare($sql);
                $stmt->execute();
                $stmt->bind_result($rentid);

                while($stmt->fetch()){
                    echo $rentid;
                }           
            }
        }else{
            echo "error";
        }

    }

?>