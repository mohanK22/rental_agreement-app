<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrId=intval($_POST['usrId']);
        $propId=intval($_POST['prop_id']);

        $fname=$_POST['fname'];
        $mname=$_POST['mname'];
        $lname=$_POST['lname'];
        $dob=$_POST['dob'];
        $gender=$_POST['gender'];

        $mobno=intval($_POST['mobno']);

        $prof=$_POST['prof'];

        $panno=$_POST['panno'];
        $aadharno=$_POST['aadharno'];

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

        $nocoowner=$_POST['co'];

        $stat=intval($_POST['stat']);

        $sql = "UPDATE tent_mast SET tent_fname='" . $fname . "', tent_mname='" . $mname . "', tent_lname='" . $lname . "', tent_age='" . $dob . "', tent_gender='" . $gender . "', tent_mobno='" . $mobno . "', tent_prof='" . $prof . "', tent_panno='" . $panno . "', tent_aadharno='" . $aadharno . "', tent_bldg='" . $bldg . "', tent_plotno='" . $plot . "', tent_floorno='" . $floorno . "', tent_road='" . $road . "', tent_area='" . $area . "', tent_suburban='" . $suburban . "', tent_city='" . $city . "', tent_taluka='" . $taluka . "', tent_state='" . $state . "', tent_pcode='" . $pcode . "', tent_notenant='" . $nocoowner . "' WHERE tent_prop_id=" . $propId . ";" ;

        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }

    }

?>