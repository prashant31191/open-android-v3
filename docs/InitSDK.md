<h2><b>How to initiate Citrus SDK?</b></h2>

<b>Create a object of CitrusClient</b>
```java
    CitrusClient citrusClient = CitrusClient.getInstance(Context);
```
<p><b>Pass merchant parameters in init</b>
```java
    citrusClient.init(
              "test-signup", "c78ec84e389814a05d3ae46546d16d2e", 
              "test-signin", "52f7e15efd4208cf5345dd554443fd99", 
              "testing", Environment.SANDBOX); // Make sure change the environment to PRODUCTION while going live.
```
  <ul>
  <li> First Parameter –  SignUp Key </li>
  <li>Second Parameter –  SignUp Secret</li>
  <li>Third Parameter  –  SignIn Key</li>
  <li>Fourth Parameter -  SignIn Secret</li>
  <li>Fifth Parameter  -  Vanity</li>
  <li>Sixth Parameter  -  Environment.</li>
  </ul>


<b> What are different Environments? When I can use those? </b>

1. SDK supports two different environments. Sandbox and Production. Testing should be done on Sandbox. Once your app is working fine with Sandbox environment, you can switch environment to Production. Make sure you have set environment to Production before releasing your app to Play-Store. 
2. Pass your environment as sixth parameter to citrusClient.init method as mentioned above.
3. User created on Sandbox environment will not be available on production environment.  You have to explicitly create account for both environments.
4. SignUp Key, SignUp Secret, SignIn Key, SignIn Secret and vanity will be same for both environments. (Secret Key and Access Key will be different for both the environments).
