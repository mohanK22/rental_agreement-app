package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
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
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.property.PropertyActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.property.ViewPropertyActivity;

public class AgmDataActivity extends AppCompatActivity {

    private String agmID;
    private String userID;
    private String propID;
    private String ownID;
    private String tentID;
    private String witID;
    private String rentID;
    private String rentTransID;

    private CardView cardViewProperty;
    private CardView cardViewOwner;
    private CardView cardViewTenant;
    private CardView cardViewWitness;
    private CardView cardViewPayment;
    private CardView cardViewDownloadReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agm_data);

        Intent intent = getIntent();
        agmID = intent.getStringExtra("agm_id");
        userID = intent.getStringExtra("user_id");

        getPropID();

    }

    private void getPropID() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_PROP_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        propID = response;
                        getAllID();
                        /*setSharedPreference();*/
                        cardViewListener();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgmDataActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_AGM_ID, agmID);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AgmDataActivity.this);
        requestQueue.add(request);
    }

    private void getAllID() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.CHECK_FROM_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject data = jsonArray.getJSONObject(i);

                                    ownID = data.getString("own_stat");
                                    tentID = data.getString("tent_stat");
                                    witID = data.getString("wit_stat");
                                    rentID = data.getString("prop_pay_stat");
                                    rentTransID = data.getString("rent_trans_stat");
                                }

                                //Toast.makeText(CreateAgreementDashboardActivity.this, "checkFormStatus", Toast.LENGTH_SHORT).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(AgmDataActivity.this ,"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(AgmDataActivity.this, "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgmDataActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                /*SharedPreferences sharedPreferences = AgmDataActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);*/
                /*userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);*/

                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_PROPERTY_ID, propID);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AgmDataActivity.this);
        requestQueue.add(request);
    }

    private void setSharedPreference() {

        Toast.makeText(AgmDataActivity.this, "User ID : " + userID + "\nAgm ID  : " + agmID + "\nProp ID : " + propID, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = AgmDataActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IDTracker.PROP_ID, propID);
        editor.putString(MyAppSharedPreferences.USR_ID, userID);
    }

    private void cardViewListener() {
        cardViewProperty = findViewById(R.id.property);
        cardViewProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgmDataActivity.this, PropertyDataActivity.class)
                        .putExtra("propID", propID)
                        .putExtra("userID", userID));
            }
        });

        cardViewOwner = findViewById(R.id.owner);
        cardViewOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgmDataActivity.this,OwnerDataActivity.class)
                        .putExtra("propID", propID)
                        .putExtra("userID", userID)
                        .putExtra("ownID", ownID));
            }
        });

        cardViewTenant = findViewById(R.id.tenant);
        cardViewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgmDataActivity.this, TenantDataActivity.class)
                        .putExtra("propID", propID)
                        .putExtra("userID", userID)
                        .putExtra("tentID", tentID));
            }
        });

        cardViewWitness = findViewById(R.id.witness);
        cardViewWitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgmDataActivity.this, WitnessDataActivity.class)
                        .putExtra("propID", propID)
                        .putExtra("userID", userID)
                        .putExtra("witID", witID));
            }
        });

        cardViewPayment = findViewById(R.id.payment);
        cardViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgmDataActivity.this, PaymentDataActivity.class)
                        .putExtra("propID", propID)
                        .putExtra("userID", userID)
                        .putExtra("rentID", rentID)
                        .putExtra("rentTransID", rentTransID));
            }
        });

        cardViewDownloadReport = findViewById(R.id.download_report);
        cardViewDownloadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileUrl = APIURLLists.GET_REPORT + "" + agmID + "" + APIURLLists.PDF_EXT;
                String fileName = agmID + "" + APIURLLists.PDF_EXT;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));
                startActivity(browserIntent);
            }
        });

    }

}
