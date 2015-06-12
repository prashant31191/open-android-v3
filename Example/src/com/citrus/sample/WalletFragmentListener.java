package com.citrus.sample;

import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.payment.PaymentType;

/**
 * Created by salil on 3/6/15.
 */
public interface WalletFragmentListener {
    void onPaymentComplete(TransactionResponse transactionResponse);

    void onPaymentTypeSelected(Utils.PaymentType paymentType);
}
