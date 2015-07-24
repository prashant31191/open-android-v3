package com.citrus.sdk.dynamicPricing;

import android.os.Parcel;
import android.os.Parcelable;

import com.citrus.sdk.CitrusUser;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.payment.PaymentBill;
import com.citrus.sdk.payment.PaymentOption;

import java.util.Map;

/**
 * Created by salil on 23/7/15.
 */
public class DynamicPricingResponse implements Parcelable {

    private final int resultCode;
    private final String resultMessage;
    private final Amount originalAmount;
    private final Amount alteredAmount;
    private final String offerToken;
    private final Map<String, String> extraParameters;
    private PaymentBill paymentBill = null;
    private PaymentOption paymentOption = null;
    private CitrusUser citrusUser = null;

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

    public PaymentBill getPaymentBill() {
        return paymentBill;
    }

    public void setPaymentBill(PaymentBill paymentBill) {
        this.paymentBill = paymentBill;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    public CitrusUser getCitrusUser() {
        return citrusUser;
    }

    public void setCitrusUser(CitrusUser citrusUser) {
        this.citrusUser = citrusUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultCode);
        dest.writeString(this.resultMessage);
        dest.writeParcelable(this.originalAmount, 0);
        dest.writeParcelable(this.alteredAmount, 0);
        dest.writeString(this.offerToken);
        dest.writeMap(this.extraParameters);
        dest.writeParcelable(this.paymentBill, 0);
        dest.writeParcelable(this.paymentOption, 0);
        dest.writeParcelable(this.citrusUser, 0);
    }

    protected DynamicPricingResponse(Parcel in) {
        this.resultCode = in.readInt();
        this.resultMessage = in.readString();
        this.originalAmount = in.readParcelable(Amount.class.getClassLoader());
        this.alteredAmount = in.readParcelable(Amount.class.getClassLoader());
        this.offerToken = in.readString();
        this.extraParameters = in.readHashMap(String.class.getClassLoader());
        this.paymentBill = in.readParcelable(PaymentBill.class.getClassLoader());
        this.paymentOption = in.readParcelable(PaymentOption.class.getClassLoader());
        this.citrusUser = in.readParcelable(CitrusUser.class.getClassLoader());
    }

    public static final Creator<DynamicPricingResponse> CREATOR = new Creator<DynamicPricingResponse>() {
        public DynamicPricingResponse createFromParcel(Parcel source) {
            return new DynamicPricingResponse(source);
        }

        public DynamicPricingResponse[] newArray(int size) {
            return new DynamicPricingResponse[size];
        }
    };
}
