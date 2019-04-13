<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','design');

$con = mysqli_connect(HOST,USER,PASS,DB);
/* $user_id = $_POST['user_id']; */

$name=$_POST['custname'];
$password=$_POST['custpass'];

if (mysqli_connect_errno())
  {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  
$result=mysqli_query($con,"SELECT * FROM seller_details Where seller_id='$name' AND password='$password'");
$rows['customers']  = array();

while($r = mysqli_fetch_array($result,MYSQL_ASSOC)) {
	
	$row_array= $r;
 
	
	array_push($rows['customers'],$row_array);
}

echo json_encode($rows);
 
mysqli_close($con);
?>
