package in.aartimkarande.rentalagreement.myrentalagreement.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.admin.AdminMainActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;

public class LoginActivity extends AppCompatActivity /*implements NetworkConnectivityReceiver.NetworkConnectivityReceiverListener*/ {

    //Component
    private RelativeLayout relativeLayout;

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPwd;

    private TextInputEditText inputEditTextUsername;
    private TextInputEditText inputEditTextPwd;

    private Button btnLogin;
    private Button btnRegister;

    /*private boolean status;*/

    /*PermissionStatusTracker permissionStatusTracker;*/



    //Primitive Datatypes and Wrapper Classes
    String usrName;
    String pwd;

    String username;

    private boolean validate = false;
    private boolean isLoggenIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Initializing Component
        relativeLayout = findViewById(R.id.login_relative_layout);

        textInputLayoutUsername =findViewById(R.id.txt_ip_username);
        textInputLayoutPwd = findViewById(R.id.txt_ip_password);

        inputEditTextUsername =findViewById(R.id.edt_username_login);
        inputEditTextPwd = findViewById(R.id.edt_pwd_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnSignUp);


        //Methods
        setErrorEnable();


        //Action Listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if(!usrName.equalsIgnoreCase("admin")){
                    if (validate) sendData();
                }else{
                    if(validate) adminLogin();
                }

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });


    }

    private void adminLogin() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.ADMIN_LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {


                            //Start Welcome Activity (Login Activity)
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Admin Login Success.", Snackbar.LENGTH_LONG);

                            //Create shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to the editor
                            editor.putBoolean(MyAppSharedPreferences.LOGGEDIN, true);
                            editor.putString(MyAppSharedPreferences.USERNAME, usrName);

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
                                    // yourMethod();
                                    /*Intent regIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(regIntent);*/
                                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                }
                            }, 2000);



                        }else {

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Invalid Username and Password", Snackbar.LENGTH_LONG);

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

                //Adding Parameter to the request
                params.put(Keys.REG_USR_USRNAME, usrName);
                params.put(Keys.REG_USR_PWD, pwd);

                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }

    private void sendData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {


                            //Start Welcome Activity (Login Activity)
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Login Success. User ID : " + response, Snackbar.LENGTH_LONG);

                            //Create shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to the editor
                            editor.putBoolean(MyAppSharedPreferences.LOGGEDIN, true);
                            editor.putString(MyAppSharedPreferences.USR_ID, response);
                            editor.putString(MyAppSharedPreferences.USERNAME, usrName);

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
                                    // yourMethod();
                                    /*Intent regIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(regIntent);*/
                                    startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                                }
                            }, 2000);



                        }else {

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Invalid Username and Password", Snackbar.LENGTH_LONG);

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

                //Adding Parameter to the request
                params.put(Keys.REG_USR_USRNAME, usrName);
                params.put(Keys.REG_USR_PWD, pwd);

                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }

    private void clearData() {
        inputEditTextUsername.setText(FinalValues.EMPTY_STR);
        inputEditTextPwd.setText(FinalValues.EMPTY_STR);
    }

    private void setErrorEnable() {
        textInputLayoutUsername.setErrorEnabled(true);
        textInputLayoutPwd.setErrorEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences =  getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        isLoggenIn = sharedPreferences.getBoolean(MyAppSharedPreferences.LOGGEDIN, false);

        if(isLoggenIn){
            username = sharedPreferences.getString(MyAppSharedPreferences.USERNAME, null);

            if(username.equalsIgnoreCase("admin")){
                startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
            }else{
                startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
            }
        }


    }


    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}
