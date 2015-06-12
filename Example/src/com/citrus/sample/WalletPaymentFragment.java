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

package com.citrus.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusActivity;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Constants;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;

import static com.citrus.sample.Utils.PaymentType.*;
import static com.citrus.sample.Utils.PaymentType.CITRUS_CASH;
import static com.citrus.sample.Utils.PaymentType.PG_PAYMENT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WalletFragmentListener} interface
 * to handle interaction events.
 * Use the {@link WalletPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletPaymentFragment extends Fragment implements View.OnClickListener {

    private WalletFragmentListener mListener;
    private CitrusClient mCitrusClient = null;
    private Context mContext = null;

    private Button btnGetBalance = null;
    private Button btnLoadMoney = null;
    private Button btnPayUsingCash = null;
    private Button btnPGPayment = null;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WalletPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletPaymentFragment newInstance() {
        WalletPaymentFragment fragment = new WalletPaymentFragment();
        return fragment;
    }

    public WalletPaymentFragment() {
        // Required empty public constructor


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        mCitrusClient = CitrusClient.getInstance(mContext.getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wallet_payment, container, false);


        btnGetBalance = (Button) rootView.findViewById(R.id.btn_get_balance);
        btnLoadMoney = (Button) rootView.findViewById(R.id.btn_load_money);
        btnPayUsingCash = (Button) rootView.findViewById(R.id.btn_pay_using_cash);
        btnPGPayment = (Button) rootView.findViewById(R.id.btn_pg_payment);

        btnGetBalance.setOnClickListener(this);
        btnLoadMoney.setOnClickListener(this);
        btnPayUsingCash.setOnClickListener(this);
        btnPGPayment.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (WalletFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WalletFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_get_balance:
                getBalance();
                break;
            case R.id.btn_load_money:
                loadMoney();
                break;
            case R.id.btn_pay_using_cash:
                payUsingCash();
                break;
            case R.id.btn_pg_payment:
                pgPayment();
                break;
        }

    }

    private void startCitrusActivity(PaymentType paymentType) {
        Intent intent = new Intent(mContext, CitrusActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_PAYMENT_TYPE, paymentType);
        startActivityForResult(intent, Constants.REQUEST_CODE_PAYMENT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TransactionResponse transactionResponse = data.getParcelableExtra(Constants.INTENT_EXTRA_TRANSACTION_RESPONSE);
        if (transactionResponse != null) {
            Toast.makeText(mContext, transactionResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getBalance() {

        mCitrusClient.getBalance(new Callback<Amount>() {
            @Override
            public void success(Amount amount) {
                Utils.showToast(mContext, "Balance : " + amount.getValue());
            }

            @Override
            public void error(CitrusError error) {
                Utils.showToast(mContext, error.getMessage());
            }
        });
    }

    private void loadMoney() {
        mListener.onPaymentTypeSelected(LOAD_MONEY);
    }

    private void payUsingCash() {
        mListener.onPaymentTypeSelected(CITRUS_CASH);
    }

    private void pgPayment() {
        mListener.onPaymentTypeSelected(PG_PAYMENT);
    }

}


