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
* [To check if the user is a Citrus member or not?](https://github.com/citruspay/open-android-v3/blob/documentation/docs/isCitrusMember.md)
* [SignUp User](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Sign%20Up%20User.md)
* [SignIn User](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Sign%20In%20User.md) 
* [Reset Password](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Reset%20Password.md)
* [Logout the user](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Logout.md)

<b>Wallet</b>

* [Get Citrus Cash balance](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Get%20Balance.md)
* [Add Money/Load Money into Citrus Account](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Load%20Money.md)
	* Load Money using Dedit Card
	* Load Money using Credit Card
	* Load Money using Net Banking option Card
* [Add Money/Load Money into Citrus Account](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Load%20using%20Saved%20Card%20&%20Net%20bank.md)
	* Load Money using Dedit Card Token
	* Load Money using Credit Card Token
	* Load Money using Net Banking option Token


<b>Payment</b>

* [Pay using Credit/Debit Card & Net Banking](https://github.com/citruspay/open-android-v3/blob/documentation/docs/CC%20%2CDC%20%2CNB%20Direct%20Payment.md)
* [Pay using Saved Cards and Net banking](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Pay%20Using%20Saved%20Cards%20and%20Bank.md)
* [Pay using Citrus Cash](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Pay%20using%20Citrus%20Cash.md)




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
