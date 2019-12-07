<?php include 'mysqlconfig.php';

/*

        $usrId
        $serviceType    1. Rental Agreement     2. Immediate Rental Agreement       3. Notary Agreement
        $bldg
        $plot
        $floorno
        $road
        $area
        $suburban
        $city
        $taluka
        $state
        $pcode
        $propertyType   Apartment/Flat/Shop/Godown/Room
        $propertyArea
        $propertyStat

    */

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrId=intval($_POST['usrId']);
        
        $serviceType=intval($_POST['serviceType']);

        $bldg=$_POST['bldg'];
        $plot=$_POST['plot'];
        $floorno=$_POST['floorno'];
        $road=$_POST['road'];
        $area=$_POST['area'];
        $suburban=$_POST['suburban'];
        $city=$_POST['city'];
        $taluka=$_POST['taluka'];
        $state=$_POST['state'];

        $pcode=intval($_POST['pcode']);

        $propertyType=$_POST['propertyType'];
        $propertyArea=$_POST['propertyArea'];

        $propertyStat=intval($_POST['stat']);

        $status="";

        //Save user's data in the database
        $sql="INSERT INTO prop_mast(prop_user_id, prop_serv_type, prop_bldg, prop_plotno, prop_floorno, prop_road, prop_area, prop_suburban, prop_city, prop_taluka, prop_state, prop_pincode, prop_type, prop_flat_area, prop_stat) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        $stmt=$conn->prepare($sql);

        //binding params
        $stmt->bind_param("iisssssssssissi", $usrId, $serviceType, $bldg, $plot, $floorno, $road, $area, $suburban, $city, $taluka, $state, $pcode, $propertyType, $propertyArea, $propertyStat);

        if($stmt->execute()) {
            $status="success";

            if($status=="success") {
                $sql="SELECT prop_id, prop_serv_type FROM prop_mast WHERE prop_user_id=". $usrId . ";";
                $stmt=$conn->prepare($sql);
                $stmt->execute();
                $stmt->bind_result($propid, $propservtype);

                while($stmt->fetch()) {
                    echo $propid;
                }
            }
        }else{
            echo "error";
        }

    }

?>