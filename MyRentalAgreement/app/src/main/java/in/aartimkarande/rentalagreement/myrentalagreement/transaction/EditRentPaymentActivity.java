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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import in.aartimkarande.rentalagreement.myrentalagreement.model.Rent;

public class EditRentPaymentActivity extends AppCompatActivity {

    //Components
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutRentDuration;
    private TextInputLayout textInputLayoutRefundableDepositAmt;
    private TextInputLayout textInputLayoutMonthlyRentAmt;
    private TextInputLayout textInputLayoutFixedTypeAmt;
    /*private TextInputLayout textInputLayoutVariableTypeAmt;*/
    private TextInputLayout textInputLayoutVariableMonth1Amt;
    private TextInputLayout textInputLayoutVariableMonth2Amt;
    private TextInputLayout textInputLayoutVariableMonth3Amt;
    private TextInputLayout textInputLayoutVariableMonth4Amt;
    private TextInputLayout textInputLayoutVariableMonth5Amt;
    private TextInputLayout textInputLayoutVariableMonth6Amt;
    private TextInputLayout textInputLayoutVariableMonth7Amt;
    private TextInputLayout textInputLayoutVariableMonth8Amt;
    private TextInputLayout textInputLayoutVariableMonth9Amt;
    private TextInputLayout textInputLayoutVariableMonth10Amt;
    private TextInputLayout textInputLayoutVariableMonth11Amt;
    private TextInputLayout textInputLayoutVariableMonth12Amt;
    private TextInputLayout textInputLayoutCashAmt;
    private TextInputLayout textInputLayoutBnkName;
    private TextInputLayout textInputLayoutBranchName;
    private TextInputLayout textInputLayoutChqDate;
    private TextInputLayout textInputLayoutChqAmt;
    private TextInputLayout textInputLayoutAccNo;
    private TextInputLayout textInputLayoutStartDate;
    private TextInputLayout textInputLayoutEndDate;

    private TextInputEditText inputEditTextRentDuration;
    private TextInputEditText inputEditTextRefundableDepositAmt;
    private TextInputEditText inputEditTextMonthlyRentAmt;
    private TextInputEditText inputEditTextFixedTypeAmt;
    /*private TextInputEditText inputEditTextVariableTypeAmt;*/
    private TextInputEditText inputEditTextVariableMonth1Amt;
    private TextInputEditText inputEditTextVariableMonth2Amt;
    private TextInputEditText inputEditTextVariableMonth3Amt;
    private TextInputEditText inputEditTextVariableMonth4Amt;
    private TextInputEditText inputEditTextVariableMonth5Amt;
    private TextInputEditText inputEditTextVariableMonth6Amt;
    private TextInputEditText inputEditTextVariableMonth7Amt;
    private TextInputEditText inputEditTextVariableMonth8Amt;
    private TextInputEditText inputEditTextVariableMonth9Amt;
    private TextInputEditText inputEditTextVariableMonth10Amt;
    private TextInputEditText inputEditTextVariableMonth11Amt;
    private TextInputEditText inputEditTextVariableMonth12Amt;
    private TextInputEditText inputEditTextCashAmt;
    private TextInputEditText inputEditTextBnkName;
    private TextInputEditText inputEditTextBranchName;
    private TextInputEditText inputEditTextChqDate;
    private TextInputEditText inputEditTextChqAmt;
    private TextInputEditText inputEditTextAccNo;
    private TextInputEditText inputEditTextStartDate;
    private TextInputEditText inputEditTextEndDate;


    private RadioGroup radioGroupRentType;
    private RadioButton radioButtonFixed;
    private RadioButton radioButtonVariable;

    private RadioGroup radioGroupIsDepositAmtPaid;
    private RadioButton radioButtonYes;
    private RadioButton radioButtonNo;

    private RadioGroup radioGroupPaymentMode;
    private RadioButton radioButtonCash;
    private RadioButton radioButtonCheque;

    private RadioGroup radioGroupCourierService;
    private RadioButton radioButtonCSYes;
    private RadioButton radioButtonCSNo;

    private TextView txtViewPaymentMode;

    private Button btnSubmit;
    private Button btnNext;
    private Button btnBack;

    private AlertDialog alertDialog;

    private List<Rent> rentList;
    private Rent rent;

    //Primitive Datatypes and Wrapper Classes
    private String rentID;

    private String userID;

    private String rentDuration;
    private String refundableDepositAmt;
    private String monthlyRentAmt;

    private String rentType;
    private String fixedAmt;
    private String month1Amt;
    private String month2Amt;
    private String month3Amt;
    private String month4Amt;
    private String month5Amt;
    private String month6Amt;
    private String month7Amt;
    private String month8Amt;
    private String month9Amt;
    private String month10Amt;
    private String month11Amt;
    private String month12Amt;

    private String isDepositAmtPaid;

    private String paymentMode;
    private String cashAmt;
    private String bnkName;
    private String branchName;
    private String chqDate;
    private String chqAmt;
    private String accNo;

    private String startDate;
    private String endDate;

    private String csMailDocs;

    private String propertyID;

    private int mYear;
    private int mMonth;
    private int mDay;

    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rent_payment);


        //Initializing Components
        initializeComponents();
        setDefaultStrValues();

        //Methods
        getPropertyID();

        getData();

        //Method
        disableSomeComponents();
        setErrorEnable();

        //Action Listener
        inputEditTextCashAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                //c.add(Calendar.YEAR, (1960 - c.get(Calendar.YEAR)));

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRentPaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                inputEditTextCashAmt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);


                //Toast.makeText(getApplicationContext(),"Year Difference : " +(mYear - 18),Toast.LENGTH_SHORT).show();

                //c.add(Calendar.YEAR, +40);


//                long maxDate = c.getTime().getTime();
//                datePickerDialog.getDatePicker().setMaxDate(maxDate);

                datePickerDialog.show();


            }
        });

        inputEditTextChqDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                //c.add(Calendar.YEAR, (1960 - c.get(Calendar.YEAR)));

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRentPaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                inputEditTextChqDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);


                //Toast.makeText(getApplicationContext(),"Year Difference : " +(mYear - 18),Toast.LENGTH_SHORT).show();

                //c.add(Calendar.YEAR, +40);


//                long maxDate = c.getTime().getTime();
//                datePickerDialog.getDatePicker().setMaxDate(maxDate);

                datePickerDialog.show();


            }
        });



        inputEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                //c.add(Calendar.YEAR, (1960 - c.get(Calendar.YEAR)));

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRentPaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                inputEditTextStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);


                //Toast.makeText(getApplicationContext(),"Year Difference : " +(mYear - 18),Toast.LENGTH_SHORT).show();

                //c.add(Calendar.YEAR, +40);


//                long maxDate = c.getTime().getTime();
//                datePickerDialog.getDatePicker().setMaxDate(maxDate);

                datePickerDialog.show();


            }
        });




        inputEditTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                //c.add(Calendar.YEAR, (1960 - c.get(Calendar.YEAR)));

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRentPaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                inputEditTextEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);


                //Toast.makeText(getApplicationContext(),"Year Difference : " +(mYear - 18),Toast.LENGTH_SHORT).show();

                //c.add(Calendar.YEAR, +40);


//                long maxDate = c.getTime().getTime();
//                datePickerDialog.getDatePicker().setMaxDate(maxDate);

                datePickerDialog.show();


            }
        });

        rationButtonActionListener();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditRentPaymentActivity.this, AgreementPaymentActivity.class));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rentDuration = inputEditTextRentDuration.getText().toString();
                if(TextUtils.isEmpty(rentDuration)){
                    textInputLayoutRentDuration.setError("Enter Rent Duration");
                    textInputLayoutRentDuration.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutRentDuration.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutRentDuration.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutRentDuration.setError(null);
                    validate = true;
                }


                refundableDepositAmt = inputEditTextRefundableDepositAmt.getText().toString();
                if(TextUtils.isEmpty(refundableDepositAmt)){
                    textInputLayoutRefundableDepositAmt.setError("Enter Refundable Deposit Amount");
                    textInputLayoutRefundableDepositAmt.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutRefundableDepositAmt.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutRefundableDepositAmt.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutRefundableDepositAmt.setError(null);
                    validate = true;
                }


                monthlyRentAmt = inputEditTextMonthlyRentAmt.getText().toString();
                if(TextUtils.isEmpty(monthlyRentAmt)){
                    textInputLayoutMonthlyRentAmt.setError("Enter Monthly Rent Amount");
                    textInputLayoutMonthlyRentAmt.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutMonthlyRentAmt.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutMonthlyRentAmt.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutMonthlyRentAmt.setError(null);
                    validate = true;
                }


                rentType = ((RadioButton)findViewById(radioGroupRentType.getCheckedRadioButtonId())).getText().toString();
                if (TextUtils.isEmpty(rentType)){
                    radioButtonFixed.setError("Select Rent Type");
                    radioButtonFixed.setHintTextColor(getResources().getColor(R.color.red));
                    radioButtonFixed.requestFocus();

                    validate = false;

                    return;
                }
                else{
                    radioButtonFixed.setError(null);
                    validate = true;
                }

                /*Toast.makeText(getApplicationContext(),"rentType == Fixed : " + (rentType.equalsIgnoreCase("Fixed")),Toast.LENGTH_LONG).show();*/

                if(rentType.equalsIgnoreCase("Fixed")){



                    fixedAmt = inputEditTextFixedTypeAmt.getText().toString();
                    if(TextUtils.isEmpty(fixedAmt)){
                        textInputLayoutFixedTypeAmt.setError("Enter Fixed Type Amount");
                        textInputLayoutFixedTypeAmt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutFixedTypeAmt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutFixedTypeAmt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutFixedTypeAmt.setError(null);
                        validate = true;
                    }

                }
                else if(rentType.equalsIgnoreCase("Variable")){

                    month1Amt = inputEditTextVariableMonth1Amt.getText().toString();
                    if(TextUtils.isEmpty(month1Amt)){
                        textInputLayoutVariableMonth1Amt.setError("Enter Month-1 Rent Amount");
                        textInputLayoutVariableMonth1Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth1Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth1Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth1Amt.setError(null);
                        validate = true;
                    }


                    month2Amt = inputEditTextVariableMonth2Amt.getText().toString();
                    if(TextUtils.isEmpty(month2Amt)){
                        textInputLayoutVariableMonth2Amt.setError("Enter Month-2 Rent Amount");
                        textInputLayoutVariableMonth2Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth2Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth2Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth2Amt.setError(null);
                        validate = true;
                    }

                    month3Amt = inputEditTextVariableMonth3Amt.getText().toString();
                    if(TextUtils.isEmpty(month3Amt)){
                        textInputLayoutVariableMonth3Amt.setError("Enter Month-3 Rent Amount");
                        textInputLayoutVariableMonth3Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth3Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth3Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth3Amt.setError(null);
                        validate = true;
                    }


                    month4Amt = inputEditTextVariableMonth4Amt.getText().toString();
                    if(TextUtils.isEmpty(month4Amt)){
                        textInputLayoutVariableMonth4Amt.setError("Enter Month-4 Rent Amount");
                        textInputLayoutVariableMonth4Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth4Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth4Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth4Amt.setError(null);
                        validate = true;
                    }


                    month5Amt = inputEditTextVariableMonth5Amt.getText().toString();
                    if(TextUtils.isEmpty(month5Amt)){
                        textInputLayoutVariableMonth5Amt.setError("Enter Month-5 Rent Amount");
                        textInputLayoutVariableMonth5Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth5Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth5Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth5Amt.setError(null);
                        validate = true;
                    }


                    month6Amt = inputEditTextVariableMonth5Amt.getText().toString();
                    if(TextUtils.isEmpty(month6Amt)){
                        textInputLayoutVariableMonth6Amt.setError("Enter Month-6 Rent Amount");
                        textInputLayoutVariableMonth6Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth6Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth6Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth6Amt.setError(null);
                        validate = true;
                    }

                    month7Amt = inputEditTextVariableMonth7Amt.getText().toString();
                    if(TextUtils.isEmpty(month7Amt)){
                        textInputLayoutVariableMonth7Amt.setError("Enter Month-7 Rent Amount");
                        textInputLayoutVariableMonth7Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth7Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth7Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth7Amt.setError(null);
                        validate = true;
                    }


                    month8Amt = inputEditTextVariableMonth8Amt.getText().toString();
                    if(TextUtils.isEmpty(month8Amt)){
                        textInputLayoutVariableMonth8Amt.setError("Enter Month-8 Rent Amount");
                        textInputLayoutVariableMonth8Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth8Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth8Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth8Amt.setError(null);
                        validate = true;
                    }

                    month9Amt = inputEditTextVariableMonth9Amt.getText().toString();
                    if(TextUtils.isEmpty(month9Amt)){
                        textInputLayoutVariableMonth9Amt.setError("Enter Month-9 Rent Amount");
                        textInputLayoutVariableMonth9Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth9Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth9Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth9Amt.setError(null);
                        validate = true;
                    }

                    month10Amt = inputEditTextVariableMonth10Amt.getText().toString();
                    if(TextUtils.isEmpty(month10Amt)){
                        textInputLayoutVariableMonth10Amt.setError("Enter Month-10 Rent Amount");
                        textInputLayoutVariableMonth10Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth10Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth10Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth10Amt.setError(null);
                        validate = true;
                    }

                    month11Amt = inputEditTextVariableMonth11Amt.getText().toString();
                    if(TextUtils.isEmpty(month11Amt)){
                        textInputLayoutVariableMonth11Amt.setError("Enter Month-11 Rent Amount");
                        textInputLayoutVariableMonth11Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth11Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth11Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth11Amt.setError(null);
                        validate = true;
                    }

                    month12Amt = inputEditTextVariableMonth12Amt.getText().toString();
                    if(TextUtils.isEmpty(month12Amt)){
                        textInputLayoutVariableMonth12Amt.setError("Enter Month-12 Rent Amount");
                        textInputLayoutVariableMonth12Amt.setErrorTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth12Amt.setHintTextAppearance(R.style.error_appearance);
                        textInputLayoutVariableMonth12Amt.requestFocus();

                        validate = false;
                        return;
                    }else{
                        textInputLayoutVariableMonth12Amt.setError(null);
                        validate = true;
                    }

                }

                isDepositAmtPaid = ((RadioButton)findViewById(radioGroupIsDepositAmtPaid.getCheckedRadioButtonId())).getText().toString();
                if (TextUtils.isEmpty(isDepositAmtPaid)){
                    radioButtonYes.setError("Select Is Deposit Amount Paid?");
                    radioButtonYes.setHintTextColor(getResources().getColor(R.color.red));
                    radioButtonYes.requestFocus();

                    validate = false;

                    return;
                }
                else{
                    radioButtonYes.setError(null);
                    validate = true;
                }

                if(isDepositAmtPaid.equalsIgnoreCase("Yes")){

                    paymentMode = ((RadioButton)findViewById(radioGroupPaymentMode.getCheckedRadioButtonId())).getText().toString();
                    if (TextUtils.isEmpty(paymentMode)){
                        radioButtonCash.setError("Select Payment Mode");
                        radioButtonCash.setHintTextColor(getResources().getColor(R.color.red));
                        radioButtonCash.requestFocus();

                        validate = false;

                        return;
                    }
                    else{
                        radioButtonCash.setError(null);
                        validate = true;
                    }


                    if(paymentMode.equalsIgnoreCase("Cash")){

                        cashAmt = inputEditTextCashAmt.getText().toString();
                        if(TextUtils.isEmpty(cashAmt)){
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

                    }else if(paymentMode.equalsIgnoreCase("Cheque")){

                        bnkName = inputEditTextBnkName.getText().toString();
                        if(TextUtils.isEmpty(bnkName)){
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


                        branchName = inputEditTextBranchName.getText().toString();
                        if(TextUtils.isEmpty(branchName)){
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

                        chqDate = inputEditTextChqDate.getText().toString();
                        if(TextUtils.isEmpty(chqDate)){
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


                        chqAmt = inputEditTextCashAmt.getText().toString();
                        if(TextUtils.isEmpty(chqAmt)){
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


                        accNo = inputEditTextAccNo.getText().toString();
                        if(TextUtils.isEmpty(accNo)){
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

                    }


                }



                startDate = inputEditTextStartDate.getText().toString();
                if(TextUtils.isEmpty(startDate)){
                    textInputLayoutStartDate.setError("Enter Agreement Start Date");
                    textInputLayoutStartDate.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutStartDate.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutStartDate.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutStartDate.setError(null);
                    validate = true;
                }


                endDate = inputEditTextEndDate.getText().toString();
                if(TextUtils.isEmpty(endDate)){
                    textInputLayoutEndDate.setError("Enter Agreement End Date");
                    textInputLayoutEndDate.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutEndDate.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutEndDate.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutEndDate.setError(null);
                    validate = true;
                }

                csMailDocs = ((RadioButton)findViewById(radioGroupCourierService.getCheckedRadioButtonId())).getText().toString();
                if (TextUtils.isEmpty(csMailDocs)){
                    radioButtonCSNo.setError("Courier service mail to document?");
                    radioButtonCSNo.setHintTextColor(getResources().getColor(R.color.red));
                    radioButtonCSNo.requestFocus();

                    validate = false;

                    return;
                }
                else{
                    radioButtonCSNo.setError(null);
                    validate = true;
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

    private void getData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_RENT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

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

                                setData();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"catch : " + e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }else{

                            Toast.makeText(EditRentPaymentActivity.this, "Something went wrong.\n" + response, Toast.LENGTH_SHORT).show();

                            /*//Message other than Success
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

                        Toast.makeText(EditRentPaymentActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(EditRentPaymentActivity.this);
        requestQueue.add(request);

    }

    private void setData() {

        for (int i=0; i<rentList.size(); i++){

            rent = rentList.get(i);

            inputEditTextRentDuration.setText(rent.getProp_pay_period());
            inputEditTextRefundableDepositAmt.setText(rent.getProp_pay_deposit());
            inputEditTextMonthlyRentAmt.setText(rent.getProp_pay_var_mnth_per());
            //inputEditTextFixedTypeAmt.setText(rent.getProp_pay_rent_type());

            if(rent.getProp_pay_rent_type().equalsIgnoreCase("Fixed")){

                radioButtonFixed.setChecked(true);
                radioButtonVariable.setChecked(false);

                textInputLayoutFixedTypeAmt.setVisibility(View.VISIBLE);
                inputEditTextFixedTypeAmt.setVisibility(View.VISIBLE);

                inputEditTextFixedTypeAmt.setText(rent.getProp_pay_rent_fixed_amt());

                textInputLayoutVariableMonth1Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth1Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth2Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth2Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth3Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth3Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth4Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth4Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth5Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth5Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth6Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth6Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth7Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth7Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth8Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth8Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth9Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth9Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth10Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth10Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth11Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth11Amt.setVisibility(View.GONE);
                textInputLayoutVariableMonth12Amt.setVisibility(View.GONE);
                inputEditTextVariableMonth12Amt.setVisibility(View.GONE);

            }else if(rent.getProp_pay_rent_type().equalsIgnoreCase("Variable")){

                radioButtonFixed.setChecked(false);
                radioButtonVariable.setChecked(true);

                textInputLayoutFixedTypeAmt.setVisibility(View.GONE);
                inputEditTextFixedTypeAmt.setVisibility(View.GONE);


                textInputLayoutVariableMonth1Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth1Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth2Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth2Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth3Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth3Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth4Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth4Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth5Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth5Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth6Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth6Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth7Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth7Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth8Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth8Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth9Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth9Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth10Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth10Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth11Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth11Amt.setVisibility(View.VISIBLE);
                textInputLayoutVariableMonth12Amt.setVisibility(View.VISIBLE);
                inputEditTextVariableMonth12Amt.setVisibility(View.VISIBLE);


                inputEditTextVariableMonth1Amt.setText(rent.getProp_pay_rent1());
                inputEditTextVariableMonth2Amt.setText(rent.getProp_pay_rent2());
                inputEditTextVariableMonth3Amt.setText(rent.getProp_pay_rent3());
                inputEditTextVariableMonth4Amt.setText(rent.getProp_pay_rent4());
                inputEditTextVariableMonth5Amt.setText(rent.getProp_pay_rent5());
                inputEditTextVariableMonth6Amt.setText(rent.getProp_pay_rent6());
                inputEditTextVariableMonth7Amt.setText(rent.getProp_pay_rent7());
                inputEditTextVariableMonth8Amt.setText(rent.getProp_pay_rent8());
                inputEditTextVariableMonth9Amt.setText(rent.getProp_pay_rent9());
                inputEditTextVariableMonth10Amt.setText(rent.getProp_pay_rent10());
                inputEditTextVariableMonth11Amt.setText(rent.getProp_pay_rent11());
                inputEditTextVariableMonth12Amt.setText(rent.getProp_pay_rent12());

            }


            //Deposit payment status Yes or No (Radiobutton)
            //textViewIsDeptAmtPaidInfo.setText(rent.getProp_pay_dep_stat());

            if(rent.getProp_pay_dep_stat().equalsIgnoreCase("Yes")){

                radioButtonYes.setChecked(true);
                radioButtonNo.setChecked(false);

                //textViewPaymentModeInfo.setVisibility(View.VISIBLE);
                //TextViewPaymentMode.setVisibility(View.VISIBLE);

                //TextViewPaymentMode.setText(rent.getProp_pay_dep_stat_mode());


                if(rent.getProp_pay_dep_stat_mode().equalsIgnoreCase("Cash")){

                    radioButtonCash.setChecked(true);
                    radioButtonCheque.setChecked(false);

                    textInputLayoutChqAmt.setVisibility(View.GONE);
                    inputEditTextChqAmt.setVisibility(View.GONE);
                    textInputLayoutBranchName.setVisibility(View.GONE);
                    inputEditTextBnkName.setVisibility(View.GONE);
                    textInputLayoutBranchName.setVisibility(View.GONE);
                    inputEditTextBranchName.setVisibility(View.GONE);
                    textInputLayoutChqDate.setVisibility(View.GONE);
                    inputEditTextChqDate.setVisibility(View.GONE);
                    textInputLayoutAccNo.setVisibility(View.GONE);
                    inputEditTextAccNo.setVisibility(View.GONE);


                    textInputLayoutCashAmt.setVisibility(View.VISIBLE);
                    inputEditTextCashAmt.setVisibility(View.VISIBLE);

                    inputEditTextCashAmt.setText(rent.getProp_pay_cash_dt());


                }else if(rent.getProp_pay_dep_stat_mode().equalsIgnoreCase("Cheque")){

                    radioButtonCheque.setChecked(true);
                    radioButtonCash.setChecked(false);


                    textInputLayoutCashAmt.setVisibility(View.GONE);
                    inputEditTextCashAmt.setVisibility(View.GONE);

                    textInputLayoutChqAmt.setVisibility(View.VISIBLE);
                    inputEditTextChqAmt.setVisibility(View.VISIBLE);
                    textInputLayoutBranchName.setVisibility(View.VISIBLE);
                    inputEditTextBnkName.setVisibility(View.VISIBLE);
                    textInputLayoutBranchName.setVisibility(View.VISIBLE);
                    inputEditTextBranchName.setVisibility(View.VISIBLE);
                    textInputLayoutChqDate.setVisibility(View.VISIBLE);
                    inputEditTextChqDate.setVisibility(View.VISIBLE);
                    textInputLayoutAccNo.setVisibility(View.VISIBLE);
                    inputEditTextAccNo.setVisibility(View.VISIBLE);



                    inputEditTextChqAmt.setText(rent.getProp_pay_chk_no());
                    inputEditTextBnkName.setText(rent.getProp_pay_bank_name());
                    inputEditTextBranchName.setText(rent.getProp_pay_branch_name());
                    inputEditTextChqDate.setText(rent.getProp_pay_chk_dt());
                    inputEditTextAccNo.setText(rent.getProp_pay_bank_acc_no());

                }




            }else if(rent.getProp_pay_dep_stat().equalsIgnoreCase("No")){

                radioButtonNo.setChecked(true);
                radioButtonYes.setChecked(false);

                //textViewPaymentModeInfo.setVisibility(View.GONE);
                //TextViewPaymentMode.setVisibility(View.GONE);

                textInputLayoutCashAmt.setVisibility(View.GONE);
                inputEditTextCashAmt.setVisibility(View.GONE);

                textInputLayoutChqAmt.setVisibility(View.GONE);
                inputEditTextChqAmt.setVisibility(View.GONE);
                textInputLayoutBnkName.setVisibility(View.GONE);
                inputEditTextBnkName.setVisibility(View.GONE);
                textInputLayoutBranchName.setVisibility(View.GONE);
                inputEditTextBranchName.setVisibility(View.GONE);
                textInputLayoutChqDate.setVisibility(View.GONE);
                inputEditTextChqDate.setVisibility(View.GONE);
                textInputLayoutAccNo.setVisibility(View.GONE);
                inputEditTextAccNo.setVisibility(View.GONE);
            }

            inputEditTextStartDate.setText(rent.getProp_pay_agr_start_dt());
            inputEditTextEndDate.setText(rent.getProp_pay_agr_end_dt());

            //textViewCSStatus.setText(rent.getProp_pay_cr_srv());

            if(rent.getProp_pay_cr_srv().equalsIgnoreCase("Yes")){
                radioButtonCSNo.setChecked(false);
                radioButtonCSYes.setChecked(true);
            }else if(rent.getProp_pay_cr_srv().equalsIgnoreCase("No")){
                radioButtonCSYes.setChecked(true);
                radioButtonCSNo.setChecked(false);
            }

        }

    }

    private void showConfirmationMsg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(EditRentPaymentActivity.this);
        builder.setTitle(R.string.confirm_exit);
        builder.setMessage(R.string.confirm_msg);
        builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startActivity(new Intent(EditRentPaymentActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
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

    private void setDefaultStrValues() {
        /*rentID = FinalValues.EMPTY_STR;*/

        rentDuration = FinalValues.EMPTY_STR;
        refundableDepositAmt = FinalValues.EMPTY_STR;
        monthlyRentAmt = FinalValues.EMPTY_STR;

        rentType = FinalValues.EMPTY_STR;
        fixedAmt = FinalValues.EMPTY_STR;
        month1Amt = FinalValues.EMPTY_STR;
        month2Amt = FinalValues.EMPTY_STR;
        month3Amt = FinalValues.EMPTY_STR;
        month4Amt = FinalValues.EMPTY_STR;
        month5Amt = FinalValues.EMPTY_STR;
        month6Amt = FinalValues.EMPTY_STR;
        month7Amt = FinalValues.EMPTY_STR;
        month8Amt = FinalValues.EMPTY_STR;
        month9Amt = FinalValues.EMPTY_STR;
        month10Amt = FinalValues.EMPTY_STR;
        month11Amt = FinalValues.EMPTY_STR;
        month12Amt = FinalValues.EMPTY_STR;

        isDepositAmtPaid = FinalValues.EMPTY_STR;

        paymentMode = FinalValues.EMPTY_STR;
        cashAmt = FinalValues.EMPTY_STR;
        bnkName = FinalValues.EMPTY_STR;
        branchName = FinalValues.EMPTY_STR;
        chqDate = FinalValues.EMPTY_STR;
        chqAmt = FinalValues.EMPTY_STR;
        accNo = FinalValues.EMPTY_STR;

        startDate = FinalValues.EMPTY_STR;
        endDate = FinalValues.EMPTY_STR;

        csMailDocs = FinalValues.EMPTY_STR;

    }

    private void getPropertyID() {
        SharedPreferences sharedPreferences = EditRentPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        rentID = sharedPreferences.getString(IDTracker.RENT_ID, null);


        Toast.makeText(EditRentPaymentActivity.this, "User ID : " + userID + "\nProperty ID : " + propertyID + "\nRent ID : " + rentID, Toast.LENGTH_SHORT).show();

    }

    private void sendData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.EDIT_RENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            Toast.makeText(EditRentPaymentActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            /*//Start Welcome Activity (Owner Activity)
                            Snackbar snackbar = Snackbar.make(scrollView, "Property Rent Payment Details Created Successfully. ID : " + response, Snackbar.LENGTH_LONG);


                            //Creating a shared preference
                            SharedPreferences sharedPreferences = EditRentPaymentActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to the editor
                            editor.putString(IDTracker.RENT_ID, response);

                            //Saving values to the editor
                            editor.commit();



                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();*/

                            /*Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    Intent tenantIntent = new Intent(EditRentPaymentActivity.this, AgreementPaymentActivity.class);
                                    startActivity(tenantIntent);

                                }
                            }, 3000);*/



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
                params.put(Keys.USER_ID, userID);

                params.put(Keys.RENT_PERIOD, rentDuration);
                params.put(Keys.RENT_DEPOSIT, refundableDepositAmt);
                params.put(Keys.RENT_MONTHLY_RENT, monthlyRentAmt);
                params.put(Keys.RENT_RENT_TYPE, rentType);
                params.put(Keys.RENT_FIXED_AMT, fixedAmt);
                params.put(Keys.RENT_1, month1Amt);
                params.put(Keys.RENT_2, month2Amt);
                params.put(Keys.RENT_3, month3Amt);
                params.put(Keys.RENT_4, month4Amt);
                params.put(Keys.RENT_5, month5Amt);
                params.put(Keys.RENT_6, month6Amt);
                params.put(Keys.RENT_7, month7Amt);
                params.put(Keys.RENT_8, month8Amt);
                params.put(Keys.RENT_9, month9Amt);
                params.put(Keys.RENT_10, month10Amt);
                params.put(Keys.RENT_11, month11Amt);
                params.put(Keys.RENT_12, month12Amt);
                params.put(Keys.RENT_DEPOSIT_STAT, isDepositAmtPaid);
                params.put(Keys.RENT_DEPOSIT_STAT_MODE, paymentMode);
                params.put(Keys.RENT_CASH_DT, cashAmt);
                params.put(Keys.RENT_CHK_NO, chqAmt);
                params.put(Keys.RENT_BNK_NAME, bnkName);
                params.put(Keys.RENT_BRANCH_NAME, branchName);
                params.put(Keys.RENT_CHK_DT, chqDate);
                params.put(Keys.RENT_ACC_NO, accNo);
                params.put(Keys.RENT_AGR_START_DT, startDate);
                params.put(Keys.RENT_AGR_END_DT, endDate);
                params.put(Keys.RENT_CS_STAT, csMailDocs);
                params.put(Keys.STAT, FinalValues.STAT);


                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(EditRentPaymentActivity.this);
        requestQueue.add(request);



    }

    private void clearData() {

        inputEditTextRentDuration.setText(FinalValues.EMPTY_STR);
        inputEditTextRefundableDepositAmt.setText(FinalValues.EMPTY_STR);
        inputEditTextMonthlyRentAmt.setText(FinalValues.EMPTY_STR);
        inputEditTextFixedTypeAmt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth1Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth2Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth3Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth4Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth5Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth6Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth7Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth8Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth9Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth10Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth11Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextVariableMonth12Amt.setText(FinalValues.EMPTY_STR);
        inputEditTextCashAmt.setText(FinalValues.EMPTY_STR);
        inputEditTextBnkName.setText(FinalValues.EMPTY_STR);
        inputEditTextBranchName.setText(FinalValues.EMPTY_STR);
        inputEditTextChqDate.setText(FinalValues.EMPTY_STR);
        inputEditTextChqAmt.setText(FinalValues.EMPTY_STR);
        inputEditTextAccNo.setText(FinalValues.EMPTY_STR);
        inputEditTextStartDate.setText(FinalValues.EMPTY_STR);
        inputEditTextEndDate.setText(FinalValues.EMPTY_STR);

    }

    private void rationButtonActionListener() {

        radioGroupRentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View view = radioGroupRentType.findViewById(checkedId);
                int index = radioGroupRentType.indexOfChild(view);

                switch (index){

                    case 0:
                        textInputLayoutFixedTypeAmt.setVisibility(View.VISIBLE);
                        inputEditTextFixedTypeAmt.setVisibility(View.VISIBLE);

                        /*textInputLayoutVariableTypeAmt.setVisibility(View.GONE);
                        inputEditTextVariableTypeAmt.setVisibility(View.GONE);*/

                        textInputLayoutVariableMonth1Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth1Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth2Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth2Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth3Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth3Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth4Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth4Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth5Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth5Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth6Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth6Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth7Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth7Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth8Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth8Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth9Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth9Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth10Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth10Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth11Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth11Amt.setVisibility(View.GONE);

                        textInputLayoutVariableMonth12Amt.setVisibility(View.GONE);
                        inputEditTextVariableMonth12Amt.setVisibility(View.GONE);

                        break;
                    case 1:
                        textInputLayoutFixedTypeAmt.setVisibility(View.GONE);
                        inputEditTextFixedTypeAmt.setVisibility(View.GONE);

                        /*textInputLayoutVariableTypeAmt.setVisibility(View.VISIBLE);
                        inputEditTextVariableTypeAmt.setVisibility(View.VISIBLE);*/

                        textInputLayoutVariableMonth1Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth1Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth2Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth2Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth3Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth3Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth4Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth4Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth5Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth5Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth6Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth6Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth7Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth7Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth8Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth8Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth9Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth9Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth10Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth10Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth11Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth11Amt.setVisibility(View.VISIBLE);

                        textInputLayoutVariableMonth12Amt.setVisibility(View.VISIBLE);
                        inputEditTextVariableMonth12Amt.setVisibility(View.VISIBLE);

                        break;

                }

            }
        });



        radioGroupIsDepositAmtPaid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View view = radioGroupIsDepositAmtPaid.findViewById(checkedId);
                int index = radioGroupIsDepositAmtPaid.indexOfChild(view);

                switch (index){
                    case 0:

                        paymentMode = ((RadioButton)findViewById(radioGroupPaymentMode.getCheckedRadioButtonId())).getText().toString();

                        txtViewPaymentMode.setVisibility(View.VISIBLE);

                        radioGroupPaymentMode.setVisibility(View.VISIBLE);
                        radioButtonCash.setVisibility(View.VISIBLE);
                        radioButtonCheque.setVisibility(View.VISIBLE);

                        if(paymentMode.equalsIgnoreCase("Cash")){

                            textInputLayoutCashAmt.setVisibility(View.VISIBLE);
                            inputEditTextCashAmt.setVisibility(View.VISIBLE);

                            textInputLayoutBnkName.setVisibility(View.GONE);
                            inputEditTextBnkName.setVisibility(View.GONE);

                            textInputLayoutBranchName.setVisibility(View.GONE);
                            textInputLayoutBranchName.setVisibility(View.GONE);

                            textInputLayoutChqDate.setVisibility(View.GONE);
                            inputEditTextChqDate.setVisibility(View.GONE);

                            textInputLayoutChqAmt.setVisibility(View.GONE);
                            inputEditTextChqAmt.setVisibility(View.GONE);

                            textInputLayoutAccNo.setVisibility(View.GONE);
                            inputEditTextAccNo.setVisibility(View.GONE);

                        }else if(paymentMode.equalsIgnoreCase("Cheque")){

                            textInputLayoutCashAmt.setVisibility(View.GONE);
                            inputEditTextCashAmt.setVisibility(View.GONE);

                            textInputLayoutBnkName.setVisibility(View.VISIBLE);
                            inputEditTextBnkName.setVisibility(View.VISIBLE);

                            textInputLayoutBranchName.setVisibility(View.VISIBLE);
                            textInputLayoutBranchName.setVisibility(View.VISIBLE);

                            textInputLayoutChqDate.setVisibility(View.VISIBLE);
                            inputEditTextChqDate.setVisibility(View.VISIBLE);

                            textInputLayoutChqAmt.setVisibility(View.VISIBLE);
                            inputEditTextChqAmt.setVisibility(View.VISIBLE);

                            textInputLayoutAccNo.setVisibility(View.VISIBLE);
                            inputEditTextAccNo.setVisibility(View.VISIBLE);

                        }





                        break;

                    case 1:
                        textInputLayoutCashAmt.setVisibility(View.GONE);
                        inputEditTextCashAmt.setVisibility(View.GONE);

                        txtViewPaymentMode.setVisibility(View.GONE);

                        radioGroupPaymentMode.setVisibility(View.GONE);
                        radioButtonCash.setVisibility(View.GONE);
                        radioButtonCheque.setVisibility(View.GONE);

                        textInputLayoutBnkName.setVisibility(View.GONE);
                        inputEditTextBnkName.setVisibility(View.GONE);

                        textInputLayoutBranchName.setVisibility(View.GONE);
                        textInputLayoutBranchName.setVisibility(View.GONE);

                        textInputLayoutChqDate.setVisibility(View.GONE);
                        inputEditTextChqDate.setVisibility(View.GONE);

                        textInputLayoutChqAmt.setVisibility(View.GONE);
                        inputEditTextChqAmt.setVisibility(View.GONE);

                        textInputLayoutAccNo.setVisibility(View.GONE);
                        inputEditTextAccNo.setVisibility(View.GONE);


                        break;
                }

            }
        });



        radioGroupPaymentMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View view = radioGroupPaymentMode.findViewById(checkedId);
                int index = radioGroupPaymentMode.indexOfChild(view);

                switch (index){
                    case 0:
                        textInputLayoutCashAmt.setVisibility(View.VISIBLE);
                        inputEditTextCashAmt.setVisibility(View.VISIBLE);

                        textInputLayoutBnkName.setVisibility(View.GONE);
                        inputEditTextBnkName.setVisibility(View.GONE);

                        textInputLayoutBranchName.setVisibility(View.GONE);
                        textInputLayoutBranchName.setVisibility(View.GONE);

                        textInputLayoutChqDate.setVisibility(View.GONE);
                        inputEditTextChqDate.setVisibility(View.GONE);

                        textInputLayoutChqAmt.setVisibility(View.GONE);
                        inputEditTextChqAmt.setVisibility(View.GONE);

                        textInputLayoutAccNo.setVisibility(View.GONE);
                        inputEditTextAccNo.setVisibility(View.GONE);

                        break;
                    case 1:
                        textInputLayoutCashAmt.setVisibility(View.GONE);
                        inputEditTextCashAmt.setVisibility(View.GONE);

                        textInputLayoutBnkName.setVisibility(View.VISIBLE);
                        inputEditTextBnkName.setVisibility(View.VISIBLE);

                        textInputLayoutBranchName.setVisibility(View.VISIBLE);
                        textInputLayoutBranchName.setVisibility(View.VISIBLE);

                        textInputLayoutChqDate.setVisibility(View.VISIBLE);
                        inputEditTextChqDate.setVisibility(View.VISIBLE);

                        textInputLayoutChqAmt.setVisibility(View.VISIBLE);
                        inputEditTextChqAmt.setVisibility(View.VISIBLE);

                        textInputLayoutAccNo.setVisibility(View.VISIBLE);
                        inputEditTextAccNo.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });


    }

    private void setErrorEnable() {
        textInputLayoutRentDuration.setErrorEnabled(true);
        textInputLayoutRefundableDepositAmt.setErrorEnabled(true);
        textInputLayoutMonthlyRentAmt.setErrorEnabled(true);
        textInputLayoutFixedTypeAmt.setErrorEnabled(true);
        /*textInputLayoutVariableTypeAmt.setErrorEnabled(true);*/
        textInputLayoutVariableMonth1Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth2Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth3Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth4Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth5Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth6Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth7Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth8Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth9Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth10Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth11Amt.setErrorEnabled(true);
        textInputLayoutVariableMonth12Amt.setErrorEnabled(true);

        textInputLayoutCashAmt.setErrorEnabled(true);
        textInputLayoutBnkName.setErrorEnabled(true);
        textInputLayoutBranchName.setErrorEnabled(true);
        textInputLayoutChqDate.setErrorEnabled(true);
        textInputLayoutChqAmt.setErrorEnabled(true);
        textInputLayoutAccNo.setErrorEnabled(true);
        textInputLayoutStartDate.setErrorEnabled(true);
        textInputLayoutEndDate.setErrorEnabled(true);
    }

    private void disableSomeComponents() {
        /*textInputLayoutVariableTypeAmt.setVisibility(View.GONE);
        inputEditTextVariableTypeAmt.setVisibility(View.GONE);*/

        textInputLayoutVariableMonth1Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth1Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth2Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth2Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth3Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth3Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth4Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth4Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth5Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth5Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth6Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth6Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth7Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth7Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth8Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth8Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth9Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth9Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth10Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth10Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth11Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth11Amt.setVisibility(View.GONE);

        textInputLayoutVariableMonth12Amt.setVisibility(View.GONE);
        inputEditTextVariableMonth12Amt.setVisibility(View.GONE);



        txtViewPaymentMode.setVisibility(View.GONE);

        radioGroupPaymentMode.setVisibility(View.GONE);
        radioButtonCash.setVisibility(View.GONE);
        radioButtonCheque.setVisibility(View.GONE);

        textInputLayoutCashAmt.setVisibility(View.GONE);
        inputEditTextCashAmt.setVisibility(View.GONE);

        textInputLayoutBnkName.setVisibility(View.GONE);
        inputEditTextBnkName.setVisibility(View.GONE);

        textInputLayoutBranchName.setVisibility(View.GONE);
        textInputLayoutBranchName.setVisibility(View.GONE);

        textInputLayoutChqDate.setVisibility(View.GONE);
        inputEditTextChqDate.setVisibility(View.GONE);

        textInputLayoutChqAmt.setVisibility(View.GONE);
        inputEditTextChqAmt.setVisibility(View.GONE);

        textInputLayoutAccNo.setVisibility(View.GONE);
        inputEditTextAccNo.setVisibility(View.GONE);

    }

    private void initializeComponents() {

        scrollView = findViewById(R.id.scrollview_layout);

        textInputLayoutRentDuration = findViewById(R.id.txt_ip_rent_duration);
        textInputLayoutRefundableDepositAmt = findViewById(R.id.txt_ip_refundable_deposit_amt);
        textInputLayoutMonthlyRentAmt = findViewById(R.id.txt_ip_monthly_amt);
        textInputLayoutFixedTypeAmt = findViewById(R.id.txt_ip_fixed_amt);
        /*textInputLayoutVariableTypeAmt = findViewById(R.id.txt_ip_variable_amt);*/

        textInputLayoutVariableMonth1Amt = findViewById(R.id.txt_ip_variable_month1_amt);
        textInputLayoutVariableMonth2Amt = findViewById(R.id.txt_ip_variable_month2_amt);
        textInputLayoutVariableMonth3Amt = findViewById(R.id.txt_ip_variable_month3_amt);
        textInputLayoutVariableMonth4Amt = findViewById(R.id.txt_ip_variable_month4_amt);
        textInputLayoutVariableMonth5Amt = findViewById(R.id.txt_ip_variable_month5_amt);
        textInputLayoutVariableMonth6Amt = findViewById(R.id.txt_ip_variable_month6_amt);
        textInputLayoutVariableMonth7Amt = findViewById(R.id.txt_ip_variable_month7_amt);
        textInputLayoutVariableMonth8Amt = findViewById(R.id.txt_ip_variable_month8_amt);
        textInputLayoutVariableMonth9Amt = findViewById(R.id.txt_ip_variable_month9_amt);
        textInputLayoutVariableMonth10Amt = findViewById(R.id.txt_ip_variable_month10_amt);
        textInputLayoutVariableMonth11Amt = findViewById(R.id.txt_ip_variable_month11_amt);
        textInputLayoutVariableMonth12Amt = findViewById(R.id.txt_ip_variable_month12_amt);

        /**/
        textInputLayoutCashAmt = findViewById(R.id.txt_ip_cash_dt);
        textInputLayoutBnkName = findViewById(R.id.txt_ip_bank_name);
        textInputLayoutBranchName = findViewById(R.id.txt_ip_branch_name);
        textInputLayoutChqDate = findViewById(R.id.txt_ip_chk_date);
        textInputLayoutChqAmt = findViewById(R.id.txt_ip_chk_dt);
        textInputLayoutAccNo = findViewById(R.id.txt_ip_acc_no);
        textInputLayoutStartDate = findViewById(R.id.txt_ip_agreement_start_date);
        textInputLayoutEndDate = findViewById(R.id.txt_ip_agreement_end_date);

        inputEditTextRentDuration = findViewById(R.id.edt_rent_duration);
        inputEditTextRefundableDepositAmt = findViewById(R.id.edt_refundable_deposit_amt);
        inputEditTextMonthlyRentAmt = findViewById(R.id.edt_monthly_amt);
        inputEditTextFixedTypeAmt = findViewById(R.id.edt_fixed_amt);
        /*inputEditTextVariableTypeAmt = findViewById(R.id.edt_variable_amt);*/

        inputEditTextVariableMonth1Amt = findViewById(R.id.edt_variable_month1_amt);
        inputEditTextVariableMonth2Amt = findViewById(R.id.edt_variable_month2_amt);
        inputEditTextVariableMonth3Amt = findViewById(R.id.edt_variable_month3_amt);
        inputEditTextVariableMonth4Amt = findViewById(R.id.edt_variable_month4_amt);
        inputEditTextVariableMonth5Amt = findViewById(R.id.edt_variable_month5_amt);
        inputEditTextVariableMonth6Amt = findViewById(R.id.edt_variable_month6_amt);
        inputEditTextVariableMonth7Amt = findViewById(R.id.edt_variable_month7_amt);
        inputEditTextVariableMonth8Amt = findViewById(R.id.edt_variable_month8_amt);
        inputEditTextVariableMonth9Amt = findViewById(R.id.edt_variable_month9_amt);
        inputEditTextVariableMonth10Amt = findViewById(R.id.edt_variable_month10_amt);
        inputEditTextVariableMonth11Amt = findViewById(R.id.edt_variable_month11_amt);
        inputEditTextVariableMonth12Amt = findViewById(R.id.edt_variable_month12_amt);

        /**/
        inputEditTextCashAmt = findViewById(R.id.edt_cash_dt);
        inputEditTextBnkName = findViewById(R.id.edt_bnk_name);
        inputEditTextBranchName = findViewById(R.id.edt_branch_name);
        inputEditTextChqDate = findViewById(R.id.edt_chq_date);
        inputEditTextChqAmt = findViewById(R.id.edt_chq_dt);
        inputEditTextAccNo = findViewById(R.id.edt_acc_no);
        inputEditTextStartDate = findViewById(R.id.edt_agr_start_date);
        inputEditTextEndDate = findViewById(R.id.edt_agr_end_date);


        radioGroupRentType = findViewById(R.id.rgRentType);
        radioButtonFixed = findViewById(R.id.rdFixed);
        radioButtonVariable = findViewById(R.id.rdVariable);

        radioGroupIsDepositAmtPaid = findViewById(R.id.rgDepositPaid);
        radioButtonYes = findViewById(R.id.rdYes);
        radioButtonNo = findViewById(R.id.rdNo);

        radioGroupPaymentMode = findViewById(R.id.rgPaymentMode);
        radioButtonCash = findViewById(R.id.rdCash);
        radioButtonCheque = findViewById(R.id.rdCheque);

        radioGroupCourierService = findViewById(R.id.rgMailDocs);
        radioButtonCSYes = findViewById(R.id.rdMailDocYes);
        radioButtonCSNo = findViewById(R.id.rdMailDocNo);

        txtViewPaymentMode = findViewById(R.id.txtViewPaymentMode);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        rentList = new ArrayList<>();

    }


}
