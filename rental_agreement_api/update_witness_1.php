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


        $sql = "UPDATE wit_mast SET wit1_name='" . $fname . "', wit1_age='" . $dob . "', wit1_gender='" . $gender . "', wit1_mobno='" . $mobno . "', wit1_prof='" . $prof . "', wit1_panno='" . $panno . "', wit1_aadhar='" . $aadharno . "', wit1_add1='" . $addr1 . "', wit1_add2='" . $addr2 . "' WHERE wit_prop_id=" . $propId . ";" ;

        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }

    }

?>