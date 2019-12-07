package in.aartimkarande.rentalagreement.myrentalagreement.status_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.transaction.AgreementPaymentActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.transaction.RentPaymentActivity;

public class FiveActivity extends AppCompatActivity {

    //Components
    private TextView textViewPropertyID;
    private TextView textViewOwnerID;
    private TextView textViewTenantID;
    private TextView textViewWitnessID;
    private TextView textViewRentTransID;
    private TextView textViewAgreementTarnsID;
    private Button btnNext;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String rentTransID;

    private boolean validate = true;

    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        initializeComponents();
        checkStatus();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate) {
                    startActivity(new Intent(FiveActivity.this, AgreementPaymentActivity.class));
                }

            }
        });
    }

    private void initializeComponents() {
        textViewPropertyID = findViewById(R.id.property_status);
        textViewOwnerID = findViewById(R.id.owner_status);
        textViewTenantID = findViewById(R.id.tenant_status);
        textViewWitnessID = findViewById(R.id.witness_status);
        textViewRentTransID = findViewById(R.id.rent_status);
        /*textViewAgreementTarnsID = findViewById(R.id.rent_trans_status);*/

        btnNext = findViewById(R.id.btnNext);
    }

    private void checkStatus() {

        getIDs();

        if(TextUtils.isEmpty(propertyID)){
            textViewPropertyID.setText(R.string.incomplete);
            textViewPropertyID.setTextColor(getResources().getColor(R.color.red));
            validate = false;
        }

        if(TextUtils.isEmpty(ownerID)) {
            textViewOwnerID.setText(R.string.incomplete);
            textViewOwnerID.setTextColor(getResources().getColor(R.color.red));
            validate = false;
        }

        if(TextUtils.isEmpty(tenantID)){
            textViewTenantID.setText(R.string.incomplete);
            textViewTenantID.setTextColor(getResources().getColor(R.color.red));
            validate = false;
        }

        if(TextUtils.isEmpty(witnessID)) {
            textViewWitnessID.setText(R.string.incomplete);
            textViewWitnessID.setTextColor(getResources().getColor(R.color.red));
            validate = false;
        }

        if(TextUtils.isEmpty(rentID)){
            textViewRentTransID.setText(R.string.incomplete);
            textViewRentTransID.setTextColor(getResources().getColor(R.color.red));
            validate = false;
        }

/*        if(TextUtils.isEmpty(rentTransID)){
            textViewAgreementTarnsID.setText(R.string.incomplete);
            textViewAgreementTarnsID.setTextColor(getResources().getColor(R.color.red));
            validate = false;
        }*/


    }

    private void getIDs() {
        SharedPreferences sharedPreferences = FiveActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);
        tenantID = sharedPreferences.getString(IDTracker.TENT_ID,null);
        witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);
        rentID = sharedPreferences.getString(IDTracker.RENT_ID,null);
        /*rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null)*/;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(userID != null && serviceType != null && propertyID != null && ownerID != null && tenantID != null && witnessID != null && rentID != null /*&& rentTransID != null*/) {

            msg = "User ID : " + userID + "\nService Type : " + serviceType + "\nProperty ID  : " + "\nOwner ID : " + ownerID + "\nTenant ID :" + tenantID + "\nWitness ID : " + witnessID + "\nRent ID : " + rentID /*+ "\nRent Tarns ID : " + rentTransID*/;

            Toast.makeText(getApplicationContext(),"onResume() called.\n" +msg,Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(SixActivity.this, SixActivity.class));
        }

    }
}
