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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.response.CitrusError;

import static com.citrus.sample.Utils.PaymentType.CITRUS_CASH;
import static com.citrus.sample.Utils.PaymentType.LOAD_MONEY;
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
        showPrompt(LOAD_MONEY);
    }

    private void payUsingCash() {
        showPrompt(CITRUS_CASH);
    }

    private void pgPayment() {
        showPrompt(PG_PAYMENT);
    }

    private void showPrompt(final Utils.PaymentType paymentType) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        String message = null;
        String positiveButtonText = null;

        switch (paymentType) {
            case LOAD_MONEY:
                message = "Please enter the amount to load.";
                positiveButtonText = "Load Money";
                break;
            case CITRUS_CASH:
                message = "Please enter the transaction amount.";
                positiveButtonText = "Pay";
                break;
            case PG_PAYMENT:
                message = "Please enter the transaction amount.";
                positiveButtonText = "Make Payment";
                break;
        }

        alert.setTitle("Transaction Amount?");
        alert.setMessage(message);
        // Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();

                mListener.onPaymentTypeSelected(paymentType, new Amount(value));

                input.clearFocus();
                // Hide the keyboard.
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        input.requestFocus();
        alert.show();
    }

}


