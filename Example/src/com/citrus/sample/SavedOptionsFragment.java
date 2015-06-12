package com.citrus.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SavedOptionsFragment extends Fragment {

   /* static boolean firstFragment = true;
    private View returnView;
    private com.citruspaykit.mobile.payment.OnTaskCompleted taskExecuted;
    private OnTaskCompleted listener;
    public Editor mEditor;
    private EfficientAdapter adapter;
    private GetCustprofile custProfile;
    //private Pay payObj;
    private GetSignedorder getSignedOrder;
    private ListView payOptionList;
    private static String amount_load;
    String previous_activity;
    private JSONObject paymentObject;
    public List<OptionDetails> paymentList;
    private TextView security_text;
    public String defaultOption = "";
    private static JSONObject contactJSON;
    public static SharedPreferences mStoredValues;
    public static boolean flag = false, returnFlag = false, getAccFlag = true;
    static int list_position;
    int defaultPosition;
    String netbanking = null;
    JSONArray returnBank = null;
    List<String> validBankNames = null;
    Activity activity;
    List<String> listData = new ArrayList<String>();
    //static String data[];
    static String payOption[];
    String TAG = "Load-SavedOption ";
    static boolean flaging = false;
    FontTextView loadMoney_btn;
    Button show_moreBtn;
    long startTime;
    long endTime;
    long screenStartTime;
    long screenEndTime;*/

    ListView savedCardList;
    TextView load;
    public SavedOptionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_saved_cards, container, false);

        savedCardList= (ListView) returnView.findViewById(R.id.savedCardList);
        load= (TextView) returnView.findViewById(R.id.load);



        /*screenStartTime = new Date().getTime();

        mStoredValues = getActivity().getSharedPreferences(
                CitrusConstants.STORED_VALUES, 0);

        amount_load = getActivity().getIntent().getStringExtra("amount");
        previous_activity = getActivity().getIntent()
                .getStringExtra("activity");


        activity = getActivity();


        ProgressDialog.getInstance().show_dialogWithCancelListener(getActivity(),
                "Loading your payment options", new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        getAccFlag = false;
                        ProgressDialog.getInstance().dissmiss_dialog();

                    }
                });

        listener = new OnTaskCompleted() {
            public void onTaskExecuted(JSONObject[] returnObject, String message) {
                if (getActivity() != null && isAdded()) {
                    for (JSONObject aReturnObject : returnObject) {
                        CitrusLogging.logDebug("CustProfile Value *************** " + aReturnObject.toString());
                    }
                    if (!returnObject[0].equals(null)) {
                        contactJSON = returnObject[0];
                        String lastName = null, firstName = null, mobile = null;
                        try {
                            lastName = contactJSON.getString("lastName");
                            firstName = contactJSON.getString("firstName");
                            mobile = contactJSON.getString("mobile");
                        } catch (JSONException e1) {
                            CitrusLogging.logError("JSONException", e1);
                        }
                        mEditor = mStoredValues.edit();
                        mEditor.putString(CitrusConstants.LASTNAME, lastName);
                        mEditor.putString(CitrusConstants.FIRSTNAME, firstName);
                        mEditor.putString(CitrusConstants.MOBILE, mobile);
                        mEditor.apply();
                    }
                    if (TextUtils.equals(message, "oauth")) {
                        ProgressDialog.getInstance().dissmiss_dialog();
                        ToastUtils.showShort(getActivity(), "User is not signed in!");
                    } else {
                        if (!returnObject[1].equals(null)) {
                            initViews();
                            try {
                                if (returnObject[1].getJSONArray("paymentOptions").length() > 0) {
                                    if (getAccFlag) {
                                        storePayOptions(returnObject[1]);
                                    }
                                    if (!TextUtils.isEmpty(defaultOption)) {
                                        if (previous_activity.equalsIgnoreCase("Load")
                                                && getAccFlag == true) {

                                            ProgressDialog.getInstance().dissmiss_dialog();
                                            screenEndTime = new Date().getTime();
                                        } else {
                                            ProgressDialog.getInstance().dissmiss_dialog();
                                            screenEndTime = new Date().getTime();

                                        }
                                    } else {
                                        ProgressDialog.getInstance().dissmiss_dialog();
                                        screenEndTime = new Date().getTime();
                                    }
                                } else {
                                    ProgressDialog.getInstance().dissmiss_dialog();

                                }
                            } catch (JSONException e) {
                                CitrusLogging.logError("JSONException", e);
                            }
                        } else {
                            ProgressDialog.getInstance().dissmiss_dialog();
                        }
                    }
                }
            }

            @Override
            public void taskCompleted(String errorMessage) {

            }
        };

        getWallet();*/

        return returnView;
    }

    /*@Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (flag) {

            flag = false;
        }
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            firstFragment = true;
        }
    }

    private void initViews() {
        payOptionList = (ListView) returnView.findViewById(R.id.list);
        loadMoney_btn = (FontTextView) returnView.findViewById(R.id.select_ok_button);
        show_moreBtn = (Button) returnView.findViewById(R.id.btn_showMore);
        security_text = (TextView) returnView.findViewById(R.id.security_txt);
    }


    protected void storePayOptions(JSONObject savedOption) {
        if (savedOption == null) {
            return;
        } else {
            DBHandlerSDK dbInstance = new DBHandlerSDK(getActivity()
                    .getApplicationContext());
            dbInstance.deletePayOption();
            String name = null;

            List<BankOptions> bankOptions = new ArrayList<BankOptions>();
            bankOptions = dbInstance.getBankOptions();

            String validBanks[] = new String[bankOptions.size()];
            for (int i = 0; i < bankOptions.size(); i++) {
                validBanks[i] = bankOptions.get(i).getBankName();

            }


            try {
                JSONArray payOptionArray = null;
                try {
                    payOptionArray = savedOption.getJSONArray("paymentOptions");
                } catch (JSONException e1) {
                    CitrusLogging.logError("JSONException", e1);
                }
                int length = 0;
                if (payOptionArray != null) {
                    length = payOptionArray.length();
                }
                try {
                    defaultOption = savedOption.getString("defaultOption");
                } catch (JSONException e1) {
                    CitrusLogging.logError("JSONException", e1);
                }
                int j = 0;
                for (int i = 0; i < length; i++) {
                    try {
                        boolean store_flag = false;

                        JSONObject payoption = (JSONObject) payOptionArray
                                .get(i);
                        String cardNumber = payoption.getString("number");
                        String scheme = payoption.getString("scheme");
                        String bankName = payoption.getString("bank");
                        String owner = payoption.getString("owner");
                        String expDate = payoption.getString("expiryDate");


                        if (payoption.getString("type").equalsIgnoreCase(
                                "netbanking")) {
                            for (String valid : validBanks) {
                                if (bankName.equalsIgnoreCase(valid)) {
                                    store_flag = true;
                                    break;
                                }
                            }
                        } else if (payoption.getString("type").equalsIgnoreCase("debit")
                                || payoption.getString("type").equalsIgnoreCase("credit")) {
                            store_flag = true;
                        }
                        name = payoption.getString("name");

                        if (store_flag) {
                            dbInstance.addPayOptions(owner, bankName,
                                    cardNumber, scheme,
                                    payoption.getString("type"), expDate, name,
                                    payoption.getString("token"));
                            listData.add(String.valueOf(j));

                            if (defaultOption.equalsIgnoreCase(name)) {
                                defaultPosition = j;
                            }
                            j++;
                        }

                    } catch (JSONException e) {
                        CitrusLogging.logError("JSONException", e);
                    }
                }
               *//* data = new String[listData.size()];
                for (int k = 0; k < listData.size(); k++) {
                    data[k] = listData.get(k);
                }*//*
                showPayOptions();
                if (length == 1 && TextUtils.isEmpty(defaultOption)) {
                    setDefaultOption(name);
                } else {
                    if (defaultOption.startsWith("Net Banking")) {
                        setDefaultOption(defaultOption);
                    }
                }
            } finally {
                dbInstance.close();
            }

        }
    }

    private void setDefaultOption(String defaultOption) {
        DBHandlerSDK dbInstance = new DBHandlerSDK(getActivity()
                .getApplicationContext());
        dbInstance.setDefaultOption(defaultOption);
        dbInstance.close();

        SharedPreferences storedValues = getActivity().getApplicationContext()
                .getSharedPreferences(
                        CitrusConstants.STORED_VALUES, 0);
        Editor editor = storedValues.edit();
        editor.putString(CitrusConstants.DEFAULT_OPTION,
                defaultOption);
        editor.apply();

    }

    *//*public static final String getDefaultOption(Context context) {
        SharedPreferences storedValues = context.getSharedPreferences(
                CitrusConstants.STORED_VALUES, 0);
        return storedValues.getString(
                CitrusConstants.DEFAULT_OPTION, "");
    }*//*

    List<OptionDetails> tempList;

    private void showPayOptions() {

        DBHandlerSDK dbhandler = new DBHandlerSDK(getActivity()
                .getApplicationContext());
        this.paymentList = dbhandler.getSavedOptions();
        dbhandler.close();
        CitrusLogging.logDebug("Default Position: " +
                defaultPosition + "List Size: " + paymentList.size());
        tempList = new ArrayList<OptionDetails>();
        try {
            //Only Proceed If options Available
            if (paymentList.size() > 0) {

                for (int i = 0; i < this.paymentList.size(); i++) {
                    if (i == 0) {
                        tempList.add(i, this.paymentList.get(defaultPosition));
                    } else {
                        if (i <= defaultPosition) {
                            tempList.add(i, this.paymentList.get(i - 1));
                        } else {
                            tempList.add(i, this.paymentList.get(i));
                        }
                    }
                }
                if (!TextUtils.isEmpty(defaultOption)) {
                    List<OptionDetails> lv = new ArrayList<OptionDetails>();
                    lv.add(tempList.get(0));
                    adapter = new EfficientAdapter(activity, lv);
                    if (paymentList.size() == 1) {
                        show_moreBtn.setVisibility(View.GONE);
                    } else {
                        show_moreBtn.setVisibility(View.VISIBLE);
                    }
                    //security_text.setVisibility(View.VISIBLE);
                } else {
                    adapter = new EfficientAdapter(getActivity()
                            .getApplicationContext(), tempList);
                    show_moreBtn.setVisibility(View.GONE);
                    //security_text.setVisibility(View.GONE);
                }


                payOptionList.setAdapter(adapter);

                show_moreBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //closeKeyboard(activity, EfficientAdapter.selectedHolder.Edittext.getWindowToken());
                        //closeKeyboard();
                        View vRow = payOptionList.getChildAt(0);
                        InputMethodManager mgr = (InputMethodManager) activity
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(vRow.getWindowToken(), 0);

                        show_moreBtn.setVisibility(View.GONE);
                        //security_text.setVisibility(View.GONE);
                        adapter = new EfficientAdapter(activity, tempList);
                        adapter.notifyDataSetChanged();
                        payOptionList.setAdapter(adapter);
                    }
                });
                setOnClick();
            } else {
                //make layout invisible
                payOptionList.setAdapter(null);
                show_moreBtn.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            CitrusLogging.logError("Exception ", e);
        }
    }

    private void setOnClick() {

        loadMoney_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EfficientAdapter effiecientadapter = ((EfficientAdapter) payOptionList.getAdapter());
                int selectedpos = effiecientadapter.getSelectedPosition();
                int firstPosition = payOptionList.getFirstVisiblePosition() - payOptionList.getHeaderViewsCount(); // This is the same as child #0
                int wantedChild = selectedpos - firstPosition;
                // Say, first visible position is 8, you want position 10, wantedChild will now be 2
                // So that means your view is child #2 in the ViewGroup:
                if (wantedChild < 0 || wantedChild >= payOptionList.getChildCount()) {
                    payOptionList.setSelection(selectedpos);
                    return;
                }

                View view = payOptionList.getChildAt(wantedChild);

                CitrusLogging.logDebug("View :" + view + "Position:" + effiecientadapter.getSelectedPosition());


                EditText cvv = (EditText) view.findViewById(R.id.edittext_qty);
                String selected_cvv = cvv.getText().toString();
                String get_token = tempList.get(selectedpos).getToken();
                String get_type = tempList.get(selectedpos).getType();
                String get_name = tempList.get(selectedpos).getName();
                String scheme = tempList.get(selectedpos).getScheme();


                *//*StringBuilder sb = new StringBuilder();
                sb.append("Cvv:" + selected_cvv
                        + "\nToken " + get_token
                        + "\nType " + get_type
                        + "\nName " + get_name
                        + "\nScheme " + scheme);
                ToastUtils.showShort(getActivity(), "Details:" + sb.toString());*//*

                if (get_type.equalsIgnoreCase("credit") || get_type.equalsIgnoreCase("debit")) {
                    if (!scheme.startsWith("AMEX")) {
                        if (!TextUtils.isEmpty(selected_cvv)) {

                            if (selected_cvv.length() < 3) {
                                ToastUtils.showShort(activity, "Invalid CVV");
                            } else {

                                if (CustomFunctions.isNetAvailable(getActivity())) {
                                    createTransaction(get_token, get_name, get_type,
                                            selected_cvv);
                                } else {
                                    Intent it = new Intent(getActivity(), ConnectionLost.class);
                                    it.putExtra("activity", "PaymetActivity");
                                    startActivity(it);
                                }
                            }
                        } else {
                            ToastUtils.showShort(activity, "Please enter CVV");
                        }
                    } else {

                        if (!TextUtils.isEmpty(selected_cvv)) {
                            if (selected_cvv.length() < 4) {
                                ToastUtils.showShort(activity, "Invalid AMEX CVV");
                            } else {
                                if (CustomFunctions.isNetAvailable(getActivity())) {
                                    createTransaction(get_token, get_name, get_type,
                                            selected_cvv);
                                } else {
                                    Intent it = new Intent(getActivity(), ConnectionLost.class);
                                    it.putExtra("activity", "PaymetActivity");
                                    startActivity(it);
                                }
                            }
                        } else {
                            ToastUtils.showShort(activity, "Please enter CVV");
                        }
                    }
                } else {
                    CitrusLogging.logDebug("token:" + get_token);
                    if (CustomFunctions.isNetAvailable(getActivity())) {
                        createTransaction(get_token, get_name, get_type,
                                selected_cvv);
                    } else {
                        Intent it = new Intent(getActivity(), ConnectionLost.class);
                        it.putExtra("activity", "PaymetActivity");
                        startActivity(it);
                    }
                }

            }
        });


    }

    private void getWallet() {
        if (CustomFunctions.isNetAvailable(getActivity())) {
            new GetCustprofile(getActivity(), listener).execute();
        } else {
            Intent it = new Intent(getActivity(), ConnectionLost.class);
            it.putExtra("activity", "PaymetActivity");
            startActivity(it);
        }

    }

    public void createTransaction(String get_token, String get_name,
                                  String get_type, String selected_cvv) {
        *//* Get a signed order first *//*

        returnFlag = true;
        final String token = get_token;
        final String type = get_type;
        final String cvv = selected_cvv;


        JSONObject txnDetails = new JSONObject();
        try {
            txnDetails.put("amount", amount_load);
            txnDetails.put("currency", "INR");
            txnDetails.put("redirect", CitrusConstants.getRedirect_URL());
        } catch (JSONException e) {
            CitrusLogging.logError("JSONException", e);
        }


        ProgressDialog.getInstance().show_dialogWithCancelListener(activity,
                "Hold on. This won't take long!", new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        returnFlag = false;
                        ProgressDialog.getInstance().dissmiss_dialog();

                    }
                });

        //TODO EVent Load Pessed
        switch (type) {
            case "debit":
                CitrusCashGlobal.getInstance().setPaymentMode(CustomFunctions.PAYMENT_MODE.SAVED_DEBIT);
                CustomFunctions.setEvent(getActivity(), TAG + "Debit click");
                break;
            case "credit":
                CitrusCashGlobal.getInstance().setPaymentMode(CustomFunctions.PAYMENT_MODE.SAVED_CREDIT);
                CustomFunctions.setEvent(getActivity(), TAG + "Credit click");
                break;
            case "netbanking":
                CitrusCashGlobal.getInstance().setPaymentMode(CustomFunctions.PAYMENT_MODE.SAVED_NET);
                CustomFunctions.setEvent(getActivity(), TAG + "Netbanking click");
                break;
        }
        new GetSignedorder(activity, txnDetails, new OnTaskCompleted() {

            public void onTaskExecuted(JSONObject[] signedOrder, String message) {
                if (getActivity() != null && isAdded()) {
                    CitrusLogging.logDebug("WALLET SIGNATURE RESPONSE *** " + Arrays.toString(signedOrder));
                    if (message != null && message.equalsIgnoreCase(CitrusConstants.bad_Request_Message)) {
                        ProgressDialog.getInstance().dissmiss_dialog();
                        ((PaymentActivity) activity).DisplayErrorMessage(CitrusConstants.bad_Request_Message);
                    } else if (TextUtils.isEmpty(message)) {
                        try {
                            String txnId = signedOrder[0]
                                    .getString("merchantTransactionId");
                            String signature = signedOrder[0]
                                    .getString("signature");
                            if (type.equalsIgnoreCase("netbanking")) {
                                fillDetails(txnId, signature, token, "");
                            } else {
                                fillDetails(txnId, signature, token, cvv);

                            }

                        } catch (JSONException e) {
                            CitrusLogging.logDebug("SIGNED ORDER MESSAGE " + e.toString());
                        }
                    }
                }

            }

            @Override
            public void taskCompleted(String errorMessage) {
            }

        }).execute();
    }


    private void fillDetails(String txnId, String signature, String token,
                             String cvv) {
        try {
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
            try {
                userDetails.put("email", contactJSON.getString("email"));
                userDetails.put("firstName",
                        mStoredValues.getString(CitrusConstants.FIRSTNAME, null));
                userDetails.put("lastName",
                        mStoredValues.getString(CitrusConstants.LASTNAME, null));
                userDetails.put("mobileNo",
                        mStoredValues.getString(CitrusConstants.MOBILE, null));
            } catch (JSONException e) {

            } catch (Exception e) {
                CitrusLogging.logError("Exception", e);
            }

            userDetails.put("address", address);

            JSONObject personalDetails = new JSONObject();
            try {

                personalDetails.put("address", "Test Address");
                personalDetails.put("addressCity", "Mumbai");
                personalDetails.put("addressState", "Maharashtra");
                personalDetails.put("addressZip", "400054");
            } catch (JSONException e) {

            }

            JSONObject paymentToken = new JSONObject();
            paymentToken.put("type", "paymentOptionIdToken");
            paymentToken.put("id", token);
            if (!TextUtils.isEmpty(cvv)) {
                paymentToken.put("cvv", cvv);
            }

            paymentObject = new JSONObject();
            paymentObject.put("merchantTxnId", txnId);
            paymentObject.put("paymentToken", paymentToken);
            paymentObject.put("userDetails", userDetails);
            paymentObject.put("amount", amount);
            paymentObject.put("notifyUrl", "");
            paymentObject.put("merchantAccessKey", CitrusConstants.getAccess_Key());
            paymentObject.put("requestSignature", signature);
            paymentObject.put("returnUrl", CitrusConstants.getRedirect_URL_COMPLETE());

            CitrusLogging.logDebug("PAYMENT OBJECT ****  " + paymentObject.toString());

            if (returnFlag) {
                CitrusLogging.logDebug("NOT CAUGHT");
                initiateTxn();
            } else {
                CitrusLogging.logDebug("CAUGHT");
            }

        } catch (JSONException e) {
            CitrusLogging.logError("JSONException", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        storePayOptions(null);
        if (custProfile != null && custProfile.getStatus() == Status.RUNNING)
            custProfile.cancel(true);
        *//*if (payObj != null && payObj.getStatus() == Status.RUNNING)
            payObj.cancel(true);*//*

        if (getSignedOrder != null
                && getSignedOrder.getStatus() == Status.RUNNING)
            getSignedOrder.cancel(true);

    }

    @Override
    public void onPause() {
        super.onPause();

        getAccFlag = false;

    }

    @Override
    public void onStop() {
        super.onStop();

        ProgressDialog.getInstance().dissmiss_dialog();
        flag = false;
        getAccFlag = true;
    }

    private void initiateTxn() {
        ProgressDialog.getInstance().dissmiss_dialog();
        taskExecuted = new com.citruspaykit.mobile.payment.OnTaskCompleted() {
            public void onTaskExecuted(JSONObject[] paymentObject,
                                       String message) {
                if (getActivity() != null && isAdded()) {
                    if (TextUtils.isEmpty(message)) {
                        try {
                            String url = paymentObject[0].getString("redirectUrl");
                            CitrusLogging.logDebug("REIRECT URL ****  " + paymentObject[0].getString("redirectUrl"));
                            if (url.startsWith("https")) {
                                Intent intent = new Intent(activity,
                                        Web3DSecureActivity.class);
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
                                ToastUtils.showShort(
                                        getActivity(),
                                        "Payment option is currently not available");
                            }
                        } catch (JSONException e) {
                            CitrusLogging.logError("JSONException", e);
                        }
                    } else {
                        try {
                            paymentObject[0].put("redirectUrl", "");
                            ToastUtils.showShort(getActivity(),
                                    "Payment option is currently not available");
                        } catch (JSONException e) {
                            CitrusLogging.logError("JSONException", e);
                        }
                    }
                }
            }
        };
        new Pay(activity, paymentObject, taskExecuted).execute();
    }


    public class EfficientAdapter extends BaseAdapter {


        int pre_pos = 0;
        SavedOptionsFragment wallet = new SavedOptionsFragment();
        private LayoutInflater mInflater;
        private Context context;
        List<OptionDetails> title;
        ViewHolder holder;
        final int x = R.color.pressed_color;
        final int y = R.color.white;
        String scheme;

        public EfficientAdapter(Context context, List<OptionDetails> title) {
            this.title = title;
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            View row = convertView;

            if (row == null) {

                row = mInflater.inflate(
                        R.layout.saved_options_adapter_content, parent, false);
                holder = new ViewHolder();
                holder.textLine = (TextView) row.findViewById(R.id.textLine);
                holder.edittext = (EditText) row.findViewById(R.id.edittext_qty);
                holder.rdb = (RadioButton) row.findViewById(R.id.rb_Choice);
                holder.card_img = (ImageView) row.findViewById(R.id.card_img);
                holder.btn_load = (TextView) row.findViewById(R.id.load_btn);
                holder.rl = (RelativeLayout) row.findViewById(R.id.lineItem);


                scheme = title.get(position).getScheme();

                if (scheme.startsWith("AMEX")) {
                    holder.card_img.setBackgroundResource(R.drawable.amex_small);
                } else if (scheme.startsWith("MCRD")) {
                    holder.card_img.setBackgroundResource(R.drawable.master_small);
                } else if (scheme.startsWith("VISA")) {
                    holder.card_img.setBackgroundResource(R.drawable.visa_small);
                }


                String rowTxt = title.get(position).getName();
                if (rowTxt.startsWith("Net")) {
                    String split[] = rowTxt.split("Banking");
                    rowTxt = split[1].replace("-", "");
                    rowTxt = rowTxt.trim();
                } else {
                    rowTxt = title.get(position).getNumber();
                    if (!scheme.startsWith("AMEX")) {
                        int maxLength = 3;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        holder.edittext.setFilters(fArray);
                        //XXXX-XXXX-XXXX-5208
                        rowTxt = rowTxt.replaceAll("(.{4})", "$1-");
                        rowTxt = rowTxt.substring(0, rowTxt.length() - 1);

                    } else {
                        int maxLength = 4;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        holder.edittext.setFilters(fArray);
                        //XXXX-XXXX-XXX5-208
                        if (rowTxt.length() == 16)
                            rowTxt = rowTxt.substring(1, rowTxt.length());
                        rowTxt = rowTxt.replaceAll("(.{4})", "$1-");

                    }
                }

                holder.textLine.setText(rowTxt);

                String localCardValue = holder.textLine.getText().toString();

                if (position == 0) {
                    if (localCardValue.startsWith("XXXX")) {
                        holder.edittext.setVisibility(View.VISIBLE);
                        holder.btn_load.setVisibility(View.VISIBLE);

                    } else {
                        holder.edittext.setVisibility(View.INVISIBLE);
                        holder.btn_load.setVisibility(View.INVISIBLE);
                    }
                }
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            if (holder != null) {
                if (position == pre_pos) {
                    holder.rdb.setChecked(true);
                    holder.rl.setBackgroundResource(x);
                    holder.btn_load.setBackgroundResource(x);
                    String temp = holder.textLine.getText().toString();


                    if (temp.startsWith("XXXX")) {
                        holder.edittext.setVisibility(View.VISIBLE);
                        holder.btn_load.setVisibility(View.VISIBLE);
                    } else {
                        holder.edittext.setVisibility(View.INVISIBLE);
                        holder.btn_load.setVisibility(View.INVISIBLE);
                    }

                    if (firstFragment) {
                        holder.edittext.requestFocus();
                        holder.edittext.setCursorVisible(true);
                        holder.edittext.setFocusable(true);
                    }

                } else {
                    holder.edittext.setVisibility(View.INVISIBLE);
                    holder.btn_load.setVisibility(View.INVISIBLE);

                    holder.rdb.setChecked(false);
                    holder.rl.setBackgroundResource(y);
                    holder.btn_load.setBackgroundResource(y);

                }

                row.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        ViewHolder holder2 = ((ViewHolder) v.getTag());
                        holder2.edittext.setText("");
                        pre_pos = position;
                        String temp = holder2.textLine.getText().toString();
                        if (temp.startsWith("XXXX")) {
                            holder2.edittext.setVisibility(View.VISIBLE);
                            holder2.edittext.requestFocus();
                            holder2.edittext.setCursorVisible(true);
                            holder2.edittext.setFocusable(true);
                            holder2.btn_load.setVisibility(View.VISIBLE);

                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(holder2.edittext, InputMethodManager.SHOW_IMPLICIT);
                        } else {
                            holder2.edittext.setVisibility(View.INVISIBLE);
                        }
                        notifyDataSetChanged();
                    }
                });
            }


            return row;
        }

        public int getSelectedPosition() {
            return pre_pos;
        }

        private class ViewHolder {
            TextView textLine;
            RelativeLayout rl;
            EditText edittext;
            RadioButton rdb;
            ImageView card_img;
            TextView btn_load;
        }

        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public Object getItem(int position) {
            return title.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
*/
}
