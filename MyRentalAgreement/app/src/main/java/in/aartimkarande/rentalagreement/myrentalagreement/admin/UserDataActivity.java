package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.UsersListAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.model.UserInfo;

public class UserDataActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewUsername;
    private TextView textViewEmailID;
    private TextView textViewPhone;
    private TextView textViewDOB;
    private TextView textViewUsertype;

    private Button btnOk;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        initializeComponents();
        getData();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDataActivity.this, UsersListsActivity.class));
            }
        });

    }

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_USER_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    textViewName.setText(data.getString("user_name"));
                                    textViewUsername.setText(data.getString("user_username"));
                                    textViewEmailID.setText(data.getString("user_email_id"));
                                    textViewPhone.setText(data.getString("user_phone"));
                                    textViewDOB.setText(data.getString("user_dob"));
                                    textViewUsertype.setText(data.getString("user_type"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(UserDataActivity.this,"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(UserDataActivity.this, "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserDataActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", userId);
                return map;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void initializeComponents() {
        textViewName = findViewById(R.id.text_view_full_name);
        textViewUsername = findViewById(R.id.text_view_user_name);
        textViewEmailID = findViewById(R.id.text_view_email_id);
        textViewPhone = findViewById(R.id.text_view_mobile_no);
        textViewDOB = findViewById(R.id.text_view_dob);
        textViewUsertype = findViewById(R.id.text_view_user_type);

        btnOk = findViewById(R.id.btnOk);
    }
}
