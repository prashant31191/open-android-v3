<h2><b>How to Initiate Citrus SDK?</b></h2>

<b>Create a object of CitrusClient</b>

    CitrusClient citrusClient = CitrusClient.getInstance(Context);

<p><b>Pass merchant parameters in init</b>

    citrusClient.init(
              "test-signup", "c78ec84e389814a05d3ae46546d16d2e", 
              "test-signin", "52f7e15efd4208cf5345dd554443fd99", 
              "testing", Environment.SANDBOX); // Make sure change the environment to PRODUCTION while going live.

  <ul>
  <li> First Parameter –  SignUp Key </li>
  <li>Second Parameter –  SignUp Secret</li>
  <li>Third Parameter  –  SignIn Key</li>
  <li>Fourth Parameter -  SignIn Secret</li>
  <li>Fifth Parameter  -  Vanity</li>
  <li>Sixth Parameter  -  Environment.</li>
  </ul>
