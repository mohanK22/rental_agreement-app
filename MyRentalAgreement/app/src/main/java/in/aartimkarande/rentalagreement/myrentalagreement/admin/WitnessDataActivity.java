package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Intent;
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
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.model.Witness;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness1.ViewWitness1Activity;

public class WitnessDataActivity extends AppCompatActivity {
    private ScrollView scrollView;

    private TextView textViewName;
    private TextView textViewDOB;
    private TextView textViewGender;
    private TextView textViewMobileNo;
    private TextView textViewProfession;
    private TextView textViewPanNo;
    private TextView textViewAadharNo;
    private TextView textViewAddr1;
    private TextView textViewAddr2;

    private TextView textViewName2;
    private TextView textViewDOB2;
    private TextView textViewGender2;
    private TextView textViewMobileNo2;
    private TextView textViewProfession2;
    private TextView textViewPanNo2;
    private TextView textViewAadharNo2;
    private TextView textViewAddr12;
    private TextView textViewAddr22;

    private Button btnBack;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String witnessID;

    private float x1;
    private float x2;

    private List<Witness> witnessList;
    private Witness witness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witness_data);

        Intent intent = getIntent();
        propertyID = intent.getStringExtra("propID");
        userID = intent.getStringExtra("userID");
        witnessID = intent.getStringExtra("witID");

        initializeComponents();
        getWitnessData();

    }

    private void getWitnessData() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_WITNESS_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

                            /*Snackbar snackbar = Snackbar.make(scrollView, "All data are fetched.: ", Snackbar.LENGTH_LONG);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();*/

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

                                setWitnessData();

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
                        /*Snackbar snackbar = Snackbar.make(scrollView, "Error :  " + error, Snackbar.LENGTH_LONG);

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
        RequestQueue requestQueue = Volley.newRequestQueue(WitnessDataActivity.this);
        requestQueue.add(request);
    }

    private void setWitnessData() {

        for (int i=0; i<witnessList.size(); i++){
            witness = witnessList.get(i);

            textViewName.setText(witness.getWit1_name());
            textViewDOB.setText(witness.getWit1_age());
            textViewGender.setText(witness.getWit1_gender());
            textViewMobileNo.setText(witness.getWit1_mobno());
            textViewProfession.setText(witness.getWit1_prof());
            textViewPanNo.setText(witness.getWit1_panno());
            textViewAadharNo.setText(witness.getWit1_aadhar());
            textViewAddr1.setText(witness.getWit1_add1());
            textViewAddr2.setText(witness.getWit1_add2());


            textViewName2.setText(witness.getWit2_name());
            textViewDOB2.setText(witness.getWit2_age());
            textViewGender2.setText(witness.getWit2_gender());
            textViewMobileNo2.setText(witness.getWit2_mobno());
            textViewProfession2.setText(witness.getWit2_prof());
            textViewPanNo2.setText(witness.getWit2_panno());
            textViewAadharNo2.setText(witness.getWit2_aadhar());
            textViewAddr12.setText(witness.getWit2_add1());
            textViewAddr22.setText(witness.getWit2_add2());

        }

    }

    private void initializeComponents() {

        scrollView = findViewById(R.id.scroll_view);

        textViewName = findViewById(R.id.wit1_name);
        textViewDOB = findViewById(R.id.wit1_dob);
        textViewGender = findViewById(R.id.wit1_gender);
        textViewMobileNo = findViewById(R.id.wit1_mobile_no);
        textViewProfession = findViewById(R.id.wit1_profession);
        textViewPanNo = findViewById(R.id.wit1_pan_no);
        textViewAadharNo = findViewById(R.id.wit1_aadhar_no);
        textViewAddr1 = findViewById(R.id.wit1_add1);
        textViewAddr2 = findViewById(R.id.wit1_add2);


        textViewName2 = findViewById(R.id.wit2_name);
        textViewDOB2 = findViewById(R.id.wit2_dob);
        textViewGender2 = findViewById(R.id.wit2_gender);
        textViewMobileNo2 = findViewById(R.id.wit2_mobile_no);
        textViewProfession2 = findViewById(R.id.wit2_profession);
        textViewPanNo2 = findViewById(R.id.wit2_pan_no);
        textViewAadharNo2 = findViewById(R.id.wit2_aadhar_no);
        textViewAddr12 = findViewById(R.id.wit2_add1);
        textViewAddr22 = findViewById(R.id.wit2_add2);


        witnessList = new ArrayList<>();
    }

}
