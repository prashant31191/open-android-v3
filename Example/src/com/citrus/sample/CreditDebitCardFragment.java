package com.citrus.sample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.citrus.card.Card;
import com.citrus.widgets.CardNumberEditText;
import com.citrus.widgets.ExpiryDate;

import org.json.JSONObject;

public class CreditDebitCardFragment extends Fragment {
    private CardNumberEditText cardnumber;
    private ExpiryDate expDate;
    private EditText cvv, cardHolderName, cardHolderNickName, cardHolderNumber;
    private TextView submitButton;
    private Card card;


    public CreditDebitCardFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_credit_debit_card, container,
                false);

        cardnumber = (CardNumberEditText) returnView
                .findViewById(R.id.cardHolderNumber);
        expDate = (ExpiryDate) returnView.findViewById(R.id.cardExpiry);
        cardHolderName = (EditText) returnView.findViewById(R.id.cardHolderName);
        cardHolderNickName = (EditText) returnView.findViewById(R.id.cardHolderNickName);
        cvv = (EditText) returnView.findViewById(R.id.cardCvv);
        submitButton = (TextView) returnView.findViewById(R.id.submitButton);
        return returnView;
    }


   /* @Override
    public void onPause() {
        super.onPause();
        taskExecuted = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        taskExecuted = null;
        // initEditText();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {

        super.setMenuVisibility(menuVisible);
        if (menuVisible) {

            SavedOptionsFragment.firstFragment = false;
            cardnumber.setCursorVisible(true);
            cardnumber.setFocusable(true);
            cardnumber.requestFocus();

        }
    }



    protected String getScheme(String mCardNumber) {
        String testString = "";
        if (mCardNumber.contains(" ")) {
            testString = mCardNumber.replace(" ", "");
        } else {
            testString = mCardNumber;
        }

        if (com.stripe.android.util.TextUtils.hasAnyPrefix(testString,
                CardIds.amex)) {
            return "AMEX";
        } else if (com.stripe.android.util.TextUtils.hasAnyPrefix(testString,
                CardIds.disconver)) {
            return "DISCOVER";
        } else if (com.stripe.android.util.TextUtils.hasAnyPrefix(testString,
                CardIds.JCB)) {
            return "JCB";
        } else if (com.stripe.android.util.TextUtils.hasAnyPrefix(testString,
                CardIds.DINERCLUB)) {
            return "DINERCLUB";
        } else if (com.stripe.android.util.TextUtils.hasAnyPrefix(testString,
                CardIds.VISA)) {
            return "VISA";
        } else if (com.stripe.android.util.TextUtils.hasAnyPrefix(testString,
                CardIds.MAESTRO)) {
            return "MTRO";

        } else if (com.stripe.android.util.TextUtils.hasAnyPrefix(testString,
                CardIds.MASTERCARD)) {
            return "MCRD";
        } else {
            return "UNKNOWN";
        }

    }*/

   /* private void initSubmitButton() {
        submitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity()
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (NullPointerException e)
                {
                    CitrusLogging.logError("NullPointerException",e);
                }

                CitrusLogging.logDebug(""
                        + getScheme(cardnumber.getText().toString()));
                mCardType = getScheme(cardnumber.getText().toString());

                // if(expDate.getText().toString().length()==5){
                if (CustomFunctions.isNetAvailable(getActivity())) {
                    if (isValidCard()) {
                        // if(credit_check.isChecked()){
                        try {
                            savePayOption();
                            // sO.flag=true;
                        } catch (JSONException e) {
                            CitrusLogging.logError("JSONException", e);
                        }
                        // }
                        createTransaction();
                    }
                } else {
                    Intent it = new Intent(getActivity(), ConnectionLost.class);
                    it.putExtra("activity", "PaymetActivity");
                    startActivity(it);
                }
            }
        });
    }

    protected void savePayOption() throws JSONException {

        JSONObject mCardDetails = new JSONObject();
        CitrusLogging.logDebug("scheme:" + mCardType.toUpperCase());

        String s1 = expDate.getText().toString().substring(0, 2);
        int x = Integer.parseInt(s1);
        String str;
        if (x <= 9)
            str = "0" + x;
        else {
            str = String.valueOf(x);
        }
        // CitrusLogging.logDebug("default option"+sO.defaultOption);

        JSONObject paymentDetails = new JSONObject()
                .put("type", "credit")
                .put("owner", nameOnCard)
                .put("number",
                        cardnumber.getText().toString().replaceAll("\\s", ""))
                .put("expiryDate",
                        str + "/20" + expDate.getText().toString().substring(3))
                .put("scheme", mCardType.toUpperCase()).put("bank", "");
        mCardDetails
                .put("type", "payment")
                .put("defaultOption", "")
                .put("paymentOptions",
                        new JSONArray(((Collections.singleton(paymentDetails)))));

        new SavePayOption(getActivity(), mCardDetails,
                new TXNJSonObject() {

                    @Override
                    public void onTaskCompletedExec(JSONObject txnObject) {
                        try {
                            if (txnObject != null) {
                                if (txnObject
                                        .getBoolean(CitrusConstants.TXN_STATUS)) {
                                    SavedOptions.flag = true;

                                }
                            }
                        } catch (JSONException e) {
                            CitrusLogging.logError("JSONException", e);
                            SavedOptions.flag = false;
                        }
                    }
                }).execute();

    }*/

    /*protected boolean isValidCard() {

        card = new Card(cardnumber.getText().toString(), expDate.getText()
                .toString(), cvv.getText().toString(), nameOnCard);

       *//* if (TextUtils.isEmpty(nameOnCard.getText().toString())) {
            nameOnCard.requestFocus();
            ToastUtils.showLong(getActivity(), "Name field is empty");
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // nameOnCard, "Enter the name");
            nameOnCard.requestFocus();
            return false;
        }*//*
        final String expiryDate = expDate.getText().toString();
        int expMonth = 0, expYear = 0;

        if (expiryDate.contains("/")) {
            String expiryMonth = expiryDate.trim().split("/")[0];
            expMonth = Integer.parseInt(expiryMonth);
            String expiryYear = expiryDate.trim().split("/")[1];
            expYear = Integer.parseInt(expiryYear);
        }

        final com.stripe.android.model.Card card_num_valid = new com.stripe.android.model.Card(
                cardnumber.getText().toString(), expMonth, expYear, cvv
                .getText().toString());

        if (TextUtils.isEmpty(cardnumber.getText().toString())) {
            cardnumber.requestFocus();
            ToastUtils.showLong(getActivity(), "Card number can't be empty");
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // nameOnCard, "Enter the name");
            return false;
        }
        try {
            CitrusLogging.logDebug("Exp Month and YEar : " + expMonth + "..." + expYear);
            String cardNumberText = cardnumber.getText().toString();
            cardNumberText = cardNumberText.trim();
            cardNumberText = cardNumberText.replace(" ", "");
            cardNumberText = cardNumberText.replace("-", "");
            cardNumberText = cardNumberText.replace("\"", "");
            CitrusLogging.logDebug("Card Number :" + cardNumberText);

            if (Double.parseDouble(cardNumberText) == 0d) {
                cardnumber.setText("");
                cardnumber.requestFocus();
                ToastUtils.showLong(getActivity(), "Invalid card number");
                return false;
            }
            if (expMonth == 0 || expYear == 0) {
                expDate.setText("");
                expDate.requestFocus();
                ToastUtils.showLong(getActivity(), "Invalid Expiry Date");
                return false;
            }

        } catch (Exception e) {
            CitrusLogging.logError("Exception", e);
        }
        if (!card_num_valid.validateNumber()) {
            cardnumber.requestFocus();
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // cardnumber, "Invalid Credit Card");
            ToastUtils.showLong(getActivity(), "Invalid Credit Card");
            return false;
        }

        if (TextUtils.isEmpty(expDate.getText().toString())) {
            expDate.requestFocus();
            ToastUtils.showLong(getActivity(), "Expiry date field is empty");
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // nameOnCard, "Enter the name");
            return false;
        }

        if (expDate.getText().toString().length() != 5) {
            expDate.requestFocus();
            ToastUtils.showLong(getActivity(), "Invalid Expiry Date");
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // expDate, "Invalid Expiry Date");
            return false;
        }

        if (!card.validateExpiryDate()) {
            expDate.requestFocus();
            ToastUtils.showLong(getActivity(), "Invalid Expiry Date");
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // expDate, "Invalid Expiry Date");
            return false;
        }

        if (TextUtils.isEmpty(cvv.getText().toString())) {
            cvv.requestFocus();
            ToastUtils.showLong(getActivity(), "CVV field is empty");
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // nameOnCard, "Enter the name");
            return false;
        }

        if (!card_num_valid.validateCVC()) {
            cvv.requestFocus();
            ToastUtils.showLong(getActivity(), "Invalid CVV");
            // ErrorValidation.showError(getActivity().getApplicationContext(),
            // cvv, "Invalid CVV");
            return false;
        }
        return true;
    }*/

    /*private void createTransaction() {
        returnFlag = true;
        JSONObject txnDetails = new JSONObject();
        try {
            txnDetails.put("amount", amount_load);
            txnDetails.put("currency", "INR");
            txnDetails.put("redirect", CitrusConstants.getRedirect_URL());
        } catch (JSONException e) {

        }
        // String txnId = String.valueOf(System.currentTimeMillis());
        // String data = "merchantAccessKey=" + CitrusConstants.ACCESS_KEY +
        // "&transactionId=" + txnId + "&amount=1";
        // String signature = HMACSignature.generateHMAC(data,
        // CitrusConstants.SECRET_KEY);
        *//*mWaitBox = new AlertDialog.Builder(getActivity()).create();
        mWaitBox.setTitle("Hold on. This won't take long!");
        mWaitBox.show();
        mWaitBox.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                returnFlag = false;
                mWaitBox.dismiss();
            }
        });*//*


        ProgressDialog.getInstance().show_dialogWithCancelListener(getActivity(), "Hold on. This won't take long!", new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                returnFlag = false;
                ProgressDialog.getInstance().dissmiss_dialog();

            }
        });
        //TODO EVent Load Pessed
        CitrusCashGlobal.getInstance().setPaymentMode(CustomFunctions.PAYMENT_MODE.CREDIT);
        CustomFunctions.setEvent(getActivity(), TAG + "click");
        new GetSignedorder(getActivity(), txnDetails, new OnTaskCompleted() {

            @Override
            public void onTaskExecuted(JSONObject[] signedOrder, String message) {
                if (getActivity() != null && isAdded()) {
                    try {
                        if (message != null && message.equalsIgnoreCase(CitrusConstants.bad_Request_Message)) {
                            ProgressDialog.getInstance().dissmiss_dialog();
                            ((PaymentActivity) activity).DisplayErrorMessage(CitrusConstants.bad_Request_Message);
                        } else {
                            String txnId = signedOrder[0]
                                    .getString("merchantTransactionId");
                            String signature = signedOrder[0].getString("signature");
                            insertValues(txnId, signature);
                        }
                    } catch (JSONException e) {
                        CitrusLogging.logError("JSONException", e);
                    }
                }
            }

            @Override
            public void taskCompleted(String errorMessage) {


            }

        }).execute();
    }

    private void insertValues(String txnId, String signature) {

        try {
            *//* Payment Details - DO NOT STORE THEM LOCALLY OR ON YOUR SERVER *//*
            JSONObject amount = new JSONObject();
            amount.put("currency", "INR");
            amount.put("value", amount_load);

            JSONObject address = new JSONObject();
            address.put("street1", "1st");
            address.put("street2", "2nd");
            address.put("city", "Mumbai");
            address.put("state", "Maharastra");
            address.put("country", "India");
            address.put("zip", "400054");

            JSONObject userDetails = new JSONObject();
            // userDetails.put("email",
            // mStoredValues.getString(com.citrus.global.CitrusConstants.EMAIL,
            // null));
            // CitrusLogging.logDebug("users Email======="+mStoredValues.getString(CitrusConstants.EMAIL,
            // null));
            userDetails.put("email", mStoredValues.getString(
                    CitrusConstants.EMAIL, null));
            userDetails.put("firstName", mStoredValues.getString(
                    CitrusConstants.FIRSTNAME, null));
            userDetails.put("lastName", mStoredValues.getString(
                    CitrusConstants.LASTNAME, null));
            userDetails.put("mobileNo", mStoredValues.getString(
                    CitrusConstants.MOBILE, null));

            CitrusLogging.logDebug("mobile number"
                    + mStoredValues.getString(
                    CitrusConstants.MOBILE, null));

            userDetails.put("address", address);
            // CitrusLogging.logDebug("exp:"+expDate.getText().toString().substring(0,
            // 2)+"20"+expDate.getText().toString().substring(3));
            JSONObject paymentMode = new JSONObject();
            paymentMode.put("type", "credit");
            paymentMode.put("scheme", mCardType);

            String cardNumber = cardnumber.getText().toString();
            cardNumber = cardNumber.replaceAll("\\s", "");


            String expiry = expDate.getText().toString().substring(0, 2)
                    + "/20" + expDate.getText().toString().substring(3);
            CitrusLogging.logDebug("EXPIRY DATE:" + expiry);

            paymentMode.put("number", cardNumber);
            paymentMode.put("holder", nameOnCard);
            // paymentMode.put("expiry","12/2020");
            paymentMode.put("expiry", expiry);

            paymentMode.put("cvv", cvv.getText().toString().trim());

            JSONObject paymentToken = new JSONObject();
            paymentToken.put("type", "paymentOptionToken");

            paymentToken.put("paymentMode", paymentMode);

            JSONObject personalDetails = new JSONObject();
            try {
                personalDetails.put("firstName", "Tester");
                personalDetails.put("lastName", "Citrus");
                personalDetails.put("address", "Test Address");
                personalDetails.put("addressCity", "Mumbai");
                personalDetails.put("addressState", "Maharashtra");
                personalDetails.put("addressZip", "400054");
                personalDetails.put("email", mStoredValues.getString(
                        CitrusConstants.EMAIL, null));
                personalDetails.put("mobile", mStoredValues.getString(
                        CitrusConstants.MOBILE, null));
            } catch (JSONException e) {

            }

            // userDetails.put("address", personalDetails);

            paymentObject = new JSONObject();
            paymentObject.put("merchantTxnId", txnId);
            paymentObject.put("paymentToken", paymentToken);
            paymentObject.put("userDetails", userDetails);
            // paymentObject.put("userDetails", personalDetails);
            paymentObject.put("amount", amount);
            paymentObject.put("notifyUrl", "");
            paymentObject.put("merchantAccessKey", CitrusConstants.getAccess_Key());
            paymentObject.put("requestSignature", signature);
            paymentObject.put("returnUrl", CitrusConstants.getRedirect_URL_COMPLETE());
            CitrusLogging.logDebug("payment OBJECT **** " + paymentObject.toString());

            if (returnFlag) {
                CitrusLogging.logDebug("NOT CAUGHT");
                initiateTxn();
            } else {
                CitrusLogging.logDebug("CAUGHT");
            }
        } catch (JSONException e) {

        }

    }*/

    /*private void initiateTxn() {
        ProgressDialog.getInstance().dissmiss_dialog();
        taskExecuted = new com.citruspaykit.mobile.payment.OnTaskCompleted() {

            @Override
            public void onTaskExecuted(JSONObject[] paymentObject,
                                       String message) {
                if (getActivity() != null && isAdded()) {
                    CitrusLogging.logDebug("message:" + message);
                    if (TextUtils.isEmpty(message)) {
                        try {
                            String url = paymentObject[0].getString("redirectUrl");
                            // CitrusLogging.logDebug("url"+url);
                            Intent intent = new Intent(getActivity()
                                    .getApplicationContext(), Web3DSecureActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("amount", amount_load);
                            //TODO EVent Load Txn Initiate
                            CustomFunctions.setEvent(getActivity(), TAG + "Redirect Url :" + url);
                            //TODO Parse RedirectURL Json
                            *//*{
                                "redirectUrl":"https://sandbox.citruspay.com/mpiServlet/4765684d674336374a696e4b4e3876434e57786e63413d3d",
                                    "pgRespCode":"0",
                                    "txMsg":"Transaction successful"
                            }*//*
                            //TODO PGRESPONSE SWITCH 111(Invld Sign)-0(Succ)-118(Mand Param miss)-125(Txn failed-pay option not avail)

                            try {
                                switch (Integer.parseInt(paymentObject[0].getString("pgRespCode"))) {
                                    case 0:
                                        CustomFunctions.setEvent(getActivity(), TAG + "SuccRedirect");
                                        break;
                                    case 111:
                                        CustomFunctions.setEvent(getActivity(), TAG + "111- Invalid Signature");
                                        break;
                                    case 118:
                                        CustomFunctions.setEvent(getActivity(), TAG + "118- Mandatory Param Missing");
                                        break;
                                    case 125:
                                        CustomFunctions.setEvent(getActivity(), TAG + "125- Payment option not available");
                                        break;
                                }
                            } catch (Exception e) {
                                CitrusLogging.logError("Exception", e);
                            }
                            startActivity(intent);
                        } catch (JSONException e) {
                            CitrusLogging.logError("JSONException", e);
                        }
                    } else {
                        ToastUtils.showShort(getActivity(), CitrusConstants.SERVER_UNAVAILABLE);
                    }
                }
            }

        };


        new Pay(getActivity(), paymentObject, taskExecuted).execute();
    }*/
}
