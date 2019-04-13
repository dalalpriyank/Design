<?php
error_reporting(0);
	$servername="localhost";
	$username="root";
	$pas="";
	$db="design";
	
	$conn = mysqli_connect($servername, $username, $pas,$db);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
/* echo "Connected successfully"; */
	
	$seller_id=$_POST['seller_id'];
	$food_name=$_POST['food_name'];
	$price=$_POST['price'];
	$description=$_POST['description'];	
	//$qur = "INSERT INTO `menu` VALUES ('icebe','Puff','20','chess')";
	$qur = "INSERT INTO `menu` VALUES ('$seller_id','$food_name','$price','$description')";
	if($r=mysqli_query($conn,$qur))
	{
		echo 'inserted';
	}else
	{
		echo 'notinserted';
	}
		mysqli_close($conn);
?>