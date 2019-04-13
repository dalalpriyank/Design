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
	$restaurant_name=$_POST['restaurant_name'];
	$owner_name=$_POST['owner_name'];
	$owner_email=$_POST['owner_email'];
	$password=$_POST['password'];
	$sell_phoneno=$_POST['sell_phoneno'];
	
	if($r=mysqli_query($conn,"
	INSERT INTO `seller_details` VALUES (NULL, '$seller_id', '$restaurant_name', '$owner_name', '$owner_email', '$password', '$sell_phoneno')"))
	{
		echo 'inserted';
	}else
	{
		echo 'notinserted';
	}
		mysqli_close($conn);
?>