<h2><i>How to SignIn an User?</i></h2>
```
  citrusClient.signIn(emailId, password, new com.citrus.sdk.Callback<CitrusResponse>() {
      @Override
      public void success(CitrusResponse citrusResponse) {}

      @Override
      public void error(CitrusError error) {}
  });
 ```
