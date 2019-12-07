package in.aartimkarande.rentalagreement.myrentalagreement.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.IDs;
import in.aartimkarande.rentalagreement.myrentalagreement.property.EditPropertyActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.property.PropertyActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.status_activity.SixActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.OwnerActivity;

public class DashboardActivity extends AppCompatActivity {

    private CardView cardViewAboutUs;
    private CardView cardViewCreateAgreement;
    private CardView cardViewScheduleAppointment;
    private CardView cardViewSendMsg;
    private CardView cardViewWebsite;

    private LinearLayout linearLayout;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String rentTransID;
    private String agmID;
    private String tokenID;

    private List<IDs> idLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeComponent();

        idLists = new ArrayList<>();

        SharedPreferences sharedPreferences = DashboardActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

        Toast.makeText(getApplicationContext(),"User ID : " + userID,Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(userID) || TextUtils.isEmpty(serviceType) || TextUtils.isEmpty(propertyID) || TextUtils.isEmpty(ownerID) || TextUtils.isEmpty(tenantID) || TextUtils.isEmpty(witnessID) || TextUtils.isEmpty(rentID) || TextUtils.isEmpty(rentTransID) || TextUtils.isEmpty(agmID) || TextUtils.isEmpty(tokenID)) {
            Toast.makeText(getApplicationContext(), "getAllIDs() called", Toast.LENGTH_LONG);
            getAllIDs();
        }

        cardActionListener();
    }

    private void cardActionListener() {

        cardViewAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AboutUsActivity.class));
            }
        });

        cardViewCreateAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CreateAgreementDashboardActivity.class));
            }
        });

        cardViewScheduleAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ScheduleAppointmentActivity.class));
            }
        });

        cardViewSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, SendMsgActivity.class));
            }
        });

        cardViewWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(APIURLLists.WEBSITE)));
            }
        });

    }

    private void initializeComponent() {

        cardViewAboutUs = findViewById(R.id.about_us);
        cardViewCreateAgreement = findViewById(R.id.create_agreement);
        cardViewScheduleAppointment = findViewById(R.id.schedule_appointment);
        cardViewSendMsg = findViewById(R.id.send_msg);
        cardViewWebsite = findViewById(R.id.website);

        linearLayout = findViewById(R.id.linear_layout);

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle(R.string.confirm_exit);
        builder.setMessage(R.string.confirm_msg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void setAllIDs() {

        for (int i=0; i<idLists.size(); i++){
            IDs id = idLists.get(i);

            serviceType = id.getServiceType();
            propertyID = id.getPropertyID();
            ownerID = id.getOwnerID();
            tenantID = id.getTenantID();
            witnessID = id.getWitnessID();
            rentID = id.getRentID();
            rentTransID = id.getRentTransID();
            agmID = id.getAgreementID();
            tokenID = id.getTokenID();
        }


        //Creating a shared preference
        SharedPreferences sharedPreferences = DashboardActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Creating editor to store
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Adding values to the editor
        editor.putString(IDTracker.PROP_ID, propertyID);
        editor.putString(IDTracker.SERVICE_TYPE, serviceType);
        editor.putString(IDTracker.OWN_ID, ownerID);
        editor.putString(IDTracker.TENT_ID, tenantID);
        editor.putString(IDTracker.WIT_ID, witnessID);
        editor.putString(IDTracker.RENT_ID, rentID);
        editor.putString(IDTracker.RENT_TRANS_ID, rentTransID);
        editor.putString(IDTracker.AGREEMENT_ID, agmID);
        editor.putString(IDTracker.TOKEN_ID, tokenID);

        //Saving values to the editor
        editor.commit();

        Toast.makeText(getApplicationContext(),"Service Type : " + serviceType,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Property ID : " + propertyID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Owner ID : " + ownerID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Tenant ID : " + tenantID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Witness ID : " + witnessID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Rent ID : " + rentID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Rent Trans ID : " + rentTransID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Agreement ID : " + agmID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Token ID : " + tokenID,Toast.LENGTH_SHORT).show();

    }

    private void getAllIDs() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_ALL_IDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

                            Snackbar snackbar = Snackbar.make(linearLayout, "All IDs are fetched.: ", Snackbar.LENGTH_LONG);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();

                            try {
                                //converting the string to json array object
                                JSONArray jsonArray = new JSONArray(response);

                                Toast.makeText(getApplicationContext(),"Array Length : " + jsonArray.length(),Toast.LENGTH_SHORT).
                                        show();

                                //traversing through all the object
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject id = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    idLists.add(new IDs(
                                            id.getString(Keys.GET_ID_PROP_ID),
                                            id.getString(Keys.GET_ID_SERVICE_TYPE),
                                            id.getString(Keys.GET_ID_OWN_ID),
                                            id.getString(Keys.GET_ID_TENT_ID),
                                            id.getString(Keys.GET_ID_WIT_ID),
                                            id.getString(Keys.GET_ID_RENT_ID),
                                            id.getString(Keys.GET_ID_RENT_TRANS_ID),
                                            id.getString(Keys.GET_ID_AGM_ID),
                                            id.getString(Keys.GET_ID_TOKEN_ID)

                                    ));
                                }

                                setAllIDs();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else{

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(linearLayout, "Something went wrong. " + response, Snackbar.LENGTH_LONG);

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
                        //Message other than Success
                        Snackbar snackbar = Snackbar.make(linearLayout, "Error :  " + error, Snackbar.LENGTH_LONG);

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

                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(request);

    }



}
