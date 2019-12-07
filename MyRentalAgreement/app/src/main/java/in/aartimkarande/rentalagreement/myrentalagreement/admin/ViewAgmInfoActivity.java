package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import in.aartimkarande.rentalagreement.myrentalagreement.R;

public class ViewAgmInfoActivity extends AppCompatActivity {

    private String agmID;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_agm_info);

        Intent intent = getIntent();
        agmID = intent.getStringExtra("agm_id");
        userID = intent.getStringExtra("user_id");



        /*Toast.makeText(ViewAgmInfoActivity.this, "agm id : " + agmID, Toast.LENGTH_SHORT).show();
        Toast.makeText(ViewAgmInfoActivity.this, "user id : " + userID, Toast.LENGTH_SHORT).show();*/

    }
}
