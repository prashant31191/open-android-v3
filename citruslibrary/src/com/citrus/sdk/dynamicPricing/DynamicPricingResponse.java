package com.citrus.sdk.dynamicPricing;

import com.citrus.sdk.classes.Amount;

import java.util.Map;

/**
 * Created by salil on 23/7/15.
 */
public class DynamicPricingResponse {

    private final int resultCode;
    private final String resultMessage;
    private final Amount originalAmount;
    private final Amount alteredAmount;
    private final String offerToken;
    private final Map<String, String> extraParameters;

    public DynamicPricingResponse(int resultCode, String resultMessage, Amount originalAmount, Amount alteredAmount, String offerToken, Map<String, String> extraParameters) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.originalAmount = originalAmount;
        this.alteredAmount = alteredAmount;
        this.offerToken = offerToken;
        this.extraParameters = extraParameters;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public Amount getOriginalAmount() {
        return originalAmount;
    }

    public Amount getAlteredAmount() {
        return alteredAmount;
    }

    public String getOfferToken() {
        return offerToken;
    }

    public Map<String, String> getExtraParameters() {
        return extraParameters;
    }
}
