package com.citrus.sdk.dynamicPricing;

import com.citrus.sdk.CitrusUser;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.payment.CardOption;
import com.citrus.sdk.payment.NetbankingOption;
import com.citrus.sdk.payment.PaymentOption;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by salil on 23/7/15.
 */
public class DynamicPricingRequest {

    private final Amount originalAmount;
    private final String signature;
    private final String merchantAccessKey;
    private final String merchantTransactionId;
    private final CitrusUser user;
    private final PaymentOption paymentOption;
    private final Map<String, String> extraParameters;
    private final DynamicPricingOperation operation;

    public DynamicPricingRequest(Amount originalAmount, String signature, String merchantAccessKey, String merchantTransactionId, CitrusUser user, PaymentOption paymentOption, DynamicPricingOperation operation) {
        this.originalAmount = originalAmount;
        this.signature = signature;
        this.merchantAccessKey = merchantAccessKey;
        this.merchantTransactionId = merchantTransactionId;
        if (user != null) {
            this.user = user;
        } else {
            this.user = CitrusUser.DEFAULT_USER;
        }
        this.paymentOption = paymentOption;
        this.operation = operation;

        extraParameters = new HashMap<>();
        extraParameters.put("operation", operation.toString());
    }

    public Amount getOriginalAmount() {
        return originalAmount;
    }

    public String getSignature() {
        return signature;
    }

    public String getMerchantAccessKey() {
        return merchantAccessKey;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public CitrusUser getUser() {
        return user;
    }

    public PaymentOption getPaymentInformation() {
        return paymentOption;
    }

    public Map<String, String> getExtraParameters() {
        return extraParameters;
    }

    public static String toJSON(DynamicPricingRequest request) {
        String response = null;

        if (request != null) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("email", request.user.getEmailId());
                jsonObject.put("phone", request.user.getMobileNo());
                jsonObject.put("merchantTransactionId", request.merchantTransactionId);
                jsonObject.put("merchantAccessKey", request.merchantAccessKey);
                jsonObject.put("signature", request.signature);
                jsonObject.put("originalAmount", Amount.toJSON(request.originalAmount));
                jsonObject.put("paymentInfo", getPaymentInformation(request.paymentOption));
                jsonObject.put("extraParams", request.extraParameters);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            response = jsonObject.toString();
        }

        return response;
    }

    private static String getPaymentInformation(PaymentOption paymentOption) {

        JSONObject jsonObject = new JSONObject();
        try {
            if (paymentOption != null) {
                if (paymentOption.getToken() != null) {
                    jsonObject.put("paymentToken", paymentOption.getToken());
                } else if (paymentOption instanceof NetbankingOption) {
                    jsonObject.put("issuerId", ((NetbankingOption) paymentOption).getBankCID());
                } else if (paymentOption instanceof CardOption) {
                    jsonObject.put("cardNo", ((CardOption) paymentOption).getCardNumber());
                    jsonObject.put("cardType", ((CardOption) paymentOption).getCardScheme().toString());
                }

                jsonObject.put("paymentMode", paymentOption.getPaymentMode());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}

