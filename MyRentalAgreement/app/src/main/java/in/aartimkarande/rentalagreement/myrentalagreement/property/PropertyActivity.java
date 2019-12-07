package in.aartimkarande.rentalagreement.myrentalagreement.property;

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
import in.aartimkarande.rentalagreement.myrentalagreement.other.ActivityStatusTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.OwnerActivity;

public class PropertyActivity extends AppCompatActivity {

    //Component
    private ScrollView scrollView;

    private Spinner spinnerPropertyType; //  Apartment / Flat / Shop / Godown / Room

    private AlertDialog alertDialog;

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
    private TextInputLayout textInputLayoutType;
    private TextInputLayout textInputLayoutMeasure;


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
    private TextInputEditText inputEditTextType;
    private TextInputEditText inputEditTextMeasure;


    private Button btnSubmit;
    private Button btnBack;

/*    private TextView textView;*/


    //Primitive Datatypes and Wrapper Classes
    private String propertyID;

    private String[] serviceTypeLists;

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
    private String type;
    private String measure;

    private String userID;
    private String serviceType;

    private boolean validate = false;
    private String serviceTypeShare;
    private String rentTransID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        getUserID();

        if(!ActivityStatusTracker.isServiceTypeSet){
            selectServiceType();
        }

        initializeComponents();

        loadSpinner();
        setErrorEnable();



        //Action Listener
        spinnerPropertyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                if(s.length() > textInputLayoutPincode.getCounterMaxLength()  ||  s.length() < textInputLayoutPincode.getCounterMaxLength()){
                    textInputLayoutPincode.setError("Invalid Pincode No. Length must be 6");
                }else{
                    textInputLayoutPincode.setError(null);
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


                measure = inputEditTextMeasure.getText().toString();
                if(TextUtils.isEmpty(measure)){
                    textInputLayoutMeasure.setError("Enter Area in sq. ft.");
                    textInputLayoutMeasure.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutMeasure.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutMeasure.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutMeasure.setError(null);
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
        textInputLayoutMeasure.setErrorEnabled(true);
    }

    private void sendData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.PROPERTY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            //Start Welcome Activity (Owner Activity)
                            Snackbar snackbar = Snackbar.make(scrollView, "Property Details Created Successfully. ID : " + response, Snackbar.LENGTH_LONG);


                            //Creating a shared preference
                            SharedPreferences sharedPreferences = PropertyActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to the editor
                            editor.putString(IDTracker.PROP_ID, response);

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

                                    Intent ownerIntent = new Intent(PropertyActivity.this, OwnerActivity.class);
                                    startActivity(ownerIntent);

                                }
                            }, 3000);



                        } else {

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(scrollView, "Invalid Credential" + response, Snackbar.LENGTH_LONG);

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
                params.put(Keys.USER_ID, userID);
                params.put(Keys.SERVICE_TYPE, serviceType);
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
                params.put(Keys.TYPE, type);
                params.put(Keys.MEASURE, measure);
                params.put(Keys.STAT, FinalValues.STAT);


                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(PropertyActivity.this);
        requestQueue.add(request);

    }

    private void clearData() {
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
        inputEditTextMeasure.setText(FinalValues.EMPTY_STR);
    }

    private void loadSpinner() {
        //Loads Professional
        List<String> propertyTypes = new ArrayList<String>();
        String[] lists = getResources().getStringArray(R.array.property_type);

        for (String str: lists) {
            propertyTypes.add(str);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPropertyType.setAdapter(arrayAdapter);
    }

    private void initializeComponents() {
        scrollView = findViewById(R.id.scrollview_layout);

        spinnerPropertyType = findViewById(R.id.spinner_property_type);

        textInputLayoutBldgName = findViewById(R.id.txt_ip_property_nameBldg);
        textInputLayoutFlatNo = findViewById(R.id.txt_ip_property_flatNo);
        textInputLayoutFloorNo = findViewById(R.id.txt_ip_property_floorNo);
        textInputLayoutRoad = findViewById(R.id.txt_ip_property_road);
        textInputLayoutArea = findViewById(R.id.txt_ip_property_area);
        textInputLayoutSuburban = findViewById(R.id.txt_ip_property_suburban);
        textInputLayoutCity = findViewById(R.id.txt_ip_property_city);
        textInputLayoutTaluka = findViewById(R.id.txt_ip_property_taluka);
        textInputLayoutState = findViewById(R.id.txt_ip_property_state);
        textInputLayoutPincode = findViewById(R.id.txt_ip_property_pincode);
        /*textInputLayoutType = findViewById(R.id.txt_tp_property_type);*/
        textInputLayoutMeasure = findViewById(R.id.txt_ip_property_measure);


        inputEditTextBldgName = findViewById(R.id.edt_property_nameBldg);
        inputEditTextFlatNo = findViewById(R.id.edt_property_flatNo);
        inputEditTextFloorNo = findViewById(R.id.edt_property_floorNo);
        inputEditTextRoad = findViewById(R.id.edt_property_road);
        inputEditTextArea = findViewById(R.id.edt_property_area);
        inputEditTextSuburban = findViewById(R.id.edt_property_suburban);;
        inputEditTextCity = findViewById(R.id.edt_property_city);
        inputEditTextTaluka = findViewById(R.id.edt_property_taluka);
        inputEditTextState = findViewById(R.id.edt_property_state);
        inputEditTextPincode = findViewById(R.id.edt_property_pincode);
        /*inputEditTextType = findViewById(R.id.edt_property_type);*/
        inputEditTextMeasure = findViewById(R.id.edt_property_measure);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);
    }


    private void getUserID() {
        SharedPreferences sharedPreferences = PropertyActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
    }



    private void selectServiceType() {

        serviceTypeLists = getResources().getStringArray(R.array.property_service_types);

        AlertDialog.Builder builder = new AlertDialog.Builder(PropertyActivity.this);
        builder.setTitle(R.string.property_service_type);
        /*builder.setMessage(R.string.property_service_type_msg);*/
        builder.setCancelable(false);
        builder.setSingleChoiceItems(serviceTypeLists, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0: serviceType = "1"; break;
                    case 1: serviceType = "2"; break;
                    case 2: serviceType = "3"; break;
                }
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();

                Toast.makeText(getApplicationContext(),"Service Type : " + serviceType ,Toast.LENGTH_SHORT).show();

                //Creating a shared preference
                SharedPreferences sharedPreferences = PropertyActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                //Creating editor to store
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to the editor
                editor.putString(IDTracker.SERVICE_TYPE, serviceType);

                //Saving values to the editor
                editor.commit();

                serviceTypeShare = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);

                if(serviceTypeShare != null){
                    ActivityStatusTracker.isServiceTypeSet = true;
                }
                else{
                    selectServiceType();
                }


            }
        });
        alertDialog = builder.create();
        alertDialog.show();


    }


    @Override
    protected void onResume() {
        super.onResume();

            SharedPreferences sharedPreferences = PropertyActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
            rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null);

            if(propertyID != null) {

                Toast.makeText(getApplicationContext(),"onResume() called.\nProperty ID : " + propertyID,Toast.LENGTH_SHORT).show();

                if(rentTransID != null){
                    startActivity(new Intent(PropertyActivity.this, ViewPropertyActivity.class));
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(PropertyActivity.this);
                    builder.setTitle(R.string.edt_data_msg);
                    builder.setMessage(R.string.edt_data);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                            startActivity(new Intent(PropertyActivity.this, EditPropertyActivity.class));
                        }
                    });
                    builder.setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                            startActivity(new Intent(PropertyActivity.this, OwnerActivity.class));
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }

            }

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/

        Toast.makeText(getApplicationContext(),R.string.back_button_dashboard_msg,Toast.LENGTH_SHORT).show();

    }


    private void showConfirmationMsg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PropertyActivity.this);
        builder.setTitle(R.string.confirm_exit);
        builder.setMessage(R.string.confirm_msg);
        builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startActivity(new Intent(PropertyActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
            }
        });
        builder.setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                if(!ActivityStatusTracker.isServiceTypeSet){
                    selectServiceType();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }


}
