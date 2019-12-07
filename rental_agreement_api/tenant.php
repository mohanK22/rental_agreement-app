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
        $age=$_POST['dob'];
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


        $sql="INSERT INTO tent_mast(tent_prop_id, tent_fname, tent_mname, tent_lname, tent_age, tent_gender, tent_mobno, tent_prof, tent_panno, tent_aadharno, tent_bldg, tent_plotno, tent_floorno, tent_road, tent_area, tent_suburban, tent_city, tent_taluka, tent_state, tent_pcode, tent_notenant, tent_stat) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        $stmt=$conn->prepare($sql);

        //binding params
        $stmt->bind_param("isssssissssssssssssisi", $prop_id, $fname, $mname, $lname, $age, $gender, $mobno, $prof, $panno, $aadharno, $bldg, $plot, $floorno, $road, $area, $suburban, $city, $taluka, $state, $pcode, $nocoowner, $stat);

        if($stmt->execute()) {
            $status="success";

            if($status=="success") {
                $sql="SELECT tent_id FROM tent_mast WHERE tent_prop_id=". $prop_id . ";";
                $stmt=$conn->prepare($sql);
                $stmt->execute();
                $stmt->bind_result($tentid);

                while($stmt->fetch()) {
                    echo $tentid;
                }
            }
        }else {
            echo "error";
        }
    }

?>