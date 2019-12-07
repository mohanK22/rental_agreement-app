<?php
    include 'mysqlconfig.php';

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $usrId=intval($_POST['usrId']);
        $propId=intval($_POST['prop_id']);

        //Witness_1

        //name
        $wit1_fname=$_POST['wit1_fname'];
        $wit1_mname=$_POST['wit1_mname'];
        $wit1_lname=$_POST['wit1_lname'];

        $wit1_age=$_POST['wit1_dob'];
        $wit1_gender=$_POST['wit1_gender'];
        $wit1_mobno=intval($_POST['wit1_mobno']);
        $wit1_prof=$_POST['wit1_prof'];
        $wit1_panno=$_POST['wit1_panno'];
        $wit1_aadharno=$_POST['wit1_aadharno'];

        //addr1
        $wit1_bldg=$_POST['wit1_bldg'];
        $wit1_plot=$_POST['wit1_plot'];
        $wit1_floorno=$_POST['wit1_floorno'];
        $wit1_road=$_POST['wit1_road'];
        $wit1_area=$_POST['wit1_area'];

        //addr2
        $wit1_suburban=$_POST['wit1_suburban'];
        $wit1_city=$_POST['wit1_city'];
        $wit1_taluka=$_POST['wit1_taluka'];
        $wit1_state=$_POST['wit1_state'];
        $wit1_pcode=$_POST['wit1_pcode'];


        $wit1_name=$wit1_fname . " ". $wit1_mname . " ". $wit1_lname;
        $wit1_addr1=$wit1_bldg . " ". $wit1_plot . " ". $wit1_floorno . " ". $wit1_road . " ". $wit1_area;
        $wit1_addr2=$wit1_suburban . " ". $wit1_city . " ". $wit1_taluka . " ". $wit1_state . " ". $wit1_pcode;


        //Witness_2

        //name
        $fname=$_POST['fname'];
        $mname=$_POST['mname'];
        $lname=$_POST['lname'];

        $age=$_POST['dob'];
        $gender=$_POST['gender'];
        $mobno=intval($_POST['mobno']);
        $prof=$_POST['prof'];
        $panno=$_POST['panno'];
        $aadharno=$_POST['aadharno'];

        //addr1
        $bldg=$_POST['bldg'];
        $plot=$_POST['plot'];
        $floorno=$_POST['floorno'];
        $road=$_POST['road'];
        $area=$_POST['area'];

        //addr2
        $suburban=$_POST['suburban'];
        $city=$_POST['city'];
        $taluka=$_POST['taluka'];
        $state=$_POST['state'];
        $pcode=$_POST['pcode'];


        $name=$fname . " ". $mname . " ". $lname;
        $addr1=$bldg . " ". $plot . " ". $floorno . " ". $road . " ". $area;
        $addr2=$suburban . " ". $city . " ". $taluka . " ". $state . " ". $pcode;


        $stat=intval($_POST['stat']);

        $sql = "UPDATE wit_mast SET wit1_name='" . $wit1_name . "', wit1_age='" . $wit1_age . "', wit1_gender='" . $wit1_gender . "', wit1_mobno='" . $wit1_mobno . "', wit1_prof='" . $wit1_prof . "', wit1_panno='" . $wit1_panno . "', wit1_aadhar='" . $wit1_aadharno . "', wit1_add1='" . $wit1_addr1 . "', wit1_add2='" . $wit1_addr2 . "', wit2_name='" . $name . "', wit2_age='" . $age . "', wit2_gender='" . $gender . "', wit2_mobno='" . $mobno . "', wit2_prof='" . $prof . "', wit2_panno='" . $panno . "', wit2_aadhar='" . $aadharno . "', wit2_add1='" . $addr1 . "', wit2_add2='" . $addr2 . "' WHERE wit_prop_id=" . $propId . ";";

        if($conn->query($sql) === TRUE){
            echo "success";
        }else{
            echo "error";
        }

    }
?>