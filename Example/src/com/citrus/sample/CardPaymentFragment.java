package com.citrus.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.citrus.mobile.CType;
import com.citrus.mobile.Month;
import com.citrus.mobile.Year;
import com.citrus.sdk.CitrusActivity;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.CitrusUser;
import com.citrus.sdk.Constants;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.payment.CardOption;
import com.citrus.sdk.payment.CreditCardOption;
import com.citrus.sdk.payment.DebitCardOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.widgets.CardNumberEditText;
import com.citrus.widgets.ExpiryDate;
import com.viewpagerindicator.TabPageIndicator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WalletPaymentFragment} interface
 * to handle interaction events.
 * Use the {@link CardPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardPaymentFragment extends Fragment implements View.OnClickListener {

    private final String BILL_URL = "https://salty-plateau-1529.herokuapp.com/billGenerator.sandbox.php";
    private final String RETURN_URL_LOAD_MONEY = "https://salty-plateau-1529.herokuapp.com/redirectUrlLoadCash.php";
    private static final String[] OPTIONS = new String[]{"SAVED\nACCOUNTS", "CREDIT\nCARD", "DEBIT\nCARD", "NET\nBANKING"};
    private EditText editCardName = null;
    private EditText editCVV = null;
    private EditText editAmount = null;
    private CardNumberEditText editCardNumber = null;
    private ExpiryDate editCardExpiry = null;
    private RadioGroup radioGroup = null;
    private CType cType = CType.DEBIT;
    private Button btnPay = null;
    private Utils.PaymentType paymentType = null;


    private WalletFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardPaymentFragment newInstance(Utils.PaymentType paymentType) {
        CardPaymentFragment fragment = new CardPaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("paymentType", paymentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CardPaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            paymentType = (Utils.PaymentType) bundle.getSerializable("paymentType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.PagerIndicatorDefaultNewWithDivider);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        // Inflate the layout for this fragment
        View rootView = localInflater.inflate(R.layout.fragment_card_payment, container, false);

        /*editCardName = (EditText) rootView.findViewById(R.id.txt_card_name);
        editCVV = (EditText) rootView.findViewById(R.id.txt_cvv);
        editAmount = (EditText) rootView.findViewById(R.id.txt_amount);
        editCardNumber = (CardNumberEditText) rootView.findViewById(R.id.cardnumber);
        editCardExpiry = (ExpiryDate) rootView.findViewById(R.id.expdate);
        btnPay = (Button) rootView.findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(this);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioCredit) {
                    cType = CType.CREDIT;
                } else if (checkedId == R.id.radioDebit) {
                    cType = CType.DEBIT;
                }
            }
        });*/


        FragmentStatePagerAdapter adapter = new SavePayAdapter(getChildFragmentManager());
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator) rootView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        return rootView;
    }

    public class SavePayAdapter extends FragmentStatePagerAdapter {
        public SavePayAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new SavedOptionsFragment();
            } else if (position == 1 || position == 2) {
                return new CreditDebitCardFragment();
            } else {
                return new NetbankingFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return OPTIONS[position];
        }

        @Override
        public int getCount() {
            return OPTIONS.length;
        }
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

    private void startCitrusActivity(PaymentType paymentType) {
        Intent intent = new Intent(getActivity(), CitrusActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_PAYMENT_TYPE, paymentType);
        startActivityForResult(intent, Constants.REQUEST_CODE_PAYMENT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TransactionResponse transactionResponse = data.getParcelableExtra(Constants.INTENT_EXTRA_TRANSACTION_RESPONSE);
        if (transactionResponse != null) {
            Utils.showToast(getActivity(), transactionResponse.getMessage());
        }
    }

    @Override
    public void onClick(View v) {

        String cardHolderName = editCardName.getText().toString();
        String cardNumber = editCardNumber.getText().toString();
        String cardCVV = editCVV.getText().toString();
        Month month = editCardExpiry.getMonth();
        Year year = editCardExpiry.getYear();
        String amountStr = editAmount.getText().toString();

        Amount amount = new Amount(amountStr);
        CardOption cardOption = null;
        if (cType == CType.DEBIT) {
            cardOption = new DebitCardOption(cardHolderName, cardNumber, cardCVV, month, year);
        } else {
            cardOption = new CreditCardOption(cardHolderName, cardNumber, cardCVV, month, year);
        }
        PaymentType paymentType = null;
        CitrusClient client = CitrusClient.getInstance(getActivity());

        if (this.paymentType == Utils.PaymentType.LOAD_MONEY) {
            paymentType = new PaymentType.LoadMoney(amount, RETURN_URL_LOAD_MONEY, cardOption);
        } else if (this.paymentType == Utils.PaymentType.CITRUS_CASH) {
            paymentType = new PaymentType.CitrusCash(amount, BILL_URL);
        } else if (this.paymentType == Utils.PaymentType.PG_PAYMENT) {
            paymentType = new PaymentType.PGPayment(amount, BILL_URL, cardOption, new CitrusUser(client.getUserEmailId(), client.getUserMobileNumber()));
        }

        startCitrusActivity(paymentType);

    }
}
