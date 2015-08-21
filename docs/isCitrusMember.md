<h2><i>How to check Citrus member (isCitrusMember)</i></h2>

To check whether the user is Citrus member or not, you can use <b>isCitrusMember</b> method. <li>If it returns True the user is already a Citrus member (display a SignIn screen)</li><li>if it returns False (display a SignUp Screen)</li>


 ```
  citrusClient.isCitrusMember(emailId, mobileNo, new com.citrus.sdk.Callback<Boolean>() {
     @Override
     public void success(Boolean citrusMember) {}

     @Override
     public void error(CitrusError error) {}
  });
  ```
