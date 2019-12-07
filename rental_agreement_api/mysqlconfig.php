<?php
    // DB credentials.

    //Database Constatnts
    define('DB_HOST', 'localhost');
    // define('DB_USER', 'aartimka_myagree');
    // define('DB_PASS', 'myagree2018');
    define('DB_USER', 'root');
    define('DB_PASS', 'root@22');
    define('DB_NAME', 'aartimka_myagreement');

    //connecting to database and getting the connection object
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

     //Checking if any error occured while connecting
    if (mysqli_connect_errno()) {
        echo 'Failed to connect to MySQL: '.mysqli_connect_error();
        die();
    }
    
?>