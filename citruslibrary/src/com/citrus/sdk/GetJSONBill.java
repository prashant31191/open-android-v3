package com.citrus.sdk;

import com.citrus.retrofit.RetroFitClient;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.payment.PaymentBill;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit.Callback;

/**
 * Created by MANGESH KADAM on 5/8/2015.
 */
public class GetJSONBill {
    String billURL;
    Amount amount;
    Callback<PaymentBill> callback;
    public GetJSONBill(String billURL, Amount amount, Callback<PaymentBill> callback){
        this.billURL = billURL;
        this.amount = amount;
        this.callback = callback;
    }

    public void getJSONBill() {
        URL url = null;
        try {
            url = new URL(billURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String baseURL = url.getProtocol() + "://" + url.getAuthority();
        String path = url.getPath();
        path = path.substring(1);
        RetroFitClient.setEndPoint(baseURL);
        RetroFitClient.getBillGeneratorClient(baseURL).getBill(path, amount.getValue(), callback);
        RetroFitClient.resetEndPoint();
    }
}
