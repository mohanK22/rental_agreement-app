package in.aartimkarande.rentalagreement.myrentalagreement.user_owner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
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
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.Owner;
import in.aartimkarande.rentalagreement.myrentalagreement.model.Property;
import in.aartimkarande.rentalagreement.myrentalagreement.property.ViewPropertyActivity;

public class ViewOwnerActivity extends AppCompatActivity {

    private TextView textViewFirstName;
    private TextView textViewMiddleName;
    private TextView textViewLastName;
    private TextView textViewDOB;
    private TextView textViewPancard;
    private TextView textViewAadhar;
    private TextView textViewGender;
    private TextView textViewMobileNo;
    private TextView textViewProfession;
    private TextView textViewBldg;
    private TextView textViewFlatNo;
    private TextView textViewFloorNo;
    private TextView textViewRoad;
    private TextView textViewArea;
    private TextView textViewSuburban;
    private TextView textViewCity;
    private TextView textViewTaluka;
    private TextView textViewState;
    private TextView textViewPincode;
    private TextView textViewNoCos;


    private ScrollView scrollView;

    private Button btnBack;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String ownerID;

    private List<Owner> ownerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_owner);

        initializeComponents();

        getPrivateSharedData();

        getOwnerData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewOwnerActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
            }
        });

    }

    private void getOwnerData() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_OWNER_DATA,
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
                                    ownerList.add(new Owner(
                                            data.getString(Keys.GET_OT_ID),
                                            data.getString(Keys.GET_OT_PROP_ID),
                                            data.getString(Keys.GET_OT_FNAME),
                                            data.getString(Keys.GET_OT_MNAME),
                                            data.getString(Keys.GET_OT_LNAME),
                                            data.getString(Keys.GET_OT_PHOTO),
                                            data.getString(Keys.GET_OT_AGE),
                                            data.getString(Keys.GET_OT_GENDER),
                                            data.getString(Keys.GET_OT_MOB_NO),
                                            data.getString(Keys.GET_OT_PROF),
                                            data.getString(Keys.GET_OT_PAN_NO),
                                            data.getString(Keys.GET_OT_PAN_NO_UPD),
                                            data.getString(Keys.GET_OT_AADHAR_NO),
                                            data.getString(Keys.GET_OT_AADHAR_NO_FRONT_UPD),
                                            data.getString(Keys.GET_OT_AADHAR_NO_BACK_UPD),
                                            data.getString(Keys.GET_OT_BLD),
                                            data.getString(Keys.GET_OT_PLOT),
                                            data.getString(Keys.GET_OT_FLOOR_NO),
                                            data.getString(Keys.GET_OT_ROAD),
                                            data.getString(Keys.GET_OT_AREA),
                                            data.getString(Keys.GET_OT_SUBURBAN),
                                            data.getString(Keys.GET_OT_CITY),
                                            data.getString(Keys.GET_OT_TALUKA),
                                            data.getString(Keys.GET_OT_STATE),
                                            data.getString(Keys.GET_OT_PCODE),
                                            data.getString(Keys.GET_OT_CO),
                                            data.getString(Keys.GET_OT_STAT)

                                    ));
                                }

                                setOwnerData();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else{

                            //Message other than Success
                            Snackbar snackbar = Snackbar.make(scrollView, "Something went wrong. " + response, Snackbar.LENGTH_LONG);

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
                        Snackbar snackbar = Snackbar.make(scrollView, "Error :  " + error, Snackbar.LENGTH_LONG);

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
                params.put(Keys.ID_PROPERTY_ID, propertyID);
                params.put(Keys.ID_OWNER_ID, ownerID);

                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(ViewOwnerActivity.this);
        requestQueue.add(request);
    }

    private void setOwnerData() {

        for (int i=0; i<ownerList.size(); i++){
            Owner owner = ownerList.get(i);

            textViewFirstName.setText(owner.getfName());
            textViewMiddleName.setText(owner.getmName());
            textViewLastName.setText(owner.getlName());
            textViewDOB.setText(owner.getAge());
            textViewPancard.setText(owner.getPanNo());;
            textViewAadhar.setText(owner.getAadharNo());
            textViewGender.setText(owner.getGender());
            textViewMobileNo.setText(owner.getMobNo());
            textViewProfession.setText(owner.getProf());
            textViewBldg.setText(owner.getBldg());
            textViewFlatNo.setText(owner.getPlot());
            textViewFloorNo.setText(owner.getFloorNo());
            textViewRoad.setText(owner.getRoad());
            textViewArea.setText(owner.getArea());
            textViewSuburban.setText(owner.getSuburban());
            textViewCity.setText(owner.getCity());
            textViewTaluka.setText(owner.getTaluka());
            textViewState.setText(owner.getState());
            textViewPincode.setText(owner.getPcode());
            textViewNoCos.setText(owner.getNoCo());

        }

    }

    private void getPrivateSharedData() {
        SharedPreferences sharedPreferences = ViewOwnerActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);
    }

    private void initializeComponents() {

        scrollView = findViewById(R.id.scroll_view);

        textViewFirstName = findViewById(R.id.first_name);
        textViewMiddleName = findViewById(R.id.middle_name);
        textViewLastName = findViewById(R.id.last_name);
        textViewDOB = findViewById(R.id.dob);
        textViewPancard = findViewById(R.id.pan_no);
        textViewAadhar = findViewById(R.id.aadhar_no);
        textViewGender = findViewById(R.id.gender);
        textViewMobileNo = findViewById(R.id.mobile_no);
        textViewProfession = findViewById(R.id.profession);
        textViewBldg = findViewById(R.id.bldg);
        textViewFlatNo = findViewById(R.id.flat_no);
        textViewFloorNo = findViewById(R.id.floor_no);
        textViewRoad = findViewById(R.id.road_name);
        textViewArea = findViewById(R.id.area);
        textViewSuburban = findViewById(R.id.suburban);
        textViewCity = findViewById(R.id.city);
        textViewTaluka = findViewById(R.id.taluka);
        textViewState = findViewById(R.id.state);
        textViewPincode = findViewById(R.id.pincode);
        textViewNoCos = findViewById(R.id.no_co);

        btnBack = findViewById(R.id.btnBack);

        ownerList = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(ViewOwnerActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));

    }
}
