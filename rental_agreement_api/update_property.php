<?php
    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrId=intval($_POST['usrId']);
        $propId=intval($_POST['prop_id']);
        
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
    
        $sql="UPDATE prop_mast SET prop_serv_type=" . $serviceType . ",  prop_bldg='" . $bldg . "', prop_plotno='" . $plot . "', prop_floorno='" . $floorno . "', prop_road='" . $road . "', prop_area='" . $area ."', prop_suburban='" . $suburban . "', prop_city='" . $city . "', prop_taluka='" . $taluka . "', prop_state='" . $state . "', prop_pincode='" . $pcode . "', prop_type='" . $propertyType . "', prop_flat_area='" . $propertyArea ."' WHERE prop_id=" . $propId . " AND prop_user_id=" . $usrId . ";";
        
        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }
    }
    
?>