package in.aartimkarande.rentalagreement.myrentalagreement.transaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
import in.aartimkarande.rentalagreement.myrentalagreement.model.Rent;
import in.aartimkarande.rentalagreement.myrentalagreement.model.RentTrans;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness1.ViewWitness1Activity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness2.ViewWitness2Activity;

public class ViewAgreementPaymentActivity extends AppCompatActivity {

    private ScrollView scrollView;

    private TextView textViewPaymentModeInfo;
    private TextView textViewPaymentMode;

    private TextView textViewCashAmtInfo;
    private TextView textViewCashAmt;

    private TextView textViewBankNameInfo;
    private TextView textViewBankName;
    private TextView textViewBranchNameInfo;
    private TextView textViewBranchName;
    private TextView textViewChkDtInfo;
    private TextView textViewChkDt;
    private TextView textViewChkNoInfo;
    private TextView textViewChkNo;
    private TextView textViewChkAmtInfo;
    private TextView textViewChkAmt;
    private TextView textViewAccNoInfo;
    private TextView textViewAccNo;

    private TextView textViewOnlineTransTypeInfo;
    private TextView textViewOnlineTransType;
    private TextView textViewOnlineTransBnkNameInfo;
    private TextView textViewOnlineTransBnkName;
    private TextView textViewOnlineTransBranchNameInfo;
    private TextView textViewOnlineTransBranchName;
    private TextView textViewOnlineTransAccNoInfo;
    private TextView textViewOnlineTransAccNo;
    private TextView textViewOnlineTransDtInfo;
    private TextView textViewOnlineTransDt;
    private TextView textViewOnlineTransIDInfo;
    private TextView textViewOnlineTransID;

    private TextView textViewPayAmtInfo;
    private TextView textViewPayAmt;

    private TextView textViewPayOptInfo;
    private TextView textViewPayOpt;

    private Button btnBack;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String rentTransID;

    private List<RentTrans> rentTransList;

    private RentTrans rentTrans;

    private float x1;
    private float x2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_agreement_payment);

        initializeComponents();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();

                        float deltaX = x2 - x1;

                        if (Math.abs(deltaX) > FinalValues.MIN_DISTANCE) {

                            if (x2 > x1) {
                                /*Toast.makeText(getApplicationContext(),"Left to Right swipe [Next]",Toast.LENGTH_SHORT).show();*/
                                startActivity(new Intent(ViewAgreementPaymentActivity.this, ViewRentPaymentActivity.class));
                            } else {
                                /*Toast.makeText(getApplicationContext(),"Right to Left swipe [Previous]",Toast.LENGTH_SHORT).show();*/
                            }

                        }
                        break;
                }

                return false;

            }
        });

        disableSomeComponents();

        getPrivateSharedData();

        getRentTransData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewAgreementPaymentActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
            }
        });

    }

    private void getPrivateSharedData() {

        SharedPreferences sharedPreferences = ViewAgreementPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID, null);
        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE, null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID, null);
        rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID, null);

    }

    private void disableSomeComponents() {
        textViewCashAmtInfo.setVisibility(View.GONE);
        textViewCashAmt.setVisibility(View.GONE);

        textViewBankNameInfo.setVisibility(View.GONE);
        textViewBankName.setVisibility(View.GONE);
        textViewBranchNameInfo.setVisibility(View.GONE);
        textViewBranchName.setVisibility(View.GONE);
        textViewChkDtInfo.setVisibility(View.GONE);
        textViewChkDt.setVisibility(View.GONE);
        textViewChkNoInfo.setVisibility(View.GONE);
        textViewChkNo.setVisibility(View.GONE);
        textViewChkAmtInfo.setVisibility(View.GONE);
        textViewChkAmt.setVisibility(View.GONE);
        textViewAccNoInfo.setVisibility(View.GONE);
        textViewAccNo.setVisibility(View.GONE);

        textViewOnlineTransTypeInfo.setVisibility(View.GONE);
        textViewOnlineTransType.setVisibility(View.GONE);
        textViewOnlineTransBnkNameInfo.setVisibility(View.GONE);
        textViewOnlineTransBnkName.setVisibility(View.GONE);
        textViewOnlineTransBranchNameInfo.setVisibility(View.GONE);
        textViewOnlineTransBranchName.setVisibility(View.GONE);
        textViewOnlineTransAccNoInfo.setVisibility(View.GONE);
        textViewOnlineTransAccNo.setVisibility(View.GONE);
        textViewOnlineTransDtInfo.setVisibility(View.GONE);
        textViewOnlineTransDt.setVisibility(View.GONE);
        textViewOnlineTransIDInfo.setVisibility(View.GONE);
        textViewOnlineTransID.setVisibility(View.GONE);
    }

    private void getRentTransData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_RENT_TRANS_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            Snackbar snackbar = Snackbar.make(scrollView, "All data are fetched.: ", Snackbar.LENGTH_LONG);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();

                            try {

                                /*Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();*/

                                //converting the string to json array object
                                JSONArray jsonArray = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject data = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    rentTransList.add(new RentTrans(
                                            data.getString(Keys.GET_RENT_TRANS_ID),
                                            data.getString(Keys.GET_RENT_TRANS_PROP_ID),
                                            data.getString(Keys.GET_RENT_TRANS_MODE),
                                            data.getString(Keys.GET_RENT_TRANS_CASH_AMT),
                                            data.getString(Keys.GET_RENT_TRANS_CHK_BANK_NAME),
                                            data.getString(Keys.GET_RENT_TRANS_CHK_BRANCH_NAME),
                                            data.getString(Keys.GET_RENT_TRANS_CHK_DATE),
                                            data.getString(Keys.GET_RENT_TRANS_CHK_NO),
                                            data.getString(Keys.GET_RENT_TRANS_CHK_AMT),
                                            data.getString(Keys.GET_RENT_TRANS_CHK_ACC_NO),
                                            data.getString(Keys.GET_RENT_TRANS_MODE_TYPE),
                                            data.getString(Keys.GET_RENT_TRANS_BNK_NAME),
                                            data.getString(Keys.GET_RENT_TRANS_BRANCH_NAME),
                                            data.getString(Keys.GET_RENT_TRANS_ACC_NO),
                                            data.getString(Keys.GET_RENT_TRANS_TRANS_DT),
                                            data.getString(Keys.GET_RENT_TRANS_REF_ID),
                                            data.getString(Keys.GET_RENT_TRANS_PAY_AMT),
                                            data.getString(Keys.GET_RENT_TRANS_PAY_OPT),
                                            data.getString(Keys.GET_RENT_TRANS_CHARGES),
                                            data.getString(Keys.GET_RENT_TRANS_BALANCE),
                                            data.getString(Keys.GET_RENT_TRANS_STAT)
                                    ));
                                }

                                setRentTransData();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "catch : " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        } else {

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(scrollView, "Something went wrong. " + response, Snackbar.LENGTH_LONG);

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
                        //Message other than Success
                        Snackbar snackbar = Snackbar.make(scrollView, "Error :  " + error, Snackbar.LENGTH_LONG);

                        //Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.red));
                        snackbar.show();
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_PROPERTY_ID, propertyID);
                params.put(Keys.ID_RENT_TRANS_ID, rentTransID);

                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAgreementPaymentActivity.this);
        requestQueue.add(request);

    }

    private void setRentTransData() {

        for (int i = 0; i < rentTransList.size(); i++) {
            rentTrans = rentTransList.get(i);

            textViewPaymentMode.setText(rentTrans.getRent_trans_mode());

            if(rentTrans.getRent_trans_mode().equalsIgnoreCase("Cash")){

                textViewBankNameInfo.setVisibility(View.GONE);
                textViewBankName.setVisibility(View.GONE);
                textViewBranchNameInfo.setVisibility(View.GONE);
                textViewBranchName.setVisibility(View.GONE);
                textViewChkDtInfo.setVisibility(View.GONE);
                textViewChkDt.setVisibility(View.GONE);
                textViewChkNoInfo.setVisibility(View.GONE);
                textViewChkNo.setVisibility(View.GONE);
                textViewChkAmtInfo.setVisibility(View.GONE);
                textViewChkAmt.setVisibility(View.GONE);
                textViewAccNoInfo.setVisibility(View.GONE);
                textViewAccNo.setVisibility(View.GONE);

                textViewOnlineTransTypeInfo.setVisibility(View.GONE);
                textViewOnlineTransType.setVisibility(View.GONE);
                textViewOnlineTransBnkNameInfo.setVisibility(View.GONE);
                textViewOnlineTransBnkName.setVisibility(View.GONE);
                textViewOnlineTransBranchNameInfo.setVisibility(View.GONE);
                textViewOnlineTransBranchName.setVisibility(View.GONE);
                textViewOnlineTransAccNoInfo.setVisibility(View.GONE);
                textViewOnlineTransAccNo.setVisibility(View.GONE);
                textViewOnlineTransDtInfo.setVisibility(View.GONE);
                textViewOnlineTransDt.setVisibility(View.GONE);
                textViewOnlineTransIDInfo.setVisibility(View.GONE);
                textViewOnlineTransID.setVisibility(View.GONE);


                textViewCashAmtInfo.setVisibility(View.VISIBLE);
                textViewCashAmt.setVisibility(View.VISIBLE);

                textViewCashAmt.setText(rentTrans.getRent_trans_cash_amt());


            }else if(rentTrans.getRent_trans_mode().equalsIgnoreCase("Cheque")){

                textViewCashAmtInfo.setVisibility(View.GONE);
                textViewCashAmt.setVisibility(View.GONE);

                textViewOnlineTransTypeInfo.setVisibility(View.GONE);
                textViewOnlineTransType.setVisibility(View.GONE);
                textViewOnlineTransBnkNameInfo.setVisibility(View.GONE);
                textViewOnlineTransBnkName.setVisibility(View.GONE);
                textViewOnlineTransBranchNameInfo.setVisibility(View.GONE);
                textViewOnlineTransBranchName.setVisibility(View.GONE);
                textViewOnlineTransAccNoInfo.setVisibility(View.GONE);
                textViewOnlineTransAccNo.setVisibility(View.GONE);
                textViewOnlineTransDtInfo.setVisibility(View.GONE);
                textViewOnlineTransDt.setVisibility(View.GONE);
                textViewOnlineTransIDInfo.setVisibility(View.GONE);
                textViewOnlineTransID.setVisibility(View.GONE);

                textViewBankNameInfo.setVisibility(View.VISIBLE);
                textViewBankName.setVisibility(View.VISIBLE);
                textViewBranchNameInfo.setVisibility(View.VISIBLE);
                textViewBranchName.setVisibility(View.VISIBLE);
                textViewChkDtInfo.setVisibility(View.VISIBLE);
                textViewChkDt.setVisibility(View.VISIBLE);
                textViewChkNoInfo.setVisibility(View.VISIBLE);
                textViewChkNo.setVisibility(View.VISIBLE);
                textViewChkAmtInfo.setVisibility(View.VISIBLE);
                textViewChkAmt.setVisibility(View.VISIBLE);
                textViewAccNoInfo.setVisibility(View.VISIBLE);
                textViewAccNo.setVisibility(View.VISIBLE);


                textViewBankName.setText(rentTrans.getRent_trans_chk_bank_name());
                textViewBranchName.setText(rentTrans.getRent_trans_chk_branch_name());
                textViewChkDt.setText(rentTrans.getRent_trans_chk_date());
                textViewChkNo.setText(rentTrans.getRent_trans_chk_no());
                textViewChkAmt.setText(rentTrans.getRent_trans_chk_amt());
                textViewAccNo.setText(rentTrans.getRent_trans_chk_acc_no());

            }else if(rentTrans.getRent_trans_mode().equalsIgnoreCase("Net Banking")){

                textViewCashAmtInfo.setVisibility(View.GONE);
                textViewCashAmt.setVisibility(View.GONE);

                textViewBankNameInfo.setVisibility(View.GONE);
                textViewBankName.setVisibility(View.GONE);
                textViewBranchNameInfo.setVisibility(View.GONE);
                textViewBranchName.setVisibility(View.GONE);
                textViewChkDtInfo.setVisibility(View.GONE);
                textViewChkDt.setVisibility(View.GONE);
                textViewChkNoInfo.setVisibility(View.GONE);
                textViewChkNo.setVisibility(View.GONE);
                textViewChkAmtInfo.setVisibility(View.GONE);
                textViewChkAmt.setVisibility(View.GONE);
                textViewAccNoInfo.setVisibility(View.GONE);
                textViewAccNo.setVisibility(View.GONE);

                textViewOnlineTransTypeInfo.setVisibility(View.VISIBLE);
                textViewOnlineTransType.setVisibility(View.VISIBLE);
                textViewOnlineTransBnkNameInfo.setVisibility(View.VISIBLE);
                textViewOnlineTransBnkName.setVisibility(View.VISIBLE);
                textViewOnlineTransBranchNameInfo.setVisibility(View.VISIBLE);
                textViewOnlineTransBranchName.setVisibility(View.VISIBLE);
                textViewOnlineTransAccNoInfo.setVisibility(View.VISIBLE);
                textViewOnlineTransAccNo.setVisibility(View.VISIBLE);
                textViewOnlineTransDtInfo.setVisibility(View.VISIBLE);
                textViewOnlineTransDt.setVisibility(View.VISIBLE);
                textViewOnlineTransIDInfo.setVisibility(View.VISIBLE);
                textViewOnlineTransID.setVisibility(View.VISIBLE);


                textViewOnlineTransType.setText(rentTrans.getRent_trans_mode_type());
                textViewOnlineTransBnkName.setText(rentTrans.getRent_trans_bnk_name());
                textViewOnlineTransBranchName.setText(rentTrans.getRent_trans_branch_name());
                textViewOnlineTransAccNo.setText(rentTrans.getRent_trans_acc_no());
                textViewOnlineTransDt.setText(rentTrans.getRent_trans_dt());
                textViewOnlineTransID.setText(rentTrans.getRent_trans_refid());

            }

            textViewPayAmt.setText(rentTrans.getRent_trans_pay_amt());
            textViewPayOpt.setText(rentTrans.getRent_trans_pay_opt());

        }

    }


    private void initializeComponents() {
        scrollView = findViewById(R.id.scroll_view);


        textViewPaymentModeInfo = findViewById(R.id.rent_trans_mode_info);
        textViewPaymentMode = findViewById(R.id.rent_trans_mode);

        textViewCashAmtInfo = findViewById(R.id.rent_trans_cash_amt_info);
        textViewCashAmt = findViewById(R.id.rent_trans_cash_amt);

        textViewBankNameInfo = findViewById(R.id.rent_trans_bnk_name_info);
        textViewBankName = findViewById(R.id.rent_trans_bnk_name);
        textViewBranchNameInfo = findViewById(R.id.rent_trans_branch_name_info);
        textViewBranchName = findViewById(R.id.rent_trans_branch_name);
        textViewChkDtInfo = findViewById(R.id.rent_trans_chk_dt_info);
        textViewChkDt = findViewById(R.id.rent_trans_chk_dt);
        textViewChkNoInfo = findViewById(R.id.rent_trans_chk_no_info);
        textViewChkNo = findViewById(R.id.rent_trans_chk_no);
        textViewChkAmtInfo = findViewById(R.id.rent_trans_chk_amt_info);
        textViewChkAmt = findViewById(R.id.rent_trans_chk_amt);
        textViewAccNoInfo = findViewById(R.id.rent_trans_acc_no_info);
        textViewAccNo = findViewById(R.id.rent_trans_acc_no);

        textViewOnlineTransTypeInfo = findViewById(R.id.rent_trans_online_type_info);
        textViewOnlineTransType = findViewById(R.id.rent_trans_online_type);
        textViewOnlineTransBnkNameInfo = findViewById(R.id.rent_trans_online_bnk_name_info);
        textViewOnlineTransBnkName = findViewById(R.id.rent_trans_online_bnk_name);
        textViewOnlineTransBranchNameInfo = findViewById(R.id.rent_trans_online_branch_name_info);
        textViewOnlineTransBranchName = findViewById(R.id.rent_trans_online_branch_name);
        textViewOnlineTransAccNoInfo = findViewById(R.id.rent_trans_online_acc_no_info);
        textViewOnlineTransAccNo = findViewById(R.id.rent_trans_online_acc_no);
        textViewOnlineTransDtInfo = findViewById(R.id.rent_trans_online_dt_info);
        textViewOnlineTransDt = findViewById(R.id.rent_trans_online_dt);
        textViewOnlineTransIDInfo = findViewById(R.id.rent_trans_online_id_info);
        textViewOnlineTransID = findViewById(R.id.rent_trans_online_id);

        textViewPayAmtInfo = findViewById(R.id.rent_trans_pay_amt_info);
        textViewPayAmt = findViewById(R.id.rent_trans_pay_amt);

        textViewPayOptInfo = findViewById(R.id.rent_trans_pay_opt_info);
        textViewPayOpt = findViewById(R.id.rent_trans_pay_opt);

        btnBack = findViewById(R.id.btnBack);

        rentTransList = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(ViewAgreementPaymentActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
    }
}
