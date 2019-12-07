package in.aartimkarande.rentalagreement.myrentalagreement.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;

public class RegisterActivity extends AppCompatActivity {

    //Component
    private RelativeLayout relativeLayout;

    private Spinner spinnerProfession; // Salaried / Business / Retired / Housewife
    private Spinner spinnerUserType; // owner / tenant / agent / broker / employee


    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPhone;
    private TextInputLayout textInputLayoutDob;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPwd;


    private TextInputEditText inputEditTextName;
    private TextInputEditText inputEditTextEmail;
    private TextInputEditText inputEditTextPhone;
    private TextInputEditText inputEditTextDob;
    private TextInputEditText inputEditTextUsername;
    private TextInputEditText inputEditTextPwd;


    private Button btnRegister;



    //Primitive Datatypes and Wrapper Classes
    private static final String[] PROFESSION_LISTS = {"Salaried", "Business", "Retired", "Housewife"};
    private static final String[] USER_TYPES_LISTS = {"Owner", "Tenant", "Agent", "Broker", "Employee"};

    String fullName;
    String email;
    String phone;
    String profession;
    String dob;
    String usrName;
    String pwd;
    String userType;

    private int mYear;
    private int mMonth;
    private int mDay;

    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        //Initializing Components
        initializeComponents();

        //Methods
        loadSpinners();
        setErrorEnable();





        //Action Listener
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


        inputEditTextPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if( s.length() < 8){
                    textInputLayoutPwd.setError("Password length must be at least 8");
                }else{
                    textInputLayoutPwd.setError(null);
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


        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        inputEditTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                c.add(Calendar.YEAR, (1960 - c.get(Calendar.YEAR)));

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

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



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fullName = inputEditTextName.getText().toString();
                if(TextUtils.isEmpty(fullName)){
                    textInputLayoutName.setError("Enter Name");
                    textInputLayoutName.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutName.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutName.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutName.setError(null);
                    validate = true;
                }



                email = inputEditTextEmail.getText().toString();
                if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                    textInputLayoutEmail.setError("Enter Email-ID");
                    textInputLayoutEmail.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutEmail.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutEmail.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutEmail.setError(null);
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



                usrName = inputEditTextUsername.getText().toString();
                if(TextUtils.isEmpty(usrName)){
                    textInputLayoutUsername.setError("Enter Username");
                    textInputLayoutUsername.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutUsername.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutUsername.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutUsername.setError(null);
                    validate = true;
                }


                pwd = inputEditTextPwd.getText().toString();
                if(TextUtils.isEmpty(pwd)){
                    textInputLayoutPwd.setError("Enter Password");
                    textInputLayoutPwd.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutPwd.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutPwd.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutPwd.setError(null);
                    validate = true;
                }


                if (validate) sendData();



            }
        });




    }

    private void initializeComponents() {
        relativeLayout = findViewById(R.id.reg_relative_layout);

        textInputLayoutName = findViewById(R.id.txt_ip_name);
        textInputLayoutEmail = findViewById(R.id.txt_ip_email);
        textInputLayoutPhone = findViewById(R.id.txt_ip_phone);
        textInputLayoutDob = findViewById(R.id.txt_ip_dob);
        textInputLayoutUsername =findViewById(R.id.txt_ip_username_reg);
        textInputLayoutPwd = findViewById(R.id.txt_ip_password_reg);


        spinnerProfession = findViewById(R.id.spinner_profession);
        spinnerUserType = findViewById(R.id.spinner_reg_user_type);


        inputEditTextName = findViewById(R.id.edt_reg_full_name);
        inputEditTextEmail = findViewById(R.id.edt_reg_email);
        inputEditTextPhone = findViewById(R.id.edt_reg_phone);
        inputEditTextDob = findViewById(R.id.edt_reg_dob);
        inputEditTextUsername =findViewById(R.id.edt_username_reg);
        inputEditTextPwd = findViewById(R.id.edt_pwd_reg);

        btnRegister = findViewById(R.id.btnRegNow);
    }

    private void sendData() {


        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (response.equalsIgnoreCase(FinalValues.SUCCESS_MSG)) {


                            //Start Welcome Activity (Login Activity)
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Registration Success", Snackbar.LENGTH_LONG);

                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    // yourMethod();
                                    Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(regIntent);
                                }
                            }, 3000);



                        }
                        else if(response.equalsIgnoreCase(FinalValues.EXISTS_MSG)){

                            Snackbar snackbar = Snackbar.make(relativeLayout, "You're already registered", Snackbar.LENGTH_LONG);

                            //Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            snackbar.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    // yourMethod();
                                    Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(regIntent);
                                }
                            }, 3000);


                        } else {

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Something went wrong. Please try again later!", Snackbar.LENGTH_LONG);

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

                        Snackbar snackbar = Snackbar.make(relativeLayout, "Error : " + error.getMessage(), Snackbar.LENGTH_LONG);

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

                //Adding Paramenter to the request
                params.put(Keys.REG_USR_NAME, fullName);
                params.put(Keys.REG_USR_EMAIL, email);
                params.put(Keys.REG_USR_PHONE, phone);
                params.put(Keys.REG_USR_PROFESSION, profession);
                params.put(Keys.REG_USR_DOB, dob);
                params.put(Keys.REG_USR_USRNAME, usrName);
                params.put(Keys.REG_USR_PWD, pwd);
                params.put(Keys.REG_USR_TYPE, userType);

                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(request);


    }

    private void clearData() {
        inputEditTextName.setText(FinalValues.EMPTY_STR);
        inputEditTextEmail.setText(FinalValues.EMPTY_STR);
        inputEditTextPhone.setText(FinalValues.EMPTY_STR);
        inputEditTextDob.setText(FinalValues.EMPTY_STR);
        inputEditTextUsername.setText(FinalValues.EMPTY_STR);
        inputEditTextPwd.setText(FinalValues.EMPTY_STR);
    }


    private void setErrorEnable() {
        textInputLayoutName.setErrorEnabled(true);
        textInputLayoutEmail.setErrorEnabled(true);
        textInputLayoutPhone.setErrorEnabled(true);
        textInputLayoutDob.setErrorEnabled(true);
        textInputLayoutUsername.setErrorEnabled(true);
        textInputLayoutPwd.setErrorEnabled(true);
    }



    private void loadSpinners() {

        //Loads Professional
        List<String> professionLists = new ArrayList<String>();
        for (String str: PROFESSION_LISTS) {
            professionLists.add(str);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, professionLists);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfession.setAdapter(arrayAdapter);



        //Loads User Types
        List<String> userTypeLists = new ArrayList<String>();
        for (String str: USER_TYPES_LISTS) {
            userTypeLists.add(str);
        }

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userTypeLists);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(arrayAdapter1);

    }


}
