<?php
    include 'mysqlconfig.php';
    
    /*
        $usrID;
        $propID;
        $ownID;

        $id, $prop_id, $fname, $mname, $lname, $photo, $age, $gender, $mobno, $prof, $panno, $panno_upd, $aadharno, $adharno_front_upd, $adharno_back_upd, $bldg, $plot, $floorno, $road, $area, $suburban, $city, $taluka, $state, $pcode, $nocoowner, $stat

    */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['user_id']);
        $propID = intval($_POST['property_id']);
        $ownID = intval($_POST['owner_id']);

        $sql = "SELECT o.own_id, o.own_prop_id, o.own_fname, o.own_mname, o.own_lname, o.own_photo, o.own_age, o.own_gender, o.own_mobno, o.own_prof, o.own_panno, o.own_panno_upd, o.own_aadharno, o.own_adharno_front_upd, o.own_adharno_back_upd, o.own_bldg, o.own_plot, o.own_floorno, o.own_road, o.own_area, o.own_suburban, o.own_city, o.own_taluka, o.own_state, o.own_pcode, o.own_nocoowner, o.own_stat FROM own_mast o, prop_mast p WHERE p.prop_user_id=" . $usrID . " AND p.prop_id=" . $propID . " AND o.own_id=" . $ownID . " AND o.own_prop_id=" . $propID .";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($id, $prop_id, $fname, $mname, $lname, $photo, $age, $gender, $mobno, $prof, $panno, $panno_upd, $aadharno, $adharno_front_upd, $adharno_back_upd, $bldg, $plot, $floorno, $road, $area, $suburban, $city, $taluka, $state, $pcode, $nocoowner, $stat);

        $owner = array();

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
            $tmpArr['plot'] = $plot; 
            $tmpArr['floor_no'] = $floorno;
            $tmpArr['road'] = $road; 
            $tmpArr['area'] = $area; 
            $tmpArr['suburban'] = $suburban; 
            $tmpArr['city'] = $city; 
            $tmpArr['taluka'] = $taluka; 
            $tmpArr['state'] = $state; 
            $tmpArr['pcode'] = $pcode; 
            $tmpArr['co'] = $nocoowner; 
            $tmpArr['stat'] = $stat;

            array_push($owner, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($owner);
    }
?>