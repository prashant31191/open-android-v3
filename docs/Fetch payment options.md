<h2><i>How to fetch Payment Options enabled/available for you?</i></h2>

<p>This is useful to find the type of <b>debit/credit</b> card & list of <b>banks</b> enabled/available to you for Transactions.</p><li>You have to use this list of banks and show in the UI.</li><li> When user selects the particular bank, you should use CID against that bank for payment.</li>

There are <b>two</b> types of Payment Options

<b>1. Payment Options for PG Payment</b>

Following method should be used to fetch <b>PG Payment Options</b>
```java
     CitrusClient.getInstance(getActivity()).getMerchantPaymentOptions(new Callback<MerchantPaymentOption>() {
            @Override
            public void success(MerchantPaymentOption merchantPaymentOption) {

                ArrayList<NetbankingOption> mNetbankingOptionsList = mMerchantPaymentOption.getNetbankingOptionList();//this will give you only bank list

            }

            @Override
            public void error(CitrusError error) {

            }
        });
```
<b>2. Payment Options for Load Money</b>

<b>Note:</b><li>This method is only required when you are implementing <b>Load Money into Wallet</b> feature.</li>
<li>Load Money payment options <b>differes</b> from normal PG Payment.</li>

Following method should be used to fetch <b>Load Money Payment Options</b>
```java
    citrusClient.getInstance(getActivity()).getLoadMoneyPaymentOptions(new Callback<MerchantPaymentOption>() {
            @Override
            public void success(MerchantPaymentOption loadMoneyPaymentOptions) {
               ArrayList<NetbankingOption> mNetbankingOptionsList = mMerchantPaymentOption.getNetbankingOptionList();//this will give you only bank list
            }

            @Override
            public void error(CitrusError error) {

            }
        });
```
