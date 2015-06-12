
<html>										
  <head>										
  <script type="text/javascript">										
    function postResponse(data) {										
        CitrusResponse.pgResponse(data);										
    }										
   </script>										
   </head>										
   <body>										
   </body>										
   </html>            										
   <?php               										
   $secret_key = "MERCHANT_SECRET_KEY";										
   $data =array();										
   foreach ($_POST as $name => $value) {										
         $data[$name] = $value;										
   }	
									
   $verification_data =  $data['TxId']										
                      . $data['TxStatus']										
                      . $data['amount']										
                      . $data['pgTxnNo']										
                      . $data['issuerRefNo']										
                      . $data['authIdCode']										
                      . $data['firstName']										
                      . $data['lastName']										
                      . $data['pgRespCode']										
                      . $data['addressZip'];	

    $signature = hash_hmac('sha1', $verification_data, $secret_key);
    $reqSignature = $data['signature'];

    if ($signature == $data['signature'])  {										
    $json_object = json_encode($data);										
    	echo "<script> postResponse('$json_object'); </script>";										
    	echo $json_object;										
    }										
    else {										
	    $response_data = array("Error" => "Transaction Failed",										
	    	"Reason" => "Signature Verfication Failed");										
	    $json_object = json_encode($response_data);										
	      echo "<script> postResponse('$json_object'); </script>";	

	echo $json_object;									
    }										
    ?>

