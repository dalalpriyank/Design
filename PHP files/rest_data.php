<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','design');

$con = mysqli_connect(HOST,USER,PASS,DB);
/* $user_id = $_POST['user_id']; */


$id=$_POST['sellerid'];
  if (mysqli_connect_errno())
  {
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  
$result=mysqli_query($con,"SELECT restaurant_name,owner_name FROM seller_details");

if(mysql_num_rows($result)>0)
{
	$response["seller_details"]=array();
	while($row=mysql_fetch_array($result)){
		$product=array();
		$product["restaurant_name"]=$row["restaurant_name"];
		$product["owner_name"]=$row["owner_name"];
		
		array_push($response["seller_details"],$product);
	}
	$response["success"]=1;
	echo json_encode($response);
}
else
{
	$response["success"]=0;
	$response["message"]="No products found";
	echo json_encode($response);
}

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

 echo json_encode($rows);

 mysqli_close($con);
?>
