package in.aartimkarande.rentalagreement.myrentalagreement.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
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
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.config.PermissionStatusTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.property.PropertyActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.transaction.RentPaymentActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.OwnerActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_tenant.TenantActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness1.Witness1Activity;

public class CreateAgreementDashboardActivity extends AppCompatActivity {

    private CardView cardViewProperty;
    private CardView cardViewOwner;
    private CardView cardViewTenant;
    private CardView cardViewWitness;
    private CardView cardViewPayment;
    private CardView cardViewReport;

    private FrameLayout frameLayoutProperty;
    private FrameLayout frameLayoutOwner;
    private FrameLayout frameLayoutTenant;
    private FrameLayout frameLayoutWitness;
    private FrameLayout frameLayoutPayment;

    private TextView textViewPropertyStatus;
    private TextView textViewOwnerStatus;
    private TextView textViewTenantStatus;
    private TextView textViewWitnessStatus;
    private TextView textViewPaymentStatus;
    private TextView textViewReportStatus;

    private DownloadManager downloadManager;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String rentTransID;
    private String tokenID;

    private boolean validate = true;

    private String msg;

    private String status;
    private String prop_id;
    private String agm_id;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_agreement_dashboard);

        sharedPreferences = CreateAgreementDashboardActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getIDs();

        initializeComponent();

        Intent intent = getIntent();
        status = intent.getStringExtra("status");

        //Toast.makeText(CreateAgreementDashboardActivity.this, "Status : " + status, Toast.LENGTH_SHORT).show();

        if(status.equalsIgnoreCase(FinalValues.STATUS_CREATE_NEW)){

            //Toast.makeText(CreateAgreementDashboardActivity.this, "create _new called", Toast.LENGTH_SHORT).show();
            setIDTrackerAsNull();

        }else if(status.equalsIgnoreCase(FinalValues.STATUS_EDIT)) {

            //Toast.makeText(CreateAgreementDashboardActivity.this, "edit called", Toast.LENGTH_SHORT).show();
            prop_id = intent.getStringExtra("prop_id");
            editor.putString(IDTracker.PROP_ID, prop_id);
            editor.commit();

            Toast.makeText(CreateAgreementDashboardActivity.this, "prop_id : " + prop_id, Toast.LENGTH_SHORT).show();

            checkFormStatus();

        }else if(status.equalsIgnoreCase(FinalValues.STATUS_VIEW)){
            //Toast.makeText(CreateAgreementDashboardActivity.this, "view called", Toast.LENGTH_SHORT).show();
            agm_id = intent.getStringExtra("agm_id");
            getPropID();

        }else if(status.equalsIgnoreCase(FinalValues.STATUS_UPDATE)){

        } else if(status.equalsIgnoreCase(FinalValues.STATUS_ADMIN_VIEW)){
            agm_id = intent.getStringExtra("agm_id");
            getPropID();
        }

        setVisibilityOfFrames();

        cardActionListener();
    }

    private void getPropID() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_PROP_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prop_id = response;
                        editor.putString(IDTracker.PROP_ID, prop_id);
                        editor.commit();
                        checkFormStatus();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAgreementDashboardActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences sharedPreferences = CreateAgreementDashboardActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_AGM_ID, agm_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CreateAgreementDashboardActivity.this);
        requestQueue.add(request);
    }

    private void checkFormStatus() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.CHECK_FROM_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject data = jsonArray.getJSONObject(i);

                                    editor.putString(IDTracker.OWN_ID, manageData(data.getString("own_stat")));
                                    editor.putString(IDTracker.TENT_ID, manageData(data.getString("tent_stat")));
                                    editor.putString(IDTracker.WIT_ID, manageData(data.getString("wit_stat")));
                                    editor.putString(IDTracker.RENT_ID, manageData(data.getString("prop_pay_stat")));
                                    editor.putString(IDTracker.RENT_TRANS_ID, manageData(data.getString("rent_trans_stat")));

                                    editor.commit();
                                }

                                //Toast.makeText(CreateAgreementDashboardActivity.this, "checkFormStatus", Toast.LENGTH_SHORT).show();

                                ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);
                                tenantID = sharedPreferences.getString(IDTracker.TENT_ID,null);
                                witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);
                                rentID = sharedPreferences.getString(IDTracker.RENT_ID,null);
                                rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null);

                                setVisibilityOfFrames();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CreateAgreementDashboardActivity.this ,"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(CreateAgreementDashboardActivity.this, "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAgreementDashboardActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                SharedPreferences sharedPreferences = CreateAgreementDashboardActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_PROPERTY_ID, prop_id);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CreateAgreementDashboardActivity.this);
        requestQueue.add(request);
    }

    private String manageData(String data) {

        //Toast.makeText(CreateAgreementDashboardActivity.this, "Condition : " + (data.equalsIgnoreCase(FinalValues.NULL_VALUE)), Toast.LENGTH_SHORT ).show();

        if(data.equalsIgnoreCase(FinalValues.NULL_VALUE) ){
            return null;
        }else{
            return data;
        }
    }


    private void setIDTrackerAsNull() {

        editor.putString(IDTracker.SERVICE_TYPE, null);
        editor.putString(IDTracker.PROP_ID, null);
        editor.putString(IDTracker.OWN_ID, null);
        editor.putString(IDTracker.TENT_ID, null);
        editor.putString(IDTracker.WIT_ID, null);
        editor.putString(IDTracker.RENT_ID, null);
        editor.putString(IDTracker.RENT_TRANS_ID, null);
        editor.putString(IDTracker.TOKEN_ID, null);
        editor.putString(IDTracker.AGREEMENT_ID, null);

        editor.commit();

        setVisibilityOfFrames();
    }

    private void cardActionListener() {

        cardViewProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAgreementDashboardActivity.this, PropertyActivity.class));
            }
        });

        cardViewOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAgreementDashboardActivity.this, OwnerActivity.class));
            }
        });

        cardViewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAgreementDashboardActivity.this, TenantActivity.class));
            }
        });

        cardViewWitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAgreementDashboardActivity.this, Witness1Activity.class));
            }
        });

        cardViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAgreementDashboardActivity.this, RentPaymentActivity.class));
            }
        });

        cardViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileUrl = APIURLLists.GET_REPORT + "" + agm_id + "" + APIURLLists.PDF_EXT;
                String fileName = agm_id + "" + APIURLLists.PDF_EXT;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));
                startActivity(browserIntent);
            }
        });

    }

    private void setVisibilityOfFrames() {

        if(propertyID != null){
            validate = true;
            frameLayoutProperty.setBackgroundColor(getResources().getColor(R.color.green));
            textViewPropertyStatus.setText(getResources().getString(R.string.completed));
        }
        else{
            validate = false;
            frameLayoutProperty.setBackgroundColor(getResources().getColor(R.color.red));
            textViewPropertyStatus.setText(getResources().getString(R.string.incomplete));
        }

        if(ownerID != null){
            validate = true;
            frameLayoutOwner.setBackgroundColor(getResources().getColor(R.color.green));
            textViewOwnerStatus.setText(getResources().getString(R.string.completed));
        }
        else{
            validate = false;
            frameLayoutOwner.setBackgroundColor(getResources().getColor(R.color.red));
            textViewOwnerStatus.setText(getResources().getString(R.string.incomplete));
        }

        if(tenantID != null){
            validate = true;
            frameLayoutTenant.setBackgroundColor(getResources().getColor(R.color.green));
            textViewTenantStatus.setText(getResources().getString(R.string.completed));
        }
        else{
            validate = false;
            frameLayoutTenant.setBackgroundColor(getResources().getColor(R.color.red));
            textViewTenantStatus.setText(getResources().getString(R.string.incomplete));
        }

        if(witnessID != null){
            validate = true;
            frameLayoutWitness.setBackgroundColor(getResources().getColor(R.color.green));
            textViewWitnessStatus.setText(getResources().getString(R.string.completed));
        }
        else{
            validate = false;
            frameLayoutWitness.setBackgroundColor(getResources().getColor(R.color.red));
            textViewWitnessStatus.setText(getResources().getString(R.string.incomplete));
        }

        // && tokenID != null
        if(rentID != null && rentTransID != null){
            validate = true;
            frameLayoutPayment.setBackgroundColor(getResources().getColor(R.color.green));
            textViewPaymentStatus.setText(getResources().getString(R.string.completed));
        }
        else{
            validate = false;
            frameLayoutPayment.setBackgroundColor(getResources().getColor(R.color.red));
            textViewPaymentStatus.setText(getResources().getString(R.string.incomplete));
        }

        if (validate){
            setReportEnable();
        }
        else{
            cardViewReport.setVisibility(View.INVISIBLE);
            textViewReportStatus.setText(getResources().getString(R.string.report_download_disable));
        }


    }

    private void setReportEnable() {
        cardViewReport.setVisibility(View.VISIBLE);
        textViewReportStatus.setText(getResources().getString(R.string.report_download));
    }

    private void downloadReport() {

        /*downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse("http://www.gti.bh/Library/assets/web-3t-dhfj1829.pdf");
        DownloadManager.Request request= new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference =downloadManager.enqueue(request);*/

        generateReportRequest();

    }

    private void generateReportRequest() {
        SharedPreferences sharedPreferences = CreateAgreementDashboardActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GENERATE_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase(FinalValues.REPORT_GENERATED)) {
                            Toast.makeText(CreateAgreementDashboardActivity.this, "Report is generated. Now downloading report", Toast.LENGTH_SHORT).show();
                            downloadReportNow();

                            /*fileUrl = APIURLLists.GET_REPORT + "" + agmId + "" + APIURLLists.PDF_EXT;
                            fileName = agmId + "" + APIURLLists.PDF_EXT;

                            new DownloadPDFFile().execute(fileUrl, fileName);*/
                        }else {
                            Toast.makeText(CreateAgreementDashboardActivity.this, "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAgreementDashboardActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(Keys.USER_ID, userID);
                params.put(Keys.AGM_ID, agm_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CreateAgreementDashboardActivity.this);
        requestQueue.add(request);
    }

    private void downloadReportNow() {
        downloadManager = (DownloadManager) CreateAgreementDashboardActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

        String url = APIURLLists.GET_REPORT + "" + agm_id + "" + APIURLLists.PDF_EXT;

        Uri uri = Uri.parse(url);
        DownloadManager.Request request= new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }


    private void getIDs() {
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

        //Property
        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);

        //Owner
        ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);

        //Tenant
        tenantID = sharedPreferences.getString(IDTracker.TENT_ID,null);

        //Witness
        witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);

        //Payment
        rentID = sharedPreferences.getString(IDTracker.RENT_ID,null);
        rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null);
        tokenID = sharedPreferences.getString(IDTracker.TOKEN_ID,null);
    }

    private void initializeComponent() {
        cardViewProperty = findViewById(R.id.property);
        cardViewOwner = findViewById(R.id.owner);
        cardViewTenant = findViewById(R.id.tenant);
        cardViewWitness = findViewById(R.id.witness);
        cardViewPayment = findViewById(R.id.payment);
        cardViewReport = findViewById(R.id.report);

        frameLayoutProperty = findViewById(R.id.property_frame);
        frameLayoutOwner = findViewById(R.id.owner_frame);
        frameLayoutTenant = findViewById(R.id.tenant_frame);
        frameLayoutWitness = findViewById(R.id.witness_frame);
        frameLayoutPayment = findViewById(R.id.payment_frame);


        textViewPropertyStatus = findViewById(R.id.property_status);
        textViewOwnerStatus = findViewById(R.id.owner_status);
        textViewTenantStatus = findViewById(R.id.tenant_status);
        textViewWitnessStatus = findViewById(R.id.witness_status);
        textViewPaymentStatus = findViewById(R.id.payment_status);
        textViewReportStatus = findViewById(R.id.report_status);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(CreateAgreementDashboardActivity.this, UserMainActivity.class));
    }
}
