<?php
$access_key = "MERCHANT_ACCESS_KEY"; //put your own access_key - found in admin panel
$secret_key = "MERCHANT_SECRET_KEY"; //put your own secret_key - found in admin panel
$return_url = "http://www.yourwebsite.com/returnData.php"; //put your own return_url.php here.

$txn_id = time() . rand(10000,99999);

$value = $_GET["amount"]; //Make sure the datatype of the value is STRING. //Charge amount is in INR by default

$data_string = "merchantAccessKey=" . $access_key . "&transactionId=" .$txn_id . "&amount=" . $value;

$signature = hash_hmac('sha1', $data_string, $secret_key);

$amount = array('value' => $value, 'currency' => 'INR');

$bill = array('merchantTxnId' => $txn_id, 
              'amount' => $amount, 
              'requestSignature' => $signature, 
              'merchantAccessKey' => $access_key, 
              'returnUrl' => $return_url);

/*You can update these details in your records*/

echo json_encode($bill);

?>
