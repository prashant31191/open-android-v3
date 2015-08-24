# open-android-v3

Welcome to the open-source Android SDK Documentation of Citrus Payments Solution!

<h5>Introduction</h5>
___
* This document details merchant <b><i>Android App</i></b> integration with Citrus Payment gateway.There is a diffrence between <b>Normal(PG) Payment</b> and <b>Prepaid Payment</b>!
* <b>Normal</b> payment requires only <b><i>email</i></b> and <b><i>mobile</i></b>. Citrus User account will be created by only using email and mobile combination. We call it as <b><i>Bind</i></b>. Once user is Bind, card can be saved against his account, saved cards can be fetched against his account, and user can see his Citrus Cash balance.
* <b>Prepaid</b> Payment requires <b></i>email</i></b> and <b><i>password</i></b>. Citrus User account will be created by using email and password combination. We call it prepaid account.

open-android-v3 is enhanced version of SDK V2 wherein you can implement your App with features such as:

* Pay using Credit Card/Debit Card & Netbanking 
* Pay Using Citrus Cash - user can make a payment using Citrus Cash account if he has sufficient amount for payment. 
* Get Citrus Cash balance
* Load Money – money can be loaded to user’s account using CC/DC/NB option.
* Save Cards and Bank options to user account for faster checkout
* Fetch Saved cards of user
* Delete Cards
* Send Money
* Logout your user from App
* Withdraw your Money

<h5>Few more points on SDK V3</h5>
___
   * SDK response time optimization-  over 5x faster response compared to V2
   * Everything is Object now. No more JSON parsing required.
   * Improved WebView. WebView is handled by SDK.  
   * Uniform responses from SDK across different API’s
   * Support for Citrus Cash.
   * Added support to gradle/Android Studio
   * Less Integration Time Required.
   * Zero click payment using Citrus Cash.

<b> Prerequisites </b>
___
  <b>SDK Installation prerequisite</b><br>
   You must already have installed and configured:
   * Java JDK version 1.6 or greater.
   * Android SDK Platform 22 (Android 5.1.1)
   * A Git client
   * Android Studio (Currently this project is compatible with Android Studio Only. Do get in touch with us
     if you are using Eclipse IDE).
   * All Citrus PG Prerequisites.
   * Note: Please DO NOT PROCEED if the above mentioned requirements have not been met.

<b>Citrus PG Prerequisites</b>

    You need to enroll with Citrus as a merchant.
    Make sure that you have the following parameters from Citrus.

   1. Secret Key
   2. Access Key 
   3. SignIn Key 
   4. SignIn Secret 
   5. SignUp Key 
   6. SignUp Secret
   7. Bill Generator Hosted on your server.
   8. Redirect URL page hosted on your server.(After the transaction is complete, Citrus posts a response to this URL.)




<b> How to import CitrusLibrary in your Project? </b>

      git clone https://github.com/citruspay/open-android-v3.git

<h2>Getting Started</h2>

<b>Initiation</b>
* [Initiate Citrus SDK](https://github.com/citruspay/open-android-v3/blob/documentation/docs/InitSDK.md)
* [How to enable logs and progaurd changes](https://github.com/citruspay/open-android-v3/blob/documentation/docs/enable%20logs%20.md)


<b> User Creation- API Endpoints: </b>

* [See if the user is logged/Signed in  ](https://github.com/citruspay/open-android-v3/blob/documentation/docs/isUserSignedIn.md)
* [To Check if the user is a Citrus member or not? (isCitrusMember)](https://github.com/citruspay/open-android-v3/blob/documentation/docs/isCitrusMember.md)
* [SignUp User](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Sign%20Up%20User.md)
* [SignIn User](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Sign%20In%20User.md) 
* [Reset Password](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Reset%20Password.md)

<b>Wallet<b/>

* [Get Citrus Cash Balance](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Get%20Balance.md)


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
      
      citrusClient.pgPayment(pgPayment, new Callback<TransactionResponse>() {
      
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
      
      citrusClient.pgPayment(pgPayment, new Callback<TransactionResponse>() {
      
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
      
      citrusClient.pgPayment(pgPayment, new Callback<TransactionResponse>() {
      
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
      
      citrusClient.pgPayment(pgPayment, new Callback<TransactionResponse>() {
      
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
      
      citrusClient.pgPayment(pgPayment, new Callback<TransactionResponse>() {
      
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
      
      citrusClient.pgPayment(pgPayment, new Callback<TransactionResponse>() {
      
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


<b> Send Money To Your Friend </b>
Now you can send money to your friend using Mobile No.
Please refer below code snippet.

	citrusClient.sendMoneyToMoblieNo(new Amount("10"), "9999999999", "My contribution", new Callback<PaymentResponse>() {
		@Override
		public void success(PaymentResponse paymentResponse) { }
		
		@Override
		public void error(CitrusError error) { }
	});
	

<b> Withdraw Money to Your Account </b>
You can withdraw money to your bank account.
Make sure the user is signed in with password to be able to withdraw the money.

	String amount = "10";
        String accontNo = "12345678901";
        String accountHolderName = "FirstName LastName;
        String ifsc = "BANK0000123";

	CashoutInfo cashoutInfo = new CashoutInfo(new Amount(amount), accontNo, accountHolderName, ifsc);
	citrusClient.cashout(cashoutInfo, new Callback<PaymentResponse>() {
            @Override
            public void success(PaymentResponse paymentResponse) { }

            @Override
            public void error(CitrusError error) {}
        });
