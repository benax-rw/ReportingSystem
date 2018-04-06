<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

    include 'DatabaseConfig.php';
    $response = array("error" => FALSE);

    $name = $_POST['name'];
    $email = $_POST['email'];
    $level = $_POST['level'];
    $password = $_POST['password'];

    $CheckSQL = "SELECT * FROM users WHERE email='$email'";
 
    $check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));
 
    if(isset($check)){

        echo 'Email Already Exist';

    }
    else{ 
        $Sql_Query = "INSERT INTO users (name,email,password,level, created_at) values ('$name','$email','$password','$level', NOW())";

        if(mysqli_query($con,$Sql_Query)){
    
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
            } 
            else {
                // user is not found with the credentials
                $response["error"] = TRUE;
                $response["error_msg"] = "Login credentials are wrong. Please try again!";
                echo json_encode($response);
            }
    

        }
        else{
            echo 'Something went wrong';
        }
 
 
    }
 
    mysqli_close($con);
}

?>
