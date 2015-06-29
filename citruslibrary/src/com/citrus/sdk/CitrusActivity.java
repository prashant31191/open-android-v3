/*
 *
 *    Copyright 2014 Citrus Payment Solutions Pvt. Ltd.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.citrus.sdk;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.citrus.analytics.EventsManager;
import com.citrus.analytics.WebViewEvents;
import com.citrus.cash.LoadMoney;
import com.citrus.cash.PersistentConfig;
import com.citrus.cash.Prepaid;
import com.citrus.library.R;
import com.citrus.mobile.Callback;
import com.citrus.mobile.Config;
import com.citrus.payment.Bill;
import com.citrus.payment.PG;
import com.citrus.payment.UserDetails;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusConfig;
import com.citrus.sdk.payment.PaymentBill;
import com.citrus.sdk.payment.PaymentOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class CitrusActivity extends ActionBarActivity {

    private WebView mPaymentWebview = null;
    private Context mContext = this;
    private ProgressDialog mProgressDialog = null;

    @Deprecated
    private PaymentParams mPaymentParams = null;
    private PaymentType mPaymentType = null;
    private PaymentOption mPaymentOption = null;
    private String mTransactionId = null;
    private ActionBar mActionBar = null;
    private String mColorPrimary = null;
    private String mColorPrimaryDark = null;
    private String mTextColorPrimary = null;
    private CitrusConfig mCitrusConfig = null;
    private CitrusUser mCitrusUser = null;
    private String sessionCookie;
    private CookieManager cookieManager;
    private String mpiServletUrl = null;

    private boolean isBackKeyPressedByUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citrus);

        mPaymentParams = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_PAYMENT_PARAMS);
        mPaymentType = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_PAYMENT_TYPE);
        mCitrusConfig = CitrusConfig.getInstance();

        // Set payment Params
        if (mPaymentParams != null) {
            mPaymentType = mPaymentParams.getPaymentType();
            mPaymentOption = mPaymentParams.getPaymentOption();
            mCitrusUser = mPaymentParams.getUser();

            mColorPrimary = mPaymentParams.getColorPrimary();
            mColorPrimaryDark = mPaymentParams.getColorPrimaryDark();
            mTextColorPrimary = mPaymentParams.getTextColorPrimary();
        } else if (mPaymentType != null) {
            mPaymentOption = mPaymentType.getPaymentOption();
            mCitrusUser = mPaymentType.getCitrusUser();

            mColorPrimary = mCitrusConfig.getColorPrimary();
            mColorPrimaryDark = mCitrusConfig.getColorPrimaryDark();
            mTextColorPrimary = mCitrusConfig.getTextColorPrimary();
        } else {
            throw new IllegalArgumentException("Payment Type Should not be null");
        }

        mActionBar = getSupportActionBar();
        mProgressDialog = new ProgressDialog(mContext);
        mPaymentWebview = (WebView) findViewById(R.id.payment_webview);
        mPaymentWebview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*
            This setting is required to enable redirection of urls from https to http or vice-versa.
            This redirection is blocked by default from Lollipop (Android 21).
             */
            mPaymentWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mPaymentWebview.addJavascriptInterface(new JsInterface(), Constants.JS_INTERFACE_NAME);

        mPaymentWebview.setWebChromeClient(new WebChromeClient());

        mPaymentWebview.setWebViewClient(new CitrusWebClient());
        if (mPaymentType instanceof PaymentType.PGPayment || mPaymentType instanceof PaymentType.CitrusCash) {
            if (mPaymentType.getPaymentBill() != null) {
                // TODO Need to refactor the code.
                if (PaymentBill.toJSONObject(mPaymentType.getPaymentBill()) != null) {
                    proceedToPayment(PaymentBill.toJSONObject(mPaymentType.getPaymentBill()).toString());
                }
            } else {
                fetchBill();
            }
        } else { //load cash does not requires Bill Generator
            Amount amount = mPaymentType.getAmount();

            LoadMoney loadMoney = new LoadMoney(amount.getValue(), mPaymentType.getUrl());
            PG paymentgateway = new PG(mPaymentOption, loadMoney, new UserDetails(CitrusUser.toJSONObject(mCitrusUser)));

            paymentgateway.load(CitrusActivity.this, new Callback() {
                @Override
                public void onTaskexecuted(String success, String error) {
                    processresponse(success, error);
                }
            });
        }

        setTitle("Processing...");
        setActionBarBackground(mColorPrimary, mColorPrimaryDark);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setActionBarBackground(String colorPrimary, String colorPrimaryDark) {
        // Set primary color
        if (mColorPrimary != null && mActionBar != null) {
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorPrimary)));
        }

        // Set action bar color. Available only on android version Lollipop or higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mColorPrimaryDark != null) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(mColorPrimaryDark));
        }
    }

    private void fetchBill() {
        showDialog("Validating Payment Details. Please wait...", false);

        String billUrl = mPaymentType.getUrl();

        if (billUrl.contains("?")) {
            billUrl = billUrl + "&amount=" + mPaymentType.getAmount().getValue();
        } else {
            billUrl = billUrl + "?amount=" + mPaymentType.getAmount().getValue();
        }

      /*  new GetBill(billUrl, new Callback() {
            @Override
            public void onTaskexecuted(String bill, String error) {
                dismissDialog();

                if (!android.text.TextUtils.isEmpty(error)) {

                    TransactionResponse transactionResponse = new TransactionResponse(TransactionResponse.TransactionStatus.FAILED, error, mTransactionId);
                    sendResult(transactionResponse);
                } else {
                    proceedToPayment(bill);
                }
            }
        }).execute();*/

        CitrusClient.getInstance(CitrusActivity.this).getBill(mPaymentType.getUrl(), mPaymentType.getAmount(), new com.citrus.sdk.Callback<PaymentBill>() {
            @Override
            public void success(PaymentBill paymentBill) {
                proceedToPayment(paymentBill.getBillJSON().toString());
            }

            @Override
            public void error(CitrusError error) {
                TransactionResponse transactionResponse = new TransactionResponse(TransactionResponse.TransactionStatus.FAILED, error.getMessage(), mTransactionId);
                sendResult(transactionResponse);
            }
        });
    }

    private void proceedToPayment(String billJSON) {


        if (mPaymentType instanceof PaymentType.CitrusCash) { //pay using citrus cash

            // analyticsPaymentType = com.citrus.analytics.PaymentType.CITRUS_CASH;
            CitrusClient citrusClient = CitrusClient.getInstance(CitrusActivity.this);
            String emailId = citrusClient.getUserEmailId();
            String mobileNo = citrusClient.getUserMobileNumber();

            if (mCitrusUser == null) {
                mCitrusUser = new CitrusUser(emailId, mobileNo);
            }

            UserDetails userDetails = new UserDetails(CitrusUser.toJSONObject(mCitrusUser));
            Prepaid prepaid = new Prepaid(userDetails.getEmail());
            Bill bill = new Bill(billJSON);
            mTransactionId = bill.getTxnId();
            PG paymentgateway = new PG(prepaid, bill, userDetails);
            if (bill.getCustomParameters() != null) {
                paymentgateway.setCustomParameters(bill.getCustomParameters());
            }
            paymentgateway.charge(new Callback() {
                @Override
                public void onTaskexecuted(String success, String error) {
                    //showDialog("Redirecting to Citrus. Please wait...", false);
                    prepaidPayment(success, error);
                }
            });

        } else {


            showDialog("Redirecting to Citrus. Please wait...", false);
            UserDetails userDetails = new UserDetails(CitrusUser.toJSONObject(mCitrusUser));
            Bill bill = new Bill(billJSON);
            mTransactionId = bill.getTxnId();

            PG paymentgateway = new PG(mPaymentOption, bill, userDetails);

            paymentgateway.charge(new Callback() {
                @Override
                public void onTaskexecuted(String success, String error) {
                    processresponse(success, error);
                    dismissDialog();
                }
            });
        }
    }

    private void processresponse(String response, String error) {

        TransactionResponse transactionResponse = null;
        if (!android.text.TextUtils.isEmpty(response)) {
            try {

                JSONObject redirect = new JSONObject(response);
                mpiServletUrl = redirect.optString("redirectUrl");

                if (!android.text.TextUtils.isEmpty(mpiServletUrl)) {


                    mPaymentWebview.loadUrl(mpiServletUrl);
                    if (mPaymentOption != null) {
                        EventsManager.logWebViewEvents(CitrusActivity.this, WebViewEvents.OPEN, mPaymentOption.getAnalyticsPaymentType()); //analytics event - WebView Event
                    }
                } else {
                    transactionResponse = new TransactionResponse(TransactionResponse.TransactionStatus.FAILED, response, mTransactionId);
                    sendResult(transactionResponse);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            transactionResponse = new TransactionResponse(TransactionResponse.TransactionStatus.FAILED, error, mTransactionId);
            sendResult(transactionResponse);
        }

    }

    private void prepaidPayment(String response, String error) {

        TransactionResponse transactionResponse = null;
        if (!android.text.TextUtils.isEmpty(response)) {
            try {

                JSONObject redirect = new JSONObject(response);
                if (!android.text.TextUtils.isEmpty(redirect.getString("redirectUrl"))) {
                    setCookie();
                    mPaymentWebview.loadUrl(redirect.getString("redirectUrl"));
                    if (mPaymentOption != null) {
                        EventsManager.logWebViewEvents(CitrusActivity.this, WebViewEvents.OPEN, mPaymentOption.getAnalyticsPaymentType()); //analytics event
                    }
                } else {
                    transactionResponse = new TransactionResponse(TransactionResponse.TransactionStatus.FAILED, response, mTransactionId);
                    sendResult(transactionResponse);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            transactionResponse = new TransactionResponse(TransactionResponse.TransactionStatus.FAILED, error, mTransactionId);
            sendResult(transactionResponse);
        }
    }


    private void showDialog(String message, boolean cancelable) {
        if (mProgressDialog != null) {
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    private void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                isBackKeyPressedByUser = true;
//                TransactionResponse transactionResponse = new TransactionResponse(TransactionResponse.TransactionStatus.CANCELLED, "Cancelled By User", mTransactionId);
//                sendResult(transactionResponse);

                mPaymentWebview.loadUrl(mpiServletUrl);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        // Set other dialog properties
        builder.setMessage("Do you want to cancel the transaction?")
                .setTitle("Cancel Transaction?");
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void setCookie() {
        cookieManager = CookieManager.getInstance();
        sessionCookie = new PersistentConfig(CitrusActivity.this).getCookieString();
        cookieManager.setCookie(Config.getBaseURL(), sessionCookie);
    }


    private static void removeCookies() {
        String setCookie = CookieManager.getInstance().getCookie(Config.getBaseURL());
        CookieManager.getInstance().setCookie(Config.getBaseURL(), Constants.CITRUS_PREPAID_COOKIE);
    }


    private void sendResult(TransactionResponse transactionResponse) {
        // Log the events
        if (mPaymentOption != null) {
            if (isBackKeyPressedByUser) {
                EventsManager.logWebViewEvents(CitrusActivity.this, WebViewEvents.BACK_KEY, mPaymentOption.getAnalyticsPaymentType()); //analytics event
            } else {
                EventsManager.logWebViewEvents(CitrusActivity.this, WebViewEvents.CLOSE, mPaymentOption.getAnalyticsPaymentType());//WebView close event
            }

            EventsManager.logPaymentEvents(CitrusActivity.this, mPaymentOption.getAnalyticsPaymentType(), transactionResponse.getAnalyticsTransactionType());//Payment Events
        }


        // Send the response to the caller.
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_EXTRA_TRANSACTION_RESPONSE, transactionResponse);

        // According new implementation, finish the activity and post the event to citrusClient.
        intent.setAction(mPaymentType.getIntentAction());
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

        dismissDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPaymentWebview != null) {
            mPaymentWebview.stopLoading();
            mPaymentWebview.destroy();
        }
        mPaymentWebview = null;
        mPaymentType = null;
        mPaymentParams = null;
        mCitrusConfig = null;
        mCitrusUser = null;
        mTransactionId = null;

        dismissDialog();
        mProgressDialog = null;
        mPaymentOption = null;
    }

    /**
     * Handle all the Webview loading in custom webview client.
     */
    private class CitrusWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // Let this webview handle all the urls loaded inside. Return false to denote that.
            view.loadUrl(url);

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // Display the message.
            if (isBackKeyPressedByUser) {
                showDialog("Cancelling the transaction. Please wait..", true);
            } else {
                showDialog("Processing your payment. Please do not refresh the page.", true);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            // Dismiss the progress/message dialog.
            dismissDialog();
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }
    }

    /**
     * This class will be loaded as JSInterface and the methods of this class will be called from
     * the javascript loaded inside webview.
     * <p/>
     * Handle the payment response and take actions accordingly.
     */
    private class JsInterface {

        @JavascriptInterface
        public void pgResponse(String response) {

            Logger.d("PG Response :: " + response);

            if (mPaymentType instanceof PaymentType.CitrusCash) {
                removeCookies();
            }
            TransactionResponse transactionResponse = TransactionResponse.fromJSON(response);
            sendResult(transactionResponse);
        }

        /**
         * This method will be called by returnURL when Cash is loaded in user's account
         *
         * @param response post parameters sent by Citrus
         */
        @JavascriptInterface
        public void loadWalletResponse(String response) {

            Logger.d("Wallet response :: " + response);

            TransactionResponse transactionResponse = TransactionResponse.parseLoadMoneyResponse(response);
            sendResult(transactionResponse);
        }
    }
}
