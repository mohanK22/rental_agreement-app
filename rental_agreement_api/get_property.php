<?php
    include 'mysqlconfig.php';
    
    /*
        $usrID;
        $propID;

        $prop_id, $prop_user_id, $prop_serv_type, $prop_bldg, $prop_plotno, $prop_floorno, $prop_road, $prop_area, $prop_suburban, $prop_city, $prop_taluka, $prop_state, $prop_pincode, $prop_type, $prop_flat_area, $prop_index2, $prop_elect_bill, $prop_gas_bill, $prop_tax_bill, $prop_main_bill, $prop_stat

    */

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['user_id']);
        $propID = intval($_POST['property_id']);

        $sql = "SELECT p.prop_id, p.prop_user_id, p.prop_serv_type, p.prop_bldg, p.prop_plotno, p.prop_floorno, p.prop_road, p.prop_area, p.prop_suburban, p.prop_city, p.prop_taluka, p.prop_state, p.prop_pincode, p.prop_type, p.prop_flat_area, p.prop_index2, p.prop_elect_bill, p.prop_gas_bill, p.prop_tax_bill, p.prop_main_bill, p.prop_stat FROM prop_mast p WHERE p.prop_id=" . $propID . " AND p.prop_user_id=" . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($id, $user_id, $serv_type, $bldg, $plotno, $floorno, $road, $area, $suburban, $city, $taluka, $state, $pincode, $type, $flat_area, $index2, $elect_bill, $gas_bill, $tax_bill, $main_bill, $stat);

        $property = array();

        while($stmt->fetch()){
            $tmpArr = array();

            $tmpArr['id'] = $id; 
            $tmpArr['user_id'] = $user_id; 
            $tmpArr['serv_type'] = $serv_type; 
            $tmpArr['bldg'] = $bldg; 
            $tmpArr['plot_no'] = $plotno; 
            $tmpArr['floor_no'] = $floorno; 
            $tmpArr['road'] = $road; 
            $tmpArr['area'] = $area; 
            $tmpArr['suburban'] = $suburban; 
            $tmpArr['city'] = $city; 
            $tmpArr['taluka'] = $taluka; 
            $tmpArr['state'] = $state;
            $tmpArr['pincode'] = $pincode; 
            $tmpArr['type'] = $type; 
            $tmpArr['flat_area'] = $flat_area; 
            $tmpArr['index2'] = $index2; 
            $tmpArr['elect_bill'] = $elect_bill; 
            $tmpArr['gas_bill'] = $gas_bill; 
            $tmpArr['tax_bill'] = $tax_bill; 
            $tmpArr['main_bill'] = $main_bill; 
            $tmpArr['stat'] = $stat;

            array_push($property, $tmpArr);
        }

        header('Content-Type: application/json');
        echo json_encode($property);
    }
?>