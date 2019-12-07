package in.aartimkarande.rentalagreement.myrentalagreement.user_witness2;

import android.app.DatePickerDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.transaction.RentPaymentActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.OwnerActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_tenant.TenantActivity;

public class Witness2Activity extends AppCompatActivity {

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


    //Primitive Datatypes and Wrapper Classes
    private String witnessID;



    private String wit1_fName;
    private String wit1_mName;
    private String wit1_lName;

    private String wit1_dob;

    private String wit1_pan;
    private String wit1_aadhar;

    private String wit1_gender;

    private String wit1_phone;

    private String wit1_profession;
    private String wit1_bldgName;
    private String wit1_flatNo;
    private String wit1_floorNo;
    private String wit1_road;
    private String wit1_area;
    private String wit1_suburban;
    private String wit1_city;
    private String wit1_taluka;
    private String wit1_state;
    private String wit1_pincode;






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

    private int mYear;
    private int mMonth;
    private int mDay;

    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witness2);


        //Methods
        getPropertyID();

        //Initializing Components
        initializeComponents();

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


                DatePickerDialog datePickerDialog = new DatePickerDialog(Witness2Activity.this,
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

                if(s.length() > textInputLayoutAadhar.getCounterMaxLength()  ||  s.length() < textInputLayoutAadhar.getCounterMaxLength()){
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




        spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profession = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


/*        spinnerCoLists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                co = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

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

    }

    private void sendData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.WITNESS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            //Start Welcome Activity (Owner Activity)
                            Snackbar snackbar = Snackbar.make(scrollView, "Witness Details Created Successfully. ID : " + response, Snackbar.LENGTH_LONG);


                            //Creating a shared preference
                            SharedPreferences sharedPreferences = Witness2Activity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to the editor
                            editor.putString(IDTracker.WIT_ID, response);

                            //Saving values to the editor
                            editor.commit();



                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    Intent ownerIntent = new Intent(Witness2Activity.this, RentPaymentActivity.class);
                                    startActivity(ownerIntent);

                                }
                            }, 3000);



                        } else {

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


                params.put(Keys.WIT1_F_NAME, wit1_fName);
                params.put(Keys.WIT1_M_NAME, wit1_mName);
                params.put(Keys.WIT1_L_NAME, wit1_lName);
                params.put(Keys.WIT1_DOB, wit1_dob);
                params.put(Keys.WIT1_GENDER, wit1_gender);
                params.put(Keys.WIT1_PHONE, wit1_phone);
                params.put(Keys.WIT1_PROFESSION, wit1_profession);
                params.put(Keys.WIT1_PAN_CARD, wit1_pan);
                params.put(Keys.WIT1_AADHAR_CARD, wit1_aadhar);
                params.put(Keys.WIT1_BLDG, wit1_bldgName);
                params.put(Keys.WIT1_PLOT, wit1_flatNo);
                params.put(Keys.WIT1_FLOOR_NO, wit1_floorNo);
                params.put(Keys.WIT1_ROAD, wit1_road);
                params.put(Keys.WIT1_AREA, wit1_area);
                params.put(Keys.WIT1_SUBURBAN, wit1_suburban);
                params.put(Keys.WIT1_CITY, wit1_city);
                params.put(Keys.WIT1_TALUKA, wit1_taluka);
                params.put(Keys.WIT1_STATE, wit1_state);
                params.put(Keys.WIT1_PIN_CODE, wit1_pincode);


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

                /*params.put(Keys.CO_NUM, co);*/

                params.put(Keys.STAT, FinalValues.STAT);


                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(Witness2Activity.this);
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




/*        //Loads co_num
        List<String> coNumLists = new ArrayList<String>();
        String[] lists1 = getResources().getStringArray(R.array.co_num);

        coNumLists.addAll(Arrays.asList(lists1));

*//*        for (String str: lists) {
            coNumLists.add(str);
        }*//*

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoLists.setAdapter(arrayAdapter1);*/
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
        /*spinnerCoLists = findViewById(R.id.spinner_co);*/

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





        Intent intent = getIntent();

        wit1_fName =  intent.getStringExtra("wit1_fname");
        wit1_mName =  intent.getStringExtra("wit1_mname");
        wit1_lName =  intent.getStringExtra("wit1_lname");
        wit1_dob =  intent.getStringExtra("wit1_dob");
        wit1_pan =  intent.getStringExtra("wit1_pan");
        wit1_aadhar =  intent.getStringExtra("wit1_aadhar");
        wit1_gender =  intent.getStringExtra("wit1_gender");
        wit1_phone =  intent.getStringExtra("wit1_phone");
        wit1_profession =  intent.getStringExtra("wit1_prof");
        wit1_bldgName =  intent.getStringExtra("wit1_bldg");
        wit1_flatNo =  intent.getStringExtra("wit1_flatno");
        wit1_floorNo =  intent.getStringExtra("wit1_floorno");
        wit1_road =  intent.getStringExtra("wit1_road");
        wit1_area =  intent.getStringExtra("wit1_area");
        wit1_suburban =  intent.getStringExtra("wit1_suburban");
        wit1_city =  intent.getStringExtra("wit1_city");
        wit1_taluka =  intent.getStringExtra("wit1_taluka");
        wit1_state =  intent.getStringExtra("wit1_state");
        wit1_pincode =  intent.getStringExtra("wit1_pincode");


    }

    private void getPropertyID() {
        SharedPreferences sharedPreferences = Witness2Activity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = Witness2Activity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);

        if(witnessID != null) {
            Toast.makeText(getApplicationContext(),"onResume() called.\nWitness ID : " + witnessID,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Witness2Activity.this, TenantActivity.class));
        }

        //trackFormStatus();
    }

}
