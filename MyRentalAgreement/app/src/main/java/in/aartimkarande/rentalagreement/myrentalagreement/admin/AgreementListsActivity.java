package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.AgmsListAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.UsersListAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.model.AgreementLists;
import in.aartimkarande.rentalagreement.myrentalagreement.model.UserInfo;

public class AgreementListsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<AgreementLists> agmList;
    private AgmsListAdapter agmsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_lists);

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        agmList = new ArrayList<>();

        getList();
    }

    private void getList() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_AGMS_LISTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    agmList.add(new AgreementLists(
                                            data.getString("agm_id"),
                                            data.getString("user_id"),
                                            data.getString("user_name"),
                                            data.getString("user_username")));
                                }

                                //Toast.makeText(UsersListsActivity.this, "Size : " + userInfoList.size(), Toast.LENGTH_SHORT).show();

                                if(agmList.size() > 0){
                                    agmsListAdapter = new AgmsListAdapter(getApplicationContext(), agmList);
                                    recyclerView.setAdapter(agmsListAdapter);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(AgreementListsActivity.this,"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(AgreementListsActivity.this, "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgreementListsActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        startActivity(new Intent(AgreementListsActivity.this, AdminMainActivity.class));
    }
}
