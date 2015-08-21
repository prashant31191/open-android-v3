<h2><i>Pay using Citrus Cash</i></h2>

<li>You can perform a transaction using Citrus cash.</li>  

```java
citrusClient.payUsingCitrusCash(new PaymentType.CitrusCash(amount, BILL_URL), new Callback<TransactionResponse>() {

     @Override
     public void success(TransactionResponse transactionResponse) { }

     @Override
     public void error(CitrusError error) { }
  });
  ```
