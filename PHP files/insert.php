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

  $c_name=$_POST['custname'];
	$c_clgid=$_POST['clgid'];
	$c_password=$_POST['custpass'];
	$c_email=$_POST['custemail'];
	$c_phoneno=$_POST['custphn'];
	$c_institute=$_POST['custinstitute'];
	
	
		
	 
	 //INSERT INTO `cust_details`(`id`, `cust_name`, `college_id`, `cust_password`, `cust_email`, `cust_phoneno`, `cust_institute`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6],[value-7])
	 
	/* if($r=mysqli_query($conn,"insert into cust_details(cust_name,college_id,cust_password,cust_email,cust_phoneno,cust_institute)values
	('$c_name','$c_clgid','$c_password','$c_email','$c_phoneno','$c_institute')")) */
	
	if($r=mysqli_query($conn,"
	INSERT INTO `cust_details`(`id`, `cust_name`, `college_id`, `cust_password`, `cust_email`, `cust_phoneno`, `cust_institute`) VALUES (NULL, '$c_name', '$c_clgid', '$c_password', '$c_email', '$c_phoneno', '$c_institute')"))
	{
		echo 'inserted';
	}else
	{
		echo 'notinserted';
	}
		mysqli_close($conn);
	


?>