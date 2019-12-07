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

        $sql = "UPDATE own_mast SET own_fname='" . $fname . "', own_mname='" . $mname . "', own_lname='" . $lname . "', own_age='" . $dob . "', own_gender='" . $gender . "', own_mobno='" . $mobno . "', own_prof='" . $prof . "', own_panno='" . $panno . "', own_aadharno='" . $aadharno . "', own_bldg='" . $bldg . "', own_plot='" . $plot . "', own_floorno='" . $floorno . "', own_road='" . $road . "', own_area='" . $area . "', own_suburban='" . $suburban . "', own_city='" . $city . "', own_taluka='" . $taluka . "', own_state='" . $state . "', own_pcode='" . $pcode . "', own_nocoowner='" . $nocoowner . "' WHERE own_prop_id=" . $propId . ";" ;

        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }

    }

?>