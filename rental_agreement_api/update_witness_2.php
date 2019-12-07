<?php

    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrId=intval($_POST['usrId']);
        $propId=intval($_POST['prop_id']);

        $fname=$_POST['fname'];
        $dob=$_POST['dob'];
        $gender=$_POST['gender'];
        $mobno=intval($_POST['mobno']);
        $prof=$_POST['prof'];
        $panno=$_POST['panno'];
        $aadharno=$_POST['aadharno'];
        $addr1 = $_POST['addr1'];
        $addr2 = $_POST['addr2'];

        $sql = "UPDATE wit_mast SET wit2_name='" . $fname . "', wit2_age='" . $dob . "', wit2_gender='" . $gender . "', wit2_mobno='" . $mobno . "', wit2_prof='" . $prof . "', wit2_panno='" . $panno . "', wit2_aadhar='" . $aadharno . "', wit2_add1='" . $addr1 . "', wit2_add2='" . $addr2 . "' WHERE wit_prop_id=" . $propId . ";" ;

        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }

    }

?>