<?php 
    include 'mysqlconfig.php';

/*

        $prop_id

        $wit1_fname
        $wit1_mname
        $wit1_lname
        $wit1_age
        $wit1_gender
        $wit1_mobno
        $wit1_prof
        $wit1_panno
        $wit1_aadharno
        $wit1_bldg
        $wit1_plot
        $wit1_floorno
        $wit1_road
        $wit1_area
        $wit1_suburban
        $wit1_city
        $wit1_taluka
        $wit1_state
        $wit1_pcode


        $fname
        $mname
        $lname
        $age(dob)
        $gender
        $mobno
        $prof
        $panno
        $aadharno
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

        $stat
        
    */

    if($_SERVER['REQUEST_METHOD']=='POST') {
        $prop_id=intval($_POST['prop_id']);

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

        $sql="INSERT INTO wit_mast(wit_prop_id, wit1_name, wit1_age, wit1_gender, wit1_mobno, wit1_prof, wit1_panno, wit1_aadhar, wit1_add1, wit1_add2, wit2_name, wit2_age, wit2_gender, wit2_mobno, wit2_prof, wit2_panno, wit2_aadhar, wit2_add1, wit2_add2, wit_stat) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        $stmt=$conn->prepare($sql);

        //binding params            
        $stmt->bind_param("isssissssssssisssssi", $prop_id, $wit1_name, $wit1_age, $wit1_gender, $wit1_mobno, $wit1_prof, $wit1_panno, $wit1_aadharno, $wit1_addr1, $wit1_addr2, $name, $age, $gender, $mobno, $prof, $panno, $aadharno, $addr1, $addr2, $stat);

        if($stmt->execute()) {
            $status="success";

            if($status=="success") {
                
                $sql="SELECT wit_id FROM wit_mast WHERE wit_prop_id=". $prop_id . ";";
                $stmt=$conn->prepare($sql);
                $stmt->execute();
                $stmt->bind_result($witid);

                while($stmt->fetch()) {
                    echo $witid;
                }
            }
        }else {
            echo "error";
        }
    }
    
?>