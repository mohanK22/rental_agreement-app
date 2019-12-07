<?php 
    include 'mysqlconfig.php';


/*
        $prop_id
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
        $nocoowner
        $stat
    */

    if($_SERVER['REQUEST_METHOD']=='POST') {

        $prop_id=intval($_POST['prop_id']);

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

        $sql="INSERT INTO own_mast(own_prop_id, own_fname, own_mname, own_lname, own_age, own_gender, own_mobno, own_prof, own_panno, own_aadharno, own_bldg, own_plot, own_floorno, own_road, own_area, own_suburban, own_city, own_taluka, own_state, own_pcode, own_nocoowner, own_stat) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        $stmt=$conn->prepare($sql);

        //binding params
        $stmt->bind_param("isssssissssssssssssisi", $prop_id, 
        $fname, 
        $mname, 
        $lname, 
        $dob, 
        $gender, 
        $mobno, 
        $prof, 
        $panno, 
        $aadharno, 
        $bldg, 
        $plot, 
        $floorno, 
        $road, 
        $area, 
        $suburban, 
        $city, 
        $taluka, 
        $state, 
        $pcode, 
        $nocoowner, 
        $stat);

        if($stmt->execute()) {
            $status="success";

            if($status=="success") {

                $sql="SELECT own_id FROM own_mast WHERE own_prop_id=". $prop_id . ";";
                $stmt=$conn->prepare($sql);
                $stmt->execute();
                $stmt->bind_result($ownid);

                while($stmt->fetch()) {
                    echo $ownid;
                }
            }
        }else {
            echo "error";
        }
    }

?>