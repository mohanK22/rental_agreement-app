<?php
    include 'mysqlconfig.php';
    
    /*
        $usrID;
        $propID;
        $tentID;

        $id, $prop_id, $fname, $mname, $lname, $photo, $age, $gender, $mobno, $prof, $panno, $panno_upd, $aadharno, $adharno_front_upd, $adharno_back_upd, $bldg, $plotno, $floorno, $road, $area, $suburban, $city, $taluka, $state, $pcode, $notenant, $stat

    */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['user_id']);
        $propID = intval($_POST['property_id']);
        $tentID = intval($_POST['tenant_id']);

        $sql = "SELECT t.tent_id, t.tent_prop_id, t.tent_fname, t.tent_mname, t.tent_lname, t.tent_photo, t.tent_age, t.tent_gender, t.tent_mobno, t.tent_prof, t.tent_panno, t.tent_panno_upd, t.tent_aadharno, t.tent_adharno_front_upd, t.tent_adharno_back_upd, t.tent_bldg, t.tent_plotno, t.tent_floorno, t.tent_road, t.tent_area, t.tent_suburban, t.tent_city, t.tent_taluka, t.tent_state, t.tent_pcode, t.tent_notenant, t.tent_stat FROM tent_mast t, prop_mast p WHERE p.prop_user_id=" . $usrID . " AND p.prop_id=" . $propID . " AND t.tent_id=" . $tentID . " AND t.tent_prop_id=" . $propID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($id, $prop_id, $fname, $mname, $lname, $photo, $age, $gender, $mobno, $prof, $panno, $panno_upd, $aadharno, $adharno_front_upd, $adharno_back_upd, $bldg, $plotno, $floorno, $road, $area, $suburban, $city, $taluka, $state, $pcode, $notenant, $stat);

        $tenant = array();

        while($stmt->fetch()){
            $tmpArr = array();

            $tmpArr['id'] = $id; 
            $tmpArr['prop_id'] = $prop_id; 
            $tmpArr['fname'] = $fname; 
            $tmpArr['mname'] = $mname; 
            $tmpArr['lname'] = $lname;
            $tmpArr['photo'] = $photo; 
            $tmpArr['age'] = $age; 
            $tmpArr['gender'] = $gender; 
            $tmpArr['mob_no'] = $mobno;
            $tmpArr['prof'] = $prof; 
            $tmpArr['pan_no'] = $panno; 
            $tmpArr['pan_no_upd'] = $panno_upd; 
            $tmpArr['aadhar_no'] = $aadharno; 
            $tmpArr['aadhar_no_front_upd'] = $adharno_front_upd; 
            $tmpArr['aadhar_no_back_upd'] = $adharno_back_upd; 
            $tmpArr['bld'] = $bldg; 
            $tmpArr['plot'] = $plotno; 
            $tmpArr['floor_no'] = $floorno;
            $tmpArr['road'] = $road; 
            $tmpArr['area'] = $area; 
            $tmpArr['suburban'] = $suburban; 
            $tmpArr['city'] = $city; 
            $tmpArr['taluka'] = $taluka; 
            $tmpArr['state'] = $state; 
            $tmpArr['pcode'] = $pcode; 
            $tmpArr['co'] = $notenant; 
            $tmpArr['stat'] = $stat;

            array_push($tenant, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($tenant);
    }
?>