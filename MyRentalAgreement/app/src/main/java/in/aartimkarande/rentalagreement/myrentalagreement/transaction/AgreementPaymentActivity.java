package in.aartimkarande.rentalagreement.myrentalagreement.transaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.other.TokenIDGenerator;
import in.aartimkarande.rentalagreement.myrentalagreement.property.PropertyActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.status_activity.SixActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.OwnerActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_tenant.TenantActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness1.Witness1Activity;

public class AgreementPaymentActivity extends AppCompatActivity {

    //Components
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutCashAmt;
    private TextInputLayout textInputLayoutBnkName;
    private TextInputLayout textInputLayoutBranchName;
    private TextInputLayout textInputLayoutChqDate;
    private TextInputLayout textInputLayoutChqNo;
    private TextInputLayout textInputLayoutChqAmt;
    private TextInputLayout textInputLayoutAccNo;

    private TextInputEditText inputEditTextCashAmt;
    private TextInputEditText inputEditTextBnkName;
    private TextInputEditText inputEditTextBranchName;
    private TextInputEditText inputEditTextChqDate;
    private TextInputEditText inputEditTextChqNo;
    private TextInputEditText inputEditTextChqAmt;
    private TextInputEditText inputEditTextAccNo;


    private RadioGroup radioGroupPaymentMode;
    private RadioButton radioButtonCash;
    private RadioButton radioButtonCheque;
    private RadioButton radioButtonNetBanking;

    private RadioGroup radioGroupTransMode;
    private RadioButton radioButtonTransNetBanking;
    private RadioButton radioButtonTransCredit;
    private RadioButton radioButtonTransDebit;


    private TextInputLayout textInputLayoutTransBnkName;
    private TextInputLayout textInputLayoutTransBranchName;
    private TextInputLayout textInputLayoutTransAccNo;
    private TextInputLayout textInputLayoutTransDate;
    private TextInputLayout textInputLayoutTransID;
    private TextInputLayout textInputLayoutTransAmt;

    private TextInputEditText inputEditTextTransBnkName;
    private TextInputEditText inputEditTextTransBranchName;
    private TextInputEditText inputEditTextTransAccNo;
    private TextInputEditText inputEditTextTransDate;
    private TextInputEditText inputEditTextTransID;
    private TextInputEditText inputEditTextTransAmt;

    private TextView textViewOnlineTrans;

    private Spinner spinner;

    private Button btnSubmit;
    private Button btnBack;

    private AlertDialog alertDialog;

    //Primitive Datatypes and Wrapper Classes
    private String transPayMode;
    private String transPayCashAmt;
    private String transPayBnkName;
    private String transPayBranchName;
    private String transPayChqDate;
    private String transPayChqNo;
    private String transPayChqAmt;
    private String transPayChqAccNo;
    private String onlineTransMode;
    private String onlineTransBnkName;
    private String onlineTransBranchName;
    private String onlineTransAccNo;
    private String onlineTransDate;
    private String onlineTransID;
    private String onlineTransAmt;
    private String transPayOpt;

    private String rentTransID;

    private String propertyID;

    private int mYear;
    private int mMonth;
    private int mDay;

    private boolean validate = false;


    private String userID;
    private String serviceType;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String tokenID;
    private String agreementID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_payment);


        //Initializing Components
        initializeComponents();
        setDefaultStrValues();

        //Methods
        getPropertyID();

        //Method
        disableSomeComponents();
        setErrorEnable();
        loadSpinner();


        //Action Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transPayOpt = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rationButtonActionListener();

        inputEditTextChqDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AgreementPaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                inputEditTextChqDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.show();
            }
        });

        inputEditTextTransDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();


                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AgreementPaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                inputEditTextTransDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.show();

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transPayMode = ((RadioButton)findViewById(radioGroupPaymentMode.getCheckedRadioButtonId())).getText().toString();
                if (TextUtils.isEmpty(transPayMode)){
                    radioButtonCash.setError("Select Mode of Payment Transaction");
                    radioButtonCash.setHintTextColor(getResources().getColor(R.color.red));
                    radioButtonCash.requestFocus();

                    validate = false;

                    return;
                }
                else{
                    radioButtonCash.setError(null);
                    validate = true;
                }

                if(transPayMode.equalsIgnoreCase("Cash")){

                    transPayCashAmt = inputEditTextCashAmt.getText().toString();
                    if(TextUtils.isEmpty(transPayCashAmt)){
                        textInputLayoutCashAmt.setError("Enter Cash Amount");
                        textInputLayoutCashAmt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutCashAmt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutCashAmt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutCashAmt.setError(null);
                        validate = true;
                    }

                }else if(transPayMode.equalsIgnoreCase("Cheque")){

                    transPayBnkName = inputEditTextBnkName.getText().toString();
                    if(TextUtils.isEmpty(transPayBnkName)){
                        textInputLayoutBnkName.setError("Enter Bank Name");
                        textInputLayoutBnkName.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutBnkName.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutBnkName.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutBnkName.setError(null);
                        validate = true;
                    }


                    transPayBranchName = inputEditTextBranchName.getText().toString();
                    if(TextUtils.isEmpty(transPayBranchName)){
                        textInputLayoutBranchName.setError("Enter Branch Name");
                        textInputLayoutBranchName.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutBranchName.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutBranchName.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutBranchName.setError(null);
                        validate = true;
                    }

                    transPayChqDate = inputEditTextChqDate.getText().toString();
                    if(TextUtils.isEmpty(transPayChqDate)){
                        textInputLayoutChqDate.setError("Enter Cheque Date");
                        textInputLayoutChqDate.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutChqDate.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutChqDate.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutChqDate.setError(null);
                        validate = true;
                    }


                    transPayChqAmt = inputEditTextChqAmt.getText().toString();
                    if(TextUtils.isEmpty(transPayChqAmt)){
                        textInputLayoutChqAmt.setError("Enter Cheque Amount");
                        textInputLayoutChqAmt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutChqAmt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutChqAmt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutChqAmt.setError(null);
                        validate = true;
                    }

                    transPayChqNo = inputEditTextChqNo.getText().toString();
                    if(TextUtils.isEmpty(transPayChqNo)){
                        textInputLayoutChqNo.setError("Enter Cheque Number");
                        textInputLayoutChqNo.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutChqNo.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutChqNo.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutChqNo.setError(null);
                        validate = true;
                    }


                    transPayChqAccNo = inputEditTextAccNo.getText().toString();
                    if(TextUtils.isEmpty(transPayChqAccNo)){
                        textInputLayoutAccNo.setError("Enter Account Number");
                        textInputLayoutAccNo.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutAccNo.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutAccNo.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutCashAmt.setError(null);
                        validate = true;
                    }


                }else if(transPayMode.equalsIgnoreCase("Net Banking")){

                    onlineTransMode = ((RadioButton)findViewById(radioGroupTransMode.getCheckedRadioButtonId())).getText().toString();
                    if (TextUtils.isEmpty(onlineTransMode)){
                        radioButtonTransNetBanking.setError("Select Online Transaction");
                        radioButtonTransNetBanking.setHintTextColor(getResources().getColor(R.color.red));
                        radioButtonTransNetBanking.requestFocus();

                        validate = false;

                        return;
                    }
                    else{
                        radioButtonTransNetBanking.setError(null);
                        validate = true;
                    }

                    onlineTransBnkName = inputEditTextTransBnkName.getText().toString();
                    if(TextUtils.isEmpty(onlineTransBnkName)){
                        textInputLayoutTransBnkName.setError("Enter Bank Name");
                        textInputLayoutTransBnkName.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutTransBnkName.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutTransBnkName.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutTransBnkName.setError(null);
                        validate = true;
                    }


                    onlineTransBranchName = inputEditTextTransBranchName.getText().toString();
                    if(TextUtils.isEmpty(onlineTransBranchName)){
                        textInputLayoutTransBranchName.setError("Enter Branch Name");
                        textInputLayoutTransBranchName.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutTransBranchName.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutTransBranchName.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutTransBranchName.setError(null);
                        validate = true;
                    }

                    onlineTransAccNo = inputEditTextTransAccNo.getText().toString();
                    if(TextUtils.isEmpty(onlineTransAccNo)){
                        textInputLayoutTransAccNo.setError("Enter Account Number");
                        textInputLayoutTransAccNo.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutTransAccNo.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutTransAccNo.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutTransAccNo.setError(null);
                        validate = true;
                    }

                    onlineTransDate = inputEditTextTransDate.getText().toString();
                    if(TextUtils.isEmpty(onlineTransDate)){
                        textInputLayoutTransDate.setError("Enter Transaction Date");
                        textInputLayoutTransDate.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutTransDate.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutTransDate.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutTransDate.setError(null);
                        validate = true;
                    }


                    onlineTransID = inputEditTextTransID.getText().toString();
                    if(TextUtils.isEmpty(onlineTransID)){
                        textInputLayoutTransID.setError("Enter Transaction ID");
                        textInputLayoutTransID.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutTransID.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutTransID.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutTransID.setError(null);
                        validate = true;
                    }

                    onlineTransAmt = inputEditTextTransAmt.getText().toString();
                    if(TextUtils.isEmpty(onlineTransAmt)){
                        textInputLayoutTransAmt.setError("Enter Transaction Payment Amount");
                        textInputLayoutTransAmt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutTransAmt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutTransAmt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutTransAmt.setError(null);
                        validate = true;
                    }

                }

                if (validate) sendData();

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationMsg();
            }
        });

    }



    private void showConfirmationMsg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AgreementPaymentActivity.this);
        builder.setTitle(R.string.confirm_exit);
        builder.setMessage(R.string.confirm_msg);
        builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startActivity(new Intent(AgreementPaymentActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
            }
        });
        builder.setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }




    private void sendData() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.RENT_TRANS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            //Start Welcome Activity (Owner Activity)
                            Snackbar snackbar = Snackbar.make(scrollView, "Property Agreement Payment Details Created Successfully. ID : " + response, Snackbar.LENGTH_LONG);


                            //Creating a shared preference
                            SharedPreferences sharedPreferences = AgreementPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to the editor
                            editor.putString(IDTracker.RENT_TRANS_ID, response);

                            //Saving values to the editor
                            editor.commit();



                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();

                            /*

                                CODE FOR TAKEN GENERATION AND ALL ID'S IN AGREE_MAST TABLE

                            */
                            saveAllIDs();


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    Intent tenantIntent = new Intent(AgreementPaymentActivity.this, SixActivity.class);
                                    startActivity(tenantIntent);

                                }
                            }, 3000);



                        } else {

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(scrollView, "Invalid Credential : " + response, Snackbar.LENGTH_LONG);

                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.red));
                            snackbar.show();

                        }


                        clearData();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Snackbar snackbar = Snackbar.make(scrollView, "Error : " + error.getMessage(), Snackbar.LENGTH_LONG);

                        //Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.red));
                        snackbar.show();

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //Adding Parameter to the request
                params.put(Keys.PROP_ID, propertyID);

                params.put(Keys.TRANS_MODE, transPayMode);
                params.put(Keys.TRANS_CASH_AMT, transPayCashAmt);
                params.put(Keys.TRANS_CHK_BNK_NAME, transPayBnkName);
                params.put(Keys.TRANS_CHK_BRANCH_NAME, transPayBranchName);
                params.put(Keys.TRANS_CHK_DT, transPayChqDate);
                params.put(Keys.TRANS_CHK_NO, transPayChqNo);
                params.put(Keys.TRANS_CHK_AMT, transPayChqAmt);
                params.put(Keys.TRANS_CHK_ACC_NO, transPayChqAccNo);
                params.put(Keys.TRANS_MODE_TYPE, onlineTransMode);
                params.put(Keys.TRANS_BNK_NAME, onlineTransBnkName);
                params.put(Keys.TRANS_BRANCH_NAME, onlineTransBranchName);
                params.put(Keys.TRANS_ACC_NO, onlineTransAccNo);
                params.put(Keys.TRANS_DT, onlineTransDate);
                params.put(Keys.TRANS_REF_ID, onlineTransID);
                params.put(Keys.TRANS_PAY_AMT, onlineTransAmt);
                params.put(Keys.TRANS_PAY_OPT, transPayOpt);

                params.put(Keys.STAT, FinalValues.STAT);


                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(AgreementPaymentActivity.this);
        requestQueue.add(request);


    }

    private void saveAllIDs() {

        SharedPreferences sharedPreferences = AgreementPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);
        tenantID = sharedPreferences.getString(IDTracker.TENT_ID,null);
        witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);
        rentID = sharedPreferences.getString(IDTracker.RENT_ID,null);
        rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null);

        tokenID = new TokenIDGenerator().getTokenID();


        sendAllIDs();
        
    }

    private void sendAllIDs() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.AGM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            //Start Welcome Activity (Owner Activity)
                            Snackbar snackbar = Snackbar.make(scrollView, "Agreement No, Agreement Type & Token Id has been created Successfully. ID : " + response, Snackbar.LENGTH_LONG);


                            //Creating a shared preference
                            SharedPreferences sharedPreferences = AgreementPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to the editor
                            editor.putString(IDTracker.AGREEMENT_ID, response);
                            editor.putString(IDTracker.TOKEN_ID, tokenID);

                            //Saving values to the editor
                            editor.commit();



                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();




                        } else {

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(scrollView, "Something Went Wrong : " + response, Snackbar.LENGTH_LONG);

                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.red));
                            snackbar.show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Snackbar snackbar = Snackbar.make(scrollView, "Error : " + error.getMessage(), Snackbar.LENGTH_LONG);

                        //Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.red));
                        snackbar.show();

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //Adding Parameter to the request
                params.put(Keys.AGM_USER_ID, userID);
                params.put(Keys.AGM_SERVICE_TYPE, serviceType);
                params.put(Keys.AGM_PROP_ID, propertyID);
                params.put(Keys.AGM_PROP_PAY_ID, rentID);
                params.put(Keys.AGM_RENT_TRANS_ID, rentTransID);
                params.put(Keys.AGM_TOKEN_ID, tokenID);

                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(AgreementPaymentActivity.this);
        requestQueue.add(request);

    }

    private void clearData() {

        inputEditTextCashAmt.setText(FinalValues.EMPTY_STR);
        inputEditTextBnkName.setText(FinalValues.EMPTY_STR);
        inputEditTextBranchName.setText(FinalValues.EMPTY_STR);
        inputEditTextChqDate.setText(FinalValues.EMPTY_STR);
        inputEditTextChqNo.setText(FinalValues.EMPTY_STR);
        inputEditTextChqAmt.setText(FinalValues.EMPTY_STR);
        inputEditTextAccNo.setText(FinalValues.EMPTY_STR);

        inputEditTextTransBnkName.setText(FinalValues.EMPTY_STR);
        inputEditTextTransBranchName.setText(FinalValues.EMPTY_STR);
        inputEditTextTransAccNo.setText(FinalValues.EMPTY_STR);
        inputEditTextTransDate.setText(FinalValues.EMPTY_STR);
        inputEditTextTransID.setText(FinalValues.EMPTY_STR);
        inputEditTextTransAmt.setText(FinalValues.EMPTY_STR);

    }

    private void rationButtonActionListener() {

        radioGroupPaymentMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View view = radioGroupPaymentMode.findViewById(checkedId);
                int index = radioGroupPaymentMode.indexOfChild(view);

                switch (index){

                    case 0:
                        textInputLayoutCashAmt.setVisibility(View.VISIBLE);
                        textInputLayoutBnkName.setVisibility(View.GONE);
                        textInputLayoutBranchName.setVisibility(View.GONE);
                        textInputLayoutChqDate.setVisibility(View.GONE);
                        textInputLayoutChqNo.setVisibility(View.GONE);
                        textInputLayoutChqAmt.setVisibility(View.GONE);
                        textInputLayoutAccNo.setVisibility(View.GONE);

                        inputEditTextCashAmt.setVisibility(View.VISIBLE);
                        inputEditTextBnkName.setVisibility(View.GONE);
                        inputEditTextBranchName.setVisibility(View.GONE);
                        inputEditTextChqDate.setVisibility(View.GONE);
                        inputEditTextChqNo.setVisibility(View.GONE);
                        inputEditTextChqAmt.setVisibility(View.GONE);
                        inputEditTextAccNo.setVisibility(View.GONE);

                        textViewOnlineTrans.setVisibility(View.GONE);

                        radioGroupTransMode.setVisibility(View.GONE);
                        radioButtonTransNetBanking.setVisibility(View.GONE);
                        radioButtonTransCredit.setVisibility(View.GONE);
                        radioButtonTransDebit.setVisibility(View.GONE);


                        textInputLayoutTransBnkName.setVisibility(View.GONE);
                        textInputLayoutTransBranchName.setVisibility(View.GONE);
                        textInputLayoutTransAccNo.setVisibility(View.GONE);
                        textInputLayoutTransDate.setVisibility(View.GONE);
                        textInputLayoutTransID.setVisibility(View.GONE);
                        textInputLayoutTransAmt.setVisibility(View.GONE);

                        inputEditTextTransBnkName.setVisibility(View.GONE);
                        inputEditTextTransBranchName.setVisibility(View.GONE);
                        inputEditTextTransAccNo.setVisibility(View.GONE);
                        inputEditTextTransDate.setVisibility(View.GONE);
                        inputEditTextTransID.setVisibility(View.GONE);
                        inputEditTextTransAmt.setVisibility(View.GONE);

                        break;

                    case 1:

                        textInputLayoutCashAmt.setVisibility(View.GONE);
                        textInputLayoutBnkName.setVisibility(View.VISIBLE);
                        textInputLayoutBranchName.setVisibility(View.VISIBLE);
                        textInputLayoutChqDate.setVisibility(View.VISIBLE);
                        textInputLayoutChqNo.setVisibility(View.VISIBLE);
                        textInputLayoutChqAmt.setVisibility(View.VISIBLE);
                        textInputLayoutAccNo.setVisibility(View.VISIBLE);

                        inputEditTextCashAmt.setVisibility(View.GONE);
                        inputEditTextBnkName.setVisibility(View.VISIBLE);
                        inputEditTextBranchName.setVisibility(View.VISIBLE);
                        inputEditTextChqDate.setVisibility(View.VISIBLE);
                        inputEditTextChqNo.setVisibility(View.VISIBLE);
                        inputEditTextChqAmt.setVisibility(View.VISIBLE);
                        inputEditTextAccNo.setVisibility(View.VISIBLE);

                        textViewOnlineTrans.setVisibility(View.GONE);

                        radioGroupTransMode.setVisibility(View.GONE);
                        radioButtonTransNetBanking.setVisibility(View.GONE);
                        radioButtonTransCredit.setVisibility(View.GONE);
                        radioButtonTransDebit.setVisibility(View.GONE);


                        textInputLayoutTransBnkName.setVisibility(View.GONE);
                        textInputLayoutTransBranchName.setVisibility(View.GONE);
                        textInputLayoutTransAccNo.setVisibility(View.GONE);
                        textInputLayoutTransDate.setVisibility(View.GONE);
                        textInputLayoutTransID.setVisibility(View.GONE);
                        textInputLayoutTransAmt.setVisibility(View.GONE);

                        inputEditTextTransBnkName.setVisibility(View.GONE);
                        inputEditTextTransBranchName.setVisibility(View.GONE);
                        inputEditTextTransAccNo.setVisibility(View.GONE);
                        inputEditTextTransDate.setVisibility(View.GONE);
                        inputEditTextTransID.setVisibility(View.GONE);
                        inputEditTextTransAmt.setVisibility(View.GONE);


                        break;

                    case 2:

                        textInputLayoutCashAmt.setVisibility(View.GONE);
                        textInputLayoutBnkName.setVisibility(View.GONE);
                        textInputLayoutBranchName.setVisibility(View.GONE);
                        textInputLayoutChqDate.setVisibility(View.GONE);
                        textInputLayoutChqNo.setVisibility(View.GONE);
                        textInputLayoutChqAmt.setVisibility(View.GONE);
                        textInputLayoutAccNo.setVisibility(View.GONE);

                        inputEditTextCashAmt.setVisibility(View.GONE);
                        inputEditTextBnkName.setVisibility(View.GONE);
                        inputEditTextBranchName.setVisibility(View.GONE);
                        inputEditTextChqDate.setVisibility(View.GONE);
                        inputEditTextChqNo.setVisibility(View.GONE);
                        inputEditTextChqAmt.setVisibility(View.GONE);
                        inputEditTextAccNo.setVisibility(View.GONE);

                        textViewOnlineTrans.setVisibility(View.VISIBLE);

                        radioGroupTransMode.setVisibility(View.VISIBLE);
                        radioButtonTransNetBanking.setVisibility(View.VISIBLE);
                        radioButtonTransCredit.setVisibility(View.VISIBLE);
                        radioButtonTransDebit.setVisibility(View.VISIBLE);


                        textInputLayoutTransBnkName.setVisibility(View.VISIBLE);
                        textInputLayoutTransBranchName.setVisibility(View.VISIBLE);
                        textInputLayoutTransAccNo.setVisibility(View.VISIBLE);
                        textInputLayoutTransDate.setVisibility(View.VISIBLE);
                        textInputLayoutTransID.setVisibility(View.VISIBLE);
                        textInputLayoutTransAmt.setVisibility(View.VISIBLE);

                        inputEditTextTransBnkName.setVisibility(View.VISIBLE);
                        inputEditTextTransBranchName.setVisibility(View.VISIBLE);
                        inputEditTextTransAccNo.setVisibility(View.VISIBLE);
                        inputEditTextTransDate.setVisibility(View.VISIBLE);
                        inputEditTextTransID.setVisibility(View.VISIBLE);
                        inputEditTextTransAmt.setVisibility(View.VISIBLE);

                        break;
                }

            }
        });

    }

    private void loadSpinner() {
        List<String> paymentOptLists = new ArrayList<String>();
        String[] lists = getResources().getStringArray(R.array.rent_trans);

        paymentOptLists.addAll(Arrays.asList(lists));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private void setErrorEnable() {
        textInputLayoutCashAmt.setErrorEnabled(true);
        textInputLayoutBnkName.setErrorEnabled(true);
        textInputLayoutBranchName.setErrorEnabled(true);
        textInputLayoutChqDate.setErrorEnabled(true);
        textInputLayoutChqNo.setErrorEnabled(true);
        textInputLayoutChqAmt.setErrorEnabled(true);
        textInputLayoutAccNo.setErrorEnabled(true);

        textInputLayoutTransBnkName.setErrorEnabled(true);
        textInputLayoutTransBranchName.setErrorEnabled(true);
        textInputLayoutTransAccNo.setErrorEnabled(true);
        textInputLayoutTransDate.setErrorEnabled(true);
        textInputLayoutTransID.setErrorEnabled(true);
        textInputLayoutTransAmt.setErrorEnabled(true);

    }

    private void disableSomeComponents() {

        /*textInputLayoutCashAmt.setVisibility(View.GONE);*/
        textInputLayoutBnkName.setVisibility(View.GONE);
        textInputLayoutBranchName.setVisibility(View.GONE);
        textInputLayoutChqDate.setVisibility(View.GONE);
        textInputLayoutChqNo.setVisibility(View.GONE);
        textInputLayoutChqAmt.setVisibility(View.GONE);
        textInputLayoutAccNo.setVisibility(View.GONE);

        /*inputEditTextCashAmt.setVisibility(View.GONE);*/
        inputEditTextBnkName.setVisibility(View.GONE);
        inputEditTextBranchName.setVisibility(View.GONE);
        inputEditTextChqDate.setVisibility(View.GONE);
        inputEditTextChqNo.setVisibility(View.GONE);
        inputEditTextChqAmt.setVisibility(View.GONE);
        inputEditTextAccNo.setVisibility(View.GONE);

        textViewOnlineTrans.setVisibility(View.GONE);

        radioGroupTransMode.setVisibility(View.GONE);
        radioButtonTransNetBanking.setVisibility(View.GONE);
        radioButtonTransCredit.setVisibility(View.GONE);
        radioButtonTransDebit.setVisibility(View.GONE);


        textInputLayoutTransBnkName.setVisibility(View.GONE);
        textInputLayoutTransBranchName.setVisibility(View.GONE);
        textInputLayoutTransAccNo.setVisibility(View.GONE);
        textInputLayoutTransDate.setVisibility(View.GONE);
        textInputLayoutTransID.setVisibility(View.GONE);
        textInputLayoutTransAmt.setVisibility(View.GONE);

        inputEditTextTransBnkName.setVisibility(View.GONE);
        inputEditTextTransBranchName.setVisibility(View.GONE);
        inputEditTextTransAccNo.setVisibility(View.GONE);
        inputEditTextTransDate.setVisibility(View.GONE);
        inputEditTextTransID.setVisibility(View.GONE);
        inputEditTextTransAmt.setVisibility(View.GONE);

    }

    private void getPropertyID() {
        SharedPreferences sharedPreferences = AgreementPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
    }

    private void initializeComponents() {
        scrollView = findViewById(R.id.scrollview_layout);

        textInputLayoutCashAmt = findViewById(R.id.txt_ip_cash_amt);
        textInputLayoutBnkName = findViewById(R.id.txt_ip_bank_name);
        textInputLayoutBranchName = findViewById(R.id.txt_ip_bnk_Branch_name);
        textInputLayoutChqDate = findViewById(R.id.txt_ip_chk_date);
        textInputLayoutChqNo = findViewById(R.id.txt_ip_chq_no);
        textInputLayoutChqAmt = findViewById(R.id.txt_ip_chq_amt);
        textInputLayoutAccNo = findViewById(R.id.txt_ip_acc_no);

        inputEditTextCashAmt = findViewById(R.id.edt_cash_amt);
        inputEditTextBnkName = findViewById(R.id.edt_bnk_name);
        inputEditTextBranchName = findViewById(R.id.edt_branch_name);
        inputEditTextChqDate = findViewById(R.id.edt_chq_date);
        inputEditTextChqNo = findViewById(R.id.edt_chq_no);
        inputEditTextChqAmt = findViewById(R.id.edt_chq_amt);
        inputEditTextAccNo = findViewById(R.id.edt_acc_no);


        radioGroupPaymentMode = findViewById(R.id.rgPaymentModeTrans);
        radioButtonCash = findViewById(R.id.rbCash);
        radioButtonCheque = findViewById(R.id.rbChq);
        radioButtonNetBanking = findViewById(R.id.rbNetBank);

        radioGroupTransMode = findViewById(R.id.transMode);
        radioButtonTransNetBanking = findViewById(R.id.netBank);
        radioButtonTransCredit = findViewById(R.id.credit);
        radioButtonTransDebit = findViewById(R.id.debit);


        textInputLayoutTransBnkName = findViewById(R.id.txt_ip_trans_bank_name);
        textInputLayoutTransBranchName = findViewById(R.id.txt_ip_trans_branch_name);
        textInputLayoutTransAccNo = findViewById(R.id.txt_ip_trans_acc_no);
        textInputLayoutTransDate = findViewById(R.id.txt_ip_trans_date);
        textInputLayoutTransID = findViewById(R.id.txt_ip_trans_id);
        textInputLayoutTransAmt = findViewById(R.id.txt_ip_trans_amt);

        inputEditTextTransBnkName = findViewById(R.id.edt_trans_bnk_name);
        inputEditTextTransBranchName = findViewById(R.id.edt_trans_branch_name);
        inputEditTextTransAccNo = findViewById(R.id.edt_trans_acc_no);
        inputEditTextTransDate = findViewById(R.id.edt_trans_date);
        inputEditTextTransID = findViewById(R.id.edt_trans_id);
        inputEditTextTransAmt = findViewById(R.id.edt_trans_amt);

        textViewOnlineTrans = findViewById(R.id.online_trans);

        spinner = findViewById(R.id.spinner_payment_opt);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setDefaultStrValues() {
        propertyID = FinalValues.EMPTY_STR;
        transPayMode = FinalValues.EMPTY_STR;
        transPayCashAmt = FinalValues.EMPTY_STR;
        transPayBnkName = FinalValues.EMPTY_STR;
        transPayBranchName = FinalValues.EMPTY_STR;
        transPayChqDate = FinalValues.EMPTY_STR;
        transPayChqNo = FinalValues.EMPTY_STR;
        transPayChqAmt = FinalValues.EMPTY_STR;
        transPayChqAccNo = FinalValues.EMPTY_STR;
        onlineTransMode = FinalValues.EMPTY_STR;
        onlineTransBnkName = FinalValues.EMPTY_STR;
        onlineTransBranchName = FinalValues.EMPTY_STR;
        onlineTransAccNo = FinalValues.EMPTY_STR;
        onlineTransDate = FinalValues.EMPTY_STR;
        onlineTransID = FinalValues.EMPTY_STR;
        onlineTransAmt = FinalValues.EMPTY_STR;
        transPayOpt = FinalValues.EMPTY_STR;
    }

    @Override
    protected void onResume() {
        super.onResume();

/*
        //Start Welcome Activity (Owner Activity)
        //Snackbar snackbar = Snackbar.make(scrollView, "Property Rent Payment Details Created Successfully. ID : " + response, Snackbar.LENGTH_LONG);

        //Creating a shared preference
        SharedPreferences sharedPreferences = AgreementPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Creating editor to store
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Adding values to the editor
        editor.putString(IDTracker.RENT_ID, null);

        //Saving values to the editor
        editor.commit();
*/

        SharedPreferences sharedPreferences = AgreementPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);
        tenantID = sharedPreferences.getString(IDTracker.TENT_ID,null);
        witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);
        rentID = sharedPreferences.getString(IDTracker.RENT_ID,null);
        rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null);
        tokenID = sharedPreferences.getString(IDTracker.TOKEN_ID,null);

        if(propertyID == null){
            startActivity(new Intent(AgreementPaymentActivity.this, PropertyActivity.class));
        }

        if(ownerID == null){
            startActivity(new Intent(AgreementPaymentActivity.this, OwnerActivity.class));
        }

        if(tenantID == null){
            startActivity(new Intent(AgreementPaymentActivity.this, TenantActivity.class));
        }

        if (witnessID == null){
            startActivity(new Intent(AgreementPaymentActivity.this, Witness1Activity.class));
        }

        if(rentID == null){
            startActivity(new Intent(AgreementPaymentActivity.this, RentPaymentActivity.class));
        }

        if(rentTransID != null) {
            Toast.makeText(getApplicationContext(),"onResume() called.\nRent Trans ID : " + rentTransID,Toast.LENGTH_SHORT).show();

            if(tokenID != null){
                startActivity(new Intent(AgreementPaymentActivity.this, ViewAgreementPaymentActivity.class));
            }else{

                AlertDialog.Builder builder = new AlertDialog.Builder(AgreementPaymentActivity.this);
                builder.setTitle(R.string.msg);
                builder.setMessage(R.string.msg_data);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        startActivity(new Intent(AgreementPaymentActivity.this, CreateAgreementDashboardActivity.class));
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }

        }

    }
}
