package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.model.Rent;
import in.aartimkarande.rentalagreement.myrentalagreement.transaction.ViewRentPaymentActivity;

public class PaymentDataActivity extends AppCompatActivity {


    private ScrollView scrollView;

    private TextView textViewRentDuration;
    private TextView textViewRefundableDeptAmt;
    private TextView textViewMonthlyRentAmt;
    private TextView textViewRentType;

    private TextView textViewFixedAmtInfo;
    private TextView textViewFixedAmt;

    private TextView textViewRent1Info;
    private TextView textViewRent1;
    private TextView textViewRent2Info;
    private TextView textViewRent2;
    private TextView textViewRent3Info;
    private TextView textViewRent3;
    private TextView textViewRent4Info;
    private TextView textViewRent4;
    private TextView textViewRent5Info;
    private TextView textViewRent5;
    private TextView textViewRent6Info;
    private TextView textViewRent6;
    private TextView textViewRent7Info;
    private TextView textViewRent7;
    private TextView textViewRent8Info;
    private TextView textViewRent8;
    private TextView textViewRent9Info;
    private TextView textViewRent9;
    private TextView textViewRent10Info;
    private TextView textViewRent10;
    private TextView textViewRent11Info;
    private TextView textViewRent11;
    private TextView textViewRent12Info;
    private TextView textViewRent12;

    private TextView textViewIsDeptAmtPaidInfo;

    private TextView textViewPaymentModeInfo;
    private TextView TextViewPaymentMode;

    private TextView textViewCashDtInfo;
    private TextView textViewCashDate;

    private TextView textViewChkNoInfo;
    private TextView textViewChkNo;
    private TextView textViewBnkNameInfo;
    private TextView textViewBnkName;
    private TextView textViewBranchNameInfo;
    private TextView textViewBranchName;
    private TextView textViewChkDtInfo;
    private TextView textViewChkDt;
    private TextView textViewChkAmtInfo;
    private TextView textViewChkAmt;

    private TextView textViewAgmStartDt;
    private TextView textViewAgmEndDt;
    private TextView textViewCSStatus;

    private Button btnBack;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String rentID;
    private String rentTransID;

    private List<Rent> rentList;

    private Rent rent;

    private float x1;
    private float x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_data);

        Intent intent = getIntent();
        propertyID = intent.getStringExtra("propID");
        userID = intent.getStringExtra("userID");
        rentID = intent.getStringExtra("rentID");
        rentTransID = intent.getStringExtra("rentTransID");

        initializeComponents();
        disableSomeComponents();
        getRentData();

    }


    private void getRentData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_RENT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

                            /*Snackbar snackbar = Snackbar.make(scrollView, "All data are fetched.: ", Snackbar.LENGTH_LONG);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();*/

                            try {

                                /*Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();*/

                                //converting the string to json array object
                                JSONArray jsonArray = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject data = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    rentList.add(new Rent(
                                            data.getString(Keys.GET_RENT_ID),
                                            data.getString(Keys.GET_RENT_PROP_ID),
                                            data.getString(Keys.GET_RENT_PERIOD),
                                            data.getString(Keys.GET_RENT_DEPOSIT),
                                            data.getString(Keys.GET_RENT_VAR_MNTH_PER),
                                            data.getString(Keys.GET_RENT_TYPE),
                                            data.getString(Keys.GET_RENT_FIXED_AMT),
                                            data.getString(Keys.GET_RENT_MNTH1),
                                            data.getString(Keys.GET_RENT_RENT1),
                                            data.getString(Keys.GET_RENT_MNTH2),
                                            data.getString(Keys.GET_RENT_RENT2),
                                            data.getString(Keys.GET_RENT_MNTH3),
                                            data.getString(Keys.GET_RENT_RENT3),
                                            data.getString(Keys.GET_RENT_MNTH4),
                                            data.getString(Keys.GET_RENT_RENT4),
                                            data.getString(Keys.GET_RENT_MNTH5),
                                            data.getString(Keys.GET_RENT_RENT5),
                                            data.getString(Keys.GET_RENT_MNTH6),
                                            data.getString(Keys.GET_RENT_RENT6),
                                            data.getString(Keys.GET_RENT_MNTH7),
                                            data.getString(Keys.GET_RENT_RENT7),
                                            data.getString(Keys.GET_RENT_MNTH8),
                                            data.getString(Keys.GET_RENT_RENT8),
                                            data.getString(Keys.GET_RENT_MNTH9),
                                            data.getString(Keys.GET_RENT_RENT9),
                                            data.getString(Keys.GET_RENT_MNTH10),
                                            data.getString(Keys.GET_RENT_RENT10),
                                            data.getString(Keys.GET_RENT_MNTH11),
                                            data.getString(Keys.GET_RENT_RENT11),
                                            data.getString(Keys.GET_RENT_MNTH12),
                                            data.getString(Keys.GET_RENT_RENT12),
                                            data.getString(Keys.GET_RENT_DEP_STAT),
                                            data.getString(Keys.GET_RENT_DEP_STAT_MODE),
                                            data.getString(Keys.GET_RENT_CASH_DT),
                                            data.getString(Keys.GET_RENT_CHK_NO),
                                            data.getString(Keys.GET_RENT_BANK_NAME),
                                            data.getString(Keys.GET_RENT_BRANCH_NAME),
                                            data.getString(Keys.GET_RENT_CHK_DT),
                                            data.getString(Keys.GET_RENT_BANK_ACC_NO),
                                            data.getString(Keys.GET_RENT_AGR_START_DT),
                                            data.getString(Keys.GET_RENT_AGR_END_DT),
                                            data.getString(Keys.GET_RENT_CR_SRV),
                                            data.getString(Keys.GET_RENT_STAT)

                                    ));
                                }

                                setRentData();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"catch : " + e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }else{

                           /* //Message other than Success
                            Snackbar snackbar = Snackbar.make(scrollView, "Something went wrong. " + response, Snackbar.LENGTH_LONG);

                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.red));
                            snackbar.show();*/

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /*//Message other than Success
                        Snackbar snackbar = Snackbar.make(scrollView, "Error :  " + error, Snackbar.LENGTH_LONG);

                        //Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(getResources().getColor(R.color.red));
                        snackbar.show();*/
                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_PROPERTY_ID, propertyID);
                params.put(Keys.ID_RENT_ID, rentID);

                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentDataActivity.this);
        requestQueue.add(request);

    }

    private void setRentData() {

        for (int i=0; i<rentList.size(); i++){
            rent = rentList.get(i);

            textViewRentDuration.setText(rent.getProp_pay_period());
            textViewRefundableDeptAmt.setText(rent.getProp_pay_deposit());
            textViewMonthlyRentAmt.setText(rent.getProp_pay_var_mnth_per());
            textViewRentType.setText(rent.getProp_pay_rent_type());

            if(rent.getProp_pay_rent_type().equalsIgnoreCase("Fixed")){

                textViewFixedAmtInfo.setVisibility(View.VISIBLE);
                textViewFixedAmt.setVisibility(View.VISIBLE);

                textViewFixedAmt.setText(rent.getProp_pay_rent_fixed_amt());

                textViewRent1Info.setVisibility(View.GONE);
                textViewRent1.setVisibility(View.GONE);
                textViewRent2Info.setVisibility(View.GONE);
                textViewRent2.setVisibility(View.GONE);
                textViewRent3Info.setVisibility(View.GONE);
                textViewRent3.setVisibility(View.GONE);
                textViewRent4Info.setVisibility(View.GONE);
                textViewRent4.setVisibility(View.GONE);
                textViewRent5Info.setVisibility(View.GONE);
                textViewRent5.setVisibility(View.GONE);
                textViewRent6Info.setVisibility(View.GONE);
                textViewRent6.setVisibility(View.GONE);
                textViewRent7Info.setVisibility(View.GONE);
                textViewRent7.setVisibility(View.GONE);
                textViewRent8Info.setVisibility(View.GONE);
                textViewRent8.setVisibility(View.GONE);
                textViewRent9Info.setVisibility(View.GONE);
                textViewRent9.setVisibility(View.GONE);
                textViewRent10Info.setVisibility(View.GONE);
                textViewRent10.setVisibility(View.GONE);
                textViewRent11Info.setVisibility(View.GONE);
                textViewRent11.setVisibility(View.GONE);
                textViewRent12Info.setVisibility(View.GONE);
                textViewRent12.setVisibility(View.GONE);

            }else if(rent.getProp_pay_rent_type().equalsIgnoreCase("Variable")){

                textViewFixedAmtInfo.setVisibility(View.GONE);
                textViewFixedAmt.setVisibility(View.GONE);


                textViewRent1Info.setVisibility(View.VISIBLE);
                textViewRent1.setVisibility(View.VISIBLE);
                textViewRent2Info.setVisibility(View.VISIBLE);
                textViewRent2.setVisibility(View.VISIBLE);
                textViewRent3Info.setVisibility(View.VISIBLE);
                textViewRent3.setVisibility(View.VISIBLE);
                textViewRent4Info.setVisibility(View.VISIBLE);
                textViewRent4.setVisibility(View.VISIBLE);
                textViewRent5Info.setVisibility(View.VISIBLE);
                textViewRent5.setVisibility(View.VISIBLE);
                textViewRent6Info.setVisibility(View.VISIBLE);
                textViewRent6.setVisibility(View.VISIBLE);
                textViewRent7Info.setVisibility(View.VISIBLE);
                textViewRent7.setVisibility(View.VISIBLE);
                textViewRent8Info.setVisibility(View.VISIBLE);
                textViewRent8.setVisibility(View.VISIBLE);
                textViewRent9Info.setVisibility(View.VISIBLE);
                textViewRent9.setVisibility(View.VISIBLE);
                textViewRent10Info.setVisibility(View.VISIBLE);
                textViewRent10.setVisibility(View.VISIBLE);
                textViewRent11Info.setVisibility(View.VISIBLE);
                textViewRent11.setVisibility(View.VISIBLE);
                textViewRent12Info.setVisibility(View.VISIBLE);
                textViewRent12.setVisibility(View.VISIBLE);


                textViewRent1.setText(rent.getProp_pay_rent1());
                textViewRent2.setText(rent.getProp_pay_rent2());
                textViewRent3.setText(rent.getProp_pay_rent3());
                textViewRent4.setText(rent.getProp_pay_rent4());
                textViewRent5.setText(rent.getProp_pay_rent5());
                textViewRent6.setText(rent.getProp_pay_rent6());
                textViewRent7.setText(rent.getProp_pay_rent7());
                textViewRent8.setText(rent.getProp_pay_rent8());
                textViewRent9.setText(rent.getProp_pay_rent9());
                textViewRent10.setText(rent.getProp_pay_rent10());
                textViewRent11.setText(rent.getProp_pay_rent11());
                textViewRent12.setText(rent.getProp_pay_rent12());

            }


            textViewIsDeptAmtPaidInfo.setText(rent.getProp_pay_dep_stat());

            if(rent.getProp_pay_dep_stat().equalsIgnoreCase("Yes")){

                textViewPaymentModeInfo.setVisibility(View.VISIBLE);
                TextViewPaymentMode.setVisibility(View.VISIBLE);

                TextViewPaymentMode.setText(rent.getProp_pay_dep_stat_mode());


                if(rent.getProp_pay_dep_stat_mode().equalsIgnoreCase("Cash")){

                    textViewChkNoInfo.setVisibility(View.GONE);
                    textViewChkNo.setVisibility(View.GONE);
                    textViewBnkNameInfo.setVisibility(View.GONE);
                    textViewBnkName.setVisibility(View.GONE);
                    textViewBranchNameInfo.setVisibility(View.GONE);
                    textViewBranchName.setVisibility(View.GONE);
                    textViewChkDtInfo.setVisibility(View.GONE);
                    textViewChkDt.setVisibility(View.GONE);
                    textViewChkAmtInfo.setVisibility(View.GONE);
                    textViewChkAmt.setVisibility(View.GONE);


                    textViewCashDtInfo.setVisibility(View.VISIBLE);
                    textViewCashDate.setVisibility(View.VISIBLE);

                    textViewCashDate.setText(rent.getProp_pay_cash_dt());


                }else if(rent.getProp_pay_dep_stat_mode().equalsIgnoreCase("Cheque")){


                    textViewCashDtInfo.setVisibility(View.GONE);
                    textViewCashDate.setVisibility(View.GONE);

                    textViewChkNoInfo.setVisibility(View.VISIBLE);
                    textViewChkNo.setVisibility(View.VISIBLE);
                    textViewBnkNameInfo.setVisibility(View.VISIBLE);
                    textViewBnkName.setVisibility(View.VISIBLE);
                    textViewBranchNameInfo.setVisibility(View.VISIBLE);
                    textViewBranchName.setVisibility(View.VISIBLE);
                    textViewChkDtInfo.setVisibility(View.VISIBLE);
                    textViewChkDt.setVisibility(View.VISIBLE);
                    textViewChkAmtInfo.setVisibility(View.VISIBLE);
                    textViewChkAmt.setVisibility(View.VISIBLE);



                    textViewChkNo.setText(rent.getProp_pay_chk_no());
                    textViewBnkName.setText(rent.getProp_pay_bank_name());
                    textViewBranchName.setText(rent.getProp_pay_branch_name());
                    textViewChkDt.setText(rent.getProp_pay_chk_dt());
                    textViewChkAmt.setText(rent.getProp_pay_bank_acc_no());

                }




            }else if(rent.getProp_pay_dep_stat().equalsIgnoreCase("No")){

                textViewPaymentModeInfo.setVisibility(View.GONE);
                TextViewPaymentMode.setVisibility(View.GONE);

                textViewCashDtInfo.setVisibility(View.GONE);
                textViewCashDate.setVisibility(View.GONE);

                textViewChkNoInfo.setVisibility(View.GONE);
                textViewChkNo.setVisibility(View.GONE);
                textViewBnkNameInfo.setVisibility(View.GONE);
                textViewBnkName.setVisibility(View.GONE);
                textViewBranchNameInfo.setVisibility(View.GONE);
                textViewBranchName.setVisibility(View.GONE);
                textViewChkDtInfo.setVisibility(View.GONE);
                textViewChkDt.setVisibility(View.GONE);
                textViewChkAmtInfo.setVisibility(View.GONE);
                textViewChkAmt.setVisibility(View.GONE);
            }

            textViewAgmStartDt.setText(rent.getProp_pay_agr_start_dt());
            textViewAgmEndDt.setText(rent.getProp_pay_agr_end_dt());
            textViewCSStatus.setText(rent.getProp_pay_cr_srv());

        }

    }



    private void disableSomeComponents() {

        textViewFixedAmtInfo.setVisibility(View.GONE);
        textViewFixedAmt.setVisibility(View.GONE);

        textViewRent1Info.setVisibility(View.GONE);
        textViewRent1.setVisibility(View.GONE);
        textViewRent2Info.setVisibility(View.GONE);
        textViewRent2.setVisibility(View.GONE);
        textViewRent3Info.setVisibility(View.GONE);
        textViewRent3.setVisibility(View.GONE);
        textViewRent4Info.setVisibility(View.GONE);
        textViewRent4.setVisibility(View.GONE);
        textViewRent5Info.setVisibility(View.GONE);
        textViewRent5.setVisibility(View.GONE);
        textViewRent6Info.setVisibility(View.GONE);
        textViewRent6.setVisibility(View.GONE);
        textViewRent7Info.setVisibility(View.GONE);
        textViewRent7.setVisibility(View.GONE);
        textViewRent8Info.setVisibility(View.GONE);
        textViewRent8.setVisibility(View.GONE);
        textViewRent9Info.setVisibility(View.GONE);
        textViewRent9.setVisibility(View.GONE);
        textViewRent10Info.setVisibility(View.GONE);
        textViewRent10.setVisibility(View.GONE);
        textViewRent11Info.setVisibility(View.GONE);
        textViewRent11.setVisibility(View.GONE);
        textViewRent12Info.setVisibility(View.GONE);
        textViewRent12.setVisibility(View.GONE);

        textViewPaymentModeInfo.setVisibility(View.GONE);
        TextViewPaymentMode.setVisibility(View.GONE);

        textViewCashDtInfo.setVisibility(View.GONE);
        textViewCashDate.setVisibility(View.GONE);

        textViewChkNoInfo.setVisibility(View.GONE);
        textViewChkNo.setVisibility(View.GONE);
        textViewBnkNameInfo.setVisibility(View.GONE);
        textViewBnkName.setVisibility(View.GONE);
        textViewBranchNameInfo.setVisibility(View.GONE);
        textViewBranchName.setVisibility(View.GONE);
        textViewChkDtInfo.setVisibility(View.GONE);
        textViewChkDt.setVisibility(View.GONE);
        textViewChkAmtInfo.setVisibility(View.GONE);
        textViewChkAmt.setVisibility(View.GONE);

    }

    private void initializeComponents() {
        scrollView = findViewById(R.id.scroll_view);

        textViewRentDuration = findViewById(R.id.rent_duration);
        textViewRefundableDeptAmt = findViewById(R.id.deposit_amt);
        textViewMonthlyRentAmt = findViewById(R.id.monthly_rent);
        textViewRentType = findViewById(R.id.rent_type);

        textViewFixedAmtInfo = findViewById(R.id.fixed_amt_info);
        textViewFixedAmt = findViewById(R.id.fixed_amt);

        textViewRent1Info = findViewById(R.id.variable_amt_1_info);
        textViewRent1 = findViewById(R.id.variable_amt_1);
        textViewRent2Info = findViewById(R.id.variable_amt_2_info);
        textViewRent2 = findViewById(R.id.variable_amt_2);
        textViewRent3Info = findViewById(R.id.variable_amt_3_info);
        textViewRent3 = findViewById(R.id.variable_amt_3);
        textViewRent4Info = findViewById(R.id.variable_amt_4_info);
        textViewRent4 = findViewById(R.id.variable_amt_4);
        textViewRent5Info = findViewById(R.id.variable_amt_5_info);
        textViewRent5 = findViewById(R.id.variable_amt_5);
        textViewRent6Info = findViewById(R.id.variable_amt_6_info);
        textViewRent6 = findViewById(R.id.variable_amt_6);
        textViewRent7Info = findViewById(R.id.variable_amt_7_info);
        textViewRent7 = findViewById(R.id.variable_amt_7);
        textViewRent8Info = findViewById(R.id.variable_amt_8_info);
        textViewRent8 = findViewById(R.id.variable_amt_8);
        textViewRent9Info = findViewById(R.id.variable_amt_9_info);
        textViewRent9 = findViewById(R.id.variable_amt_9);
        textViewRent10Info = findViewById(R.id.variable_amt_10_info);
        textViewRent10 = findViewById(R.id.variable_amt_10);
        textViewRent11Info = findViewById(R.id.variable_amt_11_info);
        textViewRent11 = findViewById(R.id.variable_amt_11);
        textViewRent12Info = findViewById(R.id.variable_amt_12_info);
        textViewRent12 = findViewById(R.id.variable_amt_12);

        textViewIsDeptAmtPaidInfo = findViewById(R.id.deposit_amt_status);

        textViewPaymentModeInfo = findViewById(R.id.payment_mode_info);
        TextViewPaymentMode = findViewById(R.id.payment_mode);

        textViewCashDtInfo = findViewById(R.id.cash_dt_info);
        textViewCashDate = findViewById(R.id.cash_dt);

        textViewChkNoInfo = findViewById(R.id.chk_no_info);
        textViewChkNo = findViewById(R.id.chk_no);
        textViewBnkNameInfo = findViewById(R.id.bnk_name_info);
        textViewBnkName = findViewById(R.id.bnk_name);
        textViewBranchNameInfo = findViewById(R.id.bnk_branch_name_info);
        textViewBranchName = findViewById(R.id.bnk_branch_name);
        textViewChkDtInfo = findViewById(R.id.chk_date_info);
        textViewChkDt = findViewById(R.id.chk_date);
        textViewChkAmtInfo = findViewById(R.id.chk_amt_info);
        textViewChkAmt = findViewById(R.id.chk_amt);

        textViewAgmStartDt = findViewById(R.id.agreement_start_date);
        textViewAgmEndDt = findViewById(R.id.agreement_end_date);
        textViewCSStatus = findViewById(R.id.courier_service_status);

        btnBack = findViewById(R.id.btnBack);

        rentList = new ArrayList<>();

    }

}
