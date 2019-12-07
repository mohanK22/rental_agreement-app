<?php
    include 'mysqlconfig.php';
    
    /*
        $usrID;
        $propID;
        $witID;

        wit_id, wit_prop_id, wit1_name, wit1_photo, wit1_age, wit1_gender, wit1_mobno, wit1_prof, wit1_panno, wit1_aadhar, wit1_aadhar_front_upd, wit1_aadhar_back_upd, wit1_add1, wit1_add2, wit2_name, wit2_photo, wit2_age, wit2_gender, wit2_mobno, wit2_prof, wit2_panno, wit2_aadhar, wit2_aadhar_front_upd, wit2_aadhar_back_upd, wit2_add1, wit2_add2, wit_stat

    */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['user_id']);
        $propID = intval($_POST['property_id']);
        $witID = intval($_POST['witness_id']);

        $sql = "SELECT w.wit_id, w.wit_prop_id, w.wit1_name, w.wit1_photo, w.wit1_age, w.wit1_gender, w.wit1_mobno, w.wit1_prof, w.wit1_panno, w.wit1_aadhar, w.wit1_aadhar_front_upd, w.wit1_aadhar_back_upd, w.wit1_add1, w.wit1_add2, w.wit2_name, w.wit2_photo, w.wit2_age, w.wit2_gender, w.wit2_mobno, w.wit2_prof, w.wit2_panno, w.wit2_aadhar, w.wit2_aadhar_front_upd, w.wit2_aadhar_back_upd, w.wit2_add1, w.wit2_add2, w.wit_stat FROM wit_mast w, prop_mast p WHERE p.prop_user_id=" . $usrID . " AND p.prop_id=" . $propID . " AND w.wit_id=" . $witID . " AND w.wit_prop_id=" . $propID. ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($wit_id, $wit_prop_id, $wit1_name, $wit1_photo, $wit1_age, $wit1_gender, $wit1_mobno, $wit1_prof, $wit1_panno, $wit1_aadhar, $wit1_aadhar_front_upd, $wit1_aadhar_back_upd, $wit1_add1, $wit1_add2, $wit2_name, $wit2_photo, $wit2_age, $wit2_gender, $wit2_mobno, $wit2_prof, $wit2_panno, $wit2_aadhar, $wit2_aadhar_front_upd, $wit2_aadhar_back_upd, $wit2_add1, $wit2_add2, $wit_stat);

        $witness = array();

        while($stmt->fetch()){
            $tmpArr = array();

            $tmpArr['id'] = $wit_id;
            $tmpArr['prop_id'] = $wit_prop_id;
            $tmpArr['1_name'] = $wit1_name;
            $tmpArr['1_photo'] = $wit1_photo;
            $tmpArr['1_age'] = $wit1_age;
            $tmpArr['1_gender'] = $wit1_gender;
            $tmpArr['1_mob_no'] = $wit1_mobno;
            $tmpArr['1_prof'] = $wit1_prof;
            $tmpArr['1_pan_no'] = $wit1_panno;
            $tmpArr['1_aadhar'] = $wit1_aadhar;
            $tmpArr['1_aadhar_front_upd'] = $wit1_aadhar_front_upd;
            $tmpArr['1_aadhar_back_upd'] = $wit1_aadhar_back_upd;
            $tmpArr['1_add1'] = $wit1_add1;
            $tmpArr['1_add2'] = $wit1_add2;
            $tmpArr['2_name'] = $wit2_name;
            $tmpArr['2_photo'] = $wit2_photo;
            $tmpArr['2_age'] = $wit2_age;
            $tmpArr['2_gender'] = $wit2_gender;
            $tmpArr['2_mob_no'] = $wit2_mobno;
            $tmpArr['2_prof'] = $wit2_prof;
            $tmpArr['2_pan_no'] = $wit2_panno;
            $tmpArr['2_aadhar'] = $wit2_aadhar;
            $tmpArr['2_aadhar_front_upd'] = $wit2_aadhar_front_upd;
            $tmpArr['2_aadhar_back_upd'] = $wit2_aadhar_back_upd;
            $tmpArr['2_add1'] = $wit2_add1;
            $tmpArr['2_add2'] = $wit2_add2;
            $tmpArr['stat'] = $wit_stat;

            array_push($witness, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($witness);
    }
?>