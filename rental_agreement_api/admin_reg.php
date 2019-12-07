<?php

    include 'mysqlconfig.php';

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $username = $_POST['username'];
        $passwd = $_POST['pwd'];

        $pwd = md5($passwd);

        $sql = "INSERT INTO admin_mast(admin_usrname, admin_pwd) VALUES (?, ?);";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param('ss', $username, $pwd);

        if ($stmt->execute()) {
            echo 'success';
        } else {
            echo "failed:\n".mysqli_error($conn);
        }

    }

?>