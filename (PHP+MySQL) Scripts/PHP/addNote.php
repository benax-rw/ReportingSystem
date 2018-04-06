<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

    include 'DatabaseConfig.php';


    $name = $_POST['name'];
    $email = $_POST['email'];
    $note = $_POST['note'];

        
    $Sql_Query = "INSERT INTO notes (user_name,user_email,note, created_at) values ('$name','$email','$note', NOW())";

        if(mysqli_query($con,$Sql_Query)){
    
            echo "Note Inserted";

        }
        else{
            echo 'Something went wrong';
        }
 
 
    
 
    mysqli_close($con);
}

?>
