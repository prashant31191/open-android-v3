<h2><i>How to reset a Password?</i></h2>

<li>To implement forget password feature use <b>resetPassword</b> method.</li>
```
  citrusClient.resetPassword(emailId, new com.citrus.sdk.Callback<CitrusResponse>() {
     @Override
     public void success(CitrusResponse citrusResponse) {}

     @Override
     public void error(CitrusError error) {}
  });
  ```
