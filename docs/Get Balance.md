<h2><i>How to fetch Citrus Cash balance?</i></h2>

<li>You can get user’s <b>Citrus Cash</b> balance after you have <b>Signed In</b> the user.</i>
```
  citrusClient.getBalance(new com.citrus.sdk.Callback<Amount>() {
     @Override
     public void success(Amount amount) {}

     @Override
     public void error(CitrusError error) {}
  });
  ```
