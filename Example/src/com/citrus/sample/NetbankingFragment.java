package com.citrus.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class NetbankingFragment extends Fragment {
    /*private View returnView;

    private Spinner spinner;
    private String amount_load;
    private Map<String, String> bankOptions;
    private ArrayAdapter<String> dataAdapter;
    private List<String> bankNames, bankCodes;
    private String selectedBank;
    private FontTextView submit;
    private JSONObject paymentObject;

    private RadioGroup mFirstGroup;
    private RadioGroup mSecondGroup;
    private RadioButton rdb_axis, rdb_idbi, rdb_icici, rdb_sbi;
    private boolean returnFlag = false;
    // public AlertDialog mWaitBox;
    private boolean isChecking = true, flag = true;
    String TAG = "Load-NetBanking ";
    int mCheckedId = R.id.type1;
    JSONArray returnBank = null;
    List<String> validBankNames = null;
    List<String> validBankCID = null;
    String result;

    private com.citruspaykit.mobile.payment.OnTaskCompleted taskExecuted;

    SavedOptions sO;
    public SparseBooleanArray checked = new SparseBooleanArray();

    public SharedPreferences mStoredValues;*/

    Spinner bankOptions;
    TextView load;

    public NetbankingFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_net_banking, container, false);


        bankOptions = (Spinner) returnView.findViewById(R.id.bankOptionsSpinner);
        load = (TextView) returnView.findViewById(R.id.load);

       /* amount_load = getActivity().getIntent().getStringExtra("amount");
        mStoredValues = getActivity().getSharedPreferences(CitrusConstants.STORED_VALUES, 0);

        selectedBank = "";
        sO = new SavedOptions();
        initBanks();
        initViews();*/
        return returnView;
    }

    /*private void initBanks() {
        bankOptions = new HashMap<String, String>();

        DBHandlerSDK dbInstance = new DBHandlerSDK(getActivity().getApplicationContext());
        List<BankOptions> bankOption = new ArrayList<BankOptions>();

        bankOption = dbInstance.getBankOptions();

        String validBanks[] = new String[bankOption.size()];
        String validBanksCID[] = new String[bankOption.size()];
        for (int i = 0; i < bankOption.size(); i++) {
            validBanks[i] = bankOption.get(i).getBankName();
            validBanksCID[i] = bankOption.get(i).getBankcid();
            bankOptions.put(validBanks[i], validBanksCID[i]);
        }

        *//*bankOptions.put("AXIS Bank",  "CID002");
        bankOptions.put("Bank of Maharashtra","CID021");
		bankOptions.put("Central Bank of India","CID023");
		bankOptions.put("City Union Bank","CID024");
		bankOptions.put("Corporation Bank","CID025");    
		bankOptions.put("Deutsche Bank", "CID006");	
		bankOptions.put("Federal Bank","CID009");
		bankOptions.put("ICICI Bank", "CID001");
		bankOptions.put("IDBI Bank","CID011");
		bankOptions.put("Indian Bank",	"CID008");	
		bankOptions.put("Indian Overseas Bank","CID027");
		bankOptions.put("Karnataka Bank","CID031");
		bankOptions.put("Kotak Mahindra Bank","CID033");
		bankOptions.put("PNB Retail","CID044");
		bankOptions.put("SBI Bank",	"CID005");
		bankOptions.put("State Bank of Bikaner and Jaipur",	"CID013");
		bankOptions.put("State Bank of Hyderabad",	"CID012");
		bankOptions.put("State Bank of Mysore",	"CID014");
		bankOptions.put("State Bank of Patiala","CID043");
		bankOptions.put("State Bank of Travancore",	"CID015");		
		bankOptions.put("Syndicate Bank","CID039");		
		bankOptions.put("United Bank of India","CID041");
		bankOptions.put("Union Bank",	"CID007");		
		bankOptions.put("Vijaya Bank",	"CID042");	
		bankOptions.put("YES Bank",	"CID004");*//*

        List sortedKeys = new ArrayList(bankOptions.keySet());
        Collections.sort(sortedKeys);


    }*/

    /*@Override
    public void setMenuVisibility(boolean menuVisible) {

        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            CitrusLogging.logDebug("Netbanking card vsible");
            SavedOptionsFragment.firstFragment = false;
            if (getActivity() != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isChecking = true;
    }

    private void initViews() {


        bankNames = new ArrayList<String>();
        bankCodes = new ArrayList<String>();

        for (Map.Entry<String, String> entry : bankOptions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            bankNames.add(key);
            bankCodes.add(value);

        }
        Collections.sort(bankNames);
        bankNames.add(0, "Select Bank");


        final NetBankAdapter bankListAdapter = new NetBankAdapter(getActivity().getApplicationContext(), bankNames);

        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, bankNames);

        spinner = (Spinner) returnView.findViewById(R.id.bankOptions);
        //list_banks = (ListView)returnView.findViewById(R.id.bankOptions);
        //list_banks.setCacheColorHint(Color.TRANSPARENT);
        //list_banks.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        spinner.setAdapter(bankListAdapter);
        //list_banks.setAdapter(bankListAdapter);
        //list_banks.setAdapter(dataAdapter);
        rdb_idbi = (RadioButton) returnView.findViewById(R.id.type2);
        rdb_axis = (RadioButton) returnView.findViewById(R.id.type1);
        rdb_icici = (RadioButton) returnView.findViewById(R.id.type4);
        rdb_sbi = (RadioButton) returnView.findViewById(R.id.type5);

        mFirstGroup = (RadioGroup) returnView.findViewById(R.id.first_group);
        mSecondGroup = (RadioGroup) returnView.findViewById(R.id.second_group);


        mFirstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    flag = true;
                    //selectedBank="";
                    mSecondGroup.clearCheck();
                    mCheckedId = checkedId;

                    spinner.setSelection(0);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        CitrusLogging.logError("InterruptedException", e);
                    }
                    showType(getView());
                }

                isChecking = true;


            }
        });

        mSecondGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                *//*	if(mCheckedId ==R.id.type4 || mCheckedId==R.id.type5){
                        flag=true;
	            	}*//*
                if (checkedId != -1 && isChecking) {

                    isChecking = false;
                    selectedBank = "";
                    mFirstGroup.clearCheck();

                    mCheckedId = checkedId;

                    spinner.setSelection(0);

                    showType(getView());
                }
                isChecking = true;
            }
        });


        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                //selectedBank = "";
                spinner.setSelection(arg2);
                //spinner.setSelection(arg2);
                //spinner.setSelection(arg2);

                if (arg2 == 0) {
                    selectedBank = "";
                } else {

                    selectedBank = bankNames.get(arg2);
                    //selectedBank = bankNames.get(arg2);
                    //selectedBank = bankNames.get(arg2);
                    isChecking = false;
                    flag = false;

                    mFirstGroup.clearCheck();
                    isChecking = false;
                    mSecondGroup.clearCheck();
                    //rdb_sbi.setSelected(false);
                    //rdb_sbi.setChecked(false);
                    //rdb_icici.setChecked(false);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selectedBank = "";
            }
        });

        initSubmit();
    }

    public void showType(View view) {
        if (mCheckedId == R.id.type1) {
            selectedBank = "AXIS Bank";
        } else if (mCheckedId == R.id.type2) {
            selectedBank = "IDBI Bank";
        } else if (mCheckedId == R.id.type4) {
            selectedBank = "ICICI Bank";
        } else if (mCheckedId == R.id.type5) {
            selectedBank = "HDFC Bank";
        }
    }


    private void initSubmit() {
        submit = (FontTextView) returnView.findViewById(R.id.submitButton);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                if (selectedBank != null && !TextUtils.isEmpty(selectedBank)) {
                    if (bankNames.contains(selectedBank)) {


                        if (CustomFunctions.isNetAvailable(getActivity())) {
                            if (!TextUtils.isEmpty(selectedBank)) {
                                try {
                                    savePayOption();
                                } catch (JSONException e) {
                                    CitrusLogging.logError("JSONException", e);
                                }
                                createTransaction();
                            }
                        } else {
                            Intent it = new Intent(getActivity(), ConnectionLost.class);
                            it.putExtra("activity", "PaymetActivity");
                            startActivity(it);
                        }
                    } else {
                        ToastUtils.showShort(getActivity(), "Selected bank is not available");
                    }
                } else {
                    ToastUtils.showShort(getActivity(), "Please select a bank");
                }
            }
        });
    }

    protected void savePayOption() throws JSONException {

        JSONObject mCardDetails = new JSONObject();
        JSONObject paymentDetails = new JSONObject().put("type", "netbanking").put("bank", selectedBank).put("owner", "");
        mCardDetails.put("type", CitrusConstants.FASTCHECKOUT_TYPE)
                .put("defaultOption", "")
                .put("paymentOptions", new JSONArray(((Collections.singleton(paymentDetails)))));
        CitrusLogging.logDebug("Save Option Details : " + mCardDetails.toString());
        new SavePayOption(getActivity(), mCardDetails, new TXNJSonObject() {

            @Override
            public void onTaskCompletedExec(JSONObject txnObject) {
                if (getActivity() != null && isAdded()) {
                    try {
                        if (txnObject != null) {
                            if (txnObject.getBoolean(CitrusConstants.TXN_STATUS)) {
                            *//*								DBHandler testDBHandler = new DBHandler(getActivity().getApplicationContext());
                        int result = testDBHandler.addPayOptions(nameOnCard, null, cardNumber, mCardType.toUpperCase(), "credit", expiryDate, cardNumber);
						testDBHandler.close();*//*

                            }
                        }
                    } catch (JSONException e) {
                        CitrusLogging.logError("JSONException", e);
                    }
                }
            }
        }).execute();
    }

    private void createTransaction() {
        *//*Get a signed order first*//*
        returnFlag = true;
        JSONObject txnDetails = new JSONObject();
        try {
            txnDetails.put("amount", amount_load);
            txnDetails.put("currency", "INR");
            txnDetails.put("redirect", CitrusConstants.getRedirect_URL());
        } catch (JSONException e) {

        }

        ProgressDialog.getInstance().show_dialogWithCancelListener(getActivity(), "Hold on. This won't take long!", new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                returnFlag = false;
                ProgressDialog.getInstance().dissmiss_dialog();

            }
        });

        //TODO EVent Load Pessed
        CitrusCashGlobal.getInstance().setPaymentMode(CustomFunctions.PAYMENT_MODE.NETBANKING);
        CustomFunctions.setEvent(getActivity(), TAG + "click");
        new GetSignedorder(getActivity(), txnDetails, new OnTaskCompleted() {

            @Override
            public void onTaskExecuted(JSONObject[] signedOrder, String message) {
                if (getActivity() != null && isAdded()) {
                    try {
                        if (message != null && message.equalsIgnoreCase(CitrusConstants.bad_Request_Message)) {
                            ProgressDialog.getInstance().dissmiss_dialog();
                            ((PaymentActivity) getActivity()).DisplayErrorMessage(CitrusConstants.bad_Request_Message);
                        } else {
                            String txnId = signedOrder[0].getString("merchantTransactionId");
                            String signature = signedOrder[0].getString("signature");
                            insertValues(txnId, signature);
                        }
                        //initiateTxn();
                    } catch (JSONException e) {
                        CitrusLogging.logError("JSONException", e);
                    }
                }
            }

            @Override
            public void taskCompleted(String errorMessage) {


            }

        }).execute();
        //   insertValues(txnId, signature);
        //initiateTxn();
    }

    private void insertValues(String txnId, String signature) {

        try {
            *//*Payment Details - DO NOT STORE THEM LOCALLY OR ON YOUR SERVER*//*
            JSONObject amount = new JSONObject();
            amount.put("currency", "INR");
            amount.put("value", amount_load);

            JSONObject address = new JSONObject();
            address.put("street1", "1st");
            address.put("street2", "2nd");
            address.put("city", "Mumbai");
            address.put("state", "Maharastra");
            address.put("country", "India");
            address.put("zip", "400054");

            JSONObject userDetails = new JSONObject();
            CitrusLogging.logDebug(mStoredValues.getString(CitrusConstants.EMAIL, null) + " "
                    + mStoredValues.getString(CitrusConstants.FIRSTNAME, null)
                    + " " + mStoredValues.getString(CitrusConstants.LASTNAME, null) + " "
                    + mStoredValues.getString(CitrusConstants.MOBILE, null));
            userDetails.put("email", mStoredValues.getString(CitrusConstants.EMAIL, null));
            userDetails.put("firstName", mStoredValues.getString(CitrusConstants.FIRSTNAME, null));
            userDetails.put("lastName", mStoredValues.getString(CitrusConstants.LASTNAME, null));
            userDetails.put("mobileNo", mStoredValues.getString(CitrusConstants.MOBILE, null));

            userDetails.put("address", address);

            JSONObject paymentMode = new JSONObject();
            paymentMode.put("type", "netbanking");
            paymentMode.put("bank", "");
            paymentMode.put("code", bankOptions.get(selectedBank));

            JSONObject paymentToken = new JSONObject();
            paymentToken.put("type", "paymentOptionToken");

            paymentToken.put("paymentMode", paymentMode);

            paymentObject = new JSONObject();
            paymentObject.put("merchantTxnId", txnId);
            paymentObject.put("paymentToken", paymentToken);
            paymentObject.put("userDetails", userDetails);
            paymentObject.put("amount", amount);
            paymentObject.put("notifyUrl", "");
            paymentObject.put("merchantAccessKey", CitrusConstants.getAccess_Key());
            paymentObject.put("requestSignature", signature);
            paymentObject.put("returnUrl", CitrusConstants.getRedirect_URL_COMPLETE());
            if (returnFlag) {
                CitrusLogging.logDebug("NOT CAUGHT");
                initiateTxn();
            } else {
                CitrusLogging.logDebug("CAUGHT");
            }

        } catch (JSONException e) {

        }

    }

    private void initiateTxn() {

        ProgressDialog.getInstance().dissmiss_dialog();
        taskExecuted = new com.citruspaykit.mobile.payment.OnTaskCompleted() {

            @Override
            public void onTaskExecuted(JSONObject[] paymentObject, String message) {
                if (getActivity() != null && isAdded()) {
                    if (TextUtils.isEmpty(message)) {
                        try {
                            String url = paymentObject[0].getString("redirectUrl");

                            CitrusLogging.logDebug("url:" + url);
                            if (url.startsWith("https")) {
                                Intent intent = new Intent(getActivity(), Web3DSecureActivity.class);
                                intent.putExtra("url", url);
                                intent.putExtra("amount", amount_load);
                                //TODO EVent Load Txn Initiate
                                CustomFunctions.setEvent(getActivity(), TAG + "Redirect Url :" + url);
                                //TODO Parse RedirectURL Json
                            *//*{
                                "redirectUrl":"https://sandbox.citruspay.com/mpiServlet/4765684d674336374a696e4b4e3876434e57786e63413d3d",
                                    "pgRespCode":"0",
                                    "txMsg":"Transaction successful"
                            }*//*
                                //TODO PGRESPONSE SWITCH 111(Invld Sign)-0(Succ)-118(Mand Param miss)-125(Txn failed-pay option not avail)

                                try {
                                    switch (Integer.parseInt(paymentObject[0].getString("pgRespCode"))) {
                                        case 0:
                                            CustomFunctions.setEvent(getActivity(), TAG + "SuccRedirect");
                                            break;
                                        case 111:
                                            CustomFunctions.setEvent(getActivity(), TAG + "111- Invalid Signature");
                                            break;
                                        case 118:
                                            CustomFunctions.setEvent(getActivity(), TAG + "118- Mandatory Param Missing");
                                            break;
                                        case 125:
                                            CustomFunctions.setEvent(getActivity(), TAG + "125- Payment option not available");
                                            break;
                                    }
                                } catch (Exception e) {
                                    CitrusLogging.logError("Exception", e);
                                }
                                startActivity(intent);
                            } else {
                                ToastUtils.showShort(getActivity(), "Bank is currently not available");
                            }
                        } catch (JSONException e) {
                            CitrusLogging.logError("JSONException", e);
                        }
                    }
                }
            }

        };
        new Pay(getActivity(), paymentObject, taskExecuted).execute();
    }

    private JSONObject paymentOptionobject = null;
    HttpPost httpPost;


    @Override
    public void onPause() {
        super.onPause();
        CitrusLogging.logDebug("testing fragment onPause");

    }*/
}
