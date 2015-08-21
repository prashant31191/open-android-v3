<i><h2><b>Is User Signed In</b></h2>  </i>  

<li><b>isUserSignedIn</b> helps you know if the user is signed in or not.</li>

```
citrusClient.isUserSignedIn(new com.citrus.sdk.Callback<Boolean>() {
     @Override
     public void success(Boolean loggedIn) {}

     @Override
     public void error(CitrusError error) {}
  });
```
