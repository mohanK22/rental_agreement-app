<?php
    include 'mysqlconfig.php'; 

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['usrId']);

        $sql = "SELECT COUNT(agm_id) FROM agree_mast WHERE agm_user_id=" . $usrID . ";";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $stmt->bind_result($no);

        $nos = 0;

        while($stmt->fetch()){
            
            $nos = $no;
        }
    
        echo $nos;
    }
?>