<h2><i>How to Sign Up an User?</i></h2>
```
  citrusClient.signUp(emailId, mobileNo, password, new com.citrus.sdk.Callback <CitrusResponse > () {
     @Override
    public void success(CitrusResponse citrusResponse) {}

    @Override
    public void error(CitrusError error) {}
  });
  ```
