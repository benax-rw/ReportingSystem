<?php


 if($_SERVER['REQUEST_METHOD']=='POST'){

     include 'DatabaseConfig.php';
     
     $response = array("error" => FALSE);
 
    $email = $_POST['email'];
    $password = $_POST['password'];
 
    $Sql_Query = "SELECT * FROM users WHERE email = '$email' AND password = '$password' ";
 
    $user = mysqli_fetch_array(mysqli_query($con,$Sql_Query));
 
    if(isset($user)){
        // use is found
        $response["error"] = FALSE;
        $response["uid"] = $user["unique_id"];
        $response["user"]["name"] = $user["name"];
        $response["user"]["email"] = $user["email"];
        $response["user"]["level"] = $user["level"];
        $response["user"]["created_at"] = $user["created_at"];
        $response["user"]["updated_at"] = $user["updated_at"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
    
    
    mysqli_close($con);

 }
 

?>
