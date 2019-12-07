package in.aartimkarande.rentalagreement.myrentalagreement.user_witness2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.user_witness1.ViewWitness1Activity;

public class ViewWitness2Activity extends AppCompatActivity {

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

    private Button btnBack;

    private float x1;
    private float x2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_witness2);

        initializeComponents();

        setDataToComponents();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: x1 = event.getX(); break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();

                        float deltaX = x2 - x1;

                        if(Math.abs(deltaX) > FinalValues.MIN_DISTANCE){

                            if(x2 > x1){
                                /*Toast.makeText(getApplicationContext(),"Left to Right swipe [Next]",Toast.LENGTH_SHORT).show();*/
                                startActivity(new Intent(ViewWitness2Activity.this, ViewWitness1Activity.class));
                            }else{
                                /*Toast.makeText(getApplicationContext(),"Right to Left swipe [Previous]",Toast.LENGTH_SHORT).show();*/
                            }

                        }
                        break;
                }

                return false;

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewWitness2Activity.this, CreateAgreementDashboardActivity.class).putExtra("status", "update"));
            }
        });

    }

    private void setDataToComponents() {

        Intent intent = getIntent();

        textViewName.setText(intent.getExtras().getString("name"));
        textViewDOB.setText(intent.getExtras().getString("dob"));
        textViewGender.setText(intent.getExtras().getString("gender"));
        textViewMobileNo.setText(intent.getExtras().getString("phone"));
        textViewProfession.setText(intent.getExtras().getString("prof"));
        textViewPanNo.setText(intent.getExtras().getString("pan"));
        textViewAadharNo.setText(intent.getExtras().getString("aadhar"));
        textViewAddr1.setText(intent.getExtras().getString("add1"));
        textViewAddr2.setText(intent.getExtras().getString("add2"));


    }

    private void initializeComponents() {

        scrollView = findViewById(R.id.scroll_view);

        textViewName = findViewById(R.id.name);
        textViewDOB = findViewById(R.id.dob);
        textViewGender = findViewById(R.id.gender);
        textViewMobileNo = findViewById(R.id.mobile_no);
        textViewProfession = findViewById(R.id.profession);
        textViewPanNo = findViewById(R.id.pan_no);
        textViewAadharNo = findViewById(R.id.aadhar_no);
        textViewAddr1 = findViewById(R.id.add1);
        textViewAddr2 = findViewById(R.id.add2);

        btnBack = findViewById(R.id.btnBack);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(ViewWitness2Activity.this, ViewWitness1Activity.class).putExtra("status", "update"));
    }
}
