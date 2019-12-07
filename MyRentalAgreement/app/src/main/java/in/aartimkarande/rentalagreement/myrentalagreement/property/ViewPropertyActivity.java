package in.aartimkarande.rentalagreement.myrentalagreement.property;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import in.aartimkarande.rentalagreement.myrentalagreement.activity.DashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.IDs;
import in.aartimkarande.rentalagreement.myrentalagreement.model.Property;

public class ViewPropertyActivity extends AppCompatActivity {

    private TextView textViewBldg;
    private TextView textViewFlatNo;
    private TextView textViewFloorNo;
    private TextView textViewRoadName;
    private TextView textViewArea;
    private TextView textViewSuburban;
    private TextView textViewCity;
    private TextView textViewTaluka;
    private TextView textViewState;
    private TextView textViewPincode;
    private TextView textViewPropertyType;
    private TextView textViewPropertyArea;

    private ScrollView scrollView;

    private Button btnBack;

    private String userID;
    private String serviceType;
    private String propertyID;

    private List<Property> propertyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);


        initializeComponents();

        getPrivateSharedData();

        getPropertyData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPropertyActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
            }
        });
    }

    private void getPrivateSharedData() {
        SharedPreferences sharedPreferences = ViewPropertyActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
    }

    private void getPropertyData() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_PROPERTY_DATA,
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
                                    JSONObject id = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    propertyList.add(new Property(
                                            id.getString(Keys.GET_PROP_ID),
                                            id.getString(Keys.GET_PROP_USER_ID),
                                            id.getString(Keys.GET_PROP_SERV_TYPE),
                                            id.getString(Keys.GET_PROP_BLDG),
                                            id.getString(Keys.GET_PROP_PLOT_NO),
                                            id.getString(Keys.GET_PROP_FLOOR_NO),
                                            id.getString(Keys.GET_PROP_ROAD),
                                            id.getString(Keys.GET_PROP_AREA),
                                            id.getString(Keys.GET_PROP_SUBURBAN),
                                            id.getString(Keys.GET_PROP_CITY),
                                            id.getString(Keys.GET_PROP_TALUKA),
                                            id.getString(Keys.GET_PROP_STATE),
                                            id.getString(Keys.GET_PROP_PINCODE),
                                            id.getString(Keys.GET_PROP_TYPE),
                                            id.getString(Keys.GET_PROP_FLAT_AREA),
                                            id.getString(Keys.GET_PROP_INDEX2),
                                            id.getString(Keys.GET_PROP_ELECT_BILL),
                                            id.getString(Keys.GET_PROP_GAS_BILL),
                                            id.getString(Keys.GET_PROP_TAX_BILL),
                                            id.getString(Keys.GET_PROP_MAIN_BILL),
                                            id.getString(Keys.GET_PROP_STAT_STR)

                                    ));
                                }

                                setPropertyData();

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

                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(ViewPropertyActivity.this);
        requestQueue.add(request);
    }

    private void setPropertyData() {

        for (int i=0; i<propertyList.size(); i++){
            Property property = propertyList.get(i);

            textViewBldg.setText(property.getPropBldg());
            textViewFlatNo.setText(property.getPropPlotNo());
            textViewFloorNo.setText(property.getPropFloorNo());
            textViewRoadName.setText(property.getPropRoad());
            textViewArea.setText(property.getPropArea());
            textViewSuburban.setText(property.getPropSuburban());
            textViewCity.setText(property.getPropCity());
            textViewTaluka.setText(property.getPropTaluka());
            textViewState.setText(property.getPropState());
            textViewPincode.setText(property.getPropPincode());
            textViewPropertyType.setText(property.getPropType());
            textViewPropertyArea.setText(property.getPropFlatArea());

        }

    }

    private void initializeComponents() {

        scrollView = findViewById(R.id.scroll_view);


        textViewBldg = findViewById(R.id.bldg);
        textViewFlatNo = findViewById(R.id.flat_no);
        textViewFloorNo = findViewById(R.id.floor_no);
        textViewRoadName = findViewById(R.id.road_name);
        textViewArea = findViewById(R.id.area);
        textViewSuburban = findViewById(R.id.suburban);
        textViewCity = findViewById(R.id.city);
        textViewTaluka = findViewById(R.id.taluka);
        textViewState = findViewById(R.id.state);
        textViewPincode = findViewById(R.id.pincode);
        textViewPropertyType = findViewById(R.id.property_type);
        textViewPropertyArea = findViewById(R.id.property_area);

        btnBack = findViewById(R.id.btnBack);

        propertyList = new ArrayList<>();
    }


    @Override
    protected void onResume() {
        super.onResume();

        initializeComponents();

        getPrivateSharedData();

        getPropertyData();

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        startActivity(new Intent(ViewPropertyActivity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
    }
}
