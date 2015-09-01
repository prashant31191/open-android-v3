# open-android-v3

Welcome to the open-source Android SDK Documentation of Citrus Payments Solution!

<h5>Introduction</h5>
___
* This document details the merchant's <b>Android App</b> integration with Citrus Payment gateway.There is a difference between <b>Normal(PG) Payment</b> and <b>Prepaid Payment</b>!
* <b>Normal</b> payment requires only <b>email</b> and <b>mobile</b>. Citrus User account will be created by only using email and mobile combination. We call it as <b><i>Bind</i></b>. Once user is Bind, card can be saved against his account, saved cards can be fetched against his account.
* <b>Prepaid</b> Payment requires <b>email</b> and <b>password</b>. Citrus User account will be created by using email and password combination. We call it prepaid account.

open-android-v3 is enhanced [version](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Version%20History.md) of SDK V2 wherein you can implement your App with features such as:

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

<h2> Prerequisites </h2>
* [Before you Start...](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Prerequisite.md)

<b> How to import CitrusLibrary in your Project? </b>

      git clone https://github.com/citruspay/open-android-v3.git

<h2>Lets Start...</h2>

<b>Initiation</b>
* [Initiate Citrus SDK](https://github.com/citruspay/open-android-v3/blob/documentation/docs/InitSDK.md)
* [How to enable logs](https://github.com/citruspay/open-android-v3/blob/documentation/docs/enable%20logs%20.md)

<b> Implementation for User creation and management </b>

* [See if the User is logged/Signed in  ](https://github.com/citruspay/open-android-v3/blob/documentation/docs/isUserSignedIn.md)
* [To check if the User is a Citrus member or not?](https://github.com/citruspay/open-android-v3/blob/documentation/docs/isCitrusMember.md)
* [SignUp User](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Sign%20Up%20User.md)
* [SignIn User](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Sign%20In%20User.md) 
* [Reset Password](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Reset%20Password.md)
* [Logout the User](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Logout.md)

<b> Get Payment Options</b>
* [Fetch Normal(PG) Payment Options](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Fetch%20payment%20options.md) (Required while making a <b>Normal</b> payment)
* [Fetch Load Money Payment Options](https://github.com/citruspay/open-android-v3/blob/documentation/docs/fetch%20load%20money%20options.md) (Required during <b>Adding/Loading</b> Money to Wallet)

<b>Wallet</b>
* [Get Citrus Cash balance](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Get%20Balance.md) (<b>Note:</b> User should be <b>Signed In</b> for this Implementation)
* [Add Money/Load Money into Citrus Account](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Load%20Money.md) (Normal payment to Load money)
* [Add Money/Load Money into Citrus Account using Saved cards](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Load%20using%20Saved%20Card%20&%20Net%20bank.md) (Tokenized payment to Load money)

<b>Payment</b>
* [Pay using Credit/Debit Card & Net Banking](https://github.com/citruspay/open-android-v3/blob/documentation/docs/CC%20%2CDC%20%2CNB%20Direct%20Payment.md) (Normal payments)
* [Pay using Saved Cards and Net banking](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Pay%20Using%20Saved%20Cards%20and%20Bank.md) (Tokenized payment)
* [Pay using Citrus Cash](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Pay%20using%20Citrus%20Cash.md) (<b>Note:</b> User should be <b>Signed In</b> for this Implementation)

<b>How to save Cards option and Banks</b>
* [Save Debit/Credit Card and Net banking](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Save%20payment%20option.md)

<b>Others...</b>
* [Send Money To Your Friend](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Send%20Money.md)
* [Withdraw Money to Your Account](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Withdraw.md)
* Delete Card
___
<h5>How to Go Live...</h5>

* [Keys and Environment inside citrusClient.init](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Go%20Live.md)
* [Progaurd changes](https://github.com/citruspay/open-android-v3/blob/documentation/docs/Progaurd%20changes.md) (If required)


___


