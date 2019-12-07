package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import in.aartimkarande.rentalagreement.myrentalagreement.activity.LoginActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.AgreementLists;
import in.aartimkarande.rentalagreement.myrentalagreement.model.IDs;

public class AdminMainActivity extends AppCompatActivity {

    private Button btnLogout;

    private ImageView imageView;

    private CardView cardViewUsers;
    private CardView cardViewAgreements;

    private TextView textViewNoUsers;
    private TextView textViewNoAgms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        imageView = findViewById(R.id.img_admin);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AdminInfoActivity.class));
            }
        });

        cardViewUsers = findViewById(R.id.usrs);
        cardViewAgreements = findViewById(R.id.agms);

        textViewNoUsers = findViewById(R.id.no_users);
        textViewNoAgms = findViewById(R.id.no_agm);

        loadData();

        cardViewListener();

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create shared preference
                SharedPreferences sharedPreferences = AdminMainActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                //Creating editor to store
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to the editor
                editor.putBoolean(MyAppSharedPreferences.LOGGEDIN, false);
                editor.putString(MyAppSharedPreferences.USERNAME, null);

                //Saving values to the editor
                editor.commit();

                startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
            }
        });

    }

    private void loadData() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.ADMIN_DASHBOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

                            try {
                                //converting the string to json array object
                                JSONArray jsonArray = new JSONArray(response);


                                //traversing through all the object
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject values = jsonArray.getJSONObject(i);

                                    //adding the product to event list
                                    textViewNoUsers.setText(values.getString(Keys.NO_USERS));
                                    textViewNoAgms.setText(values.getString(Keys.NO_AGMS));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               return null;
            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
        requestQueue.add(request);

    }

    private void cardViewListener() {

        cardViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, UsersListsActivity.class));
            }
        });



        cardViewAgreements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AgreementListsActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}
