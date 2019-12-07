<?php
    include 'mysqlconfig.php';
    
    /*
        $usrID;
        $propID;
        $rentID;

        $prop_pay_id, $prop_pay_prop_id, $prop_pay_period, $prop_pay_deposit, $prop_pay_var_mnth_per, $prop_pay_rent_type, $prop_pay_rent_fixed_amt, $prop_pay_mnth1, $prop_pay_rent1, $prop_pay_mnth2, $prop_pay_rent2, $prop_pay_mnth3, $prop_pay_rent3, $prop_pay_mnth4, $prop_pay_rent4, $prop_pay_mnth5, $prop_pay_rent5, $prop_pay_mnth6, $prop_pay_rent6, $prop_pay_mnth7, $prop_pay_rent7, $prop_pay_mnth8, $prop_pay_rent8, $prop_pay_mnth9, $prop_pay_rent9, $prop_pay_mnth10, $prop_pay_rent10, $prop_pay_mnth11, $prop_pay_rent11, $prop_pay_mnth12, $prop_pay_rent12, $prop_pay_dep_stat, $prop_pay_dep_stat_mode, $prop_pay_cash_dt, $prop_pay_chk_no, $prop_pay_bank_name, $prop_pay_branch_name, $prop_pay_chk_dt, $prop_pay_bank_acc_no, $prop_pay_agr_start_dt, $prop_pay_agr_end_dt, $prop_pay_cr_srv, $prop_pay_stat

    */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['user_id']);
        $propID = intval($_POST['property_id']);
        $rentID = intval($_POST['rent_id']);

        $sql = "SELECT pay.prop_pay_id, pay.prop_pay_prop_id, pay.prop_pay_period, pay.prop_pay_deposit, pay.prop_pay_var_mnth_per, pay.prop_pay_rent_type, pay.prop_pay_rent_fixed_amt, pay.prop_pay_mnth1, pay.prop_pay_rent1, pay.prop_pay_mnth2, pay.prop_pay_rent2, pay.prop_pay_mnth3, pay.prop_pay_rent3, pay.prop_pay_mnth4, pay.prop_pay_rent4, pay.prop_pay_mnth5, pay.prop_pay_rent5, pay.prop_pay_mnth6, pay.prop_pay_rent6, pay.prop_pay_mnth7, pay.prop_pay_rent7, pay.prop_pay_mnth8, pay.prop_pay_rent8, pay.prop_pay_mnth9, pay.prop_pay_rent9, pay.prop_pay_mnth10, pay.prop_pay_rent10, pay.prop_pay_mnth11, pay.prop_pay_rent11, pay.prop_pay_mnth12, pay.prop_pay_rent12, pay.prop_pay_dep_stat, pay.prop_pay_dep_stat_mode, pay.prop_pay_cash_dt, pay.prop_pay_chk_no, pay.prop_pay_bank_name, pay.prop_pay_branch_name, pay.prop_pay_chk_dt, pay.prop_pay_bank_acc_no, pay.prop_pay_agr_start_dt, pay.prop_pay_agr_end_dt, pay.prop_pay_cr_srv, pay.prop_pay_stat FROM prop_pay_trans pay, prop_mast p WHERE p.prop_id=" . $propID . " AND p.prop_user_id=" . $usrID . " AND pay.prop_pay_id=" . $rentID . " AND pay.prop_pay_prop_id=" . $propID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($prop_pay_id, $prop_pay_prop_id, $prop_pay_period, $prop_pay_deposit, $prop_pay_var_mnth_per, $prop_pay_rent_type, $prop_pay_rent_fixed_amt, $prop_pay_mnth1, $prop_pay_rent1, $prop_pay_mnth2, $prop_pay_rent2, $prop_pay_mnth3, $prop_pay_rent3, $prop_pay_mnth4, $prop_pay_rent4, $prop_pay_mnth5, $prop_pay_rent5, $prop_pay_mnth6, $prop_pay_rent6, $prop_pay_mnth7, $prop_pay_rent7, $prop_pay_mnth8, $prop_pay_rent8, $prop_pay_mnth9, $prop_pay_rent9, $prop_pay_mnth10, $prop_pay_rent10, $prop_pay_mnth11, $prop_pay_rent11, $prop_pay_mnth12, $prop_pay_rent12, $prop_pay_dep_stat, $prop_pay_dep_stat_mode, $prop_pay_cash_dt, $prop_pay_chk_no, $prop_pay_bank_name, $prop_pay_branch_name, $prop_pay_chk_dt, $prop_pay_bank_acc_no, $prop_pay_agr_start_dt, $prop_pay_agr_end_dt, $prop_pay_cr_srv, $prop_pay_stat);

        $rent = array();

        while($stmt->fetch()){
            $tmpArr = array();

            $tmpArr['id'] = $prop_pay_id; 
            $tmpArr['prop_id'] = $prop_pay_prop_id; 
            $tmpArr['period'] = $prop_pay_period; 
            $tmpArr['deposit'] = $prop_pay_deposit; 
            $tmpArr['var_mnth_per'] = $prop_pay_var_mnth_per; 
            $tmpArr['rent_type'] = $prop_pay_rent_type; 
            $tmpArr['rent_fixed_amt'] = $prop_pay_rent_fixed_amt; 
            $tmpArr['mnth1'] = $prop_pay_mnth1; 
            $tmpArr['rent1'] = $prop_pay_rent1; 
            $tmpArr['mnth2'] = $prop_pay_mnth2; 
            $tmpArr['rent2'] = $prop_pay_rent2; 
            $tmpArr['mnth3'] = $prop_pay_mnth3; 
            $tmpArr['rent3'] = $prop_pay_rent3; 
            $tmpArr['mnth4'] = $prop_pay_mnth4; 
            $tmpArr['rent4'] = $prop_pay_rent4; 
            $tmpArr['mnth5'] = $prop_pay_mnth5; 
            $tmpArr['rent5'] = $prop_pay_rent5; 
            $tmpArr['mnth6'] = $prop_pay_mnth6; 
            $tmpArr['rent6'] = $prop_pay_rent6; 
            $tmpArr['mnth7'] = $prop_pay_mnth7; 
            $tmpArr['rent7'] = $prop_pay_rent7; 
            $tmpArr['mnth8'] = $prop_pay_mnth8; 
            $tmpArr['rent8'] = $prop_pay_rent8; 
            $tmpArr['mnth9'] = $prop_pay_mnth9; 
            $tmpArr['rent9'] = $prop_pay_rent9; 
            $tmpArr['mnth10'] = $prop_pay_mnth10; 
            $tmpArr['rent10'] = $prop_pay_rent10; 
            $tmpArr['mnth11'] = $prop_pay_mnth11; 
            $tmpArr['rent11'] = $prop_pay_rent11; 
            $tmpArr['mnth12'] = $prop_pay_mnth12; 
            $tmpArr['rent12'] = $prop_pay_rent12; 
            $tmpArr['dep_stat'] = $prop_pay_dep_stat; 
            $tmpArr['dep_stat_mode'] = $prop_pay_dep_stat_mode; 
            $tmpArr['cash_dt'] = $prop_pay_cash_dt; 
            $tmpArr['chk_no'] = $prop_pay_chk_no; 
            $tmpArr['bank_name'] = $prop_pay_bank_name; 
            $tmpArr['branch_name'] = $prop_pay_branch_name; 
            $tmpArr['chk_dt'] = $prop_pay_chk_dt; 
            $tmpArr['bank_acc_no'] = $prop_pay_bank_acc_no; 
            $tmpArr['agr_start_dt'] = $prop_pay_agr_start_dt; 
            $tmpArr['agr_end_dt'] = $prop_pay_agr_end_dt; 
            $tmpArr['cr_srv'] = $prop_pay_cr_srv; 
            $tmpArr['stat'] = $prop_pay_stat;

            array_push($rent, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($rent);
    }
?>