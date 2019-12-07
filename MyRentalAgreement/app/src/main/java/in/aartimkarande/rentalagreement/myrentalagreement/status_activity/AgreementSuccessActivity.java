package in.aartimkarande.rentalagreement.myrentalagreement.status_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.DashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.UserMainActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.OwnerActivity;

public class AgreementSuccessActivity extends AppCompatActivity {

    private LinearLayout linear_layout;

    private TextView textViewTokenID;
    private Button btnDashboard;

    private String tokenID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_success);

        linear_layout = findViewById(R.id.linear_layout);

        textViewTokenID = findViewById(R.id.token_id);

        SharedPreferences sharedPreferences = AgreementSuccessActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tokenID = sharedPreferences.getString(IDTracker.TOKEN_ID,null);

        if(tokenID != null){
            textViewTokenID.setText(tokenID);
        }else{
            textViewTokenID.setText(FinalValues.EMPTY_STR);

            Snackbar snackbar = Snackbar.make(linear_layout, "Something went wrong, Plz contact admin. ", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }


        btnDashboard = findViewById(R.id.btnDashboard);
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*//Creating a shared preference
                SharedPreferences sharedPreferences = AgreementSuccessActivity.this.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                //Creating editor to store
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to the editor
                editor.putString(IDTracker.SERVICE_TYPE, null);
                editor.putString(IDTracker.PROP_ID, null);
                editor.putString(IDTracker.OWN_ID, null);
                editor.putString(IDTracker.TENT_ID, null);
                editor.putString(IDTracker.WIT_ID, null);
                editor.putString(IDTracker.RENT_ID, null);
                editor.putString(IDTracker.RENT_TRANS_ID, null);
                editor.putString(IDTracker.AGREEMENT_ID, null);
                editor.putString(IDTracker.TOKEN_ID, null);

                //Saving values to the editor
                editor.commit();*/

               startActivity( new Intent(AgreementSuccessActivity.this, UserMainActivity.class));
            }
        });

    }
}
