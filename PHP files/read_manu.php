<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 

//$cond = "danny";

$cond = $_GET['seller_idsi'];

$result = mysql_query("SELECT * FROM menu where seller_id='$cond'") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["seller_id"] = $row["seller_id"];
        //$product["restaurant_name"] = $row["restaurant_name"];
		$product["food_name"]=$row["food_name"];
		$product["price"]=$row["price"];
        $product["description"]=$row["description"];
        
        // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
    // echo no users JSON
    echo json_encode($response);
}
?>