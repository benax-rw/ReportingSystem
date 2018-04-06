<?php

 if($_SERVER['REQUEST_METHOD']=='POST'){

 include 'DatabaseConfig.php';
     
  
  $query = "SELECT * FROM notes";
  
  $result = mysqli_query($con, $query);
  $rowcount=mysqli_num_rows($result);
  
  if($rowcount>=0){
  
  while($rs = mysqli_fetch_assoc($result)){
  
        $arrRows[] = $rs;

     }
 echo json_encode($arrRows);
  
  }else{
      echo "No notes yet received!";
      
  }
  

mysqli_close($con);

}

?>
  

