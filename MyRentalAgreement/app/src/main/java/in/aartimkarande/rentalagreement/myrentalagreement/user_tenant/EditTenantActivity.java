package in.aartimkarande.rentalagreement.myrentalagreement.user_tenant;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import in.aartimkarande.rentalagreement.myrentalagreement.model.Tenant;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness1.Witness1Activity;

public class EditTenantActivity extends AppCompatActivity {

    //Component
    private ScrollView scrollView;

    private Spinner spinnerProfession;
    private Spinner spinnerCoLists;

    private AlertDialog alertDialog;

    private TextInputLayout textInputLayoutFName;
    private TextInputLayout textInputLayoutMName;
    private TextInputLayout textInputLayoutLName;

    private TextInputLayout textInputLayoutDob;

    private TextInputLayout textInputLayoutPan;
    private TextInputLayout textInputLayoutAadhar;



    private TextInputLayout textInputLayoutPhone;

    private TextInputLayout textInputLayoutBldgName;
    private TextInputLayout textInputLayoutFlatNo;
    private TextInputLayout textInputLayoutFloorNo;
    private TextInputLayout textInputLayoutRoad;
    private TextInputLayout textInputLayoutArea;
    private TextInputLayout textInputLayoutSuburban;
    private TextInputLayout textInputLayoutCity;
    private TextInputLayout textInputLayoutTaluka;
    private TextInputLayout textInputLayoutState;
    private TextInputLayout textInputLayoutPincode;


    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioBtn;
    private RadioButton femaleRadioBtn;


    private TextInputEditText inputEditTextFName;
    private TextInputEditText inputEditTextMName;
    private TextInputEditText inputEditTextLName;

    private TextInputEditText inputEditTextDob;

    private TextInputEditText inputEditTextPan;
    private TextInputEditText inputEditTextAadhar;


    private TextInputEditText inputEditTextPhone;

    private TextInputEditText inputEditTextBldgName;
    private TextInputEditText inputEditTextFlatNo;
    private TextInputEditText inputEditTextFloorNo;
    private TextInputEditText inputEditTextRoad;
    private TextInputEditText inputEditTextArea;
    private TextInputEditText inputEditTextSuburban;
    private TextInputEditText inputEditTextCity;
    private TextInputEditText inputEditTextTaluka;
    private TextInputEditText inputEditTextState;
    private TextInputEditText inputEditTextPincode;

    private Button btnSubmit;
    private Button btnBack;


    //Primitive Datatypes and Wrapper Classes
    private String tenantID;

    private String fName;
    private String mName;
    private String lName;

    private String dob;

    private String pan;
    private String aadhar;

    private String gender;

    private String phone;

    private String profession;
    private String bldgName;
    private String flatNo;
    private String floorNo;
    private String road;
    private String area;
    private String suburban;
    private String city;
    private String taluka;
    private String state;
    private String pincode;

    private String co;

    private String propertyID;

    private String userID;
    private String serviceType;

    private List<Tenant> tenantList;

    private int mYear;
    private int mMonth;
    private int mDay;

    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tenant);

        //Methods
        getPropertyID();

        //Initializing Components
        initializeComponents();

        getData();

        //Methods
        loadSpinner();
        setErrorEnable();

        //Action Listener
        inputEditTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                c.add(Calendar.YEAR, (1960 - c.get(Calendar.YEAR)));

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTenantActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                inputEditTextDob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, 0, 1);


                //Toast.makeText(getApplicationContext(),"Year Difference : " +(mYear - 18),Toast.LENGTH_SHORT).show();

                //c.add(Calendar.YEAR, +40);


//                long maxDate = c.getTime().getTime();
//                datePickerDialog.getDatePicker().setMaxDate(maxDate);

                datePickerDialog.show();


            }
        });


        inputEditTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > textInputLayoutPhone.getCounterMaxLength()  ||  s.length() < textInputLayoutPhone.getCounterMaxLength()){
                    textInputLayoutPhone.setError("Invalid Mobile No. Length must be 10");
                }else{
                    textInputLayoutPhone.setError(null);
                }

            }
        });



        inputEditTextAadhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > textInputLayoutAadhar.getCounterMaxLength() ||  s.length() < textInputLayoutAadhar.getCounterMaxLength()){
                    textInputLayoutAadhar.setError("Invalid Aadhar Card No. Length must be 12");
                }else{
                    textInputLayoutAadhar.setError(null);
                }

            }
        });


        inputEditTextPan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > textInputLayoutPan.getCounterMaxLength()  ||  s.length() < textInputLayoutPan.getCounterMaxLength()){
                    textInputLayoutPan.setError("Invalid Pan Card No. Length must be 10");
                }else{
                    textInputLayoutPan.setError(null);
                }

            }
        });

        inputEditTextPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > textInputLayoutPincode.getCounterMaxLength() || s.length() < textInputLayoutPincode.getCounterMaxLength()){
                    textInputLayoutPincode.setError("Invalid pin code.");
                }else{
                    textInputLayoutPincode.setError(null);
                }

            }
        });



        spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profession = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerCoLists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                co = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fName = inputEditTextFName.getText().toString();
                if(TextUtils.isEmpty(fName)){
                    textInputLayoutFName.setError("Enter First Name");
                    textInputLayoutFName.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutFName.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutFName.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutFName.setError(null);
                    validate = true;
                }



                mName = inputEditTextMName.getText().toString();
                if(TextUtils.isEmpty(mName)){
                    textInputLayoutMName.setError("Enter Middle Name");
                    textInputLayoutMName.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutMName.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutMName.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutMName.setError(null);
                    validate = true;
                }



                lName = inputEditTextLName.getText().toString();
                if(TextUtils.isEmpty(lName)){
                    textInputLayoutLName.setError("Enter Last Name");
                    textInputLayoutLName.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutLName.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutLName.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutLName.setError(null);
                    validate = true;
                }


                dob = inputEditTextDob.getText().toString();
                if(TextUtils.isEmpty(dob)){
                    textInputLayoutDob.setError("Select Date of Birth");
                    textInputLayoutDob.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutDob.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutDob.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutDob.setError(null);
                    validate = true;
                }


                pan = inputEditTextPan.getText().toString();
                if(TextUtils.isEmpty(dob)){
                    textInputLayoutPan.setError("Enter Pan Card Number");
                    textInputLayoutPan.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutPan.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutPan.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutPan.setError(null);
                    validate = true;
                }

                aadhar = inputEditTextAadhar.getText().toString();
                if(TextUtils.isEmpty(dob)){
                    textInputLayoutAadhar.setError("Enter Aadhar Card Number");
                    textInputLayoutAadhar.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutAadhar.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutAadhar.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutAadhar.setError(null);
                    validate = true;
                }

                gender = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString();
                if (TextUtils.isEmpty(gender)){
                    maleRadioBtn.setError("Select Gender");
                    maleRadioBtn.setHintTextColor(getResources().getColor(R.color.red));
                    maleRadioBtn.requestFocus();

                    validate = false;

                    return;
                }
                else{
                    maleRadioBtn.setError(null);
                    validate = true;
                }

                phone = inputEditTextPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    textInputLayoutPhone.setError("Enter Mobile Number");
                    textInputLayoutPhone.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutPhone.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutPhone.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutPhone.setError(null);
                    validate = true;
                }

                bldgName = inputEditTextBldgName.getText().toString();
                if(TextUtils.isEmpty(bldgName)){
                    textInputLayoutBldgName.setError("Enter Building Name");
                    textInputLayoutBldgName.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutBldgName.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutBldgName.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutBldgName.setError(null);
                    validate = true;
                }



                flatNo = inputEditTextFlatNo.getText().toString();
                if(TextUtils.isEmpty(flatNo)){
                    textInputLayoutFlatNo.setError("Flat No.");
                    textInputLayoutFlatNo.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutFlatNo.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutFlatNo.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutFlatNo.setError(null);
                    validate = true;
                }


                floorNo = inputEditTextFloorNo.getText().toString();
                if(TextUtils.isEmpty(floorNo)){
                    textInputLayoutFloorNo.setError("Enter Floor No.");
                    textInputLayoutFloorNo.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutFloorNo.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutFloorNo.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutFloorNo.setError(null);
                    validate = true;
                }



                road = inputEditTextRoad.getText().toString();
                if(TextUtils.isEmpty(road)){
                    textInputLayoutRoad.setError("Enter Road Name");
                    textInputLayoutRoad.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutRoad.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutRoad.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutRoad.setError(null);
                    validate = true;
                }


                area = inputEditTextArea.getText().toString();
                if(TextUtils.isEmpty(area)){
                    textInputLayoutArea.setError("Enter Area Name");
                    textInputLayoutArea.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutArea.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutArea.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutArea.setError(null);
                    validate = true;
                }


                suburban = inputEditTextSuburban.getText().toString();
                if(TextUtils.isEmpty(suburban)){
                    textInputLayoutSuburban.setError("Enter Suburban Name");
                    textInputLayoutSuburban.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutSuburban.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutSuburban.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutSuburban.setError(null);
                    validate = true;
                }


                city = inputEditTextCity.getText().toString();
                if(TextUtils.isEmpty(city)){
                    textInputLayoutCity.setError("Enter City Name");
                    textInputLayoutCity.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutCity.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutCity.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutCity.setError(null);
                    validate = true;
                }


                taluka = inputEditTextTaluka.getText().toString();
                if(TextUtils.isEmpty(taluka)){
                    textInputLayoutTaluka.setError("Enter Taluka Name");
                    textInputLayoutTaluka.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutTaluka.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutTaluka.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutTaluka.setError(null);
                    validate = true;
                }


                state = inputEditTextState.getText().toString();
                if(TextUtils.isEmpty(state)){
                    textInputLayoutState.setError("Enter State Name");
                    textInputLayoutState.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutState.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutState.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutState.setError(null);
                    validate = true;
                }

                pincode = inputEditTextPincode.getText().toString();
                if(TextUtils.isEmpty(pincode)){
                    textInputLayoutPincode.setError("Enter Pincode");
                    textInputLayoutPincode.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutPincode.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutPincode.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutPincode.setError(null);
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

    private void showConfirmationMsg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(EditTenantActivity.this);
        builder.setTitle(R.string.confirm_exit);
        builder.setMessage(R.string.confirm_msg);
        builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startActivity(new Intent(EditTenantActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
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

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_TENANT_DATA,
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
                                //converting the string to json array object
                                JSONArray jsonArray = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject data = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    tenantList.add(new Tenant(
                                            data.getString(Keys.GET_OT_ID),
                                            data.getString(Keys.GET_OT_PROP_ID),
                                            data.getString(Keys.GET_OT_FNAME),
                                            data.getString(Keys.GET_OT_MNAME),
                                            data.getString(Keys.GET_OT_LNAME),
                                            data.getString(Keys.GET_OT_PHOTO),
                                            data.getString(Keys.GET_OT_AGE),
                                            data.getString(Keys.GET_OT_GENDER),
                                            data.getString(Keys.GET_OT_MOB_NO),
                                            data.getString(Keys.GET_OT_PROF),
                                            data.getString(Keys.GET_OT_PAN_NO),
                                            data.getString(Keys.GET_OT_PAN_NO_UPD),
                                            data.getString(Keys.GET_OT_AADHAR_NO),
                                            data.getString(Keys.GET_OT_AADHAR_NO_FRONT_UPD),
                                            data.getString(Keys.GET_OT_AADHAR_NO_BACK_UPD),
                                            data.getString(Keys.GET_OT_BLD),
                                            data.getString(Keys.GET_OT_PLOT),
                                            data.getString(Keys.GET_OT_FLOOR_NO),
                                            data.getString(Keys.GET_OT_ROAD),
                                            data.getString(Keys.GET_OT_AREA),
                                            data.getString(Keys.GET_OT_SUBURBAN),
                                            data.getString(Keys.GET_OT_CITY),
                                            data.getString(Keys.GET_OT_TALUKA),
                                            data.getString(Keys.GET_OT_STATE),
                                            data.getString(Keys.GET_OT_PCODE),
                                            data.getString(Keys.GET_OT_CO),
                                            data.getString(Keys.GET_OT_STAT)

                                    ));
                                }

                                setData();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else{

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

                        Toast.makeText(EditTenantActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();


                        //Message other than Success
                        Snackbar snackbar = Snackbar.make(scrollView, "Error :  " + error, Snackbar.LENGTH_LONG);

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
                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_PROPERTY_ID, propertyID);
                params.put(Keys.ID_TENANT_ID, tenantID);

                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(EditTenantActivity.this);
        requestQueue.add(request);
    }

    private void setData() {

        for (int i=0; i<tenantList.size(); i++){
            Tenant tenant = tenantList.get(i);

            inputEditTextFName.setText(tenant.getfName());
            inputEditTextMName.setText(tenant.getmName());
            inputEditTextLName.setText(tenant.getlName());

            inputEditTextDob.setText(tenant.getAge());
            inputEditTextPan.setText(tenant.getPanNo());;

            inputEditTextAadhar.setText(tenant.getAadharNo());

            //.setText(tenant.getGender());

            inputEditTextPhone.setText(tenant.getMobNo());



            inputEditTextBldgName.setText(tenant.getBldg());
            inputEditTextFlatNo.setText(tenant.getPlot());
            inputEditTextFloorNo.setText(tenant.getFloorNo());
            inputEditTextRoad.setText(tenant.getRoad());
            inputEditTextArea.setText(tenant.getArea());
            inputEditTextSuburban.setText(tenant.getSuburban());
            inputEditTextCity.setText(tenant.getCity());
            inputEditTextTaluka.setText(tenant.getTaluka());
            inputEditTextState.setText(tenant.getState());
            inputEditTextPincode.setText(tenant.getPcode());


            ArrayAdapter arrayAdapter = (ArrayAdapter) spinnerProfession.getAdapter();
            int position = arrayAdapter.getPosition(tenant.getProf());

            spinnerProfession.setSelection(position);

            arrayAdapter = (ArrayAdapter) spinnerCoLists.getAdapter();
            position = arrayAdapter.getPosition(tenant.getNoCo());
            spinnerCoLists.setSelection(position);

        }

    }


    private void sendData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.EDIT_TENANT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            Toast.makeText(EditTenantActivity.this, "msg : " + response, Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    Intent witness1Intent = new Intent(EditTenantActivity.this, Witness1Activity.class);
                                    startActivity(witness1Intent);

                                }
                            }, 3000);


                        } else {

                            Toast.makeText(EditTenantActivity.this, "Msg : " + response, Toast.LENGTH_SHORT).show();

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(scrollView, "Invalid Credential", Snackbar.LENGTH_LONG);

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

                params.put(Keys.F_NAME, fName);
                params.put(Keys.M_NAME, mName);
                params.put(Keys.L_NAME, lName);
                params.put(Keys.DOB, dob);
                params.put(Keys.GENDER, gender);
                params.put(Keys.PHONE, phone);
                params.put(Keys.PROFESSION, profession);

                params.put(Keys.PAN_CARD, pan);
                params.put(Keys.AADHAR_CARD, aadhar);

                params.put(Keys.BLDG, bldgName);
                params.put(Keys.PLOT, flatNo);
                params.put(Keys.FLOOR_NO, floorNo);
                params.put(Keys.ROAD, road);
                params.put(Keys.AREA, area);
                params.put(Keys.SUBURBAN, suburban);
                params.put(Keys.CITY, city);
                params.put(Keys.TALUKA, taluka);
                params.put(Keys.STATE, state);
                params.put(Keys.PIN_CODE, pincode);

                params.put(Keys.CO_NUM, co);

                params.put(Keys.STAT, FinalValues.STAT);


                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(EditTenantActivity.this);
        requestQueue.add(request);


    }

    private void clearData() {
        inputEditTextFName.setText(FinalValues.EMPTY_STR);
        inputEditTextMName.setText(FinalValues.EMPTY_STR);
        inputEditTextLName.setText(FinalValues.EMPTY_STR);

        inputEditTextPhone.setText(FinalValues.EMPTY_STR);
        inputEditTextDob.setText(FinalValues.EMPTY_STR);

        inputEditTextPan.setText(FinalValues.EMPTY_STR);
        inputEditTextAadhar.setText(FinalValues.EMPTY_STR);

        inputEditTextBldgName.setText(FinalValues.EMPTY_STR);
        inputEditTextFlatNo.setText(FinalValues.EMPTY_STR);
        inputEditTextFloorNo.setText(FinalValues.EMPTY_STR);
        inputEditTextRoad.setText(FinalValues.EMPTY_STR);
        inputEditTextArea.setText(FinalValues.EMPTY_STR);
        inputEditTextSuburban.setText(FinalValues.EMPTY_STR);
        inputEditTextCity.setText(FinalValues.EMPTY_STR);
        inputEditTextTaluka.setText(FinalValues.EMPTY_STR);
        inputEditTextState.setText(FinalValues.EMPTY_STR);
        inputEditTextPincode.setText(FinalValues.EMPTY_STR);
    }

    private void loadSpinner(){
        //Loads Professional
        List<String> professionLists = new ArrayList<String>();
        String[] lists = getResources().getStringArray(R.array.profession_lists);

        professionLists.addAll(Arrays.asList(lists));

/*        for (String str: lists) {
            professionLists.add(str);
        }*/

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfession.setAdapter(arrayAdapter);




        //Loads co_num
        List<String> coNumLists = new ArrayList<String>();
        String[] lists1 = getResources().getStringArray(R.array.co_num);

        coNumLists.addAll(Arrays.asList(lists1));

/*        for (String str: lists) {
            coNumLists.add(str);
        }*/

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoLists.setAdapter(arrayAdapter1);
    }

    private void setErrorEnable() {

        textInputLayoutBldgName.setErrorEnabled(true);
        textInputLayoutFlatNo.setErrorEnabled(true);
        textInputLayoutFloorNo.setErrorEnabled(true);
        textInputLayoutRoad.setErrorEnabled(true);
        textInputLayoutArea.setErrorEnabled(true);
        textInputLayoutSuburban.setErrorEnabled(true);
        textInputLayoutCity.setErrorEnabled(true);
        textInputLayoutTaluka.setErrorEnabled(true);
        textInputLayoutState.setErrorEnabled(true);
        textInputLayoutPincode.setErrorEnabled(true);
    }


    private void initializeComponents() {
        scrollView = findViewById(R.id.scrollview_layout);

        spinnerProfession = findViewById(R.id.spinnerProfession);
        spinnerCoLists = findViewById(R.id.spinner_co);

        textInputLayoutFName = findViewById(R.id.txt_ip_fname);
        textInputLayoutMName = findViewById(R.id.txt_ip_mname);
        textInputLayoutLName = findViewById(R.id.txt_ip_lname);

        textInputLayoutDob = findViewById(R.id.txt_ip_dob);

        textInputLayoutPan = findViewById(R.id.txt_ip_panCard);
        textInputLayoutAadhar = findViewById(R.id.txt_ip_aadharCard);

        textInputLayoutPhone = findViewById(R.id.txt_ip_phone);

        textInputLayoutBldgName = findViewById(R.id.txt_ip_nameBldg);
        textInputLayoutFlatNo = findViewById(R.id.txt_ip_flatNo);
        textInputLayoutFloorNo = findViewById(R.id.txt_ip_floorNo);
        textInputLayoutRoad = findViewById(R.id.txt_ip_road);
        textInputLayoutArea = findViewById(R.id.txt_ip_area);
        textInputLayoutSuburban = findViewById(R.id.txt_ip_suburban);
        textInputLayoutCity = findViewById(R.id.txt_ip_city);
        textInputLayoutTaluka = findViewById(R.id.txt_ip_taluka);
        textInputLayoutState = findViewById(R.id.txt_ip_state);
        textInputLayoutPincode = findViewById(R.id.txt_ip_pincode);

        genderRadioGroup = findViewById(R.id.radioGenderGroup);
        maleRadioBtn = findViewById(R.id.radioMale);
        femaleRadioBtn = findViewById(R.id.radioFemale);


        inputEditTextFName = findViewById(R.id.edt_fname);
        inputEditTextMName = findViewById(R.id.edt_mname);
        inputEditTextLName = findViewById(R.id.edt_lname);

        inputEditTextDob = findViewById(R.id.edt_dob);

        inputEditTextPan = findViewById(R.id.edt_pan);
        inputEditTextAadhar = findViewById(R.id.edt_aadhar);


        inputEditTextPhone = findViewById(R.id.edt_phone);

        inputEditTextBldgName = findViewById(R.id.edt_nameBldg);
        inputEditTextFlatNo = findViewById(R.id.edt_flatNo);
        inputEditTextFloorNo = findViewById(R.id.edt_floorNo);
        inputEditTextRoad = findViewById(R.id.edt_road);
        inputEditTextArea = findViewById(R.id.edt_area);
        inputEditTextSuburban = findViewById(R.id.edt_suburban);;
        inputEditTextCity = findViewById(R.id.edt_city);
        inputEditTextTaluka = findViewById(R.id.edt_taluka);
        inputEditTextState = findViewById(R.id.edt_state);
        inputEditTextPincode = findViewById(R.id.edt_pincode);


        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        tenantList = new ArrayList<>();

    }

    private void getPropertyID() {
        SharedPreferences sharedPreferences = EditTenantActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        tenantID = sharedPreferences.getString(IDTracker.TENT_ID, null);
    }


}
