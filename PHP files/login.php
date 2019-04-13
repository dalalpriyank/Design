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
  
$result=mysqli_query($con,"SELECT * FROM cust_details Where college_id='$name' AND cust_password='$password'");
 /* $check = mysqli_fetch_array($result);
 //if we got some result
 if($check)
 {
$userid=$check['id'];
 }
print ($userid); */


$rows['customers']  = array();

while($r = mysqli_fetch_array($result,MYSQL_ASSOC)) {
	
	$row_array= $r;
 
	
	array_push($rows['customers'],$row_array);
}
//echo $rows['customers'];
 echo json_encode($rows);
/* $json=array();
while ($row=mysqli_fetch_assoc($result))
{
	$json["customers"][]=$row;
}
echo json_encode($json);
  */
mysqli_close($con);

?>
