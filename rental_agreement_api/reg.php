<?php

    include 'mysqlconfig.php';

    /*
        name
        email-id
        phoneNo
        profession
        dob
        username
        passwd
        (pwd)
        usrType
    */

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        //Getting values
        $name = $_POST['name'];
        $emailid = $_POST['email'];
        $phoneNo = $_POST['phone'];
        $profession = $_POST['profession'];
        $dob = $_POST['dob'];
        $username = $_POST['username'];
        $passwd = $_POST['pwd'];
        $usrType = $_POST['usrType'];

        $pwd = md5($passwd);

        //Checking whether user is exsting (already registred) or not
        $sql = "SELECT * FROM user_mast WHERE user_emailid='".$emailid."' OR user_usrname='".$username."' OR user_pwd='".$pwd."';";

        //executing query
        $result = mysqli_query($conn, $sql);

        //fetching result
        $check = mysqli_fetch_array($result);

        if (isset($check)) {
            //displaying if data exists
            echo 'exists';
        } else {
            //Save user's data in the database
            $sql = 'INSERT INTO user_mast(user_name, user_emailid, user_mobno, user_profession, user_dob, user_usrname, user_pwd, user_type) VALUES(?, ?, ?, ?, ?, ?, ?, ?);';
            $stmt = $conn->prepare($sql);

            //binding params
            $stmt->bind_param('ssssssss', $name, $emailid, $phoneNo, $profession, $dob, $username, $pwd, $usrType);

            if ($stmt->execute()) {
                //displaying success
                echo 'success';
            } else {
                echo "failed:\n".mysqli_error($conn);
            }
        }
    }
