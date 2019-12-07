package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;

public class AdminInfoActivity extends AppCompatActivity {

    private LinearLayout linearLayoutAdminName;
    private LinearLayout linearLayoutAdminPwd;
    private TextView textViewPwd;

    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);

        linearLayoutAdminName = findViewById(R.id.admin_name);
        linearLayoutAdminPwd = findViewById(R.id.admin_pwd);

        textViewPwd = findViewById(R.id.text_view_pwd);
        textViewPwd.setText(FinalValues.PWD_STAR);


        btnOk = findViewById(R.id.btnOk);


        linearLayoutAdminName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AdminInfoActivity.this, UpdateAdminNameActivity.class));
            }
        });

        linearLayoutAdminPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminInfoActivity.this, UpdateAdminPwdActivity.class));
            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminInfoActivity.this, AdminMainActivity.class));
            }
        });

    }

}
