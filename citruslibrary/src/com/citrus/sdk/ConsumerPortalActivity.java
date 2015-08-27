package com.citrus.sdk;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.citrus.sdk.classes.CitrusConfig;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class ConsumerPortalActivity extends ActionBarActivity {

    private WebView mWebView = null;
    private CitrusConfig mCitrusConfig = CitrusConfig.getInstance();
    private String mColorPrimary = null;
    private String mColorPrimaryDark = null;
    private String mTextColorPrimary = null;
    private Context mContext = this;
    private String mAccessToken = null;
    private String mUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView = new WebView(mContext);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(citrusWebViewClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*
            This setting is required to enable redirection of urls from https to http or vice-versa.
            This redirection is blocked by default from Lollipop (Android 21).
             */
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        setContentView(mWebView);

        mColorPrimary = mCitrusConfig.getColorPrimary();
        mColorPrimaryDark = mCitrusConfig.getColorPrimaryDark();
        mTextColorPrimary = mCitrusConfig.getTextColorPrimary();

        mAccessToken = getIntent().getStringExtra(Constants.INTENT_EXTRA_ACCESS_TOKEN);
        mUrl = getIntent().getStringExtra(Constants.INTENT_EXTRA_CONSUMER_PORTAL_URL);

        Logger.d("AccessToken : %s, mURL : %s", mAccessToken, mUrl);

        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Token", mAccessToken);
        mWebView.loadUrl(mUrl, headers);

        setTitle(Html.fromHtml("<font color=\"" + mTextColorPrimary + "\"> Your Transactions </font>"));
        setActionBarBackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mWebView = null;
        mContext = null;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        // Set other dialog properties
        builder.setMessage("Do you want to go back?")
                .setTitle("Go Back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setActionBarBackground() {
        // Set primary color
        if (mColorPrimary != null && getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(mColorPrimary)));
        }

        // Set action bar color. Available only on android version Lollipop or higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mColorPrimaryDark != null) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(mColorPrimaryDark));
        }
    }

    private WebViewClient citrusWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);

            handler.proceed();
        }
    };
}
