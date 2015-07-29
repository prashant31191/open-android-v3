# open-android-v3
Enhanced Payment SDK 

Release Notes:
v3.0.2 
* BindByMobileNo API integrated.
* Fix for SSL error.
* Avoided multiple initialization of the CitrusClient
* Bug fixes.

Last updated : 29/07/2015

What's new?
open-android-v3 is enhanced version of SDK V2. 

   1.	SDK response time optimization-  over 5x faster response compared to V2
   2.	Everything is Object now. No more JSON parsing required.
   3.	Improved WebView. WebView is handled by SDK.  
   4.	Uniform responses from SDK across different API’s
   5.	Support for Citrus Cash.
   6.	Added support to gradle/Android Studio
   7. Less Integration Time Required.
   8. Zero click payment using Citrus Cash.

<b> Prerequisite </>
   You must already have installed and configured:
   * Java JDK version 1.6 or greater.
   * Android SDK Platform 22 (Android 5.1.1)
   * A Git client
   * Android Studio (Currently this project is compatible with Android Studio Only. Do get in touch with us
     if you are using Eclipse IDE).
   * All Citrus PG Prerequisites.

Note: Please DO NOT PROCEED if the above mentioned requirements have not been met.
Citrus PG Prerequisites

    You need to enroll with Citrus as a merchant.
    Make sure that you have the following parameters from Citrus.

   1. Secret Key
   2. Access Key 
   3. SignIn Key 
   4. SignIn Secret 
   5. SignUp Key 
   6. SignUp Secret 
   7. Bill Generator Hosted on your server. 
   8. Redirect URL page hosted on your server. (After the transaction is complete, Citrus posts a response to this URL.) 
 
<b> What is difference between Normal Payment and Prepaid Payment? </b>

      Normal payment requires only email and mobile. Citrus User account will be created by only using email and mobile combination. We call it as Bind. Once user is Bind, card can be saved against his account, saved cards can be fetched against his account, and user can see his Citrus Cash balance.
      Prepaid Payment requires email and password. Citrus User account will be created by using email and password combination. We call it prepaid account. Once user has prepaid account, following things can be done 

   1. Save cards to user account
   2. Fetch saved cards of user
   3. Get Citrus Cash balance
   4. Send Money
   5. Pay Using Citrus Cash - user can make a payment using Citrus Cash account if he has sufficient amount for payment.
   6. Load Money – money can be loaded to user’s account using CC/DC/NB option.
   7. Withdraw money

<b> How to Init SDK? </b>

   Create a object of CitrusClient.
      
      CitrusClient citrusClient = CitrusClient.getInstance(Context);

Pass Merchant parameters in init. e.g

      citrusClient.init(
            "test-signup", "c78ec84e389814a05d3ae46546d16d2e", 
            "test-signin", "52f7e15efd4208cf5345dd554443fd99", 
            "testing", Environment.SANDBOX); // Make sure change the environment to PRODUCTION while going live.

      //First Parameter –   SignUp Key
      //Second Parameter –  SignUp Secret
      //Third Parameter –   SignIn Key
      //Fourth Parameter -  SignIn Secret
      //Fifth Parameter -   Vanity
      //Sixth Parameter -   Environment.

<b> What are different Environments? When I can use those? </b>

1. SDK supports two different environments. Sandbox and Production. Testing should be done on Sandbox. Once your app is working fine with Sandbox environment, you can switch environment to Production. Make sure you have set environment to Production before releasing your app to Play-Store. 
2. Pass your environment as sixth parameter to citrusClient.init method as mentioned above.
3. User created on Sandbox environment will not be available on production environment.  You have to explicitly create account for both environments.
4. SignUp Key, SignUp Secret, SignIn Key, SignIn Secret and vanity will be same for both environments. (Secret Key and Access Key will be different for both the environments).


<b> How to enable Logs? </b>

      citrusClient.enableLog(true); // (Make sure you are enabling logs before citrusClient.init() method.)

   1. Logs can be used while debugging any issue.
   2. Logs are disabled by default.
   3. Make sure you are turning off the logs when you are using Production Environment. 

<b> Pro-Guard Changes </b>

   If you are using Proguard in your project add the following lines to your configuration:

<pre>
   -keep class com.squareup.okhttp.** { *; }
   -keep interface com.squareup.okhttp.** { *; }
   -dontwarn com.squareup.okhttp.**
   -dontwarn okio.**
   -dontwarn okio.**
   -dontwarn retrofit.**
   -keep class retrofit.** { *; }
   -keepattributes Signature
   -keepattributes Exceptions
   -keep class com.google.gson.** { *; }
   -keep class com.google.inject.** { *; }
   -keep class org.apache.http.** { *; }
   -keep class org.apache.james.mime4j.** { *; }
   -keep class javax.inject.** { *; }
   -keep class com.citrus.** { *; } 
   -keepattributes *Annotation*
</pre>

<b> How to import CitrusLibrary in your Project? </b>

      git clone https://github.com/citruspay/open-android-v3.git

<h1> Android Integration - API Endpoints: </h1>

Fetch the code from
      
      git clone https://github.com/citruspay/open-android-v3.git

<b> Is User Signed In    </b>

Lets you know if the user is signed in.

      citrusClient.isUserSignedIn(new com.citrus.sdk.Callback<Boolean>() {
         @Override
         public void success(Boolean loggedIn) {}
         
         @Override
         public void error(CitrusError error) {}
      });

You would receive a status inside callback.

<b> Check Citrus Member (isCitrusMember) </b>

To check whether the user is Citrus member or not, you can use the following method.
If it returns true the user is already a Citrus Member (display a SignIn screen) and if it returns false (display a SignUp Screen).

      citrusClient.isCitrusMember(emailId, mobileNo, new com.citrus.sdk.Callback<Boolean>() {
         @Override
         public void success(Boolean citrusMember) {}
         
         @Override
         public void error(CitrusError error) {}
      });
      
<b> SignUp User. </b>

      citrusClient.signUp(emailId, mobileNo, password, new com.citrus.sdk.Callback <CitrusResponse > () {
         @Override
      	public void success(CitrusResponse citrusResponse) {}
      
      	@Override
      	public void error(CitrusError error) {}
      });

<b> SignIn User </b>

      citrusClient.signIn(emailId, password, new com.citrus.sdk.Callback<CitrusResponse>() {
        @Override
         public void success(CitrusResponse citrusResponse) {}
        
        @Override
        public void error(CitrusError error) {}
     });
     
<b> Reset Password </b>

Forgot password if the user forgets password.

      citrusClient.resetPassword(emailId, new com.citrus.sdk.Callback<CitrusResponse>() {
         @Override
         public void success(CitrusResponse citrusResponse) {}
         
         @Override
         public void error(CitrusError error) {}
      });

<b> Get Citrus Cash Balance </b>

You can get user’s Citrus Cash balance after you have Signed In the user. 

      citrusClient.getBalance(new com.citrus.sdk.Callback<Amount>() {
         @Override
         public void success(Amount amount) {}
         
         @Override
         public void error(CitrusError error) {}
      });
------------------------------------------------------------------------------------------------

<b> Add Money/ Load Money </b>

This feature is used for loading money to Citrus wallet.

      Load Money using Debit Card
      // If you have already initiated the CitrusClient, no need to initialize again.
      // Just get the reference to the <b> CitrusClient </b> object
      
      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      DebitCardOption debitCardOption 
            = new DebitCardOption("Card Holder Name", "4111111111111111", "123", Month.getMonth("12"), Year.getYear("18"));
            
      Amount amount = new Amount("5"); 
            
      // Init Load Money PaymentType     
      PaymentType.LoadMoney loadMoney = new PaymentType.LoadMoney(amount, LOAD_MONEY_RETURN_URL,            debitCardOption);
      
      // Call Load Money
      citrusClient.loadMoney(loadMoney, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) {}
         
         @Override
         public void error(CitrusError error) {}
      });


<b> Load Money using Credit card </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      CreditCardOption creditCardOption = new CreditCardOption("Card Holder Name", "4111111111111111", "123", Month.getMonth("12"), Year.getYear("18"));
      
      Amount amount = new Amount("5");
      // Init Load Money PaymentType     
      PaymentType.LoadMoney loadMoney = new PaymentType.LoadMoney(amount, LOAD_MONEY_RETURN_URL, creditCardOption);
      
      citrusClient.loadMoney(loadMoney, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) {}
         
         @Override
         public void error(CitrusError error) {}
      });

<b> Load Money using Net Banking </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      NetbankingOption netbankingOptio = new NetbankingOption(“ICICI Bank” ,”CID001”);
      
      // Init Net Banking PaymentType     
      PaymentType.LoadMoney loadMoney = new PaymentType.LoadMoney(amount, LOAD_MONEY_RETURN_URL, netbankingOption);
      
      citrusClient.loadMoney(loadMoney, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> Load/Add Money using Debit Card Token. </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      DebitCardOption debitCardOption = new DebitCardOption("94a4def03fdac35749bfd2746e5cd6f9", "123");
      //Note: The Token for sandbox and production will be different
      
      // Init PaymentType     
      PaymentType.LoadMoney loadMoney = new PaymentType.LoadMoney(amount, LOAD_MONEY_RETURN_URL, debitCardOption);
      
      citrusClient.LoadMoney(loadMoney, new Callback<TransactionResponse>() {
         
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> Load/Add Money using Credit Card Token. </>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      CreditCardOption creditCardOption = new CreditCardOption("d7505f22bca20a97f8d8f305530e88a9", "123");
      //Note: The Token for sandbox and production will be different
      
      // Init PaymentType     
      PaymentType.LoadMoney loadMoney = new PaymentType.LoadMoney(amount, LOAD_MONEY_RETURN_URL, creditCardOption);
      // Call LoadMoney
      citrusClient.LoadMoney(loadMoney, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> Load/Add Money using Bank Token. </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      NetbankingOption netbankingOption = new NetbankingOption("b66352b2d465699d6fa7cfb520ba27b5");
      //Note: The Token for sandbox and production will be different
      
      // Init PaymentType     
      PaymentType.LoadMoney loadMoney = new PaymentType.LoadMoney(amount, LOAD_MONEY_RETURN_URL, netbankingOption, new CitrusUser("developercitrus@gmail.com","9876543210"));
      // Call LoadMoney
      citrusClient.LoadMoney(loadMoney, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> PG Payment </b>

<b>PG payment using Debit Card. </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      DebitCardOption debitCardOption = new DebitCardOption("Card Holder Name", "4111111111111111", "123", Month.getMonth("12"), Year.getYear("18"));
      
      Amount amount = new Amount("5");
      // Init PaymentType     
      PaymentType.PGPayment pgPayment = new PaymentType.PGPayment(amount, BILL_URL, debitCardOption, new CitrusUser("developercitrus@gmail.com","9876543210"));
      
      citrusClient.PGPayment(pgPayment, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> PG payment using Credit Card. </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      CreditCardOption creditCardOption = new CreditCardOption("Card Holder Name", "4111111111111111", "123", Month.getMonth("12"), Year.getYear("18"));
      
      Amount amount = new Amount("5");
      // Init PaymentType     
      PaymentType.PGPayment pgPayment = new PaymentType.PGPayment(amount, BILL_URL, creditCardOption, new CitrusUser("developercitrus@gmail.com","9876543210"));
      
      citrusClient.PGPayment(pgPayment, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> PG payment using Net Banking. </b>
   

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      NetbankingOption netbankingOption = new NetbankingOption(“ICICI Bank” ,”CID001”);
      
      // Init Net Banking PaymentType     
      PaymentType.PGPayment pgPayment = new PaymentType.PGPayment(amount, BILL_URL, netbankingOption, new CitrusUser("developercitrus@gmail.com","9876543210"));
      
      citrusClient.PGPayment(pgPayment, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> Payment using  Debit Card Token. </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      DebitCardOption debitCardOption = new DebitCardOption("94a4def03fdac35749bfd2746e5cd6f9", "123");
      
      // Init Net Banking PaymentType     
      PaymentType.PGPayment pgPayment = new PaymentType.PGPayment(amount, BILL_URL, debitCardOption, new CitrusUser("developercitrus@gmail.com","9876543210"));
      
      citrusClient.PGPayment(pgPayment, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> Payment using  Credit Card Token. </b>

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      CreditCardOption creditCardOption = new CreditCardOption("d7505f22bca20a97f8d8f305530e88a9", "123");
      
      // Init Net Banking PaymentType     
      PaymentType.PGPayment pgPayment = new PaymentType.PGPayment(amount, BILL_URL, creditCardOption, new CitrusUser("developercitrus@gmail.com","9876543210"));
      
      citrusClient.PGPayment(pgPayment, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> Payment using Bank Token.

      CitrusClient citrusClient = CitrusClient.getInstance(context); // Activity Context
      // No need to call init on CitrusClient if already done.
      
      NetbankingOption netbankingOption = new NetbankingOption("b66352b2d465699d6fa7cfb520ba27b5");
      
      // Init Net Banking PaymentType     
      PaymentType.PGPayment pgPayment = new PaymentType.PGPayment(amount, BILL_URL, ne, netbankingOption CitrusUser("developercitrus@gmail.com","9876543210"));
      
      citrusClient.PGPayment(pgPayment, new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });


<b> Pay Using Citrus Cash </b>

      citrusClient.payUsingCitrusCash(new PaymentType.CitrusCash(amount, BILL_URL), new Callback<TransactionResponse>() {
      
         @Override
         public void success(TransactionResponse transactionResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });

<b> Logout </b>

      citrusClient.signOut(new Callback<CitrusResponse>() {
      
         @Override
         public void success(CitrusResponse citrusResponse) { }
         
         @Override
         public void error(CitrusError error) { }
      });
	  
	  
<b> How to get Payment Options? </b>


This is useful to find the type of debit/credit card enabled for you, list of banks available for transaction. You have to use this list of banks and 
show in the UI. When user selects the particular bank, you should use CID against that bank for payment.
There are two types of Payment Options

<b>1. Payment Options for Load Money </b>

Load Money payment options differes from normal PG Payment. 

Following method should be used for loadMoney Payment Options
		
		citrusClient.getInstance(getActivity()).getLoadMoneyPaymentOptions(new Callback<MerchantPaymentOption>() {
                @Override
                public void success(MerchantPaymentOption loadMoneyPaymentOptions) {
                   ArrayList<NetbankingOption> mNetbankingOptionsList = mMerchantPaymentOption.getNetbankingOptionList();//this will give you only bank list
                }

                @Override
                public void error(CitrusError error) {
                 
                }
            });

			
<b>2. Payment Options for PG Payment </b>


Following method should be used for PG Payment Options


		 CitrusClient.getInstance(getActivity()).getMerchantPaymentOptions(new Callback<MerchantPaymentOption>() {
                @Override
                public void success(MerchantPaymentOption merchantPaymentOption) {
                   
					ArrayList<NetbankingOption> mNetbankingOptionsList = mMerchantPaymentOption.getNetbankingOptionList();//this will give you only bank list
                    
                }

                @Override
                public void error(CitrusError error) {
                    
                }
            });



