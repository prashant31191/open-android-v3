<i><h2><b>How to check if the user is signed in or not?</b></h2></i>  

```
citrusClient.isUserSignedIn(new com.citrus.sdk.Callback<Boolean>() {
     @Override
     public void success(Boolean loggedIn) {}

     @Override
     public void error(CitrusError error) {}
  });
```
You would receive a status inside callback.
