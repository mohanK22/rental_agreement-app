package in.aartimkarande.rentalagreement.myrentalagreement.user_witness1;

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
import in.aartimkarande.rentalagreement.myrentalagreement.model.Witness;
import in.aartimkarande.rentalagreement.myrentalagreement.user_tenant.EditTenantActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness2.EditWitness2Activity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness2.Witness2Activity;

public class EditWitness1Activity extends AppCompatActivity {

    private ScrollView scrollView;

    private Spinner spinnerProfession;
    private AlertDialog alertDialog;

    private TextInputLayout textInputLayoutFName;
    private TextInputLayout textInputLayoutDob;
    private TextInputLayout textInputLayoutPhone;
    private TextInputLayout textInputLayoutPan;
    private TextInputLayout textInputLayoutAadhar;
    private TextInputLayout textInputLayoutAddr1;
    private TextInputLayout textInputLayoutAddr2;

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioBtn;
    private RadioButton femaleRadioBtn;

    private TextInputEditText inputEditTextFName;
    private TextInputEditText inputEditTextDob;
    private TextInputEditText inputEditTextPhone;
    private TextInputEditText inputEditTextPan;
    private TextInputEditText inputEditTextAadhar;
    private TextInputEditText inputEditTextAddr1;
    private TextInputEditText inputEditTextAddr2;


    private Button btnSubmit;
    private Button btnBack;
    private Button btnWit2;

    private String witnessID;

    private String fName;
    private String dob;
    private String gender;
    private String phone;
    private String pan;
    private String aadhar;
    private String profession;
    private String addr1;
    private String addr2;

    private String propertyID;

    private int mYear;
    private int mMonth;
    private int mDay;

    private List<Witness> witnessList;

    private boolean validate = false;

    private Witness witness;

    private String userID;
    private String serviceType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_witness1);

        getPropertyID();

        initializeComponents();

        getData();

        loadSpinner();
        setErrorEnable();


        inputEditTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                c.add(Calendar.YEAR, (1960 - c.get(Calendar.YEAR)));

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditWitness1Activity.this,
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


        spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profession = parent.getItemAtPosition(position).toString();
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
                    textInputLayoutFName.setError("Enter Full Name");
                    textInputLayoutFName.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutFName.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutFName.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutFName.setError(null);
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


                addr1 = inputEditTextAddr1.getText().toString();
                if(TextUtils.isEmpty(addr1)){
                    textInputLayoutAddr1.setError("Enter Address-1");
                    textInputLayoutAddr1.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutAddr1.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutAddr1.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutAddr1.setError(null);
                    validate = true;
                }

                addr2 = inputEditTextAddr2.getText().toString();
                if(TextUtils.isEmpty(addr2)){
                    textInputLayoutAddr2.setError("Enter Address-2");
                    textInputLayoutAddr2.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutAddr2.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutAddr2.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutAddr2.setError(null);
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


        btnWit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditWitness1Activity.this, EditWitness2Activity.class));
            }
        });

    }

    private void showConfirmationMsg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(EditWitness1Activity.this);
        builder.setTitle(R.string.confirm_exit);
        builder.setMessage(R.string.confirm_msg);
        builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startActivity(new Intent(EditWitness1Activity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
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

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.EDIT_WITNESS_1_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            Toast.makeText(EditWitness1Activity.this, "msg : " + response, Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    Intent witness2Intent = new Intent(EditWitness1Activity.this, EditWitness2Activity.class);
                                    startActivity(witness2Intent);

                                }
                            }, 3000);

                        } else {

                            Toast.makeText(EditWitness1Activity.this, "msg : " + response, Toast.LENGTH_SHORT).show();
                        }

                        clearData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditWitness1Activity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
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
                params.put(Keys.DOB, dob);
                params.put(Keys.GENDER, gender);
                params.put(Keys.PHONE, phone);
                params.put(Keys.PROFESSION, profession);

                params.put(Keys.PAN_CARD, pan);
                params.put(Keys.AADHAR_CARD, aadhar);

                params.put(Keys.ADDR1, addr1);
                params.put(Keys.ADDR2, addr2);

                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(EditWitness1Activity.this);
        requestQueue.add(request);

    }

    private void clearData() {

        inputEditTextFName.setText(FinalValues.EMPTY_STR);
        inputEditTextDob.setText(FinalValues.EMPTY_STR);
        inputEditTextPhone.setText(FinalValues.EMPTY_STR);
        inputEditTextPan.setText(FinalValues.EMPTY_STR);
        inputEditTextAadhar.setText(FinalValues.EMPTY_STR);
        inputEditTextAddr1.setText(FinalValues.EMPTY_STR);
        inputEditTextAddr2.setText(FinalValues.EMPTY_STR);

    }

    private void setErrorEnable() {

        textInputLayoutFName.setErrorEnabled(true);
        textInputLayoutDob.setErrorEnabled(true);
        textInputLayoutPhone.setErrorEnabled(true);
        textInputLayoutPan.setErrorEnabled(true);
        textInputLayoutAadhar.setErrorEnabled(true);
        textInputLayoutAddr1.setErrorEnabled(true);
        textInputLayoutAddr2.setErrorEnabled(true);

    }

    private void loadSpinner() {

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

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_WITNESS_DATA,
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
                                    witnessList.add(new Witness(
                                            data.getString(Keys.GET_WIT_ID),
                                            data.getString(Keys.GET_WIT_PROP_ID),
                                            data.getString(Keys.GET_WIT_1_NAME),
                                            data.getString(Keys.GET_WIT_1_PHOTO),
                                            data.getString(Keys.GET_WIT_1_AGE),
                                            data.getString(Keys.GET_WIT_1_GENDER),
                                            data.getString(Keys.GET_WIT_1_MOB_NO),
                                            data.getString(Keys.GET_WIT_1_PROF),
                                            data.getString(Keys.GET_WIT_1_PAN_NO),
                                            data.getString(Keys.GET_WIT_1_AADHAR_NO),
                                            data.getString(Keys.GET_WIT_1_AADHAR_FRONT_UPD),
                                            data.getString(Keys.GET_WIT_1_AADHAR_BACK_UPD),
                                            data.getString(Keys.GET_WIT_1_ADD1),
                                            data.getString(Keys.GET_WIT_1_ADD2),
                                            data.getString(Keys.GET_WIT_2_NAME),
                                            data.getString(Keys.GET_WIT_2_PHOTO),
                                            data.getString(Keys.GET_WIT_2_AGE),
                                            data.getString(Keys.GET_WIT_2_GENDER),
                                            data.getString(Keys.GET_WIT_2_MOB_NO),
                                            data.getString(Keys.GET_WIT_2_PROF),
                                            data.getString(Keys.GET_WIT_2_PAN_NO),
                                            data.getString(Keys.GET_WIT_2_AADHAR_NO),
                                            data.getString(Keys.GET_WIT_2_AADHAR_FRONT_UPD),
                                            data.getString(Keys.GET_WIT_2_AADHAR_BACK_UPD),
                                            data.getString(Keys.GET_WIT_2_ADD1),
                                            data.getString(Keys.GET_WIT_2_ADD2),
                                            data.getString(Keys.GET_WIT_STAT)

                                    ));
                                }

                                setData();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else{

                            Toast.makeText(EditWitness1Activity.this, "Something went wrong\n" + response, Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(EditWitness1Activity.this, "Error : " + error, Toast.LENGTH_SHORT).show();

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
                params.put(Keys.ID_WITNESS_ID, witnessID);

                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(EditWitness1Activity.this);
        requestQueue.add(request);
    }

    private void setData() {

        for (int i=0; i<witnessList.size(); i++){
            witness = witnessList.get(i);

            inputEditTextFName.setText(witness.getWit1_name());
            inputEditTextDob.setText(witness.getWit1_age());

            //.setText(witness.getWit1_gender());

            inputEditTextPhone.setText(witness.getWit1_mobno());

            //.setText(witness.getWit1_prof());
            ArrayAdapter arrayAdapter = (ArrayAdapter) spinnerProfession.getAdapter();
            int position = arrayAdapter.getPosition(witness.getWit1_prof());

            spinnerProfession.setSelection(position);

            inputEditTextPan.setText(witness.getWit1_panno());
            inputEditTextAadhar.setText(witness.getWit1_aadhar());
            inputEditTextAddr1.setText(witness.getWit1_add1());
            inputEditTextAddr2.setText(witness.getWit1_add2());

        }

    }

    private void initializeComponents() {
        scrollView = findViewById(R.id.scrollview_layout);

        spinnerProfession = findViewById(R.id.spinnerProfession);

        textInputLayoutFName = findViewById(R.id.txt_ip_name);
        textInputLayoutDob = findViewById(R.id.txt_ip_dob);
        textInputLayoutPhone = findViewById(R.id.txt_ip_phone);
        textInputLayoutPan = findViewById(R.id.txt_ip_panCard);
        textInputLayoutAadhar = findViewById(R.id.txt_ip_aadharCard);
        textInputLayoutAddr1 = findViewById(R.id.txt_ip_addr1);
        textInputLayoutAddr2 = findViewById(R.id.txt_ip_addr2);


        genderRadioGroup = findViewById(R.id.radioGenderGroup);
        maleRadioBtn = findViewById(R.id.radioMale);
        femaleRadioBtn = findViewById(R.id.radioFemale);

        inputEditTextFName = findViewById(R.id.edt_name);
        inputEditTextDob = findViewById(R.id.edt_dob);
        inputEditTextPhone = findViewById(R.id.edt_phone);
        inputEditTextPan = findViewById(R.id.edt_pan);
        inputEditTextAadhar = findViewById(R.id.edt_aadhar);
        inputEditTextAddr1 = findViewById(R.id.edt_addr1);
        inputEditTextAddr2 = findViewById(R.id.edt_addr2);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);
        btnWit2 = findViewById(R.id.edtWit2);

        witnessList = new ArrayList<>();
    }

    private void getPropertyID() {
        SharedPreferences sharedPreferences = EditWitness1Activity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        witnessID = sharedPreferences.getString(IDTracker.WIT_ID, null);
    }

}
