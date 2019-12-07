package in.aartimkarande.rentalagreement.myrentalagreement.admin;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.property.PropertyActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.OwnerActivity;

public class UpdateAdminPwdActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutoldPwd;
    private TextInputLayout textInputLayoutNewPwd;
    private TextInputLayout textInputLayoutconfirmPwd;

    private TextInputEditText inputEditTextOldpwd;
    private TextInputEditText inputEditTextNewpwd;
    private TextInputEditText inputEditTextConfirmpwd;

    private Button btnUpdatePwd;

    private Boolean validate;

    private String oldPwd;
    private String newPwd;
    private String confirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin_pwd);

        initializeComponents();

        validate = false;

        inputEditTextConfirmpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                newPwd = inputEditTextNewpwd.getText().toString();
                confirmPwd = s.toString();

                if(s.length() != inputEditTextNewpwd.getText().length() || !newPwd.equals(confirmPwd) ){
                    validate = false;
                    textInputLayoutconfirmPwd.setError("Password is not matching");
                }else{
                    validate = true;
                    textInputLayoutconfirmPwd.setError(null);
                }

            }
        });


        btnUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldPwd = inputEditTextOldpwd.getText().toString();
                if(TextUtils.isEmpty(oldPwd)){
                    textInputLayoutoldPwd.setError("Enter old password");
                    textInputLayoutoldPwd.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutoldPwd.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutoldPwd.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutoldPwd.setError(null);
                    validate = true;
                }


                newPwd = inputEditTextNewpwd.getText().toString();
                if(TextUtils.isEmpty(newPwd)){
                    textInputLayoutNewPwd.setError("Enter new password");
                    textInputLayoutNewPwd.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutNewPwd.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutNewPwd.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutNewPwd.setError(null);
                    validate = true;
                }

                confirmPwd = inputEditTextConfirmpwd.getText().toString();
                if(TextUtils.isEmpty(newPwd)){
                    textInputLayoutconfirmPwd.setError("Enter confirm password");
                    textInputLayoutconfirmPwd.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayoutconfirmPwd.setHintTextAppearance(R.style.error_appearance);
                    textInputLayoutconfirmPwd.requestFocus();

                    validate = false;
                    return;
                }else{
                    textInputLayoutconfirmPwd.setError(null);
                    validate = true;
                }

                if(validate) updatePwd();

            }
        });

    }

    private void updatePwd() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.UPDATE_ADMIN_PWD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            Toast.makeText(UpdateAdminPwdActivity.this, "Password updated..", Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    Intent ownerIntent = new Intent(UpdateAdminPwdActivity.this, AdminMainActivity.class);
                                    startActivity(ownerIntent);

                                }
                            }, 3000);

                        } else {
                            Toast.makeText(UpdateAdminPwdActivity.this, "Something went wrong..\n" + response, Toast.LENGTH_SHORT).show();
                        }

                        clearData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateAdminPwdActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //Adding Parameter to the request
                params.put(Keys.ID, Keys.ADMIN_ID);
                params.put(Keys.PWD, confirmPwd);
                params.put(Keys.OLD_PWD, oldPwd);


                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateAdminPwdActivity.this);
        requestQueue.add(request);
    }

    private void clearData() {
        inputEditTextOldpwd.setText(FinalValues.EMPTY_STR);
        inputEditTextNewpwd.setText(FinalValues.EMPTY_STR);
        inputEditTextConfirmpwd.setText(FinalValues.EMPTY_STR);
    }

    private void initializeComponents() {

        textInputLayoutoldPwd = findViewById(R.id.txt_ip_old_pwd);
        textInputLayoutNewPwd = findViewById(R.id.txt_ip_new_pwd);
        textInputLayoutconfirmPwd = findViewById(R.id.txt_ip_confirm_pwd);

        inputEditTextOldpwd = findViewById(R.id.edt_old_pwd);
        inputEditTextNewpwd = findViewById(R.id.edt_new_pwd);
        inputEditTextConfirmpwd = findViewById(R.id.edt_confirm_pwd);


        btnUpdatePwd = findViewById(R.id.btnUpdatePwd);

    }


}
